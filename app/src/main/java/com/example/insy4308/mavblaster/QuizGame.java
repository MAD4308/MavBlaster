package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.insy4308.mavblaster.mavUtilities.Categories;
import com.example.insy4308.mavblaster.mavUtilities.Departments;
import com.example.insy4308.mavblaster.openGLES2.OurGLSurfaceView;
import com.example.insy4308.mavblaster.openGLES2.SkyboxRenderer;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;
import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;
import static com.example.insy4308.mavblaster.mavUtilities.Categories.*;

public class QuizGame extends Activity {

    private OurGLSurfaceView glSurfaceView;
    private SkyboxRenderer renderer;
    private Handler handler;
    private Intent returnResult;

    private Intent startMenu = null;
    private Map<String, String> answers = new HashMap<>();
    private boolean IsAnswerCorrect = false;
    private String correctAnswer;
    private String selectedQuestion;
    private String[] answerSet = new String[4];
    private long timerScore;
    private long savedSeconds;

    private ImageView imageView;
    private TextView categoryTitle;
    private TextView questionText;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;
    private ProgressBar timerBar;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_game);

        glSurfaceView = (OurGLSurfaceView) findViewById (R.id.quiz_game_surface_view);

        glSurfaceView.setEGLContextClientVersion(2);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        renderer = new SkyboxRenderer(this,PARTICLES_1);
        glSurfaceView.setRenderer(renderer, displayMetrics.density);

        final Departments departments = detachDeptFrom(getIntent());
        final Categories categories = detachCatFrom(getIntent());

        categoryTitle = (TextView) findViewById(R.id.categoryTitle);
        categoryTitle.setText(categories.getCategoryName(departments.getDepartmentCode()));

        questionText = (TextView) findViewById(R.id.question);
        buttonA = (Button) findViewById(R.id.A);
        buttonB = (Button) findViewById(R.id.B);
        buttonC = (Button) findViewById(R.id.C);
        buttonD = (Button) findViewById(R.id.D);
        timerBar = (ProgressBar) findViewById(R.id.timerBar);

        if(savedInstanceState !=null){
            questionText.setText(savedInstanceState.getString("question"));
            buttonA.setText(savedInstanceState.getString("button_a"));
            buttonB.setText(savedInstanceState.getString("button_b"));
            buttonC.setText(savedInstanceState.getString("button_c"));
            buttonD.setText(savedInstanceState.getString("button_d"));

            selectedQuestion = savedInstanceState.getString("question");
            answerSet[0] = savedInstanceState.getString("button_a");
            answerSet[1] = savedInstanceState.getString("button_b");
            answerSet[2] = savedInstanceState.getString("button_c");
            answerSet[3] = savedInstanceState.getString("button_d");

            setCountDownTimer(savedInstanceState.getInt("seconds"));

        }
        else {
            questionText.setVisibility(View.GONE);
            buttonA.setVisibility(View.GONE);
            buttonB.setVisibility(View.GONE);
            buttonC.setVisibility(View.GONE);
            buttonD.setVisibility(View.GONE);
            timerBar.setVisibility(View.GONE);
            JsonObjectRequest(QUIZ_URL_START + departments.getDepartmentUrl(categories.getCategoryCode()) + QUIZ_URL_END);
        }
        startMenu = new Intent(QuizGame.this, StartMenu.class);

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("question", selectedQuestion);

        outState.putString("button_a", answerSet[0]);
        outState.putString("button_b", answerSet[1]);
        outState.putString("button_c", answerSet[2]);
        outState.putString("button_d", answerSet[3]);


        outState.putInt("seconds", (int)savedSeconds);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(startMenu);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void userSubmission(View v) {
        questionText = (TextView) findViewById(R.id.question);
        imageView = (ImageView) findViewById(R.id.imageView2);
        String answer = answers.get(questionText.getText().toString());

        switch (v.getId()) {
            case R.id.A:
                if (answer.equals(buttonA.getText())) {
                    IsAnswerCorrect = true;
                    renderer.setStatus(true);
                }
                break;
            case R.id.B:
                if (answer.equals(buttonB.getText())) {
                    IsAnswerCorrect = true;
                    renderer.setStatus(true);
                }
                break;
            case R.id.C:
                if (answer.equals(buttonC.getText())) {
                    IsAnswerCorrect = true;
                    renderer.setStatus(true);
                }
                break;
            case R.id.D:
                if (answer.equals(buttonD.getText())) {
                    IsAnswerCorrect = true;
                    renderer.setStatus(true);
                }
                break;
        }

        questionText.setVisibility(v.GONE);
        timerBar.setVisibility(v.GONE);
        countDownTimer.cancel();

        buttonA.setVisibility(v.GONE);
        buttonB.setVisibility(v.GONE);
        buttonC.setVisibility(v.GONE);
        buttonD.setVisibility(v.GONE);
        continueGame();
    }

    public void continueGame() {
        returnResult = getIntent();

        if (!IsAnswerCorrect) {
            imageView.setBackgroundResource(R.drawable.incorrect);
            returnResult.putExtra("score", 0);
            setResult(RESULT_OK, returnResult);
        } else {
            imageView.setBackgroundResource(R.drawable.correct);
            returnResult.putExtra("score", (int)timerScore);
            setResult(RESULT_OK, returnResult);
        }
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 3000);
    }

    public void JsonObjectRequest(String url) {
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray terms = response.getJSONArray("terms");
                    String[] randomAnswers = compileRandomAnswers(terms);
                    String[] questionKey = compileQuestionKey(terms);
                    String question;
                    String answer;
                    Random rand = new Random();

                    for (int j = 0; j < terms.length(); j++) {
                        JSONObject term = terms.getJSONObject(j);
                        if (term.getString("term") == null || term.getString("definition") == null) {
                            throw new JSONException("No terms or definitions");
                        }

                        question = term.getString("definition");
                        answer = term.getString("term");

                        if (!answers.containsKey(question) && (question != "" || question != null)) {
                            answers.put(question, answer);
                        }
                    }
                    setQuestionAnswerSet(rand, questionKey, randomAnswers);
                    setCountDownTimer(20);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                VolleyLog.d("quiz game", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    public void setQuestionAnswerSet(Random rand, String[] questionKey, String[] randomAnswers) {
        int randomizeCorrectAnswer = rand.nextInt(4);
        selectedQuestion = "";
        boolean flag = true;
        while (flag) {
            flag = false;
            selectedQuestion = questionKey[rand.nextInt(questionKey.length)];
            if (answers.containsKey(selectedQuestion) && selectedQuestion != null) {
                correctAnswer = answers.get(selectedQuestion);
            } else {
                flag = true;
            }
        }

        answerSet = new String[4];
        answerSet[3] = correctAnswer;
        for (int x = 0; x < answerSet.length - 1; x++) {
            String tmp = "";
            flag = true;
            while (flag) {
                flag = false;
                tmp = randomAnswers[rand.nextInt(randomAnswers.length)];
                if (tmp == correctAnswer) {
                    flag = true;
                }
            }
            answerSet[x] = tmp;
        }
        String temp = answerSet[randomizeCorrectAnswer];
        answerSet[randomizeCorrectAnswer] = answerSet[3];
        answerSet[3] = temp;

        questionText.setText(selectedQuestion);
        buttonA.setText(answerSet[0]);
        buttonB.setText(answerSet[1]);
        buttonC.setText(answerSet[2]);
        buttonD.setText(answerSet[3]);

        questionText.setVisibility(View.VISIBLE);
        buttonA.setVisibility(View.VISIBLE);
        buttonB.setVisibility(View.VISIBLE);
        buttonC.setVisibility(View.VISIBLE);
        buttonD.setVisibility(View.VISIBLE);
        timerBar.setVisibility(View.VISIBLE);
    }

    public String[] compileQuestionKey(JSONArray terms) {
        try {
            String[] questionKey = new String[terms.length()];
            for (int k = 0; k < terms.length(); k++) {
                JSONObject term1 = terms.getJSONObject(k);
                questionKey[k] = term1.getString("definition");
            }
            return questionKey;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] compileRandomAnswers(JSONArray terms) {
        try {
            String[] randomAnswers = new String[terms.length()];
            for (int k = 0; k < terms.length(); k++) {
                JSONObject term1 = terms.getJSONObject(k);
                randomAnswers[k] = term1.getString("term");
            }
            return randomAnswers;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCountDownTimer(int seconds)
    {
        handler = new Handler();
        imageView = (ImageView) findViewById(R.id.imageView2);
        returnResult = getIntent();
        countDownTimer = new CountDownTimer(seconds*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerBar.setProgress((int)millisUntilFinished);
                timerScore = millisUntilFinished/10;
                savedSeconds = millisUntilFinished/1000;
            }
            public void onFinish() {
                timerBar.setProgress(0);
                timerScore = 0;
                questionText.setVisibility(View.GONE);
                buttonA.setVisibility(View.GONE);
                buttonB.setVisibility(View.GONE);
                buttonC.setVisibility(View.GONE);
                buttonD.setVisibility(View.GONE);
                timerBar.setVisibility(View.GONE);
                imageView.setBackgroundResource(R.drawable.times_up);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        returnResult.putExtra("score", 0);
                        setResult(RESULT_OK, returnResult);
                        finish();
                    }
                }, 3000);
            }
        }.start();
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