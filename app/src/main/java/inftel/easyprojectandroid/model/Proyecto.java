package inftel.easyprojectandroid.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by csalas on 6/4/16.
 */
public class Proyecto {
    private Long idProyect;
    private String nombreP;
    private String descripcion;
    private String chat;
    private Usuario director;

    // Propios de la APP
    private int numUsers;

    public Proyecto() {

    }

    public Long getIdProyect() {
        return idProyect;
    }

    public void setIdProyect(Long idProyect) {
        this.idProyect = idProyect;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public Usuario getDirector() {
        return director;
    }

    public void setDirector(Usuario director) {
        this.director = director;
    }

    public int getNumUsers() {
        return numUsers;
    }

    public void setNumUsers(int numUsers) {
        this.numUsers = numUsers;
    }

    public static Proyecto fromJSON(String response) throws JSONException {
        Proyecto project = new Proyecto();
        JSONObject jsonObject = new JSONObject(response);
        project.setNombreP(jsonObject.getString("nombreP"));
        project.setDescripcion(jsonObject.getString("descripcion"));
        project.setIdProyect(jsonObject.getLong("idProyect"));
        project.setNumUsers(1); // Cambiar los datos devueltos por el JSON
        return project;
    }


}
