package com.example.insy4308.mavblaster.objects;
import android.content.Context;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;

import java.nio.ByteBuffer;

import com.example.insy4308.mavblaster.mavUtilities.Constants;
import com.example.insy4308.mavblaster.mavUtilities.VertexArray;
import com.example.insy4308.mavblaster.programs.SkyboxShaderProgram;

public class Skybox {
    private final VertexArray vertexArray;
    private final ByteBuffer indexArray;

    private SkyboxShaderProgram skyboxProgram;

    public Skybox(Context context) {
        skyboxProgram = new SkyboxShaderProgram(context);
        vertexArray = new VertexArray(new float[] {
                -1,  1,  1,
                1,  1,  1,
                -1, -1,  1,
                1, -1,  1,
                -1,  1, -1,
                1,  1, -1,
                -1, -1, -1,
                1, -1, -1
        });

        indexArray =  ByteBuffer.allocateDirect(6 * 6).put(new byte[]{
                1, 3, 0,
                0, 3, 2,

                4, 6, 5,
                5, 6, 7,

                0, 2, 4,
                4, 2, 6,

                5, 7, 1,
                1, 7, 3,

                5, 1, 4,
                4, 1, 0,

                6, 2, 7,
                7, 2, 3
        });
        indexArray.position(0);
    }

    public void drawSkybox(float[] viewMatrix, float[] viewProjectionMatrix, float[] projectionMatrix, float xRotation, float yRotation, int skyboxTexture) {
        setIdentityM(viewMatrix, 0);
        rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f);
        rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f);
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        skyboxProgram.useProgram();
        skyboxProgram.setUniforms(viewProjectionMatrix, skyboxTexture);
        vertexArray.setVertexAttribPointer(0, skyboxProgram.getPositionAttributeLocation(), Constants.POSITION_COMPONENT_COUNT, 0);
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_BYTE, indexArray);
    }
}