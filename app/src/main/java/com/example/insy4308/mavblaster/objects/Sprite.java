package com.example.insy4308.mavblaster.objects;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Sprite {
    private static int program = -1;
    private static FloatBuffer geometryBuffer = null;
    private float centerX = 0.0f;
    private float centerY = 0.25f;
    private float width = 4.0f;
    private float height = 4.0f;
    private float rotation = 0.0f;

    //public float getCenterX() { return centerX; }
    //public void setCenterX(float centerX) { this.centerX = centerX; }
    //public float getCenterY() { return centerY; }
    //public void setCenterY(float centerY) { this.centerY = centerY; }
    //public float getWidth() { return width; }
    public void setWidth(float width) { this.width = width; }
    //public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }
    //public float getRotation() { return rotation; }
    public void setRotation(float rotation) { this.rotation = rotation; }

    public Sprite() {
        String vertexShaderSource = "" +
                "uniform mat4 modelView;" +
                "attribute vec3 position;" +
                "" +
                "void main()" +
                "" +
                "{" +
                "" +
                "   gl_Position = modelView * vec4(position, 1.0);" +
                "" +
                "}";

        String fragmentShaderSource = "" +
                "uniform sampler2D texture1;" +
                "" +
                "void main()" +
                "" +
                "{" +
                "" +
                "   gl_FragColor = vec4(0.96, 0.5, 0.14, 1.0);" +
                "}";

        int vertextShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertextShader, vertexShaderSource);
        GLES20.glCompileShader(vertextShader);
        //String vertexShaderCompileLog = GLES20.glGetShaderInfoLog(vertextShader);

        int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragmentShader, fragmentShaderSource);
        GLES20.glCompileShader(fragmentShader);
        //String fragmentShaderComplieLog = GLES20.glGetShaderInfoLog(fragmentShader);

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertextShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glBindAttribLocation(program, 0, "position");
        GLES20.glBindAttribLocation(program, 1, "texture");
        GLES20.glLinkProgram(program);
        //String programLinkLog = GLES20.glGetProgramInfoLog(program);

        GLES20.glUseProgram(program);

        float[] geometry =
                {
                        -2.0f, -2.0f, 0.0f,
                        2.0f, -2.0f, 0.0f,
                        -2.0f, 2.0f, 0.0f,
                        2.0f, 2.0f, 0.0f,
                };

        ByteBuffer geometryByteBuffer = ByteBuffer.allocateDirect(geometry.length * 4);
        geometryByteBuffer.order(ByteOrder.nativeOrder());
        geometryBuffer = geometryByteBuffer.asFloatBuffer();
        geometryBuffer.put(geometry);
        geometryBuffer.rewind();
    }

    public void draw()
    {
        float[] modelView = new float[16];
        Matrix.setIdentityM(modelView, 0);
        Matrix.translateM(modelView, 0, centerX, centerY, 0.0f);
        Matrix.rotateM(modelView, 0, rotation, 0.0f, 0.0f, 1.0f);
        Matrix.scaleM(modelView, 0, width, height, 1.0f);
        GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(program, "modelView"), 1, false, modelView, 0);
        //GLES20.glActiveTexture(GL_TEXTURE0);
        //GLES20.glBindTexture(GL_TEXTURE_2D, R.drawable.pentagon_wheel2);
        //GLES20.glUniform1i(uTextureUnitLocation, 0);

        int elements = 3;
        GLES20.glVertexAttribPointer(0, elements, GLES20.GL_FLOAT, false, 0, geometryBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, geometryBuffer.capacity() / elements);
    }
}

