package inftel.easyprojectandroid.service;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import inftel.easyprojectandroid.http.HttpRequest;
import inftel.easyprojectandroid.http.HttpTask;
import inftel.easyprojectandroid.interfaces.ResponseListener;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.model.Comentario;

/**
 * Created by anotauntanto on 8/4/16.
 */
public class CommentService implements ResponseListener {

    private ServiceListener listener;
    private String SERVER_IP;
    private String SERVER_PATH;


    public CommentService(Context context, ServiceListener listener) {
        this.listener = listener;
        SERVER_IP = context.getResources().getString(R.string.server_ip);
        SERVER_PATH = context.getResources().getString(R.string.server_path);
    }

    public void getComments(String idTask) {

        String url = SERVER_IP+SERVER_PATH+"entity.comentario/findComentsTask/"+idTask;
        Log.e("URL", url);
        HttpRequest httpRequest = new HttpRequest(HttpRequest.GET,url, null);
        new HttpTask(this,"getComments").execute(httpRequest);
    }

    @Override
    public void onResponse(Pair<String, String> response) {
        Log.e("RESPONSE", response.second);
        if (response.first.equals("getComments"))
            parseCommentList(response.second);
    }


    private void parseCommentList(String response) {
        ArrayList<Comentario> commentList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++) {
                Comentario c = Comentario.fromJSON(jsonArray.getString(i));
                commentList.add(c);
            }

            listener.onListResponse(new Pair("getComments", commentList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
