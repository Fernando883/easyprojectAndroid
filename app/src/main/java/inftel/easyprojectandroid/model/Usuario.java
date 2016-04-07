package inftel.easyprojectandroid.model;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by csalas on 6/4/16.
 */
public class Usuario extends Application {
    private GoogleApiClient mGoogleApiClient;
    private Long idUsuario;
    private String email;
    private String nombreU;

    private static Usuario userInstance = null;

    private Usuario(){};

    public static Usuario getInstance() {
        if(userInstance == null) {
            userInstance = new Usuario();
        }
        return userInstance;
    }

    public GoogleApiClient getGoogleApiClient() { return mGoogleApiClient; }

    public void setGoogleApiClient(GoogleApiClient client) { this.mGoogleApiClient = client; }

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
