package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.insy4308.mavblaster.openGLES2.StartGLSurfaceView;
import com.example.insy4308.mavblaster.openGLES2.StartRenderer;
import com.facebook.appevents.AppEventsLogger;

public class StartMenu extends Activity {
    private StartGLSurfaceView glSurfaceView;
    private StartRenderer renderer;
    Intent departmentSelection = null;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.start_menu);

            Button start = (Button) findViewById(R.id.start);
            glSurfaceView = (StartGLSurfaceView) findViewById (R.id.start_surface_view);

            glSurfaceView.setEGLContextClientVersion(2);

            final DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            renderer = new StartRenderer(this);
            glSurfaceView.setRenderer(renderer, displayMetrics.density);

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    departmentSelection = new Intent(StartMenu.this, DepartmentSelection.class);
                    startActivity(departmentSelection);
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
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

        @Override
        protected void onPause() {
            super.onPause();
                glSurfaceView.onPause();
            AppEventsLogger.deactivateApp(this);
        }

        @Override
        protected void onResume() {
            super.onResume();
                glSurfaceView.onResume();
            AppEventsLogger.activateApp(this);
        }
}


