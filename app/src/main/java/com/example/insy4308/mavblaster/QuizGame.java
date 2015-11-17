package com.example.insy4308.mavblaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
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


    private Map<String, String> answers = new HashMap<String, String>();
    private Intent finalScore = null;
    boolean IsAnswerCorrect = false;
    private String correctAnswer;
    private String selectedQuestion;
    /*
    TextView aText = (TextView) findViewById(R.id.aText);
    TextView bText = (TextView) findViewById(R.id.bText);
    TextView cText = (TextView) findViewById(R.id.cText);
    TextView dText = (TextView) findViewById(R.id.dText);
    */
    TextView categoryTitle;
    TextView questionText;
    Button buttonA;
    Button buttonB;
    Button buttonC;
    Button buttonD;
    Button returnToGame;

    // Need to save state of game when orientation changes, or app is interrupted
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_game);
        getSupportActionBar().hide();
        final Departments departments = detachDeptFrom(getIntent());
        final Categories categories = detachCatFrom(getIntent());
        // Button buttonTest = (Button) findViewById(R.id.buttonTest);

        categoryTitle = (TextView) findViewById(R.id.categoryTitle);
        questionText = (TextView) findViewById(R.id.question);
        buttonA = (Button) findViewById(R.id.A);
        buttonB = (Button) findViewById(R.id.B);
        buttonC = (Button) findViewById(R.id.C);
        buttonD = (Button) findViewById(R.id.D);
        returnToGame = (Button) findViewById(R.id.returnId);

        returnToGame.setVisibility(View.GONE);
        JsonObjectRequest(QUIZ_URL_START + departments.getDepartmentUrl(categories.getCategoryCode()) + QUIZ_URL_END);
        // finalScore = new Intent(QuizGame.this, FinalScore.class);

        // We will not use this, but go to DepartmentSelection.java to add
        // 1. A loop to keep track of questions asked (20 max or whatever)
        // 2. After loop, send final score with given code below...activity
       /* buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departments.attachDeptTo(finalScore);
                categories.attachCatTo(finalScore);
                startActivity(finalScore);
            }
        });
        */
    }

    // User button
    public void userSubmission(View v)
    {
        //questionText = (TextView) findViewById(R.id.question);
        /* SOMETHING BELOW HERE IS NOT WORKING CORRECTLY.............
        String answer = answers.get(questionText.getText().toString());
        //Log.d("question", questionText.getText().toString());
        //Log.d("cAnswer = ", answer);
        switch(v.getId())
        {
            case R.id.A:
               //Log.d("QuestionText: ", answer + "\nButton text: " + buttonA.getText().toString());
                if (answer.contains(buttonA.getText()))
                {
                    IsAnswerCorrect = true;
                }
                break;
            case R.id.B:
                //Log.d("Correct Answer: ", answer + "\nButton text: " + buttonB.getText());
                if (answer.contains(buttonB.getText()))
                {
                    IsAnswerCorrect = true;
                }
                break;
            case R.id.C:
                //Log.d("Correct Answer: ", answer + "\nButton text: " + buttonC.getText());
                if (answer.contains(buttonC.getText().toString()))
                {
                    IsAnswerCorrect = true;
                }
                break;
            case R.id.D:
                //Log.d("Correct Answer: ", answer + "\nButton text: " + buttonD.getText());
                if (answer.contains(buttonD.getText()))
                {
                    IsAnswerCorrect = true;
                }
                break;
        }

        String questionAsked = questionText.getText().toString();
        questionText.setTextSize(10);
        questionText.setText(questionAsked + ":\n" + answer);

*/
        buttonA.setVisibility(v.GONE);
        buttonB.setVisibility(v.GONE);
        buttonC.setVisibility(v.GONE);
        buttonD.setVisibility(v.GONE);
        returnToGame.setVisibility(v.VISIBLE);
        //Log.d("Before user sub ter: ", String.valueOf(IsAnswerCorrect));
    }

    // Continue game (returns to QuizGameSpin)
    public void continueGame(View v)
    {
        Intent returnResult = getIntent();

        Log.d("Correct: ", String.valueOf(IsAnswerCorrect));
        if (!IsAnswerCorrect) {
            // If incorrect
            returnResult.putExtra("score", 0);
            setResult(RESULT_OK, returnResult);
        }
        else {
            // If correct
            returnResult.putExtra("score", 1);
            setResult(RESULT_OK, returnResult);
        }
        finish(); // tell it to finish and shut down
    }

    public void JsonObjectRequest(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject response) {
                try {
                    JSONArray terms = response.getJSONArray("terms");
                    String [] randomAnswers = compileRandomAnswers(terms);
                    String [] questionKey = compileQuestionKey(terms);
                    String question = "";
                    String answer = "";
                    Random rand = new Random();

                    for (int j = 0; (j < terms.length()) && !(j > 10); j++){
                        JSONObject term = terms.getJSONObject(j);
                        if (term.getString("term") == null || term.getString("definition") == null)
                        {
                            continue;
                        }

                        question = term.getString("term");
                        answer = term.getString("definition");

                        // Ensure no duplication of keys
                        if(!answers.containsKey(question) && (question != "" || question != null))
                        {
                            answers.put(question, answer); // Add to Map
                        }
                        //Log.v("Q: ", question + "?\nA) " + answer + "\nB) " + randomAnswers[rand.nextInt(terms.length())] + "\nC) " +
                                //randomAnswers[rand.nextInt(terms.length())] + "\nD) " + randomAnswers[rand.nextInt(terms.length())]);
                    }

                    // Method to set the question and answer set texts
                    setQuestionAnswerSet(rand, questionKey, randomAnswers);

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

    public void setQuestionAnswerSet(Random rand, String[] questionKey, String[] randomAnswers)
    {
        int randomizeCorrectAnswer = rand.nextInt(4);
        selectedQuestion = "";
        // correctAnswer = "";
        // Log.d("[1] Selected Question =", selectedQuestion);
        boolean flag = true;
        while(flag)
        {
            flag = false;
            selectedQuestion = questionKey[rand.nextInt(questionKey.length)];
            if (answers.containsKey(selectedQuestion) && selectedQuestion != "") {
                //selectedQuestion = grabQuestion;
                //selectedQuestion;
                correctAnswer = answers.get(selectedQuestion);
            }
            else
            {
                flag = true;
            }
        }

        String[] answerSet = new String[4];
        answerSet[3] = correctAnswer;
        for (int x = 0; x < answerSet.length - 1; x++)
        {
            String tmp = "";
            flag = true;
            while(flag)
            {
                flag = false;
                tmp = randomAnswers[rand.nextInt(randomAnswers.length)];
                if (tmp == correctAnswer)
                {
                    flag = true;
                }
            }
            answerSet[x] = tmp;
        }

        // Scramble answerSet
        String temp = answerSet[randomizeCorrectAnswer];
        answerSet[randomizeCorrectAnswer] = answerSet[3];
        answerSet[3] = temp;

        Log.d("Question: ", selectedQuestion + "\nAnswer: " + correctAnswer);
        questionText.setText("Q: " + selectedQuestion);
        buttonA.setText("A) " + answerSet[0]);
        buttonB.setText("B) " + answerSet[1]);
        buttonC.setText("C) " + answerSet[2]);
        buttonD.setText("D) " + answerSet[3]);
    }

    public String[] compileQuestionKey(JSONArray terms)
    {
        try
        {
            // Create an array to compile list of answers to pull randomly from
            // Will also ensure answer won't be duplicated
            String [] questionKey = new String[terms.length()];
            for (int k = 0; k < terms.length(); k++)
            {
                JSONObject term1 = terms.getJSONObject(k);
                questionKey[k] = term1.getString("term");
            }
            return questionKey;
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] compileRandomAnswers(JSONArray terms)
    {
        try
        {
            // Create an array to compile list of answers to pull randomly from
            // Will also ensure answer won't be duplicated
            String [] randomAnswers = new String[terms.length()];
            for (int k = 0; k < terms.length(); k++)
            {
                JSONObject term1 = terms.getJSONObject(k);
                randomAnswers[k] = term1.getString("definition");
            }
            return randomAnswers;
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}