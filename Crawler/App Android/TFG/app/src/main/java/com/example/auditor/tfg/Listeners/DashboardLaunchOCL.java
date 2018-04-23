package com.example.auditor.tfg.Listeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.auditor.tfg.R;
import com.example.auditor.tfg.Utils.Host;
import com.example.auditor.tfg.UtilsREQUEST.DashboardCreateRequest;



public class DashboardLaunchOCL implements View.OnClickListener {

    // Listener para el botón que genera una nueva instancia para el crawler.

    Activity activity;

    public DashboardLaunchOCL(Activity context) {
        this.activity = context;
    }

    @Override
    public void onClick(View view) {

        // Se envía al servidor la petición para generar una nueva instancia, y se refresca el cuadro de control.

        EditText editText = (EditText)activity.findViewById(R.id.createET);
        String newScan = editText.getText().toString();

        DashboardCreateRequest launchRequest = new DashboardCreateRequest(activity);
        launchRequest.execute(Host.host+"/create", "host="+newScan+"&via=&search=");

        LinearLayout linearLayout1 = (LinearLayout) activity.findViewById(R.id.layout_menu_emergente);
        View child = activity.getLayoutInflater().inflate(R.layout.layout_buttons,linearLayout1,false);
        linearLayout1.removeAllViews();
        linearLayout1.addView(child);
        ImageButton boton = (ImageButton) activity.findViewById(R.id.torBTN);
        boton.setOnClickListener(new DashboardNewCrawlOCL(activity));


    }
}
