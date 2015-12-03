package com.example.insy4308.mavblaster.openGLES2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class OurGLSurfaceView extends GLSurfaceView{

    private QuizRenderer quizRenderer;
    private SkyboxRenderer skyboxRenderer;
    private float density;

    public OurGLSurfaceView(Context context){
        super(context);
    }

    public OurGLSurfaceView(Context context, AttributeSet attributes){
        super(context, attributes);
    }

    public void setParticles(boolean start) {
        skyboxRenderer.setStatus(start);
    }

    public void setRenderer(QuizRenderer renderer, float density){
        quizRenderer = renderer;
        this.density = density;
        super.setRenderer(renderer);
    }
    public void setRenderer(SkyboxRenderer renderer, float density){
        skyboxRenderer = renderer;
        this.density = density;
        super.setRenderer(renderer);
    }
}
