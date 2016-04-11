package inftel.easyprojectandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.fragment.ChatFragment;
import inftel.easyprojectandroid.fragment.LoadingFragment;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.Message;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.ProjectService;

public class ChatActivity extends AppCompatActivity implements ServiceListener {
    private ChatFragment chatFragment;
    private WebSocketClient socketClient;
    private String projectID;
    private EditText textMessage;
    private Usuario currentUser;
    private ArrayList<Message> messageList;
    private ProjectService projectService;
    private boolean bdRequestFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textMessage = (EditText) findViewById(R.id.textMessage);
        textMessage.setOnEditorActionListener(manageMessage());
        //currentUser = EasyProjectApp.getInstance().getUser();
        currentUser = new Usuario();
        currentUser.setIdUsuario(1354l);
        currentUser.setNombreU("Carlos Salas");
        currentUser.setEmail("carlos.salas@gmail.com");
        currentUser.setImgUrl("");


        //projectID = getIntent().getStringExtra("projectID");
        projectID = "1157";

        // Fragmento de carga
        LoadingFragment loadingFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_chat, loadingFragment).commit();

        projectService = new ProjectService(this, this);
        projectService.getChatFromProject(projectID);
        manageWebSocket();

    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {
        if (response.first.equals("getChatFromProject"))
            showChatFragment((ArrayList<Message>) response.second);

    }

    private TextView.OnEditorActionListener manageMessage() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String msg = v.getText().toString();
                    if (msg.length() > 0) {
                        sendMessage(msg);
                        textMessage.setText("");
                    }

                    handled = true;
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                }
                return handled;
            }
        };
    }

    private void manageWebSocket() {
        URI uri;
        try {
            uri = new URI(getResources().getString(R.string.websocket)+projectID);
            Log.e("URI", uri.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        socketClient = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.e("Websocket", "Opened ");

            }

            @Override
            public void onMessage(String m) {
                Log.e("Websocket", "OnMessage");
                Gson gson = new Gson();
                Message message = gson.fromJson(m, Message.class);
                if (bdRequestFinished) {
                    messageList.add(message);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatFragment.update();
                        }
                    });

                }

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.e("Websocket", "Closed " + reason);
            }

            @Override
            public void onError(Exception ex) {
                Log.e("Websocket", "Error " + ex.getMessage());
            }
        };
        socketClient.connect();
    }

    private void sendMessage(String message) {
        Message msg = new Message();
        msg.message = message;
        msg.name = currentUser.getNombreU();
        msg.photoURL = currentUser.getImgUrl();
        msg.email = currentUser.getEmail();
        Gson gson = new Gson();
        socketClient.send(gson.toJson(msg));
    }

    private void showChatFragment(ArrayList<Message> messageList) {
        this.messageList = messageList;
        chatFragment = new ChatFragment();
        chatFragment.setMessageList(this.messageList);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_chat, chatFragment).commit();
        bdRequestFinished = true;
    }
}
