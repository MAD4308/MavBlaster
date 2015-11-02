package com.example.insy4308.mavblaster;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_ONE;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView.Renderer;

import com.example.insy4308.mavblaster.objects.Skybox;
import com.example.insy4308.mavblaster.objects.ParticleShooter;
import com.example.insy4308.mavblaster.objects.ParticleSystem;
import com.example.insy4308.mavblaster.programs.ParticleShaderProgram;
import com.example.insy4308.mavblaster.programs.SkyboxShaderProgram;
import com.example.insy4308.mavblaster.mavUtilities.Geometry.Point;
import com.example.insy4308.mavblaster.mavUtilities.Geometry.Vector;
import com.example.insy4308.mavblaster.mavUtilities.MatrixHelper;
import com.example.insy4308.mavblaster.mavUtilities.TextureHelper;

public class StartRenderer implements Renderer {
        private final Context context;

        private final float[] projectionMatrix = new float[16];
        private final float[] viewMatrix = new float[16];
        private final float[] viewProjectionMatrix = new float[16];

        private SkyboxShaderProgram skyboxProgram;
        private Skybox skybox;

        private ParticleShaderProgram particleProgram;
        private ParticleSystem particleSystem;
        private ParticleShooter aParticleShooter;
        private ParticleShooter bParticleShooter;
        private ParticleShooter cParticleShooter;

        private long globalStartTime;
        private int particleTexture;
        private int skyboxTexture;

        private float xRotation, yRotation;

        public StartRenderer(Context context) {
                this.context = context;
        }

        public void animation(float deltaX, float deltaY) {
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

                skyboxProgram = new SkyboxShaderProgram(context);
                skybox = new Skybox();

                particleProgram = new ParticleShaderProgram(context);
                particleSystem = new ParticleSystem(10000);
                globalStartTime = System.nanoTime();

                final Vector particleDirection = new Vector(0f, 1.0f, 0f);
                final Vector particleDirectionLeft = new Vector(-0.1f, 1.0f, 0f);
                final Vector particleDirectionRight = new Vector(0.1f, 1.0f, 0f);
                final float angleVarianceInDegrees = 10f;
                final float speedVariance = 1f;

                aParticleShooter = new ParticleShooter(
                        new Point(-0.3f, -3.0f, 0f),
                        particleDirectionLeft,
                        Color.rgb(88, 211, 247),
                        angleVarianceInDegrees,
                        speedVariance);

                bParticleShooter = new ParticleShooter(
                        new Point(0f, -3.0f, 0f),
                        particleDirection,
                        Color.rgb(245, 128, 38),
                        angleVarianceInDegrees,
                        speedVariance);

                cParticleShooter = new ParticleShooter(
                        new Point(0.3f, -3.0f, 0f),
                        particleDirectionRight,
                        Color.rgb(88, 211, 247),
                        angleVarianceInDegrees,
                        speedVariance);

                particleTexture = TextureHelper.loadTexture(context, R.drawable.particle);

                skyboxTexture = TextureHelper.loadCubeMap(context,
                        new int[] { /*left*/R.drawable.right_galaxy, /*right*/R.drawable.left_galaxy,
                                /*bottom*/R.drawable.down_galaxy, /*top*/R.drawable.up_galaxy,
                                /*front*/R.drawable.front_galaxy, /*back*/R.drawable.back_galaxy});
        }

        @Override
        public void onSurfaceChanged(GL10 glUnused, int width, int height) {
                glViewport(0, 0, width, height);
                MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);
        }

        @Override
        public void onDrawFrame(GL10 glUnused) {
                glClear(GL_COLOR_BUFFER_BIT);
                drawSkybox();
                drawParticles();
                animation(1.0f,0.0f);
        }

        private void drawSkybox() {
                setIdentityM(viewMatrix, 0);
                rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f);
                rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f);
                multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
                skyboxProgram.useProgram();
                skyboxProgram.setUniforms(viewProjectionMatrix, skyboxTexture);
                skybox.bindData(skyboxProgram);
                skybox.draw();
        }

        private void drawParticles() {
                float currentTime = (System.nanoTime() - globalStartTime) / 1000000000f;

                aParticleShooter.addParticles(particleSystem, currentTime, 1);
                bParticleShooter.addParticles(particleSystem, currentTime, 1);
                cParticleShooter.addParticles(particleSystem, currentTime, 1);

                setIdentityM(viewMatrix, 0);
                translateM(viewMatrix, 0, 0f, -1.5f, -5f);
                multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

                glEnable(GL_BLEND);
                glBlendFunc(GL_ONE, GL_ONE);

                particleProgram.useProgram();
                particleProgram.setUniforms(viewProjectionMatrix, currentTime, particleTexture);
                particleSystem.bindData(particleProgram);
                particleSystem.draw();

                glDisable(GL_BLEND);
        }
}