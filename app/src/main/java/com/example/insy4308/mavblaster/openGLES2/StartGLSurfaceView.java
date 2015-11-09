package com.example.insy4308.mavblaster.openGLES2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


public class StartGLSurfaceView extends GLSurfaceView{

    private StartRenderer renderer;
    private float density;
    float previousX;
    float previousY;

    public StartGLSurfaceView(Context context){
        super(context);
    }

    public StartGLSurfaceView(Context context, AttributeSet attributes){
        super(context, attributes);
    }

    public void setRenderer(StartRenderer sRenderer, float sDensity){
        renderer = sRenderer;
        density = sDensity;
        super.setRenderer(sRenderer);
    }
}
