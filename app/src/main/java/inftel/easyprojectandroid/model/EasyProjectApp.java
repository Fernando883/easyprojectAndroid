package inftel.easyprojectandroid.model;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by inftel11 on 7/4/16.
 */
public class EasyProjectApp extends Application {
    private GoogleApiClient mGoogleApiClient;
    private static EasyProjectApp mInstance;
    private Usuario user = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized EasyProjectApp getInstance() {
        return mInstance;
    }

    public GoogleApiClient getGoogleApiClient() { return mGoogleApiClient; }

    public void setGoogleApiClient(GoogleApiClient client) { this.mGoogleApiClient = client; }

    public Usuario getUser() {
        if (user == null)
            return new Usuario();
        else
            return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
