package com.example.auditor.tfg.Listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.auditor.tfg.Activities.BrowserActivity;
import com.example.auditor.tfg.R;



public class DashboardCellOCL implements View.OnClickListener {

    // Listener para pulsaciones sobre celdas del panel de control.

    Context context;

    public DashboardCellOCL(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {

        // Al pulsar sobre alguna de las celdas se lanza la actividad de navegación para el crawler seleccionado.

        String parent;
        String crawlId;

        Intent intent = new Intent(context, BrowserActivity.class);

        TextView parentTV = (TextView) view.findViewById(R.id.host_dashboard);
        TextView crawlIdTV = (TextView) view.findViewById(R.id.crawlId_dashboard);

        parent = parentTV.getText().toString();
        crawlId = crawlIdTV.getText().toString();

        intent.putExtra("parent", parent);
        intent.putExtra("crawlId", crawlId);

        context.startActivity(intent);


    }
}
