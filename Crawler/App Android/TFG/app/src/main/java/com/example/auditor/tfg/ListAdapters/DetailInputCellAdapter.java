package com.example.auditor.tfg.ListAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.auditor.tfg.Listeners.DetailFormOCL;
import com.example.auditor.tfg.Modelos.CrawlInfo;
import com.example.auditor.tfg.Modelos.FormModel;
import com.example.auditor.tfg.Modelos.InputModel;
import com.example.auditor.tfg.R;


public class DetailInputCellAdapter extends BaseAdapter {

    // Adaptador de la celda de la vista de tabla de muestra de parámetros de los formularios.

    Context context;
    private static LayoutInflater inflater;
    InputModel[] inputModel;

    public DetailInputCellAdapter(Context context,InputModel[] inputModel) {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.inputModel = inputModel;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (vi == null)
            vi = inflater.inflate(R.layout.cell_row_inputs_detail, null);


        // Cada celda generada contiene un parámetro del formulario que representa la vista de tabla.

        TextView textViewName = (TextView) vi.findViewById(R.id.detailNameTextView);



        String name = inputModel[i].getName();


        if (name!=null) {

            textViewName.setText(name);
        } else{
            textViewName.setText("null");
        }

        return vi;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return inputModel.length;
    }
}
