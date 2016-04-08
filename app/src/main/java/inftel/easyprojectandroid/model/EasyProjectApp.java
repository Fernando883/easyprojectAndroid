package inftel.easyprojectandroid.model;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by inftel11 on 7/4/16.
 */
public class EasyProjectApp extends Application {
    private GoogleApiClient mGoogleApiClient;
    private static EasyProjectApp mInstance;

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
}