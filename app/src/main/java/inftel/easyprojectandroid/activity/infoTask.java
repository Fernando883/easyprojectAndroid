package inftel.easyprojectandroid.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.fragment.EditTaskFragment;
import inftel.easyprojectandroid.fragment.ViewTaskDetailsFragment;

public class infoTask extends AppCompatActivity {

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
        getMenuInflater().inflate(R.menu.info_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_visualizeTask:
                //delete
                ViewTaskDetailsFragment viewTaskDetailsFragment = new ViewTaskDetailsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.infotaskcontent, viewTaskDetailsFragment).commit();
                break;

            case R.id.action_editTask:
                EditTaskFragment editTaskFragment = new EditTaskFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.infotaskcontent, editTaskFragment).commit();
                break;
                //delete
        }
        return super.onOptionsItemSelected(item);
    }



}
