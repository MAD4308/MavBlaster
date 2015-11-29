package com.example.insy4308.mavblaster.openGLES2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


public class OurGLSurfaceView extends GLSurfaceView{

    private Renderer renderer;
    private float density;
    float previousX;
    float previousY;

    public OurGLSurfaceView(Context context){
        super(context);
    }

    public OurGLSurfaceView(Context context, AttributeSet attributes){
        super(context, attributes);
    }

    public void setRenderer(Renderer sRenderer, float sDensity){
        renderer = sRenderer;
        density = sDensity;
        super.setRenderer(sRenderer);
    }
}
