package inftel.easyprojectandroid.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by csalas on 6/4/16.
 */
public class Usuario {
    private Long idUsuario;
    private String email;
    private String nombreU;

    // Propia de la APP
    private String imgUrl;

    public Usuario(){
        idUsuario = 0l;
        email = "";
        nombreU = "";
        imgUrl = "";
    };

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreU() {
        return nombreU;
    }

    public void setNombreU(String nombreU) {
        this.nombreU = nombreU;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static Usuario fromJSON(String response) throws JSONException {
        Usuario user = new Usuario();
        JSONObject jsonObject = new JSONObject(response);
        user.setEmail(jsonObject.getString("email"));
        user.setIdUsuario(jsonObject.getLong("idUsuario"));
        user.setNombreU(jsonObject.getString("nombreU"));
        return user;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", email='" + email + '\'' +
                ", nombreU='" + nombreU + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
