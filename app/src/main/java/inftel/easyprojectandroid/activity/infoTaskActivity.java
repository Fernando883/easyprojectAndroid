package inftel.easyprojectandroid.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

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
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.ProjectService;
import inftel.easyprojectandroid.service.TaskService;

public class infoTaskActivity extends AppCompatActivity implements ServiceListener {

    private boolean isEdit = false;

    //Peticiones
    private ProjectService projectService;
    private TaskService taskService;

    //Variables de paso a los fragmentos
    private Tarea task;
    private ArrayList<String> emails = new ArrayList<String>();
    private ArrayList<Usuario> listUsersProject = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_task);

        // Recuperamos parámetros

        //Inicializaciones
        projectService = new ProjectService(this, this);
        taskService = new TaskService(this,this);


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEdit);
        //toolbar.setTitle(taskName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //Se lanza la primera petición para visualización
        //projectService.getTasktDetails(idProject);


        //Se lanza el fragmento de carga
        showLoadFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_visualize:
                //delete
                //ViewTaskDetailsFragment viewTaskDetailsFragment = new ViewTaskDetailsFragment();
                //projectService.getTaskDetails(idTask);
                showLoadFragment();
                break;

            case R.id.action_edit:
                projectService.getUsersEmailProject("1720");
                taskService.getUsersTask("1657");
                showLoadFragment();
                break;
                //delete

            case R.id.action_delete:

                Bundle task = new Bundle();
                task.putString("itemDelete", "1044");
                task.putInt("type", ConfirmDialog.task);
                task.putString("nameItem", "Prueba borrar");


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
            for(Object email: response.second){
                emails.add((String) email);
            }
        } else if (response.first.equals("getUsersEmailByTask")) {

            for(Object user: response.second){
                Usuario u = (Usuario) user;
                listUsersProject.add((Usuario) user);
            }
            showEditProjectFragment();
        }
    }

    private void showLoadFragment (){
        LoadingFragment loadingFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_infoProject, loadingFragment).commit();
    }

    private void showEditProjectFragment() {
        EditTaskFragment editTaskFragment = new EditTaskFragment();
        task.setNombre("");
        //editTaskFragment.setProject(task);
        //editTaskFragment.setEmails(emails);
        //editTaskFragment.setListUsersTask(listUsersProject);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_infoProject, editTaskFragment).commit();

    }

    private void showViewProjectDetailsFragment () {
        ViewTaskDetailsFragment viewTaskDetailsFragment = new ViewTaskDetailsFragment();
        //task.setNombre(taskName);
        //viewTaskDetailsFragment.setTask(task);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_infoProject, viewTaskDetailsFragment).commit();


    }
}
