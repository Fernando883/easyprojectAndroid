package inftel.easyprojectandroid.service;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.http.HttpRequest;
import inftel.easyprojectandroid.http.HttpTask;
import inftel.easyprojectandroid.interfaces.ResponseListener;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Usuario;

/**
 * Created by anotauntanto on 8/4/16.
 */
public class UserService implements ResponseListener {

    private ServiceListener listener;
    private String SERVER_IP;
    private String SERVER_PATH;


    public UserService(Context context, ServiceListener listener) {
        this.listener = listener;
        SERVER_IP = context.getResources().getString(R.string.server_ip);
        SERVER_PATH = context.getResources().getString(R.string.server_path);
    }

    public void postUser(Usuario user) {
        String url = SERVER_IP+SERVER_PATH+"entity.usuario/signInUser";
        Log.e("URL", url);
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("email", user.getEmail());
            userJson.put("nombreU", user.getNombreU());
            Log.e("userJSON", userJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpRequest httpRequest = new HttpRequest(HttpRequest.POST,url, userJson);
        new HttpTask(this,"postUser").execute(httpRequest);
    }

    @Override
    public void onResponse(Pair<String, String> response) {
        Log.e("RESPONSE", response.second);
        if (response.first.equals("postUser"))
            parseUser(response.second);
    }


    private void parseUser(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            EasyProjectApp.getInstance().getUser().setIdUsuario(jsonObject.getLong("idUsuario"));
            listener.onObjectResponse(new Pair("postUser", jsonObject.getLong("idUsuario")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
