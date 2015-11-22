package com.example.insy4308.mavblaster;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.insy4308.mavblaster.mavUtilities.Categories;
import com.example.insy4308.mavblaster.mavUtilities.Departments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;
import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;
import static com.example.insy4308.mavblaster.mavUtilities.Categories.*;

public class QuizGame extends AppCompatActivity {

    private Intent startMenu = null;
    private Map<String, String> answers = new HashMap<>();
    boolean IsAnswerCorrect = false;
    private String correctAnswer;
    private String selectedQuestion;
    private String[] answerSet = new String[4];
    private long timerScore;
    private long savedSeconds;

    private TextView categoryTitle;
    private TextView questionText;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;
    private Button returnToGame;
    private ProgressBar timerBar;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_game);
        getSupportActionBar().hide();
        final Departments departments = detachDeptFrom(getIntent());
        final Categories categories = detachCatFrom(getIntent());

        categoryTitle = (TextView) findViewById(R.id.categoryTitle);
        categoryTitle.setText(categories.getCategoryName(departments.getDepartmentCode()));

        questionText = (TextView) findViewById(R.id.question);
        buttonA = (Button) findViewById(R.id.A);
        buttonB = (Button) findViewById(R.id.B);
        buttonC = (Button) findViewById(R.id.C);
        buttonD = (Button) findViewById(R.id.D);
        returnToGame = (Button) findViewById(R.id.returnId);
        timerBar = (ProgressBar) findViewById(R.id.timerBar);

        returnToGame.setVisibility(View.GONE);


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
        String answer = answers.get(questionText.getText().toString());
        switch (v.getId()) {
            case R.id.A:
                if (answer.equals(buttonA.getText())) {
                    IsAnswerCorrect = true;
                }
                break;
            case R.id.B:
                if (answer.equals(buttonB.getText())) {
                    IsAnswerCorrect = true;
                }
                break;
            case R.id.C:
                if (answer.equals(buttonC.getText())) {
                    IsAnswerCorrect = true;
                }
                break;
            case R.id.D:
                if (answer.equals(buttonD.getText())) {
                    IsAnswerCorrect = true;
                }
                break;
        }

        String questionAsked = questionText.getText().toString();
        questionText.setTextSize(25);
        questionText.setText(questionAsked + ":\nThe Correct Answer:" + answer);
        timerBar.setVisibility(v.GONE);
        countDownTimer.cancel();

        buttonA.setVisibility(v.GONE);
        buttonB.setVisibility(v.GONE);
        buttonC.setVisibility(v.GONE);
        buttonD.setVisibility(v.GONE);
        returnToGame.setText("Next Question");
        returnToGame.setVisibility(v.VISIBLE);
    }

    public void continueGame(View v) {
        Intent returnResult = getIntent();

        if (!IsAnswerCorrect) {
            returnResult.putExtra("score", 0);
            setResult(RESULT_OK, returnResult);
        } else {
            returnResult.putExtra("score", (int)timerScore);
            setResult(RESULT_OK, returnResult);
        }
        finish();
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
        countDownTimer = new CountDownTimer(seconds*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerBar.setProgress((int)millisUntilFinished);
                timerScore = millisUntilFinished/10;
                savedSeconds = millisUntilFinished/1000;
            }

            public void onFinish() {
                timerBar.setProgress(0);
                timerScore = 0;
                buttonA.setVisibility(View.GONE);
                buttonB.setVisibility(View.GONE);
                buttonC.setVisibility(View.GONE);
                buttonD.setVisibility(View.GONE);
                returnToGame.setText("Next Question");
                returnToGame.setVisibility(View.VISIBLE);
                timerBar.setVisibility(View.GONE);

            }
        }.start();
    }
}