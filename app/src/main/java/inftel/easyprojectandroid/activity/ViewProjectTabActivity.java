package inftel.easyprojectandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.adapter.TaskPageAdapter;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.service.TaskService;

public class ViewProjectTabActivity extends AppCompatActivity implements ServiceListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TaskPageAdapter pageAdapter;
    private String idUsuario, idDirector;
    private String idProject;
    private int projectNumUsers;
    private String projectName;
    private TaskService taskService;
    private ArrayList<Tarea> listTask = null;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project_tab);

        // Recuperamos parámetros
        idUsuario = String.valueOf(EasyProjectApp.getInstance().getUser().getIdUsuario());
        Bundle projectBundle = getIntent().getBundleExtra("projectData");
        if (projectBundle != null) {
            idDirector = projectBundle.getString("idDirector");
            idProject = projectBundle.getString("idProject");
            projectNumUsers = projectBundle.getInt("projectNumUsers");
            projectName = projectBundle.getString("projectName");
        }
        else {
            idDirector = String.valueOf(getIntent().getLongExtra("idDirector", 0L));
            idProject = String.valueOf(getIntent().getLongExtra("idProject", 0L));
            projectNumUsers = getIntent().getIntExtra("proyectNumUsers", 0);
            projectName = getIntent().getStringExtra("proyectName");
        }

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(projectName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.to_do)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.doing)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.done)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Inicializar service
        taskService = new TaskService(this, this);
        taskService.getTasks(idUsuario, idProject);

        frame = (FrameLayout) findViewById(R.id.frame_task);
        LayoutInflater.from(this).inflate(R.layout.fragment_loading, frame, true);

        //LoadingFragment loadingFragment = new LoadingFragment();
        //getSupportFragmentManager().beginTransaction().add(R.id.frame_task, loadingFragment).commit();

        //FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call
                goToNewTaskActivity();

            }
        });
        if (!idUsuario.equals(idDirector))
            fab.hide();

    }

    public void goToNewTaskActivity () {
        Intent intent = new Intent(this, NewTaskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("idProject", idProject);
        bundle.putString("projectName", projectName);
        bundle.putInt("projectNumUsers", projectNumUsers);
        bundle.putString("idDirector", idDirector);
        intent.putExtra("projectData", bundle);
        startActivity(intent);

    }

    /*@Override
    protected void onStart() {
        super.onStart();
        idProject = String.valueOf(getIntent().getLongExtra("idProject", 0L));
        idUsuario = String.valueOf(EasyProjectApp.getInstance().getUser().getIdUsuario());
        projectName = getIntent().getStringExtra("proyectName");

    }*/

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
                Bundle bundle = new Bundle();
                bundle.putString("idProject", idProject);
                bundle.putString("projectName", projectName);
                bundle.putInt("projectNumUsers", projectNumUsers);
                bundle.putString("idDirector", idDirector);
                intent.putExtra("projectData", bundle);
                startActivity(intent);
                break;
            case R.id.action_chat:
                intent = new Intent(this, ChatActivity.class);
                intent.putExtra("idProject", idProject);
                intent.putExtra("proyectName", projectName);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tabId) {
                viewPager.setCurrentItem(tabId.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {
        if (response.first.equals("getTasks"))
            listTask = (ArrayList<Tarea>) response.second;

        config();
    }



    private void config() {
        frame.removeAllViews();
        viewPager = (ViewPager) findViewById(R.id.container);
        pageAdapter = new TaskPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), idProject);
        pageAdapter.setTaskList(listTask);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(tabSelectedListener());
    }

}
