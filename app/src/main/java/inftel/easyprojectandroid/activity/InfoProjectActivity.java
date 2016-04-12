package inftel.easyprojectandroid.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.dialog.ConfirmDialog;
import inftel.easyprojectandroid.fragment.EditProjectFragment;
import inftel.easyprojectandroid.fragment.ViewProjectDetailsFragment;
import inftel.easyprojectandroid.model.EasyProjectApp;


public class InfoProjectActivity extends AppCompatActivity {

    private String idProject;
    private String idUsuario;
    private int proyectNumUsers;
    private String proyectName;
    private Bundle infoProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_project);

        // Recuperamos parámetros
        idProject = getIntent().getStringExtra("idProject");
        idUsuario = String.valueOf(EasyProjectApp.getInstance().getUser().getIdUsuario());
        proyectNumUsers = getIntent().getIntExtra("proyectNumUsers", 0);
        proyectName = getIntent().getStringExtra("proyectName");

        //Construimos los datos para los fragmentos
        infoProject = new Bundle();
        infoProject.putString("idProject", idProject);
        infoProject.putString("idUsuario", idUsuario);
        infoProject.putInt("proyectNumUsers", proyectNumUsers);
        infoProject.putString("proyectName", proyectName);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEdit);
        toolbar.setTitle(proyectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //LoadingFragment loadingFragment = new LoadingFragment();
        /*EditProjectFragment editProjectFragment = new EditProjectFragment();
        editProjectFragment.setArguments(infoProject);


        getSupportFragmentManager().beginTransaction().add(R.id.frame_infoProject, editProjectFragment).commit();*/

        ViewProjectDetailsFragment viewProjectDetailsFragment = new ViewProjectDetailsFragment();
        viewProjectDetailsFragment.setArguments(infoProject);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_infoProject, viewProjectDetailsFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_visualize:
                //delete
                ViewProjectDetailsFragment viewProjectDetailsFragment = new ViewProjectDetailsFragment();
                viewProjectDetailsFragment.setArguments(infoProject);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_infoProject, viewProjectDetailsFragment).commit();

                break;

            case R.id.action_edit:

                EditProjectFragment editProjectFragment = new EditProjectFragment();
                editProjectFragment.setArguments(infoProject);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_infoProject, editProjectFragment).commit();
                break;

            case R.id.action_delete:

                Bundle project = new Bundle();
                project.putString("itemDelete", "este proyecto");

                FragmentManager fragmentManager = getFragmentManager();
                ConfirmDialog deleteProject = new ConfirmDialog();
                deleteProject.setArguments(project);
                deleteProject.show(fragmentManager, "fragmentManager");

                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
