package com.example.auditor.tfg.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.auditor.tfg.Modelos.CrawlInfo;
import com.example.auditor.tfg.Modelos.WordModel;
import com.example.auditor.tfg.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.List;


public class DetailGraphicsFragment extends Fragment {

    // MARK: - Variables globales

    CrawlInfo crawlInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Se generan los gráficos de la actividad de muestra de información

        // Se infla el fragmen que se muestra en la actividad

        View view = inflater.inflate(R.layout.fragment_graphics, container, false);

        WordModel[] wordModel = crawlInfo.getWordlist();
        GraphView graphView = (GraphView) view.findViewById(R.id.detailWordsGraphic);
        String [] labels = new String[wordModel.length];


        // Se alimenta el gráfico con la información a representar.

        DataPoint [] points = new DataPoint[crawlInfo.getWordlist().length];
        int maximo = 0;

        for (int i = 0; i < wordModel.length ; i++) {

            WordModel model = wordModel[i];
            int numero = Integer.parseInt(model.getNum());
            points[i] = new DataPoint(i, numero);
            if (maximo < numero){
                maximo = numero;
            }
            labels[i] = wordModel[i].getWord();

        }

        // Se configura el aspecto del gráfico.

        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(points);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(30);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                int pos = (int) dataPoint.getX();
                Toast toast = Toast.makeText(getActivity(), "Word: "+crawlInfo.getWordlist()[pos].getWord(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });


        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"","", "", "", ""});
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(maximo+1);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(-1);
        graphView.getViewport().setMaxX(wordModel.length);
        graphView.setTitle("Word count");
        graphView.setTitleColor(R.color.BG_Grey);

        graphView.addSeries(series);


        setGraphLinks(view, crawlInfo);

        return view;



    }

    public void setCrawlInfo (CrawlInfo crawlInfo){

        this.crawlInfo = crawlInfo;

    }


    public void setGraphLinks(View view, CrawlInfo crawlInfo){

        // Se genera el gráfico de muestra de información relativo a los enlaces internos vs externos

        String[] internalLinks = crawlInfo.getInternalLinks();
        int numInt = internalLinks.length;
        String[] externalLinks = crawlInfo.getExternalLinks();
        int numExt = externalLinks.length;

        GraphView graphView = (GraphView) view.findViewById(R.id.detailLinksGraphic);

        // Se alimenta el gráfico con la información a representar

        DataPoint [] points = new DataPoint[2];

        points[0] = new DataPoint(1, numInt);
        points[1] = new DataPoint(2, numExt);

        // Se configuran los aspectos gráficos de la representación

        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(points);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                int pos = (int) dataPoint.getX();
                if (pos == 2){
                    Toast.makeText(getActivity(),"External Links: "+(int)dataPoint.getY(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Internal Links: "+(int)dataPoint.getY(), Toast.LENGTH_SHORT).show();
                }


            }
        });

        series.setSpacing(50);


        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"",""});
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.setTitle("External/Internal links");
        graphView.setTitleColor(R.color.BG_Grey);

        if (numInt > numExt){
            graphView.getViewport().setMaxY(numInt+1);
        }else {
            graphView.getViewport().setMaxY(numExt+1);
        }

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(3);

        graphView.addSeries(series);


    }
}