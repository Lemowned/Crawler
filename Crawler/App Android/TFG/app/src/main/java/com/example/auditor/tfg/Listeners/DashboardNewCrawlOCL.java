package com.example.auditor.tfg.Listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.auditor.tfg.R;
import com.example.auditor.tfg.Utils.OnSwipeTouchListener;


public class DashboardNewCrawlOCL implements View.OnClickListener {

    // Listener para intercambio de layouts al seleccionar un tipo de red anónima en el cuadro de control.

    Context context;

    public DashboardNewCrawlOCL(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {

        /*
        Al pulsar alguno de los botones que representan cada una de las redes anónimas se muestra un layout
        con una entrada de texto personalizado para cada red anónima.
         */

        final Activity activity = (Activity) context;

        final LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.layout_menu_emergente);

        View child = activity.getLayoutInflater().inflate(R.layout.layout_new_crawl, linearLayout, false);
        linearLayout.removeAllViews();
        linearLayout.addView(child);

        TextView textView = (TextView) activity.findViewById(R.id.newCrawlTextView);
        switch (view.getId()){
            case R.id.torBTN:
                textView.setText("TOR");
                break;
            case R.id.i2pBTN:
                textView.setText("I2P");
                break;
            default:
                textView.setText("FREENET");
                break;
        }

        ImageButton buttonLaunch = (ImageButton) activity.findViewById(R.id.launch_button);
        buttonLaunch.setOnClickListener(new DashboardLaunchOCL(activity));

        // Se genera un listener para el evento de deslizar hacia abajo para ocultar el nuevo layout inflado.

        linearLayout.setOnTouchListener(new OnSwipeTouchListener(activity) {
            @Override
            public void onSwipeDown() {

                Animation closeMenu = AnimationUtils.loadAnimation(context,R.anim.slide_out_launch);
                linearLayout.setAnimation(closeMenu);

                LinearLayout linearLayout1 = (LinearLayout) activity.findViewById(R.id.layout_menu_emergente);

                View child = activity.getLayoutInflater().inflate(R.layout.layout_buttons, linearLayout1, false);
                linearLayout1.removeAllViews();
                linearLayout1.addView(child);
                ImageButton torBTN = (ImageButton) activity.findViewById(R.id.torBTN);
                torBTN.setOnClickListener(new DashboardNewCrawlOCL(activity));
                ImageButton i2pBTN = (ImageButton) activity.findViewById(R.id.i2pBTN);
                i2pBTN.setOnClickListener(new DashboardNewCrawlOCL(activity));
                ImageButton freenetBTN = (ImageButton) activity.findViewById(R.id.freenetBTN);
                freenetBTN.setOnClickListener(new DashboardNewCrawlOCL(activity));
                linearLayout.setOnTouchListener(null);

            }

            @Override
            public void onSwipeLeft() {

            }

            @Override
            public void onSwipeUp() {

            }

            @Override
            public void onSwipeRight() {

            }
        });


    }
}