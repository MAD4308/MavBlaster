package com.example.insy4308.mavblaster.openGLES2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class DepartmentGLSurfaceView extends GLSurfaceView {

    private DepartmentRenderer renderer;
    private float density;
    float previousX;
    float previousY;

    public DepartmentGLSurfaceView(Context context){
        super(context);
    }

    public DepartmentGLSurfaceView(Context context, AttributeSet attributes){
        super(context, attributes);
    }

    public void setRenderer(DepartmentRenderer dRenderer, float dDensity){
        renderer = dRenderer;
        density = dDensity;
        super.setRenderer(dRenderer);
    }
}