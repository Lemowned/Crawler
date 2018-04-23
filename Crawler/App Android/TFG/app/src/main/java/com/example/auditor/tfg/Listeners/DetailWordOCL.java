package com.example.auditor.tfg.Listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.auditor.tfg.R;
import com.example.auditor.tfg.Utils.Host;
import com.example.auditor.tfg.UtilsREQUEST.DashboardCreateRequest;


public class DetailWordOCL implements View.OnClickListener {

    // Listener para cada celda de la tabla de recuento de palabras.

    Activity activity;
    String number;

    public DetailWordOCL(Context context, String number) {

        this.activity = (Activity) context;
        this.number = number;
    }

    @Override
    public void onClick(View view) {

        /*
        Cuando se pulsa una palabra se actualiza el valor de detailNumberTextView con el núemro de
         repeticiones de esa palabra.
         */

        TextView textView = (TextView) activity.findViewById(R.id.detailNumberTextView);
        textView.setText(number);


    }
}
