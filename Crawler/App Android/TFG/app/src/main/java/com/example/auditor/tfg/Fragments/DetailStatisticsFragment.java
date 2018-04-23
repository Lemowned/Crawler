package com.example.auditor.tfg.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.auditor.tfg.ListAdapters.DetailExternalLinksAdapter;
import com.example.auditor.tfg.ListAdapters.DetailFormCellAdapter;
import com.example.auditor.tfg.ListAdapters.DetailWordCellAdapter;
import com.example.auditor.tfg.Modelos.CrawlInfo;
import com.example.auditor.tfg.R;


public class DetailStatisticsFragment extends Fragment {

    CrawlInfo crawlInfo = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Se infla el fragment que se presenta en la actividad
        View view = inflater.inflate(R.layout.fragment_lists, container, false);


        // Se asocia cada list view del layout con su cell adapter y la información a presentar.

        ListView listViewWords = (ListView) view.findViewById(R.id.detailWordsListView);
        listViewWords.setAdapter(new DetailWordCellAdapter(view.getContext(), crawlInfo));
        ListView listViewForms = (ListView) view.findViewById(R.id.detailFormsListView);
        listViewForms.setAdapter(new DetailFormCellAdapter(view.getContext(), crawlInfo));
        ListView listViewLinks = (ListView) view.findViewById(R.id.detailLinksListView);
        listViewLinks.setAdapter(new DetailExternalLinksAdapter(view.getContext(), crawlInfo));



        return view;

    }

    public void setCrawlInfo (CrawlInfo crawlInfo){

        // Utilizado para la transmisión del objeto crawlinfo desde la clase padre.

        this.crawlInfo = crawlInfo;

    }
}