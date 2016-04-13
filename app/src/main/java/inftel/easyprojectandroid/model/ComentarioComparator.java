package inftel.easyprojectandroid.model;

import java.util.Comparator;

/**
 * Created by anotauntanto on 13/4/16.
 */
public class ComentarioComparator implements Comparator<Comentario> {
    @Override
    public int compare(Comentario lhs, Comentario rhs) {
        return lhs.getFecha().compareTo(rhs.getFecha());
    }

}
