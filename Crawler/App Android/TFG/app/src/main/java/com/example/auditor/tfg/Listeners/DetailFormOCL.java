package com.example.auditor.tfg.Listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.auditor.tfg.ListAdapters.DetailInputCellAdapter;
import com.example.auditor.tfg.Modelos.FormModel;
import com.example.auditor.tfg.Modelos.InputModel;
import com.example.auditor.tfg.R;



public class DetailFormOCL implements View.OnClickListener {

    // Listener para cada celda de la vista de tabla de formularios.

    Activity activity;
    InputModel[] forms;

    public DetailFormOCL(Context context, InputModel[] forms) {

        this.activity = (Activity) context;
        this.forms = forms;
    }

    @Override
    public void onClick(View view) {

        /*
         Cuando se pulsa sobre una celda de un formulario se actualiza el contenido de la tabla con
         los parámetros del formulario seleccionado.
          */

        ListView listView = (ListView) activity.findViewById(R.id.detailFormsListView);
        listView.setAdapter(new DetailInputCellAdapter(view.getContext(), forms));



    }
}
