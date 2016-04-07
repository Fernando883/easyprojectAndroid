package inftel.easyprojectandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.model.Usuario;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private GoogleSignInOptions gso;
    private int RC_SIGN_IN = 1;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        sharedPref =  getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String email = sharedPref.getString("email", "");
        String username = sharedPref.getString("nombreU", "");
        if (!email.equals("")){
            Usuario user = Usuario.getInstance();
            user.setEmail(email);
            user.setNombreU(username);
            goMainActivity(user, false);
        }else {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            // Build a GoogleApiClient with access to the Google Sign-In API and the
            // options specified by gso.
            Usuario.getInstance().setGoogleApiClient(new GoogleApiClient.Builder(this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build());
            //Set the listener button signIn
            findViewById(R.id.sign_in_button).setOnClickListener(this);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Usuario.getInstance().getGoogleApiClient().connect();
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
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(Usuario.getInstance().getGoogleApiClient() );
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
            Usuario user = Usuario.getInstance();
            user.setEmail(acct.getEmail());
            user.setNombreU(acct.getDisplayName());
            goMainActivity(user, true);
        } else {
            Log.d("LoginActivity", "NameSignInResult Error");
        }
    }

    private void goMainActivity(Usuario user, boolean newSession){

        if (newSession) {
            sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("email", user.getEmail());
            editor.putString("username", user.getNombreU());
            editor.commit();

        }

        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
        finish();

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
