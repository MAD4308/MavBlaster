package com.example.insy4308.mavblaster;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.insy4308.mavblaster.mavUtilities.Categories;
import com.example.insy4308.mavblaster.mavUtilities.Departments;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import static com.example.insy4308.mavblaster.mavUtilities.Categories.*;
import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;
import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;

public class FinalScore extends Activity {

    ShareButton shareButton;
    ShareLinkContent content;
    ShareDialog shareDialog;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_score);
        final Departments departments = detachDeptFrom(getIntent());
        final Categories categories = detachCatFrom(getIntent());
        int highScoreTest =1000;

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        content = new ShareLinkContent.Builder()
                .setImageUrl(Uri.parse(FB_IMAGE_URL))
                .setContentTitle("Mav Blaster")
                .setContentDescription("Beat my high score of "+highScoreTest+" in Department: "+departments.getDepartmentName()+" Category: "+categories.getCategoryName(departments.getDepartmentCode()))
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
