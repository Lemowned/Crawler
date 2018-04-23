package com.example.auditor.tfg.Listeners;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.auditor.tfg.Activities.DashboardActivity;
import com.example.auditor.tfg.R;
import com.example.auditor.tfg.Utils.Host;
import com.example.auditor.tfg.UtilsREQUEST.DashboardRunRequest;


public class DashboardRunOCL implements View.OnClickListener {

    // Listener para cada uno de los botones que controlan la ejecución de las instancias del crawler (play, pause, resume, stop)

    Context context;
    String crawlId;
    String accion;

    public DashboardRunOCL(Context context, String crawlId, String accion) {

        this.context = context;
        this.crawlId = crawlId;
        this.accion = accion;
    }

    @Override
    public void onClick(View view) {
        DashboardRunRequest dashboardRunRequest = new DashboardRunRequest(context);

        Log.e("MENSAJE", "Llamada a request " + accion);

        Activity activity = (Activity) context;
        Animation slideUpIn = AnimationUtils.loadAnimation(context, R.anim.scale_button);
        view.startAnimation(slideUpIn);
        view.setVisibility(View.INVISIBLE);

        // Se lleva a cabo una llamada diferente al servidor para cada acción.


        switch (accion){
            case "play":
                dashboardRunRequest.execute(Host.host+"/init/"+crawlId);
                break;
            case "pause":
                dashboardRunRequest.execute(Host.host+"/pause/"+crawlId);
                break;
            case "stop":
                dashboardRunRequest.execute(Host.host+"/stop/"+crawlId);
                break;
            case "resume":
                dashboardRunRequest.execute(Host.host+"/resume/"+crawlId);
                break;
        }

    }

}
