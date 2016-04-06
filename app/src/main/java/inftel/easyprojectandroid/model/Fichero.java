package inftel.easyprojectandroid.model;

/**
 * Created by csalas on 6/4/16.
 */
public class Fichero {
    private Long idFichero;
    private String ruta;
    private Tarea idTarea;

    public Long getIdFichero() {
        return idFichero;
    }

    public void setIdFichero(Long idFichero) {
        this.idFichero = idFichero;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Tarea getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Tarea idTarea) {
        this.idTarea = idTarea;
    }
}
