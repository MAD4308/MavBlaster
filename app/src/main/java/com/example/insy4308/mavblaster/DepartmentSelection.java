package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.insy4308.mavblaster.openGLES2.OurGLSurfaceView;
import com.example.insy4308.mavblaster.openGLES2.SkyboxRenderer;
import com.facebook.appevents.AppEventsLogger;

import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;

public class DepartmentSelection extends Activity implements View.OnClickListener {

    private OurGLSurfaceView glSurfaceView;
    private SkyboxRenderer renderer;

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

        glSurfaceView = (OurGLSurfaceView) findViewById(R.id.department_surface_view);
        glSurfaceView.setEGLContextClientVersion(2);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        renderer = new SkyboxRenderer(this);
        glSurfaceView.setRenderer(renderer, displayMetrics.density);

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

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
        AppEventsLogger.activateApp(this);
    }
}