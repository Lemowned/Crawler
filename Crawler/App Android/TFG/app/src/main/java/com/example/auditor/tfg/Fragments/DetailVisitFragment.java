package com.example.auditor.tfg.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.auditor.tfg.Modelos.CrawlInfo;
import com.example.auditor.tfg.R;



public class DetailVisitFragment extends Fragment {

    CrawlInfo crawlInfo = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Se infla el fragment que se muestra en la actividad

        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        // Se implementa el webView con la referencia al fichero alojado en la red anónima.

        WebView webView = (WebView) view.findViewById(R.id.myWebView);

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.myProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        webView.loadUrl(crawlInfo.getLink());

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return view;

    }

    public void setCrawlInfo (CrawlInfo crawlInfo){

        this.crawlInfo = crawlInfo;

    }
}
