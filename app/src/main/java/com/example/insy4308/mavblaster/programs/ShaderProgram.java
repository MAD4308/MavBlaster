package com.example.insy4308.mavblaster.programs;

import static android.opengl.GLES20.glUseProgram;
import android.content.Context;

import com.example.insy4308.mavblaster.mavUtilities.ShaderHelper;
import com.example.insy4308.mavblaster.mavUtilities.TextResourceReader;

abstract class ShaderProgram {

    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        program = ShaderHelper.buildProgram(TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId));
    }

    public void useProgram() {
        glUseProgram(program);
    }
}
