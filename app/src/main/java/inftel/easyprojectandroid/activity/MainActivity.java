package inftel.easyprojectandroid.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.fragment.LoadingFragment;
import inftel.easyprojectandroid.fragment.ProjectListFragment;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.ProjectService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ServiceListener, SearchView.OnQueryTextListener {

    private ProjectService projectService;
    private boolean search;
    private ArrayList<Proyecto> projectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewProjectActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView textViewUserName = (TextView) headerView.findViewById(R.id.textViewUsername);
        TextView textViewUserEmail = (TextView) headerView.findViewById(R.id.textViewEmail);
        ImageView profileImage = (ImageView) headerView.findViewById(R.id.imageViewUser);

        Usuario currentUser = EasyProjectApp.getInstance().getUser();
        textViewUserName.setText(currentUser.getNombreU());
        textViewUserEmail.setText(currentUser.getEmail());

        try {
            if (currentUser.getImgUrl() != null)
                Picasso.with(this).load(currentUser.getImgUrl()).into(profileImage);
            else
                profileImage.setImageResource(R.drawable.woman);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        projectService = new ProjectService(this, this);
        projectService.getProjects(String.valueOf(currentUser.getIdUsuario()));
        // Por defecto, agregamos el fragmento de carga
        LoadingFragment loadingFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_main, loadingFragment).commit();

        //búsqueda
        /*search = false;
        handleIntent(getIntent());*/

    }

    @Override
    protected void onNewIntent(Intent intent){
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.new_project) {
            Intent intent = new Intent(this, NewProjectActivity.class);
            startActivity(intent);
        } else if (id == R.id.my_projects) {
            Toast.makeText(this, getString(R.string.my_projects), Toast.LENGTH_LONG).show();
        } else if (id == R.id.my_tasks) {
            Toast.makeText(this, getString(R.string.my_tasks), Toast.LENGTH_LONG).show();
        } else if (id == R.id.logout) {

            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("email", "");
            editor.putString("nombreU", "");
            editor.putString("imgUrl", "");
            editor.putLong("idUsuario", 0);
            editor.commit();

            signOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {
        if (response.first.equals("getProjects"))
            projectList = (ArrayList<Proyecto>) response.second;
            showProjectListFragment((ArrayList<Proyecto>) response.second);

    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(EasyProjectApp.getInstance().getGoogleApiClient()).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
    }

    private void showProjectListFragment(ArrayList<Proyecto> projectList) {
        ProjectListFragment projectListFragment = new ProjectListFragment();
        projectListFragment.setProjectList(projectList);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, projectListFragment).commit();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        System.out.println("onQueryTextSubmit Anitaaaaaaaaa");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        ArrayList<Proyecto> filteredList = filter (projectList, query);
        showProjectListFragment(filteredList);
        return false;
    }

    private ArrayList<Proyecto> filter(List<Proyecto> models, String query) {

        query = query.toLowerCase();

        final ArrayList<Proyecto> filteredModelList = new ArrayList<>();

        for (Proyecto model : models) {
            final String text = model.getNombreP().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
