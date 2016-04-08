package inftel.easyprojectandroid.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.fragment.TaskListFragment;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.service.TaskService;

public class ViewProjectActivity extends AppCompatActivity implements ServiceListener {

    private FragmentTabHost mTabHost;
    public static final int TODO = 1;
    public static final int DOING = 2;
    public static final int DONE = 3;

    private TaskService taskService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TabHost
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);

        Bundle todo = new Bundle();
        todo.putInt("Tab", TODO);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.to_do)).setIndicator(getString(R.string.to_do)),
                TaskListFragment.class, todo);

        Bundle doing = new Bundle();
        doing.putInt("Tab", DOING);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.doing)).setIndicator(getString(R.string.doing)),
                TaskListFragment.class, doing);

        Bundle done = new Bundle();
        done.putInt("Tab", DONE);
        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.done)).setIndicator(getString(R.string.done)),
                TaskListFragment.class, done);

        //custom the view
        mTabHost.setCurrentTab(1);

        //get task by user and project
        taskService = new TaskService(this, this);
        taskService.getTasks("1354", "119");

        //FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call
            }
        });
    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

        if (response.first.equals("getTasks"))
            showTaskListFragment((ArrayList<Tarea>) response.second);

    }

    private void showTaskListFragment(ArrayList<Tarea> taskList) {
        TaskListFragment taskListFragment = new TaskListFragment();
        taskListFragment.setTaskList(taskList);
        getSupportFragmentManager().beginTransaction().replace(R.id.tabcontent, taskListFragment).commit();
    }


}



