package com.example.insy4308.mavblaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;



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

import java.util.Random;

import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;
import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;
import static com.example.insy4308.mavblaster.mavUtilities.Categories.*;

public class QuizGame extends AppCompatActivity {

    private Intent finalScore = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_game);
        getSupportActionBar().hide();
        final Departments departments = detachDeptFrom(getIntent());
        final Categories categories = detachCatFrom(getIntent());
        Button buttonTest = (Button) findViewById(R.id.buttonTest);

        JsonObjectRequest(QUIZ_URL_START+departments.getDepartmentUrl(categories.getCategoryCode())+QUIZ_URL_END);
        finalScore = new Intent(QuizGame.this, FinalScore.class);

        //another test button to go to next activity
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departments.attachDeptTo(finalScore);
                categories.attachCatTo(finalScore);
                startActivity(finalScore);
            }
        });
    }
    public void JsonObjectRequest(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject response) {
                try {
                    JSONArray terms = response.getJSONArray("terms");
                    String [] randomAnswers = compileRandomAnswers(terms);
                    String question = "";
                    String answer = "";
                    Random rand = new Random();
                    //int randomIndex = rand.nextInt(terms.length());
                    //Log.v("Array length of terms=", "" + terms.length());
                    for (int j = 0; (j < terms.length()) && !(j > 10); j++){
                        JSONObject term = terms.getJSONObject(j);
                        question = term.getString("term");
                        answer = term.getString("definition");

                        Log.v("Q: ", question + "?\nA) " + answer + "\nB) " + randomAnswers[rand.nextInt(terms.length())] + "\nC) " +
                                randomAnswers[rand.nextInt(terms.length())] + "\nD) " + randomAnswers[rand.nextInt(terms.length())]);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // Log.v ("data from web", response.toString()

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                VolleyLog.d("quiz game", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(request);
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