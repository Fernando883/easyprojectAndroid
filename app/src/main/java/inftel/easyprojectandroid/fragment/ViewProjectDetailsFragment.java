package inftel.easyprojectandroid.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.ProjectService;

/**
 * Created by anotauntanto on 9/4/16.
 */
public class ViewProjectDetailsFragment extends Fragment implements ServiceListener {

    private String idProject;
    private String idUsuario;
    private int proyectNumUsers;
    private String proyectName;

    private View view;
    private ProjectService projectService;
    private Proyecto project;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        // Recuperamos parámetros
        idProject = getArguments().getString("idProject");
        idUsuario = String.valueOf(EasyProjectApp.getInstance().getUser().getIdUsuario());
        proyectNumUsers = getArguments().getInt("proyectNumUsers");
        proyectName = getArguments().getString("proyectName");

        projectService = new ProjectService(getActivity(), this);
        projectService.getProjectDetails(idProject);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_viewprojectdetails, container, false);

        return view;
    }


    @Override
    public void onObjectResponse(Pair<String, ?> response) {

        if (response.first.equals("getProjectDetails")){
            System.out.println("Ei ana");
            project = (Proyecto) response.second;
            loadContent();
        }


    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

    }

    public void loadContent () {

        //projectName
        TextView projectName = (TextView) view.findViewById(R.id.projectNameInfo);
        projectName.setText(proyectName);

        //projectDescripcion
        TextView projectDescription = (TextView) view.findViewById(R.id.projectDescriptionInfo);
        projectDescription.setText(project.getDescripcion());

        //projectMembers
        TextView projectMembers = (TextView) view.findViewById(R.id.projectMemberInfo);
        for (Usuario u: project.getUsuarioCollection()) {
            projectMembers.append(u.getNombreU() + " ");

        }

        //projectNumTasks
        TextView projectNumTasks = (TextView) view.findViewById(R.id.projectNumTasksInfo);
        projectNumTasks.append(" " + String.valueOf(project.getNumTasks()));

        //projectDirector
        TextView projectDirector = (TextView) view.findViewById(R.id.projectDirectorInfo);
        projectDirector.setText(project.getDirector().getNombreU());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_edit).setVisible(true);
        menu.findItem(R.id.action_visualize).setVisible(false);
    }
}

