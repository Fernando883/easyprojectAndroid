package inftel.easyprojectandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.interfaces.ServiceListener;
import inftel.easyprojectandroid.model.EasyProjectApp;
import inftel.easyprojectandroid.model.Usuario;
import inftel.easyprojectandroid.service.UserService;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, ServiceListener {

    private GoogleSignInOptions gso;
    private int RC_SIGN_IN = 1;
    private SharedPreferences sharedPref;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userService = new UserService(this, this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        sharedPref =  getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String email = sharedPref.getString("email", "");
        String username = sharedPref.getString("nombreU", "");
        String imgUrl = sharedPref.getString("imgUrl", "");
        Long idUsuario = sharedPref.getLong("idUsuario", 0l);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        EasyProjectApp.getInstance().setGoogleApiClient(new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build());

        if (!email.equals("")){
            Usuario user = new Usuario();
            user.setIdUsuario(idUsuario);
            user.setEmail(email);
            user.setNombreU(username);
            user.setImgUrl(imgUrl);
            EasyProjectApp.getInstance().setUser(user);
            goMainActivity(user, false);
        }else {
            
            findViewById(R.id.sign_in_button).setOnClickListener(this);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        EasyProjectApp.getInstance().getGoogleApiClient().connect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(EasyProjectApp.getInstance().getGoogleApiClient() );
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Peticion de google");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        Log.d("LoginActivity", "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Usuario user = new Usuario();
            user.setEmail(acct.getEmail());
            user.setNombreU(acct.getDisplayName());
            user.setImgUrl(acct.getPhotoUrl().toString());
            EasyProjectApp.getInstance().setUser(user);
            userService.postUser(user);

        } else {
            Log.d("LoginActivity", "NameSignInResult Error");
        }
    }

    private void goMainActivity(Usuario user, boolean newSession){

        if (newSession) {
            sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("email", user.getEmail());
            editor.putString("nombreU", user.getNombreU());
            editor.putString("imgUrl", user.getImgUrl());
            editor.putLong("idUsuario", user.getIdUsuario());
            editor.commit();

        }

        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
        //finish();

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {
        Usuario user = EasyProjectApp.getInstance().getUser();
        if (response.first.equals("postUser")){
            user.setIdUsuario((Long) response.second);
            goMainActivity(user, true);
        }

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {

    }
}
