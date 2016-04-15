package inftel.easyprojectandroid.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.dialog.ConfirmDialog;
import inftel.easyprojectandroid.fragment.EditProjectFragment;
import inftel.easyprojectandroid.fragment.LoadingFragment;
import inftel.easyprojectandroid.fragment.ViewProjectDetailsFragment;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.ProjectService;


public class InfoProjectActivity extends AppCompatActivity implements ServiceListener {

    private String idProject;
    private String idUsuario, idDirector;
    private int proyectNumUsers;
    private String proyectName;

    //Peticiones
    private ProjectService projectService;

    //Variables de paso a los fragmentos
    private Proyecto project;
    private ArrayList<String> emails = new ArrayList<String>();
    private ArrayList<Usuario> listUsersProject = new ArrayList<>();

    //Menu
    private Menu menu;
    private Bundle projectBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_project);

        // Recuperamos parámetros
        idUsuario = String.valueOf(EasyProjectApp.getInstance().getUser().getIdUsuario());
        projectBundle = getIntent().getBundleExtra("projectData");
        if (projectBundle != null) {
            idProject = projectBundle.getString("idProject");
            idDirector = projectBundle.getString("idDirector");
            proyectNumUsers = projectBundle.getInt("projectNumUsers", 0);
            proyectName = projectBundle.getString("projectName");
        }

        //peticiones e inicializaciones
        projectService = new ProjectService(this, this);
        project = new Proyecto();
        project.setNombreP(proyectName);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEdit);
        toolbar.setTitle(proyectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Se lanza la primera petición para visualización
        projectService.getProjectDetails(idProject);

        //Se lanza el fragmento de carga
        showLoadFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_menu, menu);
        menu.findItem(R.id.action_visualize).setVisible(false);
        if (!idDirector.equals(idUsuario)) {
            menu.findItem(R.id.action_delete).setVisible(false);
            menu.findItem(R.id.action_edit).setVisible(false);
        }
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_visualize:
                //delete
                projectService.getProjectDetails(idProject);
                showLoadFragment();

                break;

            case R.id.action_edit:

                projectService.getUsersEmailNonProject(idProject);
                showLoadFragment();

                break;

            case R.id.action_delete:

                Bundle project = new Bundle();
                project.putString("itemDelete", idProject);
                project.putInt("type", ConfirmDialog.project);
                project.putString("nameItem", proyectName);

                FragmentManager fragmentManager = getFragmentManager();
                ConfirmDialog deleteProject = new ConfirmDialog();
                deleteProject.setArguments(project);
                deleteProject.show(fragmentManager, "fragmentManager");

                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onObjectResponse(Pair<String, ?> response) {
        if (response.first.equals("getProjectDetails")){
            project = (Proyecto) response.second;
            showViewProjectDetailsFragment();
        }

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

        if (response.first.equals("getUsersEmailNonProject")){
            emails = (ArrayList<String>) response.second;
            projectService.getUsersProject(idProject);

        } else if (response.first.equals("getUsersProject")) {
            listUsersProject = (ArrayList<Usuario>) response.second;
            showEditProjectFragment();
        }

    }

    private void showLoadFragment (){
        LoadingFragment loadingFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_infoProject, loadingFragment).commit();
    }

    private void showEditProjectFragment() {
        EditProjectFragment editProjectFragment = new EditProjectFragment();
        editProjectFragment.setArguments(projectBundle);
        project.setNombreP(proyectName);
        editProjectFragment.setProject(project);
        editProjectFragment.setEmails(emails);
        editProjectFragment.setListUsersProject(listUsersProject);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_infoProject, editProjectFragment).commit();

    }

    private void showViewProjectDetailsFragment () {
        ViewProjectDetailsFragment viewProjectDetailsFragment = new ViewProjectDetailsFragment();
        project.setNombreP(proyectName);
        viewProjectDetailsFragment.setProject(project);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_infoProject, viewProjectDetailsFragment).commit();


    }


}
