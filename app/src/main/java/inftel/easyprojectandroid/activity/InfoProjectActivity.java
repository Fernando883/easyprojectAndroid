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


public class InfoProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_project);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEdit);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //LoadingFragment loadingFragment = new LoadingFragment();
        EditProjectFragment editProjectFragment = new EditProjectFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_infoProject, editProjectFragment).commit();
        /*ViewProjectDetailsFragment viewProjectDetailsFragment = new ViewProjectDetailsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_infoProject, viewProjectDetailsFragment).commit();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editprojectmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_visualizeProject:
                //delete
                ViewProjectDetailsFragment viewProjectDetailsFragment = new ViewProjectDetailsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_infoProject, viewProjectDetailsFragment).commit();
                break;

            case R.id.action_editProject:

                EditProjectFragment editProjectFragment = new EditProjectFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_infoProject, editProjectFragment).commit();
                break;

            case R.id.action_deleteProject:

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
