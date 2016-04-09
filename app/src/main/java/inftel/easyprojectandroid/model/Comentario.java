package inftel.easyprojectandroid.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by csalas on 6/4/16.
 */
public class Comentario {

    private Long idComent;
    private String texto;
    private Date fecha;
    private Tarea idTarea;
    private Usuario idUsuario;

    public Comentario() {

    }

    public Long getIdComent() {
        return idComent;
    }

    public void setIdComent(Long idComent) {
        this.idComent = idComent;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Tarea getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Tarea idTarea) {
        this.idTarea = idTarea;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public static Comentario fromJSON(String response) throws JSONException {
        Comentario comment = new Comentario();
        JSONObject jsonObject = new JSONObject(response);

        return comment;
    }
}
