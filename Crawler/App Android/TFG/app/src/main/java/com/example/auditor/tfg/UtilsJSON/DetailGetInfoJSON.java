package com.example.auditor.tfg.UtilsJSON;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.auditor.tfg.Activities.BrowserActivity;
import com.example.auditor.tfg.Activities.DetailActivity;
import com.example.auditor.tfg.Modelos.CrawlInfo;
import com.example.auditor.tfg.Modelos.FormModel;
import com.example.auditor.tfg.Modelos.WordModel;
import com.example.auditor.tfg.R;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Scanner;



public class DetailGetInfoJSON extends AsyncTask<String, Void, String> {

    /*
     Tarea asíncrona que solicita y recibe la información resultante del análisis del servidor.
      */

    Activity activity;

    public DetailGetInfoJSON(Context context) {

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

        DetailActivity detailActivity = (DetailActivity) activity;

        CrawlInfo crawlInfo = crawlInfoParser(s);

        detailActivity.setViews(crawlInfo);



    }

    private CrawlInfo crawlInfoParser (String json) {

        String [] internalLinks = {""};
        String [] externalLinks = {""};
        WordModel [] wordlist = null;
        FormModel[] forms = null;
        String crawlId = "", parent = "", host = "", path = "", link = "", type = "", status = "";

        try {
            JSONArray arrayChildren = new JSONArray(json);

            JSONObject jsonObject = (JSONObject) arrayChildren.get(0);

            crawlId = jsonObject.getString("crawlId");
            host = jsonObject.getString("host");
            link = jsonObject.getString("link");
            parent = jsonObject.getString("parent");
            path = jsonObject.getString("path");
            type = jsonObject.getString("type");
            status = jsonObject.getString("status");

            Gson gson = new Gson();
            internalLinks = gson.fromJson(jsonObject.get("internalLinks").toString(), String[].class);
            externalLinks = gson.fromJson(jsonObject.get("externalLinks").toString(), String[].class);
            wordlist = gson.fromJson(jsonObject.get("wordlist").toString(), WordModel[].class);
            forms = gson.fromJson(jsonObject.get("forms").toString(), FormModel[].class);




        } catch (JSONException e) {
            e.printStackTrace();
        }


        return new CrawlInfo(crawlId,parent, host, path, link, type, status, internalLinks, externalLinks, forms, wordlist);

    }


}
