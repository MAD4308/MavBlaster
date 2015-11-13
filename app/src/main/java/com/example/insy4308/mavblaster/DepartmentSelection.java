package com.example.insy4308.mavblaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;

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
                INFO_SYS.attachDeptTo(quizSpinner);
                startActivity(quizSpinner);
                break;
            case R.id.fina:
                FINANCE.attachDeptTo(quizSpinner);
                startActivity(quizSpinner);
                break;
            case R.id.mana:
                MANAGEMENT.attachDeptTo(quizSpinner);
                startActivity(quizSpinner);
                break;
            case R.id.acct:
                ACCOUNTING.attachDeptTo(quizSpinner);
                startActivity(quizSpinner);
                break;
            case R.id.econ:
                ECONOMY.attachDeptTo(quizSpinner);
                startActivity(quizSpinner);
                break;
            case R.id.mktg:
                MARKETING.attachDeptTo(quizSpinner);
                startActivity(quizSpinner);
                break;

        }
    }
}
