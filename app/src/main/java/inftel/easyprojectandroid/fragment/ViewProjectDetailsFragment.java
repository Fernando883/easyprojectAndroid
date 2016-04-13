package inftel.easyprojectandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.model.Usuario;

/**
 * Created by anotauntanto on 9/4/16.
 */
public class ViewProjectDetailsFragment extends Fragment {


    private View view;
    private Proyecto project;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_viewprojectdetails, container, false);
        loadContent();

        return view;
    }


    public void setProject(Proyecto project) {
        this.project = project;
    }


    public void loadContent () {

        //projectName
        TextView projectName = (TextView) view.findViewById(R.id.projectNameInfo);
        projectName.setText(project.getNombreP());

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

