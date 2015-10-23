package com.example.insy4308.mavblaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DepartmentSelection extends AppCompatActivity implements View.OnClickListener {

    private Intent courseSelection = null;
    private Button iNSY;
    private Button fINA;
    private Button mANA;
    private Button aCCT;
    private Button oPMA;
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
        oPMA = (Button) findViewById(R.id.opma);
        mKTG = (Button) findViewById(R.id.mktg);
        eCON = (Button) findViewById(R.id.econ);

        iNSY.setOnClickListener(this);
        fINA.setOnClickListener(this);
        mANA.setOnClickListener(this);
        aCCT.setOnClickListener(this);
        oPMA.setOnClickListener(this);
        mKTG.setOnClickListener(this);
        eCON.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.insy:
                courseSelection = new Intent(DepartmentSelection.this, CourseSelection.class);
                startActivity(courseSelection);
                break;
            case R.id.fina:
                courseSelection = new Intent(DepartmentSelection.this, CourseSelection.class);
                startActivity(courseSelection);
                break;
            case R.id.mana:
                courseSelection = new Intent(DepartmentSelection.this, CourseSelection.class);
                startActivity(courseSelection);
                break;
            case R.id.acct:
                courseSelection = new Intent(DepartmentSelection.this, CourseSelection.class);
                startActivity(courseSelection);
                break;
            case R.id.opma:
                courseSelection = new Intent(DepartmentSelection.this, CourseSelection.class);
                startActivity(courseSelection);
                break;
            case R.id.mktg:
                courseSelection = new Intent(DepartmentSelection.this, CourseSelection.class);
                startActivity(courseSelection);
                break;
            case R.id.econ:
                courseSelection = new Intent(DepartmentSelection.this, CourseSelection.class);
                startActivity(courseSelection);
                break;
        }
    }
}
