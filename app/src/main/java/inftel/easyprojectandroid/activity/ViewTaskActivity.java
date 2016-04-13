package inftel.easyprojectandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.fragment.CommentListFragment;
import inftel.easyprojectandroid.fragment.LoadingFragment;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.Comentario;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Tarea;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.CommentService;

public class ViewTaskActivity extends AppCompatActivity implements ServiceListener {

    CommentListFragment commentListFragment;

    private CommentService commentService;
    EditText editText;
    private TextView textView;
    Usuario usuario;

    private ArrayList<Comentario> commentList;
    LoadingFragment loadingFragment = new LoadingFragment();

    private Long idTask;
    private String taskDescription;
    private String taskStatus;
    private String taskName;
    private BigInteger taskTime;
    Usuario user = EasyProjectApp.getInstance().getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idTask =  getIntent().getLongExtra("idTask", 0L);
        taskDescription = getIntent().getStringExtra("taskDescription");
        taskStatus = getIntent().getStringExtra("taskStatus");
        taskName = getIntent().getStringExtra("taskName");
        setTitle(taskName);

        Bundle bundle = getIntent().getExtras();
        taskTime= (BigInteger) bundle.get("taskTime");
        commentService = new CommentService(this, this);
        commentService.getComments(idTask.toString());

        editText = (EditText) findViewById(R.id.insertComment);
        getSupportFragmentManager().beginTransaction().add(R.id.frameComment, loadingFragment).commit();
        addListenerOnButton();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.viewtask, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.info) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnButton() {


        ImageButton imageButton = (ImageButton) findViewById(R.id.enviar);


        if (imageButton != null) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Comentario comentario = new Comentario();

                    Tarea tarea = new Tarea();
                    tarea.setIdTarea(idTask);

                    //Construimos el comentario
                    comentario.setIdTarea(tarea);
                    comentario.setFecha(Calendar.getInstance().getTime());
                    comentario.setIdUsuario(EasyProjectApp.getInstance().getUser());
                    comentario.setTexto(String.valueOf(editText.getText()));

                    Gson converter = new Gson();
                    String jsonComment = converter.toJson(comentario, Comentario.class);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(jsonComment);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    commentService.setComments(json);
                    commentService.getComments(idTask.toString());

                    getSupportFragmentManager().beginTransaction().replace(R.id.frameComment, loadingFragment).commit();
                    editText.setText("");
                }
            });
        }

    }


    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

        System.out.println(response.second);
        if (response.first.equals("getComments")) {

            commentList = (ArrayList<Comentario>) response.second;
            showCommentListFragment(commentList);

        }

    }

    private void showCommentListFragment(ArrayList<Comentario> commentList) {

        commentListFragment=new CommentListFragment();
        commentListFragment.setCommentList(commentList);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameComment,commentListFragment).commit();

    }




}
