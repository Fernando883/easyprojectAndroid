package inftel.easyprojectandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.service.ProjectService;

public class EditProjectActivity extends AppCompatActivity implements ServiceListener {

    private ProjectService projectService;
    private MultiAutoCompleteTextView textAutocomplete;
    ArrayList<String> emails = new ArrayList<String>();
    String[] languages={"Android ","java","IOS","SQL","JDBC","Web services"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEdit);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        projectService = new ProjectService(this, this);
        projectService.getUsersEmailNonProject("602");

        textAutocomplete=(MultiAutoCompleteTextView)findViewById(R.id.editMultiAutoComplete);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, emails);

        textAutocomplete.setAdapter(adapter);
        textAutocomplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
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
            case R.id.action_delete:
                //delete
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {
        if (response.first.equals("getUsersEmailNonProject"))

            for(Object email: response.second){
                emails.add((String) email);
            }


    }
}
