package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.insy4308.mavblaster.mavUtilities.Departments;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;
import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;

public class FinalScore extends Activity {

    ShareButton shareButton;
    ShareLinkContent content;
    ShareDialog shareDialog;
    CallbackManager callbackManager;
    private Intent startMenu = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_score);
        final Departments departments = detachDeptFrom(getIntent());
        Bundle score = getIntent().getExtras();
        int highScore = score.getInt("score", 0);

        TextView scoreDisplay = (TextView) findViewById(R.id.score);
        scoreDisplay.setText("Score: "+String.valueOf(highScore));

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        content = new ShareLinkContent.Builder()
                .setImageUrl(Uri.parse(FB_IMAGE_URL))
                .setContentTitle("Mav Blaster")
                .setContentDescription("Beat my high score of "+highScore+" in quiz Department: "+departments.getDepartmentName())
                .setContentUrl(Uri.parse("https://developers.facebook.com/android"))
                .build();


        shareButton = (ShareButton) findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.show(content);
            }
        });
        startMenu = new Intent(FinalScore.this, StartMenu.class);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(startMenu);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
