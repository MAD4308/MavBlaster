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

public class SkyboxRenderer implements Renderer {
    private final Context context;

    private boolean start = false;
    private boolean high = true;
    private int type = 0;

    float angleVarianceInDegrees;
    float speedVariance;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];

    private int orange = Color.rgb(245, 128, 38);
    private int blue = Color.rgb(88, 211, 247);
    private int white = Color.rgb(255, 255, 255);
    private int yellow = Color.rgb(204, 255, 51);

    private Skybox skybox;
    private ParticleSystem particleSystem;
    private ArrayList<ParticleShooter> particleShooters = new ArrayList<>();
    private ArrayList<Vector> vectors = new ArrayList<>();
    private ArrayList<Point> points = new ArrayList<>();

    private long globalStartTime;
    private int particleTexture;
    private int skyboxTexture;

    private float xRotation, yRotation;
    private float animation = 0.0f;

    public SkyboxRenderer(Context context, int type, boolean high) {
        this.context = context;
        this.type = type;
        this.high = high;
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
    public void setParticleShooters(boolean start) {
        if(start)
            particleSystem.drawParticles(viewMatrix, viewProjectionMatrix, projectionMatrix, particleShooters, globalStartTime, particleTexture);
    }
    public void setStatus(boolean status){
        start = status;
    }
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        skybox = new Skybox(context);

        setParticleType(type);
        setSkyboxTexture(high);
    }

    public void setSkyboxTexture(boolean high)
    {
        if(high) {
            skyboxTexture = TextureHelper.loadCubeMap(context, new int[]{
            /*negx*/R.drawable.space_right, /*posx*/R.drawable.space_left,
            /*negy*/R.drawable.space_down, /*posy*/R.drawable.space_up,
            /*negz*/R.drawable.space_front, /*posz*/R.drawable.space_back});
        }
        else{
            skyboxTexture = TextureHelper.loadCubeMap(context, new int[]{
            /*negx*/R.drawable.space_right_low, /*posx*/R.drawable.space_left_low,
            /*negy*/R.drawable.space_down_low, /*posy*/R.drawable.space_up_low,
            /*negz*/R.drawable.space_front_low, /*posz*/R.drawable.space_back_low});
        }
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
        setParticleShooters(start);
        animation += 0.01f;
        backgroundAnimation(1.0f, (float) Math.cos(animation) * 1.5f);
    }
    public void setParticleType(int type) {
        particleSystem = new ParticleSystem(100000, context);
        globalStartTime = System.nanoTime();
        switch (type) {
            case 0:
                setParticleTypeOne();
                break;
            case 1:
                setParticleTypeTwo();
                break;
            case 2:
                setParticleTypeThree();
                break;
            case 3:
                setParticleTypeFour();
                break;
            case 4:
                setParticleTypeFive();
                break;
        }
    }
    public void setParticleTypeOne(){
        vectors.add(new Vector(0f, 1.0f, 0f));

        points.add(new Point(0f, -3.0f, 0f));
        points.add(new Point(-1f, -3.0f, 0f));
        points.add(new Point(1f, -3.0f, 0f));


        angleVarianceInDegrees = 20f;
        speedVariance = 1f;

        particleShooters.add(new ParticleShooter(points.get(0), vectors.get(0), orange, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(1), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(2), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));

        particleTexture = TextureHelper.loadTexture(context, R.drawable.particle_star);
    }
    public void setParticleTypeTwo(){
        vectors.add(new Vector(0f, 2.0f, 0f));
        vectors.add(new Vector(0.7f, 2.0f, 0f));
        vectors.add(new Vector(-0.7f, 2.0f, 0f));

        points.add(new Point(0f, -2.0f, 0f));
        points.add(new Point(-0.7f, -2.0f, 0f));
        points.add(new Point(0.7f, -2.0f, 0f));
        points.add(new Point(-2.0f, -1.0f, 0f));
        points.add(new Point(2.0f, -1.0f, 0f));

        angleVarianceInDegrees = 20f;
        speedVariance = 1f;

        particleShooters.add(new ParticleShooter(points.get(0), vectors.get(0), orange, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(1), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(2), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(3), vectors.get(1), yellow, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(4), vectors.get(2), yellow, angleVarianceInDegrees, speedVariance));
        particleTexture = TextureHelper.loadTexture(context, R.drawable.arched_star2);
    }
    public void setParticleTypeThree(){
        vectors.add(new Vector(0f, 2.0f, 0f));
        vectors.add(new Vector(0.7f, 2.0f, 0f));
        vectors.add(new Vector(-0.7f, 2.0f, 0f));

        points.add(new Point(0f, -2.0f, 0f));
        points.add(new Point(-0.7f, -2.0f, 0f));
        points.add(new Point(0.7f, -2.0f, 0f));
        points.add(new Point(-2.0f, -1.0f, 0f));
        points.add(new Point(2.0f, -1.0f, 0f));

        angleVarianceInDegrees = 20f;
        speedVariance = 1f;

        particleShooters.add(new ParticleShooter(points.get(0), vectors.get(0), orange, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(1), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(2), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(3), vectors.get(1), yellow, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(4), vectors.get(2), yellow, angleVarianceInDegrees, speedVariance));
        particleTexture = TextureHelper.loadTexture(context, R.drawable.dust);
    }
    public void setParticleTypeFour(){
        vectors.add(new Vector(0f, 0.9f, 0f));
        vectors.add(new Vector(0.05f, 0.2f, 0f));
        vectors.add(new Vector(-0.05f, 0.2f, 0f));

        points.add(new Point(0f, -3.0f, 0f));
        points.add(new Point(-1f, -3.0f, 0f));
        points.add(new Point(1f, -3.0f, 0f));
        points.add(new Point(-0.5f, -3.0f, 0f));
        points.add(new Point(0.5f, -3.0f, 0f));
        points.add(new Point(-1.5f, 4.0f, 0f));
        points.add(new Point(1.5f, 4.0f, 0f));

        angleVarianceInDegrees = 20f;
        speedVariance = 0.5f;

        particleShooters.add(new ParticleShooter(points.get(0), vectors.get(0), orange, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(1), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(2), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(3), vectors.get(0), yellow, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(4), vectors.get(0), yellow, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(5), vectors.get(1), blue, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(6), vectors.get(2), blue, angleVarianceInDegrees, speedVariance));

        particleTexture = TextureHelper.loadTexture(context, R.drawable.arched_star2);
    }
    public void setParticleTypeFive(){
        vectors.add(new Vector(0f, 0.1f, 0f));

        points.add(new Point(0f, 4f, 0f));
        points.add(new Point(-1f, 4f, 0f));
        points.add(new Point(1f, 4f, 0f));
        points.add(new Point(-0.5f, 4f, 0f));
        points.add(new Point(0.5f, 4f, 0f));

        angleVarianceInDegrees = 50f;
        speedVariance = 1f;

        particleShooters.add(new ParticleShooter(points.get(0), vectors.get(0), orange, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(1), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(2), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(3), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));
        particleShooters.add(new ParticleShooter(points.get(4), vectors.get(0), blue, angleVarianceInDegrees, speedVariance));

        particleTexture = TextureHelper.loadTexture(context, R.drawable.particle_star);
    }
}