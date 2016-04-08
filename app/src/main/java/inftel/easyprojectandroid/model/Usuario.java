package inftel.easyprojectandroid.model;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by csalas on 6/4/16.
 */
public class Usuario {
    private Long idUsuario;
    private String email;
    private String nombreU;

    public Usuario(){};

    private static Usuario userInstance = null;


    public static Usuario getInstance() {
        if(userInstance == null) {
            userInstance = new Usuario();
        }
        return userInstance;
    }


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
}
