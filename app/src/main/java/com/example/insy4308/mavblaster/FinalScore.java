package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;

import com.example.insy4308.mavblaster.mavUtilities.Departments;
import com.example.insy4308.mavblaster.mavUtilities.Scores;
import com.example.insy4308.mavblaster.openGLES2.OurGLSurfaceView;
import com.example.insy4308.mavblaster.openGLES2.SkyboxRenderer;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;

public class FinalScore extends Activity {
    private OurGLSurfaceView glSurfaceView;
    private SkyboxRenderer renderer;

    private Handler handler;

    Button replayButton;
    Button orangeShareButton;
    Button topScoresButton;

    CallbackManager callbackManager;
    private Intent startMenu = null;

    Intent departmentSelection = null;
    Intent share = null;
    Intent topScores = null;

    private int sendHighScore = 0;
    private final Context context = this;
    String [] highScores = new String[10];
    String stringOfHighScores = "";
    String rankedScores = "";
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final int TEXT_ID = 0;
    Typeface spaceFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_score);
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        final Departments departments = detachDeptFrom(getIntent());
        Bundle score = getIntent().getExtras();
        int highScore = score.getInt("score", 0);

        handler = new Handler();

        onTrimMemory(TRIM_MEMORY_RUNNING_LOW);

        // GL surface setting

        glSurfaceView = (OurGLSurfaceView) findViewById(R.id.final_surface_view);
        glSurfaceView.setEGLContextClientVersion(2);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        spaceFont = Typeface.createFromAsset(getAssets(), "fonts/dangerflightlaser.ttf");
        if(activityManager.getMemoryClass()>128)
            renderer = new SkyboxRenderer(this, PARTICLES_2, HIGH_RES);
        else
            renderer = new SkyboxRenderer(this, PARTICLES_2, LOW_RES);
        glSurfaceView.setRenderer(renderer, displayMetrics.density);

        TextView scoreDisplay = (TextView) findViewById(R.id.score);
        scoreDisplay.setTypeface(spaceFont);
        scoreDisplay.setText(String.valueOf(highScore));

        callbackManager = CallbackManager.Factory.create();
        askForNameDialog(savedInstanceState, highScore);

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

        topScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                topScores = new Intent(FinalScore.this, TopScores.class);
                departments.attachDeptTo(topScores);
                topScores.putExtra("score", sendHighScore);
                topScores.putExtra("ranked_scores", rankedScores);
                startActivity(topScores);
            }
        });
        handler.postDelayed(new Runnable() {
            public void run() {
                renderer.setStatus(true);
            }
        }, 1000);
    }

    public String askForNameDialog(Bundle savedInstanceState, int highScore)
    {
        Log.d("Dialog", "In dialog");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FinalScore.this);

        final EditText input = new EditText(this);
        input.setId(TEXT_ID);

        alertDialogBuilder.setTitle("Thanks for playing!");

        alertDialogBuilder.setView(input);
        final String[] returnName = {""};
        final Bundle[] s = {savedInstanceState};
        final int[] returnScore = {highScore};
        alertDialogBuilder
                .setMessage("Enter a username!")
                .setPositiveButton("Enter",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                String retrieveName = input.getText().toString();
                                if(input.getText().toString().isEmpty())
                                    retrieveName = "Anon";
                                returnName[0] = retrieveName;
                                saveTopScore(s[0], returnScore[0], returnName[0]);
                                return;
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();

        return returnName[0];
    }

    public void saveTopScore(Bundle savedInstanceState, int userScore, String name)
    {
        if (savedInstanceState != null)
        {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
            rankedScores = prefs.getString("highScores", "not found");
            highScores = rankedScores.split("\\|");

            rankedScores = String.format("%s%8s%8s\n","RNK","SCORE","NAME");
            int rankCount = 1;
            for (int i = highScores.length - 1; i >= 0; i--) {
                String [] info = highScores[i].split(";");

                if(rankCount==10)
                    rankedScores += String.format("%d%11s%13s\n",rankCount, info[0], info[1]);
                else
                    rankedScores += String.format("%3d%11s%11s\n",rankCount, info[0], info[1]);
                rankCount++;
            }
        }
        else {

            SharedPreferences myPrefs = getSharedPreferences(PREFS_NAME, 0); // 0 makes it private
            SharedPreferences.Editor editor = myPrefs.edit();

            stringOfHighScores = getHighScores(myPrefs, userScore, name);
            Log.d("Stringofhighscores", stringOfHighScores);
            editor.putString("highScores", stringOfHighScores);
            editor.commit();

            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
            rankedScores = prefs.getString("highScores", "not found");
            highScores = rankedScores.split("\\|");

            int rankCount = 1;
            rankedScores = String.format("%s%8s%8s\n","RNK","SCORE","NAME");
            for (int i = highScores.length - 1; i >= 0; i--) {
                String [] info = highScores[i].split(";");

                if(rankCount==10)
                    rankedScores += String.format("%d%11s%11s\n",rankCount, info[0], info[1]);
                else
                    rankedScores += String.format("%3d%11s%11s\n",rankCount, info[0], info[1]);
                rankCount++;
            }
        }
    }

    public String getHighScores(SharedPreferences prefs, int userScore, String name)
    {
        if(prefs.contains("highScores"))
        {
            String [] arrayScoresInfo;
            String scoresHistory = prefs.getString("highScores", "sorry not found");
            arrayScoresInfo = scoresHistory.split("\\|");
            ArrayList<Scores> scoreInfo = new ArrayList<Scores>(arrayScoresInfo.length);
            String returnScores = "";

            for (int i = 0; i < arrayScoresInfo.length; i++)
            {
                scoreInfo.add(new Scores(arrayScoresInfo[i]));
            }
            scoreInfo = rankUserScores(scoreInfo, userScore, name);


            for (Scores s : scoreInfo)
            {
                returnScores += s.getScore() + ";" + s.getName() + "|";
            }

            return returnScores;
        }
        else
        {
            return String.valueOf(userScore + ";" + name + "|");
        }
    }

    public ArrayList<Scores> rankUserScores(ArrayList<Scores> scoreInfo, int userScore, String name)
    {
        // Sort the array list entries
        Collections.sort(scoreInfo, new Scores());

        for (Scores scores : scoreInfo)
        {
            Log.d("in rank", scores.getName() + scores.getScore());
            if(scoreInfo.size() < 10) {
                scoreInfo.add(new Scores(userScore, name));
                break;
            }
            else  if (scores.getScore() < userScore) {
                scoreInfo.remove(0);
                scoreInfo.add(new Scores(userScore, name));
                break;
            }
        }
        Collections.sort(scoreInfo, new Scores());
        return scoreInfo;
    }

    public String [] rankTopScores(String [] topScoresArray, int highScore, String [] scoreStorage)
    {
        String [][] scoreAndName = new String [10][2];
        final Comparator<String[]> arrayComparator = new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return o1[0].compareTo(o2[0]);
            }
        };

        Log.d("length = ", String.valueOf(scoreStorage.length));
        Log.d("length = ", String.valueOf(topScoresArray.length));

        for (int i = 0; i < topScoresArray.length; i++)
        {
            scoreStorage[i] = topScoresArray[i];
        }
        Arrays.sort(scoreStorage, Collections.reverseOrder());
        for (int i = 0; i < scoreStorage.length; i++)
        {
            if (Integer.parseInt(scoreStorage[i]) < highScore)
            {
                //String name = askForNameDialog(); // needs to return a string, and only take the first 3 characters and capitalize them
                //Log.d("alert name ",name);
                // Alert dialog here, save string name and add it to the String.valueOf(highScore) entry
                //  + "\t\t\t\t" + name.toUpperCase()
                scoreStorage[scoreStorage.length-1] = String.valueOf(highScore);
            }
        }

        // Name can't be sorted here! doh!
        Arrays.sort(scoreStorage, Collections.reverseOrder());

        return scoreStorage;
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
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}