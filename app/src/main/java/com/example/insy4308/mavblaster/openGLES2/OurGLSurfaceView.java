package com.example.insy4308.mavblaster.openGLES2;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class OurGLSurfaceView extends GLSurfaceView{

    private Renderer renderer;
    private float density;

    public OurGLSurfaceView(Context context){
        super(context);
    }

    public OurGLSurfaceView(Context context, AttributeSet attributes){
        super(context, attributes);
    }

    public void setRenderer(Renderer renderer, float density){
        this.renderer = renderer;
        this.density = density;
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.RGBA_8888);
        super.setRenderer(renderer);
    }
}
