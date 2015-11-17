package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.insy4308.mavblaster.openGLES2.StartGLSurfaceView;
import com.example.insy4308.mavblaster.openGLES2.StartRenderer;

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
        protected void onPause() {
            super.onPause();
                glSurfaceView.onPause();
        }

        @Override
        protected void onResume() {
            super.onResume();
                glSurfaceView.onResume();
        }
}


