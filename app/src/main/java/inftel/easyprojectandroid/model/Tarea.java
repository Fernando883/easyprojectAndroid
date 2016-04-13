package inftel.easyprojectandroid.model;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by csalas on 6/4/16.
 */
public class Tarea {
    private Long idTarea;
    private String nombre;
    private BigInteger tiempo;
    private String estado;
    private String descripcion;
    private Proyecto idProyecto;
    private Usuario idUsuario;
    private ArrayList<Usuario> usuarioCollection;

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getTiempo() {
        return tiempo;
    }

    public void setTiempo(BigInteger tiempo) {
        this.tiempo = tiempo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Proyecto idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ArrayList<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(ArrayList<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }


    public static Tarea fromJSON(String response) throws JSONException {

        Gson gson = new Gson();
        Tarea task = gson.fromJson(response, Tarea.class);

        return task;
    }



    @Override
    public String toString() {
        return "Tarea{" +
                "idTarea=" + idTarea +
                ", nombre='" + nombre + '\'' +
                ", tiempo=" + tiempo +
                ", estado='" + estado + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", idProyecto=" + idProyecto +
                ", idUsuario=" + idUsuario +
                ", usuarioCollection=" + usuarioCollection +
                '}';
    }
}
