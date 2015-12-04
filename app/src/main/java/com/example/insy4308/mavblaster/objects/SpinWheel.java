package com.example.insy4308.mavblaster.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class SpinWheel {
    private static String TAG = "SpriteRender";

    private Context context;

    private FloatBuffer databuffer;
    private FloatBuffer texturebuffer;
    // x, y,
    float data[] =  {-0.5f,  -0.5f, //bottom left
                     -0.5f, 0.5f,   //top left
                     0.5f, 0.5f,    //top right
                     0.5f, -0.5f};  //bottom left
    // u,v
    float texturedata[] =   {0.0f, 0.0f,
                            0.0f, 1.0f,
                            1.0f, 1.0f,
                            1.0f, 0.0f};
    private int mProgram;
    private int vertexShader;
    private int fragmentShader;
    private int texture;
    private int positionHandler;
    private int texCoordHandler;
    private int textureHandler;

    private float centerX = 0.0f;
    private float centerY = 0.25f;
    private float width = 1.0f;
    private float height = 1.0f;
    private float rotation = 0.0f;

    float[] modelView = new float[16];

    public void setRotation(float rotation) { this.rotation = rotation; }

    public double findDegrees() {
        float bottomLeftX = data[0] * modelView[0] +
                            data[1] * modelView[4];
        float bottomLeftY = data[0] * modelView[1] +
                            data[1] * modelView[5];
        float topRightX = data[5] * modelView[0] +
                          data[6] * modelView[4];
        float topRightY = data[5] * modelView[1] +
                          data[6] * modelView[5];

        double distance = Math.sqrt(Math.pow((topRightX - bottomLeftX), 2) + Math.pow((topRightY - bottomLeftY), 2));
        double degree = 0;
        double first = 0;
        double second = 0;

        // so now we got a line
        //  bottomLeftX,bottomLeftY -> topRightX,topRightY
        //
        if(topRightX > 0) { //1st or 2nd quadrant
            if(topRightY > 0) { //1st
                first = topRightY;
                second = bottomLeftY;

                //Log.d("CHECK!!!!!!!!", "45 + " + Math.toDegrees(Math.acos((first - second) / (distance))));
                degree = 45 + Math.toDegrees(Math.acos((first - second) / (distance)));
            }
            else { //2nd
                first = topRightX;
                second = bottomLeftX;

                //Log.d("CHECK!!!!!!!!", "135 + " + Math.toDegrees(Math.acos((first - second) / (distance))));
                degree = 135 + Math.toDegrees(Math.acos((first - second) / (distance)));
            }
        } else { //3rd or 4th
            if(topRightY > 0) { //4th
                first = bottomLeftX;
                second = topRightX;

                //Log.d("CHECK!!!!!!!!", "-45 + " + Math.toDegrees(Math.acos((first - second)/ (distance))));
                degree = -45 + Math.toDegrees(Math.acos((first - second)/ (distance)));
                if(degree < 0)
                    degree = 360 + degree;
            }
            else { //3rd
                first = bottomLeftY;
                second = topRightY;

                //Log.d("CHECK!!!!!!!!", "225 + " + Math.toDegrees(Math.acos((first - second)/ (distance))));
                degree = 225 + Math.toDegrees(Math.acos((first - second) / (distance)));
            }
        }

        /*Log.d("findDegrees() CHECK", "bottomLeftX: " + bottomLeftX +
                                    " bottomLeftY: " + bottomLeftY +
                                    " topRightX: " + topRightX +
                                    " topRightY: " + topRightY +
                                    " distance: " + distance +
                                    " degree: " + degree);*/

        return degree;
    }

    public SpinWheel(Context context, int textureid){
        this.context = context;

        ByteBuffer bb_data = ByteBuffer.allocateDirect(data.length * 4);
        bb_data.order(ByteOrder.nativeOrder());
        ByteBuffer bb_texture = ByteBuffer.allocateDirect(texturedata.length * 4);
        bb_texture.order(ByteOrder.nativeOrder());

        databuffer = bb_data.asFloatBuffer();
        databuffer.put(data);
        databuffer.position(0);
        texturebuffer = bb_texture.asFloatBuffer();
        texturebuffer.put(texturedata);
        texturebuffer.position(0);

        String vertexShaderSource = "" +
                "uniform mat4 modelView;" +
                "attribute vec3 a_Position;" +
                "attribute vec2 a_TexCoordinate; " +
                "varying vec2 v_TexCoordinate; " +
                "void main() { " +
                    "v_TexCoordinate = a_TexCoordinate; " +
                    "gl_Position = modelView * vec4(a_Position, 1.0);" +
                "}";
        vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertexShader, vertexShaderSource);
        GLES20.glCompileShader(vertexShader);
        //String vertexShaderCompileLog = GLES20.glGetShaderInfoLog(vertextShader);

        String fragmentShaderSource = "" +
                "precision mediump float; " +
                "varying vec2 v_TexCoordinate; " +
                "uniform sampler2D u_Texture; " +
                "void main() { " +
                "   gl_FragColor = texture2D(u_Texture, v_TexCoordinate);" +
                "}";
        fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragmentShader, fragmentShaderSource);
        GLES20.glCompileShader(fragmentShader);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glLinkProgram(mProgram);

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);

        positionHandler     = GLES20.glGetAttribLocation(mProgram, "a_Position");
        texCoordHandler     = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");
        textureHandler      = GLES20.glGetUniformLocation(mProgram, "u_Texture");

        texture = loadTexture(context, textureid);
    }

    public void draw(){
        GLES20.glUseProgram(mProgram);
        databuffer.position(0);
        texturebuffer.position(0);

        Matrix.setIdentityM(modelView, 0);
        Matrix.translateM(modelView, 0, centerX, centerY, 0.0f);
        Matrix.rotateM(modelView, 0, rotation, 0.0f, 0.0f, 1.0f);
        Matrix.scaleM(modelView, 0, width, height, 1.0f);
        GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(mProgram, "modelView"), 1, false, modelView, 0);

        //GLES20.glUniform1i(textureHandler, 0);

        GLES20.glEnableVertexAttribArray(texCoordHandler);
        GLES20.glEnableVertexAttribArray(positionHandler);

        GLES20.glVertexAttribPointer(positionHandler, 2, GLES20.GL_FLOAT, false, 0, databuffer);
        GLES20.glVertexAttribPointer(texCoordHandler, 2, GLES20.GL_FLOAT, false, 0, texturebuffer);

        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_BLEND);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
    }

    private int loadTexture(Context context, int resource){
        int[] texture = new int[1];

        GLES20.glGenTextures(1, texture, 0);

        BitmapFactory.Options bo = new BitmapFactory.Options();
        bo.inScaled = false;
        Bitmap tex = BitmapFactory.decodeResource(context.getResources(), resource, bo);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, tex, 0);
        tex.recycle();

        return texture[0];

    }
}
