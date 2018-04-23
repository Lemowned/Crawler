package com.example.auditor.tfg.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.auditor.tfg.ListAdapters.DashboardCellAdapter;
import com.example.auditor.tfg.Listeners.DashboardNewCrawlOCL;
import com.example.auditor.tfg.R;
import com.example.auditor.tfg.Utils.Host;
import com.example.auditor.tfg.UtilsJSON.DashboardGetScansJSON;

import org.json.JSONException;

public class DashboardActivity extends AppCompatActivity {

    //Actividad de cuadro de mando

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //Se infla el layout que forma el menu emergente que muestra los botones para crear nuevas instancias
        //de las tres redes anónimas propuestas.

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_menu_emergente);
        View child = getLayoutInflater().inflate(R.layout.layout_buttons,linearLayout, false);
        linearLayout.addView(child);

        // Se asigna el listener para cada boton que genera un nuevo crawler.

        ImageButton torBTN = (ImageButton) findViewById(R.id.torBTN);
        torBTN.setOnClickListener(new DashboardNewCrawlOCL(this));
        ImageButton i2pBTN = (ImageButton) findViewById(R.id.i2pBTN);
        i2pBTN.setOnClickListener(new DashboardNewCrawlOCL(this));
        ImageButton freenetBTN = (ImageButton) findViewById(R.id.freenetBTN);
        freenetBTN.setOnClickListener(new DashboardNewCrawlOCL(this));

        // Se carga la información en la actividad
        refreshDashboard();


    }

    public void refreshDashboard (){

        // Se realiza la petición al Web Service para obtener los escaneos en formato existentes JSON
        DashboardGetScansJSON getJSON = new DashboardGetScansJSON(this);
        getJSON.execute(Host.host+"/scans");

    }

    // A esta función se llamará una vez el se haya obtenido la respuesta JSON del web service.
    public void setAdapterListView (String s) throws JSONException {

        // Cuando se obtiene respuesta JSON del webservice, se actualiza la información del list View.

        ListView myListViewDetalle = (ListView) findViewById(R.id.myDashListView);
        myListViewDetalle.setAdapter(new DashboardCellAdapter(this, s));
    }
}
