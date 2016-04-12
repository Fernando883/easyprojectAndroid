package inftel.easyprojectandroid.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.activity.ViewProjectActivity;
import inftel.easyprojectandroid.adapter.RecyclerViewEditProjectAdapter;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.ProjectService;

/**
 * Created by anotauntanto on 9/4/16.
 */
public class EditProjectFragment extends Fragment implements ServiceListener, android.widget.CompoundButton.OnCheckedChangeListener{

    private String idProject;
    private String idUsuario;
    private int proyectNumUsers;
    private String proyectName;

    private View view;
    private ProjectService projectService;
    private MultiAutoCompleteTextView textAutocomplete;
    private ArrayList<String> emails = new ArrayList<String>();
    private ArrayList<Usuario> listUsersProject = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewEditProjectAdapter adapter;
    private Proyecto project;

    EditText projectName;
    EditText projectDescription;

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
        projectService.getUsersEmailNonProject(idProject);
        projectService.getUsersProject(idProject);
        projectService.getProjectDetails(idProject);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_editproject, container, false);

        projectName = (EditText) view.findViewById(R.id.input_editnameProject);
        projectDescription = (EditText) view.findViewById(R.id.input_editprojectDescription);

        Button button = (Button) view.findViewById(R.id.buttonEditProject);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(v);
            }
        });

        return view;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        System.out.println("Entra aquí");


    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

        if (response.first.equals("getProjectDetails")) {
            project = (Proyecto) response.second;
            System.out.println("getProjectDetails");
            loadContentProject();
        }

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

        if (response.first.equals("getUsersEmailNonProject")) {
            for (Object email : response.second) {
                emails.add((String) email);

            }
            loadAutoCompleteContent();

        } else if (response.first.equals("getUsersProject")) {

            for (Object user : response.second) {
                listUsersProject.add((Usuario) user);
            }
            loadCheckBoxContent();

        }

    }

    public void edit(View view) {

        project = new Proyecto();


        project.setNombreP(projectName.getText().toString());
        project.setDescripcion(projectDescription.getText().toString());

        try {

            //Sería el usuario almacenado en appEasyProject
            project.setDirector(EasyProjectApp.getInstance().getUser());

            Gson trad = new Gson();

            JSONObject jsonObject = new JSONObject(trad.toJson(project));

            String editString = adapter.getRemoveUserList().toString();
            String emailsRemove = editString.substring(1, editString.lastIndexOf(']'));
            jsonObject.put("listAddEmails", textAutocomplete.getText().toString());
            jsonObject.put("listRemoveEmails", emailsRemove);

            System.out.println("Enviando ... " + jsonObject);

            projectService.putProject(idProject,jsonObject);
            Intent toViewProject = new Intent (getActivity(), ViewProjectActivity.class);
            startActivity(toViewProject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadContentProject() {

        //projectName
        android.support.design.widget.TextInputLayout projectName = (android.support.design.widget.TextInputLayout) view.findViewById(R.id.input_layout_editNameProject);
        projectName.setHintAnimationEnabled(true);
        projectName.setHint(proyectName);

        //projectDescripcion
        android.support.design.widget.TextInputLayout projectDescription = (android.support.design.widget.TextInputLayout) view.findViewById(R.id.input_layout_editprojectDescription);
        projectDescription.setHintAnimationEnabled(true);
        projectDescription.setHint(project.getDescripcion());


    }

    public void loadAutoCompleteContent() {
        textAutocomplete = (MultiAutoCompleteTextView) getActivity().findViewById(R.id.editMultiAutoComplete);
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, emails);
        textAutocomplete.setAdapter(adapter);
        textAutocomplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

    public void loadCheckBoxContent() {

        recyclerView = (RecyclerView) view.findViewById(R.id.projectEditRecyclerView);
        adapter = new RecyclerViewEditProjectAdapter(listUsersProject);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_visualize).setVisible(true);

    }
}
