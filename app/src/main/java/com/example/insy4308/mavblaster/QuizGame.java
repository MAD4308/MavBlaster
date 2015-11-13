package com.example.insy4308.mavblaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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
        // Start Activity and expect results? OR just send an activity?
        Bundle extras = getIntent().getExtras();  // capture the data that came with the intent
        int department = 0;
        // Data may be blank:
        if (extras != null) {
            department = extras.getInt("department");
        }

        // Method to retrieve department URL & UrlId set
        url = getDepartmentUrl(department);
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
    public String getDepartmentUrl(int department)
    {
        // Logic to determine department & set UrlId that will correspond with that url
        // url = some url
        // UrlId - Pulls the flash card set from the list of clash card sets in the URL's

        String url = "";
        switch(department){
            case 1:
                url = "https://api.quizlet.com/2.0/users/xCAFEBABE/sets?client_id=J2gjAn276Y&whitespace=1";
                UrlId = "18157623";
                break;
            case 2:
                url = "https://api.quizlet.com/2.0/users/xCAFEBABE/sets?client_id=J2gjAn276Y&whitespace=1";
                UrlId = "18157623";
                break;
            case 3:
                url = "https://api.quizlet.com/2.0/users/xCAFEBABE/sets?client_id=J2gjAn276Y&whitespace=1";
                UrlId = "18157623";
                break;
            case 4:
                url = "https://api.quizlet.com/2.0/users/xCAFEBABE/sets?client_id=J2gjAn276Y&whitespace=1";
                UrlId = "18157623";
                break;
            case 5:
                url = "https://api.quizlet.com/2.0/users/xCAFEBABE/sets?client_id=J2gjAn276Y&whitespace=1";
                UrlId = "18157623";
                break;
            case 6:
                url = "https://api.quizlet.com/2.0/users/xCAFEBABE/sets?client_id=J2gjAn276Y&whitespace=1";
                UrlId = "18157623";
                break;
        }
        // setUrlId("#######");

        return url;
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