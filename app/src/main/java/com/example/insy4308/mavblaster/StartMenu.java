package com.example.insy4308.mavblaster;

import android.app.Activity;
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
import android.widget.TextView;

import com.example.insy4308.mavblaster.mavUtilities.BackgroundSoundService;
import com.example.insy4308.mavblaster.openGLES2.OurGLSurfaceView;
import com.example.insy4308.mavblaster.openGLES2.SkyboxRenderer;
import com.facebook.appevents.AppEventsLogger;

public class StartMenu extends Activity {
    private OurGLSurfaceView glSurfaceView;
    private SkyboxRenderer renderer;
    private Intent departmentSelection = null;
    private Boolean mIsBound = false;
    private BackgroundSoundService bss;
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

            zoomIn = AnimationUtils.loadAnimation(this,R.anim.zoom_in);
            zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

            doBindService();
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

            renderer = new SkyboxRenderer(this, 0);
            glSurfaceView.setRenderer(renderer, displayMetrics.density);

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    departmentSelection = new Intent(StartMenu.this, DepartmentSelection.class);
                    mavLogo.setAnimation(zoomOut);
                    startLogo.setAnimation(zoomOut);
                    glSurfaceView.setParticles(true);
                    handler = new Handler();
                    start.setVisibility(v.GONE);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            startActivity(departmentSelection);
                        }
                    }, 5000);
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
            glSurfaceView.setParticles(false);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    private ServiceConnection Scon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            bss = ((BackgroundSoundService.ServiceBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bss = null;

        }
    };
    void doBindService(){
        bindService(new Intent(this, BackgroundSoundService.class), Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnBindService(){
        if(mIsBound==true){
            unbindService(Scon);
            mIsBound = false;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
        AppEventsLogger.deactivateApp(this);
        glSurfaceView.setParticles(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
        AppEventsLogger.activateApp(this);
        startLogo.setAnimation(zoomIn);
        start.setVisibility(View.VISIBLE);
        glSurfaceView.setParticles(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        doUnBindService();
        // bss.stopMusic();
        bss.onDestroy();
    }*/
}


