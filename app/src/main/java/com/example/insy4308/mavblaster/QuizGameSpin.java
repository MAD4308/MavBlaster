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

public class QuizGameSpin extends AppCompatActivity {

    private Intent quizGame = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_game_spin);
        getSupportActionBar().hide();
        final Departments departments = detachFrom(getIntent());
        Button buttonTest = (Button) findViewById(R.id.button);

        //USED TO LOAD SPINNER. NOT READY YET
        //LOADED BUTTONS FOR TEST
        //CAN ALSO USE TO SET BACKGROUND OR TEXT BASED ON DEPARTMENT
        //CASES ARE BASED ON CONSTANTS NEED TO BECAREFUL NOT TO MIX UP WITH ENUM
        quizGame = new Intent(QuizGameSpin.this, QuizGame.class);
        if(departments != null){
            switch (departments.getDepartmentCode()){
                case INSY:
                    buttonTest.setBackgroundResource(R.drawable.insy_icon);
                    Log.i("INSY", "Test");
                    break;
                case FINA:
                    buttonTest.setBackgroundResource(R.drawable.fina_icon);
                    Log.i("FINA", "Test");
                    break;
                case MANA:
                    buttonTest.setBackgroundResource(R.drawable.mana_icon);
                    Log.i("MANA", "Test");
                    break;
                case ACCT:
                    buttonTest.setBackgroundResource(R.drawable.acct_icon);
                    Log.i("ACCT", "Test");
                    break;
                case ECON:
                    buttonTest.setBackgroundResource(R.drawable.econ_icon);
                    Log.i("ECON", "Test");
                    break;
                case MKGT:
                    buttonTest.setBackgroundResource(R.drawable.mark_icon);
                    Log.i("MKGT", "Test");
                    break;
            }
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
                departments.attachTo(quizGame);
                startActivity(quizGame);
            }
        });




    }
}
