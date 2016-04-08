package inftel.easyprojectandroid.service;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.http.HttpRequest;
import inftel.easyprojectandroid.http.HttpTask;
import inftel.easyprojectandroid.interfaces.ResponseListener;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.Proyecto;

/**
 * Created by csalas on 7/4/16.
 */
public class ProjectService implements ResponseListener {
    private ServiceListener listener;
    private String SERVER_IP;
    private String SERVER_PATH;


    public ProjectService(Context context, ServiceListener listener) {
        this.listener = listener;
        SERVER_IP = context.getResources().getString(R.string.server_ip);
        SERVER_PATH = context.getResources().getString(R.string.server_path);
    }

    public void getProjects(String idUser) {
        String url = SERVER_IP+SERVER_PATH+"entity.proyecto/findProjectByIdUser/"+idUser;
        Log.e("URL", url);
        HttpRequest httpRequest = new HttpRequest(HttpRequest.GET,url, null);
        new HttpTask(this,"getProjects").execute(httpRequest);
    }

    @Override
    public void onResponse(Pair<String, String> response) {
        Log.e("RESPONSE", response.second);
        if (response.first.equals("getProjects"))
            parseProjectList(response.second);
    }


    private void parseProjectList(String response) {
        ArrayList<Proyecto> projectList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++) {
                Proyecto p = Proyecto.fromJSON(jsonArray.getString(i));
                projectList.add(p);
            }

            listener.onListResponse(new Pair("getProjects", projectList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
