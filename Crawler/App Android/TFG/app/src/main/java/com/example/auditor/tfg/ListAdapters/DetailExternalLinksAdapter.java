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



public class DetailExternalLinksAdapter extends BaseAdapter {

    // Adaptador de la celda de la vista de tabla de muestra de links.

    Context context;
    private static LayoutInflater inflater;
    CrawlInfo crawlInfo;

    public DetailExternalLinksAdapter(Context context,CrawlInfo crawlInfo) {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.crawlInfo = crawlInfo;

        Log.e("DEGUG--", ""+this.crawlInfo.getExternalLinks().length);

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (vi == null)
            vi = inflater.inflate(R.layout.cell_row_links_detail, null);

        String[] links = crawlInfo.getExternalLinks();


        TextView textView = (TextView) vi.findViewById(R.id.detailLinksTextView);

        String link = links[i];

        // La celda que se genera únicamente contiene el link contenido en el JSON

        if (link != null) {
            textView.setText(link);
        } else{
            textView.setText("null");
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
        return crawlInfo.getExternalLinks().length;
    }
}
