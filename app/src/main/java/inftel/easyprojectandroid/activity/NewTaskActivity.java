package inftel.easyprojectandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.service.ProjectService;
import inftel.easyprojectandroid.service.TaskService;

public class NewTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ServiceListener {

    private MultiAutoCompleteTextView text1;
    private ProjectService projectService;
    private TaskService taskService;
    private String idProject;
    ArrayList<String> emails = new ArrayList<String>();
    Tarea newTask = new Tarea();
    private Bundle projectBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        projectService = new ProjectService(this, this);
        taskService = new TaskService(this, this);
        projectBundle = getIntent().getBundleExtra("projectData");
        idProject = projectBundle.getString("idProject");

        projectService.getUsersEmailProject(idProject);

        text1=(MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView2);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, emails);

        text1.setAdapter(adapter);
        text1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        Spinner spinner = (Spinner) findViewById(R.id.status_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.status_arrays, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView myText = (TextView) view;
        newTask.setEstado(myText.getText().toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {
        if (response.first.equals("getUsersEmailProject")){
            for(Object email: response.second){
                emails.add((String) email);
            }
        }

    }

    public void saveTask(View view){

        newTask.setNombre(((EditText) findViewById(R.id.input_nameTask)).getText().toString());
        newTask.setDescripcion(((EditText) findViewById(R.id.input_taskDescription)).getText().toString());
        BigInteger duration = new BigInteger(((EditText) findViewById(R.id.input_taskDuration)).getText().toString());
        newTask.setTiempo(new BigInteger(String.valueOf(duration.intValue() * 60)));

        Proyecto idProyecto = new Proyecto ();
        idProyecto.setIdProyect(Long.valueOf(idProject));
        newTask.setIdProyecto(idProyecto);
        newTask.setIdUsuario(EasyProjectApp.getInstance().getUser());

        try {

            Gson converter = new Gson();

            projectService = new ProjectService(this, this);
            JSONObject jsonObject = new JSONObject(converter.toJson(newTask));
            jsonObject.put("listEmails", text1.getText().toString());

            Intent toViewProjectTabActivity = new Intent (this, ViewProjectTabActivity.class);
            toViewProjectTabActivity.putExtra("projectData", projectBundle);
            startActivity(toViewProjectTabActivity);

            taskService.postTask(jsonObject);


        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
