package com.example.insy4308.mavblaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DepartmentSelection extends AppCompatActivity implements View.OnClickListener {

    private Intent quizSpinner = null;
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

        quizSpinner = new Intent(DepartmentSelection.this, QuizGameSpin.class);
    }
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.insy:

                startActivity(quizSpinner);
                break;
            case R.id.fina:
                startActivity(quizSpinner);
                break;
            case R.id.mana:
                startActivity(quizSpinner);
                break;
            case R.id.acct:
                startActivity(quizSpinner);
                break;
            case R.id.econ:
                startActivity(quizSpinner);
                break;
            case R.id.mktg:
                startActivity(quizSpinner);
                break;

        }
    }
}
