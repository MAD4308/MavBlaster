package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.insy4308.mavblaster.openGLES2.StartGLSurfaceView;
import com.example.insy4308.mavblaster.openGLES2.StartRenderer;
import com.facebook.CallbackManager;

public class TopScores extends Activity {

    private StartGLSurfaceView glSurfaceView;
    private StartRenderer renderer;

    private static final String PREFS_NAME = "MyPrefsFile";

    private Intent startMenu = null;

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


        Bundle score = getIntent().getExtras();
        int highScore = score.getInt("score", 0);
        String rankedScores = score.getString("ranked_scores", "None");

        // GL surface setting
        glSurfaceView = (StartGLSurfaceView) findViewById (R.id.start_surface_view);
        glSurfaceView.setEGLContextClientVersion(2);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        renderer = new StartRenderer(this);
        glSurfaceView.setRenderer(renderer, displayMetrics.density);
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
}
