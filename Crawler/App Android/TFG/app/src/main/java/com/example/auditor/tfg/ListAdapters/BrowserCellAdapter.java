package com.example.auditor.tfg.ListAdapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.auditor.tfg.Activities.BrowserActivity;
import com.example.auditor.tfg.Listeners.BrowserCellOCL;
import com.example.auditor.tfg.R;
import com.example.auditor.tfg.Utils.OnSwipeTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class BrowserCellAdapter extends BaseAdapter {

    // Adaptador de la celda de la vista de tabla de navegación.

    Context context;
    private static LayoutInflater inflater;
    JSONArray arrayChildren;

    public BrowserCellAdapter(Context context,String json) throws JSONException {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arrayChildren = new JSONArray(json);

    }

    @Override
    public View getView(int i, final View view, ViewGroup viewGroup) {
        View vi = view;
        String status = "N/A";
        String link = "";
        if (vi == null)
            vi = inflater.inflate(R.layout.cell_row_browser, null);

        JSONObject objJSON = null;

        try {

            // Se setean en la celda el nombre del fichero y la imagen de su tipo.

            objJSON = (JSONObject) arrayChildren.get(i);
            BrowserActivity browserActivity = (BrowserActivity) context;
            browserActivity.setParent(objJSON.getString("parent"));
            TextView host = (TextView) vi.findViewById(R.id.browserPath);
            String []lista = objJSON.getString("link").split("/");
            host.setText("/"+lista[lista.length-1]);

            status = objJSON.getString("status");
            link = objJSON.getString("link");

            Log.d("DEBUG",link);

            ImageView imageView = (ImageView) vi.findViewById(R.id.browserIcon);
            if (objJSON.getString("type").equals("dir")){

                vi.setBackgroundColor(Color.rgb(207,216,220));
                imageView.setImageResource(R.drawable.folder);
                vi.setTag(0);

            }else {

                vi.setBackgroundColor(Color.WHITE);
                imageView.setImageResource(R.drawable.file);
                vi.setTag(1);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }




        vi.setOnClickListener(new BrowserCellOCL(context,status,link));
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
        return arrayChildren.length();
    }
}