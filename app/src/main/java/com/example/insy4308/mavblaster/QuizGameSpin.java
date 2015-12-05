package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.insy4308.mavblaster.mavUtilities.Categories;
import com.example.insy4308.mavblaster.mavUtilities.Departments;
import com.example.insy4308.mavblaster.openGLES2.QuizRenderer;
import com.example.insy4308.mavblaster.openGLES2.OurGLSurfaceView;

import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;

public class QuizGameSpin extends Activity {

    OurGLSurfaceView glView;
    private QuizRenderer renderer;

    Button spinButton;
    TextView scoreTextView;
    TextView triesTextView;

    private Intent quizGame = null;
    private Intent finalScore = null;
    private Intent startMenu = null;
    private Departments departments;
    private static final int REQUEST_CODE = 1;
    private int score = 0;
    private int tries = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_game_spin);
        departments= detachDeptFrom(getIntent());

        //setup glsurfaceview
        glView = (OurGLSurfaceView)findViewById(R.id.view);
        glView.setEGLContextClientVersion(2);
        glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        glView.setPreserveEGLContextOnPause(true); // keeps wheel from disappearing

        //setup renderer
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        renderer = new QuizRenderer(this);
        glView.setRenderer(renderer, displayMetrics.density);

        //setup intents
        departments = detachDeptFrom(getIntent());
        startMenu = new Intent(QuizGameSpin.this, StartMenu.class);
        quizGame = new Intent(QuizGameSpin.this, QuizGame.class);
        finalScore = new Intent(QuizGameSpin.this, FinalScore.class);

        //setup buttons, textfields
        spinButton = (Button)findViewById(R.id.spin_button);
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renderer.startspinning();
                while(renderer.getSpinning()); //wait for spin to be done

                Categories.values()[renderer.getCategory()].attachCatTo(quizGame);
                departments.attachDeptTo(quizGame);
                startActivityForResult(quizGame, REQUEST_CODE);
            }
        });

        scoreTextView = (TextView)findViewById(R.id.score);
        scoreTextView.setText("Score: " + score);

        triesTextView = (TextView)findViewById(R.id.tries);
        triesTextView.setText("Questions remaining: " + tries);

        quizGame = new Intent(QuizGameSpin.this, QuizGame.class);
        finalScore = new Intent(QuizGameSpin.this, FinalScore.class);
        startMenu = new Intent(QuizGameSpin.this, StartMenu.class);
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
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        tries--;
        triesTextView.setText("Questions remaining: " + tries);
        if (requestCode == REQUEST_CODE){
            if (resultCode==RESULT_OK){

                int result = data.getIntExtra("score",0);
                score += result;
                scoreTextView.setText("Score: " + score);

                Log.i("Score= ", String.valueOf(score));
                if(tries == 0){
                    departments.attachDeptTo(finalScore);
                    finalScore.putExtra("score",score);
                    startActivity(finalScore);
                }
            }
        }
    }
}