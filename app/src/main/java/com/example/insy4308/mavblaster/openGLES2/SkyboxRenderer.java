package com.example.insy4308.mavblaster.openGLES2;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.insy4308.mavblaster.R;
import com.example.insy4308.mavblaster.objects.Skybox;
import com.example.insy4308.mavblaster.mavUtilities.MatrixHelper;
import com.example.insy4308.mavblaster.mavUtilities.TextureHelper;


public class SkyboxRenderer implements GLSurfaceView.Renderer {
    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];

    private Skybox skybox;

    private int skyboxTexture;

    private float xRotation, yRotation;
    private float animation = 0.0f;

    public SkyboxRenderer(Context context) {
        this.context = context;
    }

    public void backgroundAnimation(float deltaX, float deltaY) {
        xRotation += deltaX / 16f;
        yRotation += deltaY / 16f;

        if (yRotation < -90) {
            yRotation = -90;
        } else if (yRotation > 90) {
            yRotation = 90;
        }
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        skybox = new Skybox(context);

        skyboxTexture = TextureHelper.loadCubeMap(context, new int[] {
        /*negx*/R.drawable.space_right, /*posx*/R.drawable.space_left,
        /*negy*/R.drawable.space_down, /*posy*/R.drawable.space_up,
        /*negz*/R.drawable.space_front, /*posz*/R.drawable.space_back});
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        glClear(GL_COLOR_BUFFER_BIT);
        skybox.drawSkybox(viewMatrix, viewProjectionMatrix, projectionMatrix, xRotation, yRotation, skyboxTexture);

        animation += 0.01f;
        backgroundAnimation(1.0f,(float)Math.cos(animation)*1.5f);
    }

}
