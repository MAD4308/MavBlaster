package com.example.insy4308.mavblaster.programs;

import android.content.Context;

import com.example.insy4308.mavblaster.R;

public class SquareShaderProgram extends ShaderProgram {
    public SquareShaderProgram(Context context) {
        super(context, R.raw.square_vertex_shader,
                R.raw.square_fragment_shader);
    }
}
