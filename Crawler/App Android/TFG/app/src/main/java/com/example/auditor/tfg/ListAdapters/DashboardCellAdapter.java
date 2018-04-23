package com.example.auditor.tfg.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auditor.tfg.Listeners.DashboardCellOCL;
import com.example.auditor.tfg.Listeners.DashboardRunOCL;
import com.example.auditor.tfg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class DashboardCellAdapter extends BaseAdapter {

    // Adaptador de la celda de la vista de tabla de cuadro de control.

    Context context;
    private static LayoutInflater inflater;
    JSONArray arrayScans;

    public DashboardCellAdapter(Context context,String json) throws JSONException {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arrayScans = new JSONArray(json);

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (vi == null)
            vi = inflater.inflate(R.layout.cell_row_dashboard, null);

        JSONObject objJSON = null;

        try {
            objJSON = (JSONObject) arrayScans.get(i);
            TextView host = (TextView) vi.findViewById(R.id.host_dashboard);
            host.setText(objJSON.getString("host"));
            TextView crawlId = (TextView) vi.findViewById(R.id.crawlId_dashboard);
            crawlId.setText(objJSON.getString("crawlId"));

            String status = objJSON.getString("status");

            /*
            Se setea en la celda:
                - Tipo de red anónima
                - Botones play, pause y stop en cada caso
                - Host escaneado
                - ID del crawler
             */

            ImageView statusIV = (ImageView) vi.findViewById(R.id.status_dashboard);
            ImageButton playBTN = (ImageButton) vi.findViewById(R.id.play_dashboard);
            ImageButton stopBTN = (ImageButton) vi.findViewById(R.id.stop_dashboard);
            ImageView image_type = (ImageView) vi.findViewById(R.id.image_type_scan);


            image_type.setImageResource(R.drawable.scan3);


            // Se configura el estado de los botones en factor del estado de ejecuciónd el crawler.


            switch (status) {
                case "pendant":
                    statusIV.setImageResource(R.drawable.pendant);
                    playBTN.setImageResource(R.drawable.play);
                    playBTN.setOnClickListener(new DashboardRunOCL(context,crawlId.getText().toString(),"play"));
                    stopBTN.setImageResource(R.drawable.stop_disabled);
                    break;
                case "running":
                    statusIV.setImageResource(R.drawable.running);
                    playBTN.setImageResource(R.drawable.pausa);
                    playBTN.setOnClickListener(new DashboardRunOCL(context,crawlId.getText().toString(),"pause"));
                    stopBTN.setImageResource(R.drawable.stop);
                    stopBTN.setOnClickListener(new DashboardRunOCL(context,crawlId.getText().toString(),"stop"));
                    break;
                case "paused":
                    statusIV.setImageResource(R.drawable.paused);
                    playBTN.setImageResource(R.drawable.play);
                    playBTN.setOnClickListener(new DashboardRunOCL(context,crawlId.getText().toString(),"resume"));
                    stopBTN.setImageResource(R.drawable.stop);
                    stopBTN.setOnClickListener(new DashboardRunOCL(context,crawlId.getText().toString(),"stop"));
                    break;
                case "stopped":
                    statusIV.setImageResource(R.drawable.stopped);
                    playBTN.setImageResource(R.drawable.play_disabled);
                    stopBTN.setImageResource(R.drawable.stop_disabled);
                    break;
                case "finished":
                    statusIV.setImageResource(R.drawable.finished);
                    playBTN.setImageResource(R.drawable.play_disabled);
                    stopBTN.setImageResource(R.drawable.stop_disabled);
                    break;

            }

            playBTN.setVisibility(View.VISIBLE);
            stopBTN.setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        vi.setOnClickListener(new DashboardCellOCL(context));


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
        return arrayScans.length();
    }
}