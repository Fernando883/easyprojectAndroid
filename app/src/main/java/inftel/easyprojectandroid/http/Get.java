package inftel.easyprojectandroid.http;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by anotauntanto on 7/4/16.
 */
public class Get extends AsyncTask <String, Void, StringBuffer>{

    @Override
    protected StringBuffer doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            return loginUser(urls[0]);

        } catch (IOException e) {
            Log.d("LoginActivity", "Unable to retrieve web page. URL may be invalid.");
            return null;
        }
    }

    @Override
    protected void onPostExecute(StringBuffer json) {

    }

    private StringBuffer loginUser(String url) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();


        BufferedReader in = new BufferedReader((new InputStreamReader(con.getInputStream())));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine())!= null){
            response.append(inputLine);
        }
        in.close();
        con.disconnect();

        return response;
    }

}

