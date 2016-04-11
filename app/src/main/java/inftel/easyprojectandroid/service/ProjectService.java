package inftel.easyprojectandroid.service;

import android.content.Context;
import android.util.Pair;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.http.HttpRequest;
import inftel.easyprojectandroid.http.HttpTask;
import inftel.easyprojectandroid.interfaces.ResponseListener;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.Proyecto;
import inftel.easyprojectandroid.model.Usuario;

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
        HttpRequest httpRequest = new HttpRequest(HttpRequest.GET,url, null);
        new HttpTask(this,"getProjects").execute(httpRequest);
    }

    public void getProject (String idProject) {
        String url = SERVER_IP+SERVER_PATH+"entity.proyecto/findInfoProject/"+idProject;
        HttpRequest httpRequest = new HttpRequest(HttpRequest.GET,url, null);
        new HttpTask(this,"getProject").execute(httpRequest);

    }

    public void getUsersEmail(){
        String url = SERVER_IP + SERVER_PATH + "entity.usuario/getUsersEmail";
        System.out.println(url);
        HttpRequest httpRequest = new HttpRequest(HttpRequest.GET,url, null);
        new HttpTask(this,"getUserEmailList").execute(httpRequest);
    }

    public void setNewProject(JSONObject jsonObject){
        System.out.println("Realizar Post proyecto");
        String url = SERVER_IP + SERVER_PATH + "entity.proyecto?";
        HttpRequest httpRequest = new HttpRequest(HttpRequest.POST,url, jsonObject);
        new HttpTask(this,"setNewProject").execute(httpRequest);
    }

    public void getUsersEmailNonProject(String idProjet){
        String url = SERVER_IP + SERVER_PATH + "entity.proyecto/getUsersEmailNonProject/"+idProjet;
        System.out.println("URLita" + url);
        HttpRequest httpRequest = new HttpRequest(HttpRequest.GET,url, null);
        new HttpTask(this,"getUsersEmailNonProject").execute(httpRequest);
    }

    public void getUsersEmailProject(String idProjet){
        String url = SERVER_IP + SERVER_PATH + "entity.proyecto/getUsersEmailProject/"+idProjet;
        HttpRequest httpRequest = new HttpRequest(HttpRequest.GET,url, null);
        new HttpTask(this,"getUsersEmailProject").execute(httpRequest);
    }

    public void getUsersProject(String idProjet){
        String url = SERVER_IP + SERVER_PATH + "entity.proyecto/getUsersProject/"+idProjet;
        HttpRequest httpRequest = new HttpRequest(HttpRequest.GET,url, null);
        new HttpTask(this,"getUsersProject").execute(httpRequest);
    }

    @Override
    public void onResponse(Pair<String, String> response) {
        if (response.first.equals("getProjects")) {
            parseProjectList(response.second);
        } else if (response.first.equals("getUserEmailList")){
            parseEmails(response.second, "getUserEmailList");
        } else if (response.first.equals("getUsersEmailNonProject")) {
            parseEmails(response.second, "getUsersEmailNonProject");
        } else if (response.first.equals("getUsersEmailProject")) {
            parseEmails(response.second, "getUsersEmailProject");
        } else if (response.first.equals("getProject")) {
            parseProject(response.second);
        } else if (response.first.equals("getUsersProject")) {
            parseUsers(response.second);
        }

    }

    private void parseUsers(String response){
        ArrayList<Usuario> usersList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++) {
                Usuario u = Usuario.fromJSON(jsonArray.getString(i));
                usersList.add(u);
            }

            listener.onListResponse(new Pair("getUsersProject", usersList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void parseProject (String response) {
        Gson converter = new Gson();
        Proyecto p = converter.fromJson(response, Proyecto.class);
        listener.onObjectResponse(new Pair("getProject", p));

    }

    private void parseEmails (String response, String method) {

        ArrayList<String> userEmailList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++) {
                String email = jsonArray.getString(i);
                userEmailList.add(email);
            }
            listener.onListResponse(new Pair(method, userEmailList));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
