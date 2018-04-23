package com.example.auditor.tfg.UtilsREQUEST;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.auditor.tfg.Activities.DashboardActivity;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class DashboardCreateRequest extends AsyncTask<String, Void, Integer> {


    Activity context;

    public DashboardCreateRequest(Activity context) {
        this.context = context;
    }

    @Override
    protected Integer doInBackground(String... url) {

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

        return 0;
    }


    @Override
    protected void onPostExecute(Integer i) {

        DashboardActivity activity = (DashboardActivity) context;
        activity.refreshDashboard();

        Toast toast = Toast.makeText(context,"Crawl launched", Toast.LENGTH_LONG);
        toast.show();



    }
}