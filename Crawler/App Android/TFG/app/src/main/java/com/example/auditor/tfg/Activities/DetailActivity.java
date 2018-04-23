package com.example.auditor.tfg.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auditor.tfg.Modelos.CrawlInfo;
import com.example.auditor.tfg.Modelos.FormModel;
import com.example.auditor.tfg.Modelos.WordModel;
import com.example.auditor.tfg.R;
import com.example.auditor.tfg.Utils.Host;
import com.example.auditor.tfg.UtilsJSON.DashboardGetScansJSON;
import com.example.auditor.tfg.UtilsJSON.DetailGetInfoJSON;
import com.example.auditor.tfg.ViewPagerAdapters.DetailViewPagerAdapter;

import java.util.Hashtable;

public class DetailActivity extends AppCompatActivity {

    //Actividad de muestra de información

    String link = "";
    String crawlId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Se reciben los parámetros de la actividad anterior mediante el propio Intent.

        link = getIntent().getStringExtra("link");
        crawlId = getIntent().getStringExtra("crawlId");

        Log.e("DETAIL-LINK",link);
        Log.e("DETAIL-LINK",crawlId);

        // Obtiene la información que se presenta al usuario en formato JSON

        obtainInfoJSON(crawlId, link);

    }

    private void obtainInfoJSON (String crawlId, String link) {

        Log.d("MENSAJE", "Voy a hacer la consulta");

        // Se lanza la petición al web service para obtener la información en JSON que presentar.

        DetailGetInfoJSON getJSON = new DetailGetInfoJSON(this);
        getJSON.execute(Host.host+"/info", "crawlId="+crawlId+"&link="+link);

    }

    public void setViews(CrawlInfo crawlInfo) {

        Log.d("MENSAJE RESULTADO","");

        // Se especifica la configuración del viewpager que cargara los fragments en la actividad.

        ViewPager viewPager = (ViewPager)findViewById(R.id.detailViewPager);
        DetailViewPagerAdapter myPagerAdapter = new DetailViewPagerAdapter(getSupportFragmentManager(), crawlInfo);
        viewPager.setAdapter(myPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.detailTablayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


    }
}
