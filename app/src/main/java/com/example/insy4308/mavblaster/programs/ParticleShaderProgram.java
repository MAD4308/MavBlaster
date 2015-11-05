package com.example.insy4308.mavblaster.programs;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import android.content.Context;
import com.example.insy4308.mavblaster.R;
import com.example.insy4308.mavblaster.mavUtilities.Constants;

public class ParticleShaderProgram extends ShaderProgram {

    private final int uMatrixLocation;
    private final int uTimeLocation;

    private final int aPositionLocation;
    private final int aColorLocation;
    private final int aDirectionVectorLocation;
    private final int aParticleStartTimeLocation;
    private final int uTextureUnitLocation;
    public ParticleShaderProgram(Context context) {
        super(context, R.raw.particle_vertex_shader,
                R.raw.particle_fragment_shader);

        uMatrixLocation = glGetUniformLocation(program, Constants.U_MATRIX);
        uTimeLocation = glGetUniformLocation(program, Constants.U_TIME);
        uTextureUnitLocation = glGetUniformLocation(program, Constants.U_TEXTURE_UNIT);

        aPositionLocation = glGetAttribLocation(program, Constants.A_POSITION);
        aColorLocation = glGetAttribLocation(program, Constants.A_COLOR);
        aDirectionVectorLocation = glGetAttribLocation(program, Constants.A_DIRECTION_VECTOR);
        aParticleStartTimeLocation = glGetAttribLocation(program, Constants.A_PARTICLE_START_TIME);
    }

    public void setUniforms(float[] matrix, float elapsedTime, int textureId) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        glUniform1f(uTimeLocation, elapsedTime);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(uTextureUnitLocation, 0);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }
    public int getColorAttributeLocation() {
        return aColorLocation;
    }
    public int getDirectionVectorAttributeLocation() {
        return aDirectionVectorLocation;
    }
    public int getParticleStartTimeAttributeLocation() {
        return aParticleStartTimeLocation;
    }
}

