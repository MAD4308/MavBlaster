package com.example.insy4308.mavblaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class StartMenu extends AppCompatActivity {

    Intent departmentSelection = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);
        getSupportActionBar().hide();

        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out);

        Button start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(fadeOut);
                departmentSelection = new Intent(StartMenu.this, DepartmentSelection.class);
                startActivity(departmentSelection);
            }
        });

    }
}
