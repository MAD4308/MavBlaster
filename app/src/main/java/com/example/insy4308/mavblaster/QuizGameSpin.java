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

public class QuizGameSpin extends AppCompatActivity implements View.OnClickListener{

    private Intent quizGame = null;
    Button button1;
    Departments departments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_game_spin);
        getSupportActionBar().hide();
        departments= detachDeptFrom(getIntent());

        button1 = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);

        quizGame = new Intent(QuizGameSpin.this, QuizGame.class);


    }
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button:
                CATEGORY1.attachCatTo(quizGame);
                departments.attachDeptTo(quizGame);
                startActivity(quizGame);
                break;
            case R.id.button2:
                CATEGORY2.attachCatTo(quizGame);
                departments.attachDeptTo(quizGame);
                startActivity(quizGame);
                break;
            case R.id.button3:
                CATEGORY3.attachCatTo(quizGame);
                departments.attachDeptTo(quizGame);
                startActivity(quizGame);
                break;
            case R.id.button4:
                CATEGORY4.attachCatTo(quizGame);
                departments.attachDeptTo(quizGame);
                startActivity(quizGame);
                break;
            case R.id.button5:
                CATEGORY5.attachCatTo(quizGame);
                departments.attachDeptTo(quizGame);
                startActivity(quizGame);
                break;

        }
    }
}