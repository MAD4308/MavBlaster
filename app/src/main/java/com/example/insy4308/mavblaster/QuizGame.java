package com.example.insy4308.mavblaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.insy4308.mavblaster.mavUtilities.Departments;

import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;
import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;

public class QuizGame extends AppCompatActivity {

    private Intent finalScore = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_game);
        getSupportActionBar().hide();
        final Departments departments = detachFrom(getIntent());
        Button buttonTest = (Button) findViewById(R.id.buttonTest);

        //another test to make sure departments keeps passing to next intent
        //this should be taken out
        finalScore = new Intent(QuizGame.this, FinalScore.class);
        if(departments != null){
            switch (departments.getDepartmentCode()){
                case INSY:
                    Log.i("INSY", "Test");
                    break;
                case FINA:
                    buttonTest.setBackgroundResource(R.drawable.fina_icon);
                    Log.i("FINA","Test");
                    break;
                case MANA:
                    buttonTest.setBackgroundResource(R.drawable.mana_icon);
                    Log.i("MANA","Test");
                    break;
                case ACCT:
                    buttonTest.setBackgroundResource(R.drawable.acct_icon);
                    Log.i("ACCT","Test");
                    break;
                case ECON:
                    buttonTest.setBackgroundResource(R.drawable.econ_icon);
                    Log.i("ECON","Test");
                    break;
                case MKGT:
                    buttonTest.setBackgroundResource(R.drawable.mark_icon);
                    Log.i("MKGT","Test");
                    break;
            }
        }
        //another test button to go to next activity
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departments.attachTo(finalScore);
                startActivity(finalScore);
            }
        });

    }
}
