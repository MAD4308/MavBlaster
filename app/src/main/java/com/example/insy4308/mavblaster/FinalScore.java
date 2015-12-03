package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.insy4308.mavblaster.mavUtilities.Departments;
import com.example.insy4308.mavblaster.openGLES2.OurGLSurfaceView;
import com.example.insy4308.mavblaster.openGLES2.SkyboxRenderer;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;

public class FinalScore extends Activity {
    private OurGLSurfaceView glSurfaceView;
    private SkyboxRenderer renderer;

    Button replayButton;
    Button orangeShareButton;
    Button topScoresButton;

    CallbackManager callbackManager;
    private Intent startMenu = null;

    Intent departmentSelection = null;
    Intent share = null;

    private int sendHighScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_score);
        final Departments departments = detachDeptFrom(getIntent());
        Bundle score = getIntent().getExtras();
        int highScore = score.getInt("score", 0);

        // GL surface setting
        glSurfaceView = (OurGLSurfaceView) findViewById (R.id.final_surface_view);
        glSurfaceView.setEGLContextClientVersion(2);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        renderer = new SkyboxRenderer(this,0);
        glSurfaceView.setRenderer(renderer, displayMetrics.density);
        TextView scoreDisplay = (TextView) findViewById(R.id.score);
        scoreDisplay.setText(String.valueOf(highScore));

        callbackManager = CallbackManager.Factory.create();

        replayButton = (Button) findViewById(R.id.replayId);
        orangeShareButton = (Button) findViewById(R.id.shareId);
        topScoresButton = (Button) findViewById(R.id.topScoresId);

        startMenu = new Intent(FinalScore.this, StartMenu.class);

        sendHighScore = highScore;
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departmentSelection = new Intent(FinalScore.this, DepartmentSelection.class);
                startActivity(departmentSelection);
            }
        });

        orangeShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share = new Intent(FinalScore.this, Share.class);
                departments.attachDeptTo(share);
                share.putExtra("score", sendHighScore);
                startActivity(share);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(startMenu);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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