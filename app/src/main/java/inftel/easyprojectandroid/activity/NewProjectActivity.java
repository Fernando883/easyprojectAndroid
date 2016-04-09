package inftel.easyprojectandroid.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.service.ProjectService;

public class NewProjectActivity extends AppCompatActivity implements ServiceListener {

    private ProjectService projectService;
    AutoCompleteTextView text;
    MultiAutoCompleteTextView text1;
    ArrayList<String> emails = new ArrayList<String>();

    EditText projectName;
    EditText projectDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        projectService = new ProjectService(this, this);
        projectService.getUsersEmail();

        text1=(MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView1);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line, emails);


        text1.setAdapter(adapter);
        text1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());



    }

    public void save(View view){
        projectName = (EditText) findViewById(R.id.input_nameProject);
        projectDescription = (EditText) findViewById(R.id.input_projectDescription);

        System.out.println(projectName.getText() + " " +projectDescription.getText() + " " + text1.getText());
         /*
        try {

            JSONObject newProject = new JSONObject();
            newProject.put("idUser",0);
            newProject.put("email",email);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("tittle", note.getTittle());
            jsonParam.put("content", note.getContent());

            if(path != null) {
                int i =0;
                //byte[] photo = convertImageToByte(path);
                //System.out.println("Aqui los datos son estos: " + photo.toString());
                //String p = Base64.encodeToString(photo, Base64.DEFAULT);
                //jsonParam.put("photo", p);
            }
            if(locationSaved){
                jsonParam.put("latitude", note.getLatitude());
                jsonParam.put("longitude", note.getLongitude());
            }else{
                jsonParam.put("latitude", null);
                jsonParam.put("longitude", null);
            }
            jsonParam.put("dateNote", dateFormat.format(dateNote));
            jsonParam.put("idUser", userParam);



            new PostHttp(this).execute("http://192.168.1.127:8080/PrettyNotesWS/webresources/entity.note?", jsonParam.toString());



        }catch (JSONException e) {
            e.printStackTrace();
        }

        */
    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {
        if (response.first.equals("getUserEmailList"))

            for(Object email: response.second){
                emails.add((String) email);
            }

    }

}
