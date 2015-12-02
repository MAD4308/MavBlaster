package com.example.insy4308.mavblaster.openGLES2;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.example.insy4308.mavblaster.objects.Sprite;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class QuizRenderer implements GLSurfaceView.Renderer {

    private long startTime;
    float animation = 0.0f;
    Sprite sprite = null;
    int timeSpinning;
    float speedIncrements[];
    Context context;
    boolean spinning = false;
    Random r;
    int category = 0;
    int degreesSpun = 0;

    public QuizRenderer(Context c) { context = c; }

    public boolean getSpinning() { return spinning; }

    public int getCategory() { return category; }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        //Log.d("QuizRenderer: ", "in onSurfaceCreated");
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        r = new Random();

        sprite = new Sprite();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        int dimension = Math.min(width, height) * 2;
        GLES20.glViewport((width - dimension) / 2, (height - dimension) / 2, dimension, dimension);
    }

    private void setAnimation() {
        long currentTime = System.nanoTime();

        long time = ((currentTime - startTime)/(1000000000/2)); //get time difference in half seconds
        if(spinning && time < timeSpinning)
            animation -= speedIncrements[(int) time];
        else
            spinning = false;
    }

    @Override
    public void onDrawFrame(GL10 gl)
    {
        //Log.d("in onDrawFrame", "in onDrawFrame");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        setAnimation();

        sprite.setRotation(animation*100);
        sprite.setHeight(0.2f);
        sprite.setWidth(0.2f);

        sprite.draw();
    }

    public void startspinning() {
        startTime = System.nanoTime();
        spinning = true;

        while((timeSpinning = r.nextInt(5)) < 2); //get a random number between 2 and 5
        Log.d("timeSpinning", "timeSpinning: " + timeSpinning);
        float startSpeed;
        while((startSpeed = r.nextInt(3)/(float)10) < 0.1f); // get a float between 0.3 and 0.1
        Log.d("startSpeed", "startSpeed: " + startSpeed);

        speedIncrements = new float[timeSpinning * 2];

        for(int i = 0, j = timeSpinning * 2 - 1; i < (timeSpinning * 2); i++, j--) {
            speedIncrements[i] = (float)j/(timeSpinning * 2 - 1)*startSpeed;

            degreesSpun += speedIncrements[i] * 3060; //THIS NEEDS TESTING
            Log.d("speedIncrements", "speedIncrements[" + i + "] = " + speedIncrements[i]);
        }

        Log.d("DegreesSpun", "DegreesSpun before: " + degreesSpun);
        while (degreesSpun >= 360)
            degreesSpun -= 360;
        Log.d("DegreesSpun", "DegreesSpun after: " + degreesSpun);

        if (degreesSpun < 73)       category = 0; //0 - 72
        else if (degreesSpun < 145) category = 1; // 73 - 144
        else if (degreesSpun < 217) category = 2; // 145 - 216
        else if (degreesSpun < 289) category = 3; // 217 - 288
        else                        category = 4; // 289 - 360
    }
}
