package inftel.easyprojectandroid.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.dialog.ConfirmDialog;
import inftel.easyprojectandroid.fragment.EditProjectFragment;
import inftel.easyprojectandroid.fragment.EditTaskFragment;
import inftel.easyprojectandroid.fragment.LoadingFragment;
import inftel.easyprojectandroid.fragment.ViewProjectDetailsFragment;
import inftel.easyprojectandroid.fragment.ViewTaskDetailsFragment;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.ProjectService;
import inftel.easyprojectandroid.service.TaskService;

public class infoTaskActivity extends AppCompatActivity implements ServiceListener {

    private boolean isEdit = false;

    private Long idTask;
    private String taskDescription;
    private String taskStatus;
    private String taskName;
    private BigInteger taskTime;
    private String idProject;
    private String colectionUser;
    Usuario user = EasyProjectApp.getInstance().getUser();
    private ArrayList<Usuario> arraycolectionUser = new ArrayList<>();

    //Peticiones
    private ProjectService projectService;
    private TaskService taskService;

    //Variables de paso a los fragmentos
    private Tarea task;
    private ArrayList<String> emails = new ArrayList<String>();
    private ArrayList<Usuario> listUsersProject = new ArrayList<>();

    //Menu
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_task);

        // Recuperamos parámetros e inicializaciones
        idTask =  getIntent().getLongExtra("idTask", 0L);
        taskDescription = getIntent().getStringExtra("taskDescription");
        taskStatus = getIntent().getStringExtra("taskStatus");
        taskName = getIntent().getStringExtra("taskName");
        taskTime = (BigInteger) getIntent().getExtras().get("taskTime");
        idProject = getIntent().getStringExtra("idProject");
        colectionUser = getIntent().getStringExtra("colectionUser");
        System.out.println("NOS LLEGA " + idTask + taskDescription+ taskStatus +taskName +taskTime +idProject + colectionUser);

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Usuario>>(){}.getType();
        arraycolectionUser = gson.fromJson(colectionUser,listType);



        task = new Tarea();
        task.setIdTarea(idTask);
        task.setDescripcion(taskDescription);
        task.setEstado(taskStatus);
        task.setNombre(taskName);
        task.setTiempo(taskTime);
        task.setUsuarioCollection(arraycolectionUser);

        //Inicializaciones
        projectService = new ProjectService(this, this);
        taskService = new TaskService(this,this);


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(taskName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        showViewTaskDetailsFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_menu, menu);
        menu.findItem(R.id.action_visualize).setVisible(false);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_visualize:
                showViewTaskDetailsFragment();
                break;

            case R.id.action_edit:

                projectService.getUsersEmailProject(idProject.toString());
                showLoadFragment();
                break;
                //delete

            case R.id.action_delete:

                Bundle task = new Bundle();
                task.putString("itemDelete", idTask.toString());
                task.putInt("type", ConfirmDialog.task);
                task.putString("nameItem", taskName);


                FragmentManager fragmentManager = getFragmentManager();
                ConfirmDialog deleteProject = new ConfirmDialog();
                deleteProject.setArguments(task);
                deleteProject.show(fragmentManager, "fragmentManager");



                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {
        if (response.first.equals("getUsersEmailProject")){
            emails = (ArrayList<String>) response.second;
            taskService.getUsersEmailByTask(idTask.toString());

        } else if (response.first.equals("getUsersEmailByTask")) {

            listUsersProject = (ArrayList<Usuario>) response.second;
            showEditTaskFragment();
        }
    }

    private void showLoadFragment (){
        LoadingFragment loadingFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.infotaskcontent, loadingFragment).commit();
    }

    private void showEditTaskFragment() {
        EditTaskFragment editTaskFragment = new EditTaskFragment();
        task.setIdTarea(idTask);
        task.setDescripcion(taskDescription);
        task.setEstado(taskStatus);
        task.setNombre(taskName);
        task.setTiempo(taskTime);
        task.setUsuarioCollection(arraycolectionUser);
        System.out.println("CARLOS " + task.toString());
        editTaskFragment.setTask(task);
        editTaskFragment.setIdProject(idProject);
        editTaskFragment.setEmails(emails);
        editTaskFragment.setListUsersTask(listUsersProject);
        getSupportFragmentManager().beginTransaction().replace(R.id.infotaskcontent, editTaskFragment).commit();

    }

    private void showViewTaskDetailsFragment () {
        ViewTaskDetailsFragment viewTaskDetailsFragment = new ViewTaskDetailsFragment();
        viewTaskDetailsFragment.setTask(task);
        getSupportFragmentManager().beginTransaction().replace(R.id.infotaskcontent, viewTaskDetailsFragment).commit();

    }

}
