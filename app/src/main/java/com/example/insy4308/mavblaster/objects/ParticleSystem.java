package com.example.insy4308.mavblaster.objects;



import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;
import android.graphics.Color;

import com.example.insy4308.mavblaster.mavUtilities.Constants;
import com.example.insy4308.mavblaster.mavUtilities.VertexArray;
import com.example.insy4308.mavblaster.programs.ParticleShaderProgram;
import com.example.insy4308.mavblaster.mavUtilities.Geometry.Point;
import com.example.insy4308.mavblaster.mavUtilities.Geometry.Vector;

public class ParticleSystem {


    private static final int TOTAL_COMPONENT_COUNT = Constants.POSITION_COMPONENT_COUNT
                    + Constants.COLOR_COMPONENT_COUNT
                    + Constants.VECTOR_COMPONENT_COUNT
                    + Constants.PARTICLE_START_TIME_COMPONENT_COUNT;

    private static final int STRIDE = TOTAL_COMPONENT_COUNT * Constants.BYTES_PER_FLOAT;

    private final float[] particles;
    private final VertexArray vertexArray;
    private final int maxParticleCount;

    private int currentParticleCount;
    private int nextParticle;

    public ParticleSystem(int maxParticleCount) {
        particles = new float[maxParticleCount * TOTAL_COMPONENT_COUNT];
        vertexArray = new VertexArray(particles);
        this.maxParticleCount = maxParticleCount;
    }

    public void addParticle(Point position, int color, Vector direction,
                            float particleStartTime) {
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

    public void bindData(ParticleShaderProgram particleProgram) {
        int dataOffset = 0;
        vertexArray.setVertexAttribPointer(dataOffset,
                particleProgram.getPositionAttributeLocation(),
                Constants.POSITION_COMPONENT_COUNT, STRIDE);
        dataOffset += Constants.POSITION_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset,
                particleProgram.getColorAttributeLocation(),
                Constants.COLOR_COMPONENT_COUNT, STRIDE);
        dataOffset += Constants.COLOR_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset,
                particleProgram.getDirectionVectorAttributeLocation(),
                Constants.VECTOR_COMPONENT_COUNT, STRIDE);
        dataOffset += Constants.VECTOR_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset,
                particleProgram.getParticleStartTimeAttributeLocation(),
                Constants.PARTICLE_START_TIME_COMPONENT_COUNT, STRIDE);
    }

    public void draw() {
        glDrawArrays(GL_POINTS, 0, currentParticleCount);
    }
}
