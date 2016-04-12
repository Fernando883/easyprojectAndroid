package inftel.easyprojectandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.adapter.RecyclerViewEditProjectAdapter;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.ProjectService;
import inftel.easyprojectandroid.service.TaskService;

/**
 * Created by macbookpro on 10/4/16.
 */
public class EditTaskFragment extends Fragment implements ServiceListener {

    private View view;
    private ProjectService projectService;
    private TaskService taskService;
    private MultiAutoCompleteTextView textAutocomplete;
    private ArrayList<Usuario> listUsersProject = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewEditProjectAdapter adapter;
    private EditText taskDuration;
    private List<String> emailstoRemove = new ArrayList<>();
    private RadioGroup radioSexGroup;
    private String status;



    ArrayList<String> emails = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        projectService = new ProjectService(getActivity(), this);
        projectService.getUsersEmailProject("1535");

        taskService = new TaskService(getActivity(),this);
        taskService.getUsersTask("1499");

        //projectService.getUsersProject("1535");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_task,container,false);

        taskDuration = (EditText) view.findViewById(R.id.input_nameTask);
        radioSexGroup = (RadioGroup) view.findViewById(R.id.radioSex);

        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.to_do){
                    status = "to do";
                    System.out.println("Ha pulsado el botón " + status);
                }else if (checkedId == R.id.doing){
                    status = "doing";
                    System.out.println("Ha pulsado el botón 2");
                }else if (checkedId == R.id.done){
                    status = "done";
                    System.out.println("Ha pulsado el botón 3");
                }

            }

        });

        Button button = (Button) view.findViewById(R.id.buttonEdit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(v);
            }
        });

        loadCheckBoxContent();
        return view;
    }

    public void edit(View view){

        Tarea task = new Tarea();

        BigInteger tiempo = new BigInteger(taskDuration.getText().toString());


        task.setTiempo(tiempo);
        task.setDescripcion("Prueba definitiva");
        task.setEstado(status);

        Proyecto pro = new Proyecto();
        pro.setDescripcion("Prueba definitiva");
        pro.setIdProyect(1535L);
        pro.setNombreP("Proyecto Prueba Editar Tareas Definitivo");

        //Sería el usuario almacenado en appEasyProject
        Usuario director = new Usuario();
        director.setNombreU("Fernando Galán");
        director.setIdUsuario(2L);
        director.setEmail("fernandogalanperez883@gmail.com");
        pro.setDirector(director);

        task.setIdTarea(1489L);
        task.setIdProyecto(pro);

        task.setIdUsuario(director);

        Gson trad = new Gson();

        try {
            JSONObject jsonObject = new JSONObject(trad.toJson(task));
            jsonObject.put("listAddEmails", textAutocomplete.getText().toString());
            String emails = adapter.getRemoveUserList().toString().substring(1, adapter.getRemoveUserList().toString().lastIndexOf(']'));
            //emailstoRemove = adapter.getRemoveUserList();
            jsonObject.put("listRemoveEmails", emails);

            System.out.println("Enviando ... " + jsonObject);

            taskService.setEditTask("1380", jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        if (response.first.equals("getUsersEmailProject")){
            for(Object email: response.second){
                emails.add((String) email);
                loadAutoCompleteContent();
            }
        } else if (response.first.equals("getUsersEmailByTask")) {

            for(Object user: response.second){
                Usuario u = (Usuario) user;
                System.out.println(u.getNombreU());
                listUsersProject.add((Usuario) user);
            }
            loadCheckBoxContent();

        }

    }

    public void loadAutoCompleteContent () {
        textAutocomplete=(MultiAutoCompleteTextView)getActivity().findViewById(R.id.editMultiAutoComplete);
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, emails);
        textAutocomplete.setAdapter(adapter);
        textAutocomplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

    public void loadCheckBoxContent(){

        recyclerView = (RecyclerView) view.findViewById(R.id.taskEditRecyclerView);
        adapter = new RecyclerViewEditProjectAdapter(listUsersProject);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //adapter.notifyDataSetChanged();

    }
}
