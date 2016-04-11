package inftel.easyprojectandroid.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.dialog.ConfirmDialog;
import inftel.easyprojectandroid.fragment.EditTaskFragment;
import inftel.easyprojectandroid.fragment.ViewTaskDetailsFragment;

public class infoTaskActivity extends AppCompatActivity {

    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewTaskDetailsFragment viewTaskDetailsFragment = new ViewTaskDetailsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.infotaskcontent, viewTaskDetailsFragment).commit();

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
                ViewTaskDetailsFragment viewTaskDetailsFragment = new ViewTaskDetailsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.infotaskcontent, viewTaskDetailsFragment).commit();
                break;

            case R.id.action_edit:
                EditTaskFragment editTaskFragment = new EditTaskFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.infotaskcontent, editTaskFragment).commit();
                break;
                //delete

            case R.id.action_deleteTask:

                Bundle task = new Bundle();
                task.putString("itemDelete", "esta Tarea");

                FragmentManager fragmentManager = getFragmentManager();
                ConfirmDialog deleteProject = new ConfirmDialog();
                deleteProject.setArguments(task);
                deleteProject.show(fragmentManager, "fragmentManager");

                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
