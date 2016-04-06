package inftel.easyprojectandroid.model;

/**
 * Created by csalas on 6/4/16.
 */
public class Usuario {
    private Long idUsuario;
    private String email;
    private String nombreU;

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
