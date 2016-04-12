package inftel.easyprojectandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.fragment.TaskListFragment;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.service.TaskService;

public class ViewProjectActivity extends AppCompatActivity implements ServiceListener, TabHost.OnTabChangeListener {

    private FragmentTabHost mTabHost;
    private TaskService taskService;
    private List<Tarea> listTask;
    private String idProject;
    private String idUsuario;
    private String proyectName;
    private int proyectNumUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("ViewProjectActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project);

        // Recuperamos parámetros
        idUsuario = String.valueOf(EasyProjectApp.getInstance().getUser().getIdUsuario());
        idProject = String.valueOf(getIntent().getLongExtra("idProject", 0L));
        proyectNumUsers = getIntent().getIntExtra("proyectNumUsers", 0);
        proyectName = getIntent().getStringExtra("proyectName");

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(proyectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get task by user and project
        taskService = new TaskService(this, this);
        //taskService.getTasks("10", "948");
        taskService.getTasks(idUsuario, idProject);

        //TabHost
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.to_do)).setIndicator(getString(R.string.to_do)),
                TaskListFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.doing)).setIndicator(getString(R.string.doing)),
                TaskListFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.done)).setIndicator(getString(R.string.done)),
                TaskListFragment.class, null);

        //custom the view
        mTabHost.setCurrentTab(1);
        mTabHost.setOnTabChangedListener(this);

        //FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call
                goToNewTaskActivity();

            }
        });
    }


    public void goToNewTaskActivity () {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);

    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

        if (response.first.equals("getTasks"))
            listTask = (ArrayList<Tarea>) response.second;
            List<Tarea> doingTasks = filterTask(getString(R.string.doing));
            updateTab(doingTasks);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewprojectmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_info:
                intent = new Intent(this, InfoProjectActivity.class);
                intent.putExtra("idProject", idProject);
                intent.putExtra("proyectName", proyectName);
                intent.putExtra("proyectNumUsers", proyectNumUsers);
                startActivity(intent);
                break;
            case R.id.action_chat:
                intent = new Intent(this, ChatActivity.class);
                intent.putExtra("idProject", idProject);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals(getString(R.string.to_do))) {

            List<Tarea> todoTasks = filterTask(getString(R.string.to_do));
            updateTab(todoTasks);

        } else if (tabId.equals(getString(R.string.doing))) {

            List<Tarea> doingTasks = filterTask(getString(R.string.doing));
            updateTab(doingTasks);

        } else if (tabId.equals(getString(R.string.done))){

            List<Tarea> doneTasks = filterTask(getString(R.string.done));
            updateTab(doneTasks);

        }
    }

    private void updateTab(List<Tarea> filteredTask) {

        TaskListFragment taskListFragment = new TaskListFragment();
        taskListFragment.setTaskList((ArrayList<Tarea>) filteredTask);
        getSupportFragmentManager().beginTransaction().replace(R.id.tabcontent, taskListFragment).commit();

    }

    public List<Tarea> filterTask (String type) {

        List<Tarea> filterTaks = new ArrayList<>();

        for (Tarea task: listTask) {
            if (task.getEstado().equals(type)) {
                filterTaks.add(task);
            }
        }
        return filterTaks;

    }
}



