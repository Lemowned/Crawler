package com.example.auditor.tfg.UtilsJSON;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.auditor.tfg.Activities.BrowserActivity;
import com.example.auditor.tfg.Activities.DashboardActivity;

import org.json.JSONException;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;



public class BrowserGetChildrenJSON extends AsyncTask<String, Void, String> {

    /*
     Tarea asíncrona que solicita y recibe la información de navegación del servidor.
      */

    Activity activity;

    public BrowserGetChildrenJSON(Context context) {

        this.activity = (Activity)context;
    }

    @Override
    protected String doInBackground(String... url) {

        String json_string = "";
        URL url1 = null;
        HttpURLConnection http = null;
        int respuesta = -5;
        InputStream is = null;

        try {
            url1 = new URL(url[0]);
            Log.e("MENSAJE", "URL creada");
            http = (HttpURLConnection)url1.openConnection();
            Log.e("MENSAJE", "Se abre conexion " + http.toString());
            http.setRequestMethod( "POST" );
            DataOutputStream wr = new DataOutputStream( http.getOutputStream());
            wr.write(url[1].getBytes( StandardCharsets.UTF_8 ));

            respuesta = http.getResponseCode();
            Log.e("MENSAJE", "Se comprueba el codigo de error");

            if (respuesta == HttpURLConnection.HTTP_OK){
                Log.d("MENSAJE", "La cosa fue bien");
                is = http.getInputStream();
                json_string = new Scanner(is,"UTF-8").useDelimiter("\\A").next();
                Log.d("MENSAJE", json_string);
            }

        }catch (Throwable t){
            Log.e("MENSAJE", "Algo fue mal", t);
        }
        finally {
            http.disconnect();
        }



        return json_string;

    }


    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        BrowserActivity browserActivity;
        try {
            browserActivity = (BrowserActivity) activity;
            browserActivity.setAdapterListView(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
