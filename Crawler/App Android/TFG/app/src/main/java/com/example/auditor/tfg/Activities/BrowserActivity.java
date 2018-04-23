package com.example.auditor.tfg.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.auditor.tfg.ListAdapters.BrowserCellAdapter;
import com.example.auditor.tfg.ListAdapters.DashboardCellAdapter;
import com.example.auditor.tfg.Listeners.DashboardNewCrawlOCL;
import com.example.auditor.tfg.R;
import com.example.auditor.tfg.Utils.Host;
import com.example.auditor.tfg.Utils.OnSwipeTouchListener;
import com.example.auditor.tfg.UtilsJSON.BrowserGetChildrenJSON;
import com.example.auditor.tfg.UtilsJSON.DashboardGetScansJSON;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.List;

public class BrowserActivity extends AppCompatActivity {

    //Actividad de navegación entre los ficheros detectados en el escaneo

    String parent;
    String crawlId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        // Se reciben los parámetros de la actividad anterior mediante el propio Intent.

        parent = getIntent().getStringExtra("parent");
        crawlId = getIntent().getStringExtra("crawlId");

        Log.e("RESULTADO", parent);
        Log.e("RESULTADO", crawlId);

        // Se realiza la petición para obtener de los ficheros detectados en el escaneo contenidos en el host parent.

        BrowserGetChildrenJSON getJSON = new BrowserGetChildrenJSON(this);
        getJSON.execute(Host.host+"/children", "crawlId="+crawlId+"&parent="+parent+'/');

        // Se especifica la configuración de los gestos utilizados en el list view y en la sección de resumen.

        setBrowserSwipeBack();
        setDetailSwipeBack();

    }


    public void refreshDashboard (String path){

        // Refresca la información del listview cuando se accede a un directorio hijo

        parent = parent+path;

        BrowserGetChildrenJSON getJSON = new BrowserGetChildrenJSON(this);
        getJSON.execute(Host.host+"/children", "crawlId="+crawlId+"&parent="+parent);

    }

    public void refreshDashboardBack (String parent){

        // Refresca la información del listview cuando se accede al directorio padre (mediante swipe)

        Log.e("MENSAJE", " dash back");

        BrowserGetChildrenJSON getJSON = new BrowserGetChildrenJSON(this);
        getJSON.execute(Host.host+"/updir", "crawlId="+crawlId+"&parent="+parent,"back");


    }

    public void setAdapterListView (String s) throws JSONException {

        // Una vez se ha obtenido respuesta JSON del web service con la información, se actualiza el listview y la sección resumen.

        Log.e("RESULTADO", s);

        ListView browserListView = (ListView) findViewById(R.id.myBrowserListView);
        browserListView.setAdapter(new BrowserCellAdapter(this, s));

        TextView textView = (TextView) findViewById(R.id.browserParentTextView);
        textView.setText(parent);

        browserListView.setVisibility(View.VISIBLE);
    }

    private void setDetailSwipeBack () {

        // Se expecifica funcionalidad cuando se realiza un gesto de swipe la izquierda sobre la sección resumen

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.browserDetailLinearLayout);
        linearLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeDown() {

            }

            @Override
            public void onSwipeLeft() {

                Log.e("MENSAJE", " Se ha hecho swipe");

                // Se genera una animación de movimiento

                Animation animation = AnimationUtils.loadAnimation(BrowserActivity.this, R.anim.slide_out_detail);
                linearLayout.startAnimation(animation);

                BrowserActivity activity = (BrowserActivity) context;

                TextView textView = (TextView) activity.findViewById(R.id.browserDetailLinkTextView);
                String link = textView.getText().toString();
                String crawlId = activity.crawlId;

                // Se lanza la actividad de muestra de información, enviando los parámetros necesarios

                Intent intent = new Intent(BrowserActivity.this, DetailActivity.class);
                intent.putExtra("link", link);
                intent.putExtra("crawlId", crawlId);
                BrowserActivity.this.startActivity(intent);

                linearLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onSwipeUp() {

            }

            @Override
            public void onSwipeRight() {



            }
        });

    }

    private void setBrowserSwipeBack () {

        // Se expecifica funcionalidad cuando se realiza un gesto de swipe la derecha sobre el listview

        ListView listView = (ListView) findViewById(R.id.myBrowserListView);
        listView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeDown() {

            }

            @Override
            public void onSwipeLeft() {

            }

            @Override
            public void onSwipeUp() {

            }

            @Override
            public void onSwipeRight() {
                Log.e("MENSAJE", " Se ha hecho swipe");
                TextView textView = (TextView) findViewById(R.id.browserParentTextView);
                String parent = textView.getText().toString();
                Log.e("MENSAJE", parent);


                // SE genera la animación y se refresca el listView con los ficheros del directorio padre - refreshDashboardBack(parent)

                ListView browserListView = (ListView) findViewById(R.id.myBrowserListView);
                Animation slideOut = AnimationUtils.loadAnimation(BrowserActivity.this, R.anim.slide_out);
                browserListView.startAnimation(slideOut);

                browserListView.setVisibility(View.INVISIBLE);

                refreshDashboardBack(parent);


            }
        });
    }

    public void setParent(String parent){

        // Sanitiza el formato del parámetro parent de acuerdo a lo recibido en el JSON respuesta.

        String last = parent.substring(parent.length()-1);
        String parentFinal;
        if (last.equals("/")){

            parentFinal = parent.substring(0,parent.length()-1);
            this.parent = parentFinal;

        }else{
            parentFinal = parent;
            this.parent = parentFinal;
        }

        TextView textView = (TextView) findViewById(R.id.browserParentTextView);
        textView.setText(parentFinal);

    }

}
