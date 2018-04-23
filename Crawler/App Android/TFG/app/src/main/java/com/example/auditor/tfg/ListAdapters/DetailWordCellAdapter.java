package com.example.auditor.tfg.ListAdapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auditor.tfg.Listeners.DashboardCellOCL;
import com.example.auditor.tfg.Listeners.DashboardRunOCL;
import com.example.auditor.tfg.Listeners.DetailWordOCL;
import com.example.auditor.tfg.Modelos.CrawlInfo;
import com.example.auditor.tfg.Modelos.WordModel;
import com.example.auditor.tfg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DetailWordCellAdapter extends BaseAdapter {

    // Adaptador de la celda de la vista de tabla de muestra de recuento de palabras.

    Context context;
    private static LayoutInflater inflater;
    CrawlInfo crawlInfo;

    public DetailWordCellAdapter(Context context,CrawlInfo crawlInfo) {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.crawlInfo = crawlInfo;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (vi == null)
            vi = inflater.inflate(R.layout.cell_row_word_detail, null);

        WordModel[] wordModel = crawlInfo.getWordlist();


        // Cada celda contiene cada una de las palabras del TOP 10 de palabras más repetidas, contenidas en el jSON.

        TextView textView = (TextView) vi.findViewById(R.id.detailWordTextView);

        String word = wordModel[i].getWord();


        if (word != null) {
            textView.setText(word);
        } else{
            textView.setText("null");
        }

        String num = wordModel[i].getNum();

        vi.setOnClickListener(new DetailWordOCL(context, num));



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
        return crawlInfo.getWordlist().length;
    }
}
