package com.example.insy4308.mavblaster.openGLES2;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView.Renderer;

import com.example.insy4308.mavblaster.R;
import com.example.insy4308.mavblaster.objects.Skybox;
import com.example.insy4308.mavblaster.objects.ParticleShooter;
import com.example.insy4308.mavblaster.objects.ParticleSystem;
import com.example.insy4308.mavblaster.mavUtilities.Geometry.Point;
import com.example.insy4308.mavblaster.mavUtilities.Geometry.Vector;
import com.example.insy4308.mavblaster.mavUtilities.MatrixHelper;
import com.example.insy4308.mavblaster.mavUtilities.TextureHelper;

import java.util.ArrayList;

public class StartRenderer implements Renderer {
    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];

    private Skybox skybox;
    private ParticleSystem particleSystem;
    private ArrayList<ParticleShooter> particleShooters = new ArrayList<>();

    private long globalStartTime;
    private int particleTexture;
    private int skyboxTexture;

    private float xRotation, yRotation;
    private float animation = 0.0f;

    public StartRenderer(Context context) {
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

        particleSystem = new ParticleSystem(10000, context);
        globalStartTime = System.nanoTime();

        final Vector particleDirection = new Vector(0f, 1.0f, 0f);
        final Vector particleDirectionLeft = new Vector(-0.1f, 1.0f, 0f);
        final Vector particleDirectionRight = new Vector(0.1f, 1.0f, 0f);
        final float angleVarianceInDegrees = 10f;
        final float speedVariance = 1f;

        particleShooters.add(new ParticleShooter(new Point(-0.3f, -3.0f, 0f), particleDirectionLeft, Color.rgb(88, 211, 247), angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(new Point(0f, -3.0f, 0f), particleDirection, Color.rgb(245, 128, 38), angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(new Point(0.3f, -3.0f, 0f), particleDirectionRight, Color.rgb(88, 211, 247), angleVarianceInDegrees, speedVariance));

        particleTexture = TextureHelper.loadTexture(context, R.drawable.particle);
        skyboxTexture = TextureHelper.loadCubeMap(context, new int[] {
        /*negx*/R.drawable.right_galaxy, /*posx*/R.drawable.left_galaxy,
        /*negy*/R.drawable.down_galaxy, /*posy*/R.drawable.up_galaxy,
        /*negz*/R.drawable.front_galaxy, /*posz*/R.drawable.back_galaxy});
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
        particleSystem.drawParticles(viewMatrix, viewProjectionMatrix, projectionMatrix, particleShooters, globalStartTime, particleTexture);

        animation += 0.01f;
        backgroundAnimation(1.0f,(float)Math.cos(animation)*1.5f);
    }

}