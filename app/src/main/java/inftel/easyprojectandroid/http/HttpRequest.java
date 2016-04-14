package inftel.easyprojectandroid.http;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by csalas on 7/4/16.
 */
public class HttpRequest {
    public static int GET = 0;
    public static int POST = 1;
    public static int PUT = 2;
    public static int DELETE = 3;

    private JSONObject json;
    private int method;
    private URL url;
    private HttpURLConnection connection = null;

    public HttpRequest(int method, String url, JSONObject json) {
        this.method = method;

        this.json = json;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String execute() {
        try {
            connection = (HttpURLConnection) url.openConnection();

            if (method == GET) {
                return get();
            }
            if (method == POST) {
                return post();
            }
            if (method == PUT) {
                return put();
            }
            if (method == DELETE) {
                return delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private String get() {
        StringBuilder response = new StringBuilder();

        try {

            connection.setRequestMethod("GET");
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    private String post() {
        StringBuilder response = new StringBuilder();

        try {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setRequestProperty("Content-Type","application/json; charset=utf-8");

            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dStream, "UTF-8"));

            writer.write(String.valueOf(json));
            writer.close();
            dStream.close();

            connection.getResponseCode();
            System.out.println("CODERESPONSE" + String.valueOf(connection.getResponseCode()));

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                response.append(line);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        //return "";
        System.out.println("RESPONSEPOST:" + response);
         return response.toString();
    }

    private String put() {

        StringBuilder response = new StringBuilder();

        try {

            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setRequestProperty("Content-Type","application/json; charset=utf-8");

            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dStream, "UTF-8"));

            writer.write(String.valueOf(json));
            writer.close();
            dStream.close();

            connection.getResponseCode();
            System.out.println("CODERESPONSE" + String.valueOf(connection.getResponseCode()));

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();

    }

    private String delete() {

        StringBuilder response = new StringBuilder();

        try {
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setRequestProperty("Content-Type","application/json; charset=utf-8");

            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dStream, "UTF-8"));

            writer.write(String.valueOf(json));
            writer.close();
            dStream.close();

            connection.getResponseCode();
            System.out.println("CODERESPONSE" + String.valueOf(connection.getResponseCode()));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

}
