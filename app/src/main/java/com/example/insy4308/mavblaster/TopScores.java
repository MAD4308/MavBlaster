package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.insy4308.mavblaster.openGLES2.OurGLSurfaceView;
import com.example.insy4308.mavblaster.openGLES2.SkyboxRenderer;
import com.facebook.CallbackManager;

import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;

public class TopScores extends Activity {

    private OurGLSurfaceView glView;
    private SkyboxRenderer renderer;

    private static final String PREFS_NAME = "MyPrefsFile";

    private Intent startMenu = null;
    private Handler handler;

    TextView displayHighScores;
    TextView actualScoreForRound;
    String [] highScores = new String[10];
    String stringOfHighScores = "";

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_scores);
        callbackManager = CallbackManager.Factory.create();
        handler = new Handler();
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        onTrimMemory(TRIM_MEMORY_COMPLETE);

        Bundle score = getIntent().getExtras();
        int highScore = score.getInt("score", 0);
        String rankedScores = score.getString("ranked_scores", "None");

        // GL surface setting
        glView = (OurGLSurfaceView) findViewById (R.id.top_surface_view);
        glView.setEGLContextClientVersion(2);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        if(activityManager.getMemoryClass()>128)
            renderer = new SkyboxRenderer(this, PARTICLES_3, HIGH_RES);
        else
            renderer = new SkyboxRenderer(this, PARTICLES_3, LOW_RES);
        glView.setRenderer(renderer, displayMetrics.density);
        handler.postDelayed(new Runnable() {
            public void run() {
                renderer.setStatus(true);
            }
        }, 1000);
        actualScoreForRound = (TextView) findViewById(R.id.actualScoreForRoundID);
        displayHighScores = (TextView) findViewById(R.id.highScoresId);

        actualScoreForRound.setText(String.valueOf(highScore));
        displayHighScores.setText(rankedScores);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startMenu = new Intent(TopScores.this, StartMenu.class);
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
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}

