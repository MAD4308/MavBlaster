package com.example.insy4308.mavblaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.insy4308.mavblaster.mavUtilities.Categories;
import com.example.insy4308.mavblaster.mavUtilities.Departments;

import static com.example.insy4308.mavblaster.mavUtilities.Categories.*;
import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;
import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;

public class QuizGameSpin extends AppCompatActivity {

    private Intent quizGame = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_game_spin);
        getSupportActionBar().hide();
        final Departments departments = detachDeptFrom(getIntent());
        Button buttonTest = (Button) findViewById(R.id.button);
        int test = 1;


        quizGame = new Intent(QuizGameSpin.this, QuizGame.class);
        //Use for selecting category
        switch (test){
            case 1:
                CATEGORY1.attachCatTo(quizGame);
                break;
            case 2:
                CATEGORY2.attachCatTo(quizGame);
                break;
            case 3:
                CATEGORY3.attachCatTo(quizGame);
                break;
            case 4:
                CATEGORY4.attachCatTo(quizGame);
                break;
            case 5:
                CATEGORY5.attachCatTo(quizGame);
                break;
        }

        //PROBABLY SET UP A BUTTON TO SPIN
        //ROTATION BASE ON 360.0f
        //SPLIT FIVE WAYS
        //SET ROTATION ON TANGENT WAVE FOR FAST SPIN
        //BUTTON HERE IS ONLY TO PROCEED TO NEXT ACTIVITY
        //NEEDS TO PASS THE CATEGORY CHOOSEN BY SPINNER
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departments.attachDeptTo(quizGame);
                startActivity(quizGame);
            }
        });




    }
}
