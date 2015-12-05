package com.example.insy4308.mavblaster;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.insy4308.mavblaster.mavUtilities.Departments;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import static com.example.insy4308.mavblaster.mavUtilities.Constants.FB_IMAGE_URL;
import static com.example.insy4308.mavblaster.mavUtilities.Departments.detachDeptFrom;

// Class to show share layout view to provide options to share
public class Share extends Activity {

    ShareButton shareButton;
    ShareLinkContent content;
    ShareDialog shareDialog;
    CallbackManager callbackManager;

    Intent startMenu = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_page);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        final Departments departments = detachDeptFrom(getIntent());
        Bundle score = getIntent().getExtras();
        int highScore = score.getInt("score", 0);

        // Ill need to share the high score here
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startMenu = new Intent(Share.this, StartMenu.class);
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
