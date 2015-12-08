package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.insy4308.mavblaster.mavUtilities.BackgroundSoundService;
import com.example.insy4308.mavblaster.openGLES2.OurGLSurfaceView;
import com.example.insy4308.mavblaster.openGLES2.SkyboxRenderer;
import com.facebook.appevents.AppEventsLogger;

import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;

public class StartMenu extends Activity {
    private OurGLSurfaceView glSurfaceView;
    private SkyboxRenderer renderer;
    private Intent departmentSelection = null;
    private Handler handler;
    private Animation zoomOut;
    private Animation zoomIn;
    private ImageView startLogo;
    private Button start;
    private ImageView mavLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        zoomIn = AnimationUtils.loadAnimation(this,R.anim.zoom_in);
        zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

        handler = new Handler();

        final Intent music = new Intent(this,BackgroundSoundService.class);
        startService(music);

        start = (Button) findViewById(R.id.start);

        startLogo = (ImageView) findViewById(R.id.startLogo);
        mavLogo = (ImageView) findViewById(R.id.mav_blaster_logo);
        mavLogo.setAnimation(zoomIn);
        startLogo.setAnimation(zoomIn);

        glSurfaceView = (OurGLSurfaceView) findViewById (R.id.start_surface_view);

        glSurfaceView.setEGLContextClientVersion(2);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        if(activityManager.getMemoryClass()>128)
            renderer = new SkyboxRenderer(this, PARTICLES_4, HIGH_RES);
        else
            renderer = new SkyboxRenderer(this, PARTICLES_4, LOW_RES);
        glSurfaceView.setRenderer(renderer, displayMetrics.density);

        handler.postDelayed(new Runnable() {
            public void run() {
                renderer.setStatus(true);
            }
        },100);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departmentSelection = new Intent(StartMenu.this, DepartmentSelection.class);
                mavLogo.setAnimation(zoomOut);
                startLogo.setAnimation(zoomOut);
                start.setVisibility(v.GONE);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        startActivity(departmentSelection);
                    }
                }, 3000);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            renderer.setStatus(false);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
        AppEventsLogger.deactivateApp(this);
        renderer.setStatus(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
        AppEventsLogger.activateApp(this);
        startLogo.setAnimation(zoomIn);
        start.setVisibility(View.VISIBLE);
        renderer.setStatus(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent music = new Intent(this,BackgroundSoundService.class);
        stopService(music);
    }
}