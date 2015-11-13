package com.example.insy4308.mavblaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DepartmentSelection extends AppCompatActivity implements View.OnClickListener {

    private Intent quizGame = null;
    private Button iNSY;
    private Button fINA;
    private Button mANA;
    private Button aCCT;
    private Button mKTG;
    private Button eCON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_selection);
        getSupportActionBar().hide();

        iNSY = (Button) findViewById(R.id.insy);
        fINA = (Button) findViewById(R.id.fina);
        mANA = (Button) findViewById(R.id.mana);
        aCCT = (Button) findViewById(R.id.acct);
        eCON = (Button) findViewById(R.id.econ);
        mKTG = (Button) findViewById(R.id.mktg);


        iNSY.setOnClickListener(this);
        fINA.setOnClickListener(this);
        mANA.setOnClickListener(this);
        aCCT.setOnClickListener(this);
        eCON.setOnClickListener(this);
        mKTG.setOnClickListener(this);

    }
    public void onClick(View v) {
        switch (v.getId()) {

            // Will want to send an ID with each department selection that identifies which department
            // was selected, which will determine the JSON response and Quiz Gameplay.

            case R.id.insy:
                quizGame = new Intent(DepartmentSelection.this, QuizGame.class);
                quizGame.putExtra("department", 1);
                startActivity(quizGame);
                break;
            case R.id.fina:
                quizGame = new Intent(DepartmentSelection.this, QuizGame.class);
                quizGame.putExtra("department", 2);
                startActivity(quizGame);
                break;
            case R.id.mana:
                quizGame = new Intent(DepartmentSelection.this, QuizGame.class);
                quizGame.putExtra("department", 3);
                startActivity(quizGame);
                break;
            case R.id.acct:
                quizGame = new Intent(DepartmentSelection.this, QuizGame.class);
                quizGame.putExtra("department", 4);
                startActivity(quizGame);
                break;
            case R.id.econ:
                quizGame = new Intent(DepartmentSelection.this, QuizGame.class);
                quizGame.putExtra("department", 5);
                startActivity(quizGame);
                break;
            case R.id.mktg:
                quizGame = new Intent(DepartmentSelection.this, QuizGame.class);
                quizGame.putExtra("department", 6);
                startActivity(quizGame);
                break;

        }
    }
}
