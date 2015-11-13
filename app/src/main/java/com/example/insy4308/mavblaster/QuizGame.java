package com.example.insy4308.mavblaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class QuizGame extends AppCompatActivity {

    private String url;
    private String UrlId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_game);

        // Get intent bundle extras here
        // Should make deparments identified by # 1- 6 or something to send to UrlId....

        // Method to retrieve department URL & UrlId set
        url = getDepartmentUrl();
        //Method for JsonArrayRequest
        JsonArrayRequest(url);
    }

    public void JsonArrayRequest(String url)
    {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse (JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jobj = response.getJSONObject(i);
                        String id = jobj.getString("id");

                        if (!id.contains(UrlId))
                        {
                            continue;
                        }

                        // Compile Questions & Array of random answers to populate as dummy answers
                        JSONArray terms = jobj.getJSONArray("terms");
                        String [] randomAnswers = compileRandomAnswers(terms);
                        String question = "";
                        String answer = "";
                        Random rand = new Random();
                        //int randomIndex = rand.nextInt(terms.length());
                        //Log.v("Array length of terms=", "" + terms.length());
                        for (int j = 0; j < terms.length(); j++){
                            if(j > 2)
                            {
                                break; // Only pulling three questions from url
                            }
                            JSONObject term1 = terms.getJSONObject(j);
                            question = term1.getString("term");
                            answer = term1.getString("definition");

                            Log.v("Q: ", question + "?\nA) " + answer + "\nB) " + randomAnswers[rand.nextInt(terms.length())] + "\nC) " +
                                randomAnswers[rand.nextInt(terms.length())] + "\nD) " + randomAnswers[rand.nextInt(terms.length())]);
                        }

                        Log.v("Data ID: ", id);
                        Log.v("Index i: " , "" + i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Log.v ("data from web", response.toString()
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
    public String getDepartmentUrl()
    {
        // Logic to determine department & set UrlId that will correspond with that url
        // url = some url
        UrlId = "18157623";

        // setUrlId("#######");

        // For now...
        return "https://api.quizlet.com/2.0/users/xCAFEBABE/sets?client_id=J2gjAn276Y&whitespace=1";
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