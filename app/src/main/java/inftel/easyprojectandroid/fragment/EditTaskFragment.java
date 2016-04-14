package inftel.easyprojectandroid.fragment;

import android.content.Intent;
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
import inftel.easyprojectandroid.activity.ViewProjectTabActivity;
import inftel.easyprojectandroid.adapter.RecyclerViewEditProjectAdapter;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.ProjectService;
import inftel.easyprojectandroid.service.TaskService;

/**
 * Created by macbookpro on 10/4/16.
 */
public class EditTaskFragment extends Fragment implements ServiceListener {

    //Elementos de las vistas
    private View view;
    private MultiAutoCompleteTextView textAutocomplete;
    private RecyclerView recyclerView;
    private RecyclerViewEditProjectAdapter adapter;
    private EditText taskDuration;
    private RadioGroup radioSexGroup;
    private String status;

    Usuario user = EasyProjectApp.getInstance().getUser();

    //Envío de datos al servidor
    private ProjectService projectService;
    private TaskService taskService;


    //Variables de almacenamiento de datos
    ArrayList<String> emails = new ArrayList<String>();
    private ArrayList<Usuario> listUsersTask = new ArrayList<>();
    private String idProject;
    private Tarea task;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //projectService = new ProjectService(getActivity(), this);
        taskService = new TaskService(getActivity(), this);


        //projectService.getUsersEmailProject("1720");

        //taskService = new TaskService(getActivity(),this);
        //taskService.getUsersTask("1657");

        //projectService.getUsersProject("1535");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_task,container,false);

        taskDuration = (EditText) view.findViewById(R.id.input_nameTask);
        taskDuration.setText(String.valueOf(task.getTiempo().divide(new BigInteger("60"))));
        status = task.getEstado();
        radioSexGroup = (RadioGroup) view.findViewById(R.id.radioSex);
        if (task.getEstado().equals("to do")) {
            radioSexGroup.check(R.id.to_do);
        } else if (task.getEstado().equals("doing")) {
            radioSexGroup.check(R.id.doing);
        } else if (task.getEstado().equals("done")) {
            radioSexGroup.check(R.id.done);
        }

        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.to_do) {
                    status = "to do";
                } else if (checkedId == R.id.doing) {
                    status = "doing";
                } else if (checkedId == R.id.done) {
                    status = "done";
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

        loadContentProject();
        loadAutoCompleteContent();
        loadCheckBoxContent();

        return view;
    }

    public void setTask(Tarea task) {
        this.task = task;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public void setListUsersTask(ArrayList<Usuario> listUsersTask) {
        this.listUsersTask = listUsersTask;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public void edit(View view){

        BigInteger tiempo = new BigInteger(taskDuration.getText().toString());
        tiempo = tiempo.multiply(new BigInteger("60"));

        task.setTiempo(tiempo);
        task.setDescripcion(task.getDescripcion());
        if(status != null){
            task.setEstado(status);
        }else{
            task.setEstado(task.getEstado());
        }


        Proyecto pro = new Proyecto();
        pro.setIdProyect(Long.parseLong(idProject));

        task.setIdTarea(task.getIdTarea());
        task.setIdProyecto(pro);

        task.setIdUsuario(user);

        Gson trad = new Gson();

        try {
            JSONObject jsonObject = new JSONObject(trad.toJson(task));
            jsonObject.put("listAddEmails", textAutocomplete.getText().toString());
            String emails = adapter.getRemoveUserList().toString().substring(1, adapter.getRemoveUserList().toString().lastIndexOf(']'));
            //emailstoRemove = adapter.getRemoveUserList();
            jsonObject.put("listRemoveEmails", emails);

            taskService.setEditTask(task.getIdTarea().toString(), jsonObject);

            Intent intent = new Intent(getActivity(), ViewProjectTabActivity.class);
            intent.putExtra("idProject",Long.parseLong(idProject));
            startActivity(intent);


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

    }

    public void loadContentProject() {

    }

    public void loadAutoCompleteContent () {
        textAutocomplete=(MultiAutoCompleteTextView)view.findViewById(R.id.editMultiAutoComplete2);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, emails);
        textAutocomplete.setAdapter(adapter);
        textAutocomplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

    public void loadCheckBoxContent(){

        recyclerView = (RecyclerView) view.findViewById(R.id.taskEditRecyclerView);
        adapter = new RecyclerViewEditProjectAdapter(listUsersTask);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}