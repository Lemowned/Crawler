package com.example.auditor.tfg.ListAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.auditor.tfg.Listeners.DetailFormOCL;
import com.example.auditor.tfg.Listeners.DetailWordOCL;
import com.example.auditor.tfg.Modelos.CrawlInfo;
import com.example.auditor.tfg.Modelos.FormModel;
import com.example.auditor.tfg.Modelos.InputModel;
import com.example.auditor.tfg.Modelos.WordModel;
import com.example.auditor.tfg.R;

import java.text.Normalizer;



public class DetailFormCellAdapter extends BaseAdapter {

    // Adaptador de la celda de la vista de tabla de muestra de links.

    Context context;
    private static LayoutInflater inflater;
    CrawlInfo crawlInfo;

    public DetailFormCellAdapter(Context context,CrawlInfo crawlInfo) {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.crawlInfo = crawlInfo;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (vi == null)
            vi = inflater.inflate(R.layout.cell_row_forms_detail, null);

        FormModel[] formModel = crawlInfo.getForms();

        // Cada celda generada contiene uno de los formularios detectados durante el crawling.

        TextView textView = (TextView) vi.findViewById(R.id.detailFormsTextView);

        String form = formModel[i].getAction();


        if (form != null) {
            textView.setText(form);
        } else{
            textView.setText("null");
        }

        InputModel[] inputModel = formModel[i].getInputs();

        vi.setOnClickListener(new DetailFormOCL(context, inputModel));



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
        return crawlInfo.getForms().length;
    }
}

