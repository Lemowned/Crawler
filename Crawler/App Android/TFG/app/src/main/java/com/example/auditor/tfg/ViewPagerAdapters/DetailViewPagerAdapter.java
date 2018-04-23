package com.example.auditor.tfg.ViewPagerAdapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.auditor.tfg.Activities.DetailActivity;
import com.example.auditor.tfg.Fragments.DetailGraphicsFragment;
import com.example.auditor.tfg.Fragments.DetailStatisticsFragment;
import com.example.auditor.tfg.Fragments.DetailVisitFragment;
import com.example.auditor.tfg.Modelos.CrawlInfo;


public class DetailViewPagerAdapter extends FragmentStatePagerAdapter {

    /*
    Clase view pager que permite la transición entre las 3 actividades de muestra de información al usuario.
     */

    CrawlInfo crawlInfo;


    public DetailViewPagerAdapter(FragmentManager fm, CrawlInfo crawlInfo) {

        super(fm);
        this.crawlInfo = crawlInfo;
    }

    @Override
    public Fragment getItem(int position) {

        // Infla un fragment en función de la posición del view pager que se analice.

        Fragment fragment = null;

        switch (position) {

            case 0:
                DetailStatisticsFragment fragment1 = new DetailStatisticsFragment();
                fragment1.setCrawlInfo(crawlInfo);
                fragment = fragment1;
                break;
            case 1:
                DetailGraphicsFragment fragmentGraphic = new DetailGraphicsFragment();
                fragmentGraphic.setCrawlInfo(crawlInfo);
                fragment = fragmentGraphic;
                break;
            case 2:
                DetailVisitFragment fragmentVisit = new DetailVisitFragment();
                fragmentVisit.setCrawlInfo(crawlInfo);
                fragment = fragmentVisit;
                break;

        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        // Devuelve el título de cada una de las páginas que se genera.

        switch (position){
            case 0:
                return "Statistics";
            case 1:
                return "Graphics";
            default:
                return "Visit";
        }
    }
}
