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
public class EditProjectFragment extends Fragment implements ServiceListener{

    //Parámetros de recepción/envóp
    private String idProject;
    private String idUsuario;
    private int proyectNumUsers;
    private String proyectName;

    //Elementos de las vistas
    private View view;
    private MultiAutoCompleteTextView textAutocomplete;
    private RecyclerView recyclerView;
    private RecyclerViewEditProjectAdapter adapter;
    private EditText projectDescription;

    //Variables de almacenamiento de datos
    private ArrayList<String> emails = new ArrayList<String>();
    private ArrayList<Usuario> listUsersProject = new ArrayList<>();
    private Proyecto project;

    //Envío de datos al servidor
    private ProjectService projectService;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        projectService = new ProjectService(getActivity(), this);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_editproject, container, false);

        projectDescription = (EditText) view.findViewById(R.id.input_editprojectDescription);


        Button button = (Button) view.findViewById(R.id.buttonEditProject);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        loadContentProject();
        loadAutoCompleteContent();
        loadCheckBoxContent();

        return view;
    }

    public void setProject(Proyecto project) {
        this.project = project;
    }

    public void setListUsersProject(ArrayList<Usuario> listUsersProject) {
        this.listUsersProject = listUsersProject;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }


    public void edit() {

        System.out.println("Proyectooooo: " + project.getNombreP());
        //project = new Proyecto();
        project.setDescripcion(projectDescription.getText().toString());

        try {

            //Construcción de la petición
            project.setDirector(EasyProjectApp.getInstance().getUser());
            Gson trad = new Gson();
            JSONObject jsonObject = new JSONObject(trad.toJson(project));
            String editString = adapter.getRemoveUserList().toString();
            String emailsRemove = editString.substring(1, editString.lastIndexOf(']'));
            jsonObject.put("listAddEmails", textAutocomplete.getText().toString());
            jsonObject.put("listRemoveEmails", emailsRemove);

            System.out.println("Enviando ... " + jsonObject);

            projectService.putProject(String.valueOf(project.getIdProyect()),jsonObject);

            //vuelta a la vista de tareas
            Intent toViewProject = new Intent (getActivity(), ViewProjectActivity.class);
            toViewProject.putExtra("idProject", project.getIdProyect());
            toViewProject.putExtra("proyectName", project.getNombreP());
            startActivity(toViewProject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadContentProject() {

        //projectDescripcion
        android.support.design.widget.TextInputLayout projectDescription = (android.support.design.widget.TextInputLayout) view.findViewById(R.id.input_layout_editprojectDescription);
        projectDescription.setHintAnimationEnabled(true);
        projectDescription.setHint(project.getDescripcion());


    }

    public void loadAutoCompleteContent() {
        textAutocomplete = (MultiAutoCompleteTextView) view.findViewById(R.id.editMultiAutoComplete);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, emails);
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

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

    }
}
