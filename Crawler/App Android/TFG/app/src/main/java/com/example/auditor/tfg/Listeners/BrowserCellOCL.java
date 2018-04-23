package com.example.auditor.tfg.Listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.auditor.tfg.Activities.BrowserActivity;
import com.example.auditor.tfg.R;



public class BrowserCellOCL implements View.OnClickListener {

    // Listener para pulsaciones sobre las celdas del navegador.

    Context context;
    String status, link;

    public BrowserCellOCL(Context context, String status, String link) {

        this.context = context;
        this.status = status;
        this.link = link;
    }

    @Override
    public void onClick(View view) {

        /*
         En caso de pulsar una celda de la tabla de navegación, se setea el cuadro resumen superior,
         con toda la info requerida incluyendo la imagen que muestra el tipo de fichero seleccionado.
          */

        String path;
        TextView textView = (TextView) view.findViewById(R.id.browserPath);
        path = textView.getText().toString();
        BrowserActivity browserActivity = (BrowserActivity)context;

        if ((int) view.getTag() == 0){
            browserActivity.refreshDashboard(path);
        }


        Activity activity = (Activity) context;

        TextView linkTV = (TextView) activity.findViewById(R.id.browserDetailLinkTextView);
        linkTV.setText(link);
        TextView statusTV = (TextView) activity.findViewById(R.id.browserDetailStatusTextView);
        statusTV.setText(status);

        ImageView cellImageView = (ImageView) view.findViewById(R.id.browserIcon);
        ImageView imageView = (ImageView) activity.findViewById(R.id.myBrowserDetailImageView);


        if ( (int) view.getTag() == 0 ){
            imageView.setImageResource(R.drawable.folder_detail);
        } else {
            imageView.setImageResource(R.drawable.file_detail);

        }

        LinearLayout linearLayoutOcultable = (LinearLayout) activity.findViewById(R.id.browserOcultableLinearLayout);
        LinearLayout linearLayoutDetail = (LinearLayout) activity.findViewById(R.id.browserDetailLinearLayout);
        LinearLayout linearLayoutBrowser = (LinearLayout) activity.findViewById(R.id.browserBrowserLinearLayout);
        ListView listView = (ListView) activity.findViewById(R.id.myBrowserListView);

        if (linearLayoutOcultable.getVisibility() == View.INVISIBLE) {
            //listView.setVisibility(View.INVISIBLE);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_browser);
            linearLayoutBrowser.startAnimation(animation);
            linearLayoutOcultable.setVisibility(View.VISIBLE);
        }


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,5);
        linearLayoutOcultable.setLayoutParams(params);








    }
}