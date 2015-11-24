package com.example.insy4308.mavblaster.objects;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_ONE;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnable;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;

import android.content.Context;
import android.graphics.Color;

import com.example.insy4308.mavblaster.mavUtilities.VertexArray;
import com.example.insy4308.mavblaster.programs.ParticleShaderProgram;
import com.example.insy4308.mavblaster.mavUtilities.Geometry.Point;
import com.example.insy4308.mavblaster.mavUtilities.Geometry.Vector;

import java.util.ArrayList;

public class ParticleSystem {
    private ParticleShaderProgram particleProgram;

    private final float[] particles;
    private final VertexArray vertexArray;
    private final int maxParticleCount;

    private int currentParticleCount;
    private int nextParticle;

    public ParticleSystem(int maxParticleCount, Context context) {
        particleProgram = new ParticleShaderProgram(context);
        particles = new float[maxParticleCount * TOTAL_COMPONENT_COUNT];
        vertexArray = new VertexArray(particles);
        this.maxParticleCount = maxParticleCount;
    }

    public void addParticle(Point position, int color, Vector direction, float particleStartTime) {
        final int particleOffset = nextParticle * TOTAL_COMPONENT_COUNT;

        int currentOffset = particleOffset;
        nextParticle++;

        if (currentParticleCount < maxParticleCount) {
            currentParticleCount++;
        }

        if (nextParticle == maxParticleCount) {
            nextParticle = 0;
        }

        particles[currentOffset++] = position.x;
        particles[currentOffset++] = position.y;
        particles[currentOffset++] = position.z;

        particles[currentOffset++] = Color.red(color) / 255f;
        particles[currentOffset++] = Color.green(color) / 255f;
        particles[currentOffset++] = Color.blue(color) / 255f;

        particles[currentOffset++] = direction.x;
        particles[currentOffset++] = direction.y;
        particles[currentOffset++] = direction.z;

        particles[currentOffset++] = particleStartTime;

        vertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_COUNT);
    }

    public void bindData() {
        int dataOffset = 0;
        vertexArray.setVertexAttribPointer(dataOffset, particleProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, STRIDE);
        dataOffset += POSITION_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset, particleProgram.getColorAttributeLocation(), COLOR_COMPONENT_COUNT, STRIDE);
        dataOffset += COLOR_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset, particleProgram.getDirectionVectorAttributeLocation(), VECTOR_COMPONENT_COUNT, STRIDE);
        dataOffset += VECTOR_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset, particleProgram.getParticleStartTimeAttributeLocation(), PARTICLE_START_TIME_COMPONENT_COUNT, STRIDE);
    }

    public void drawParticles(float[] viewMatrix, float[] viewProjectionMatrix, float[] projectionMatrix, ArrayList<ParticleShooter> particleShooters, long globalStartTime, int particleTexture) {
        float currentTime = (System.nanoTime() - globalStartTime) / 1000000000f;

        for(ParticleShooter p:particleShooters)
            p.addParticles(this, currentTime, 1);

        setIdentityM(viewMatrix, 0);
        translateM(viewMatrix, 0, 0f, -1.5f, -5f);
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);

        particleProgram.useProgram();
        particleProgram.setUniforms(viewProjectionMatrix, currentTime, particleTexture);
        bindData();
        glDrawArrays(GL_POINTS, 0, currentParticleCount);

        glDisable(GL_BLEND);
    }
}
