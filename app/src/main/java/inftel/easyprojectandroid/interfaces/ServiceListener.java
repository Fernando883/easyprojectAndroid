package inftel.easyprojectandroid.interfaces;

import android.util.Pair;

import java.util.List;

/**
 * Created by csalas on 7/4/16.
 */
public interface ServiceListener {
    public void onObjectResponse(Pair<String, ?> response);
    public void onListResponse(Pair<String, List<?>> response);
}
