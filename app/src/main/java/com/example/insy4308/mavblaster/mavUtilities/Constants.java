package com.example.insy4308.mavblaster.mavUtilities;

public final class Constants {

    public static final int BYTES_PER_FLOAT = 4;
    public static final int POSITION_DATA_SIZE = 3;
    public static final int TEX_COORD_DATA_SIZE = 2;
    public static final int NORMAL_DATA_SIZE = 3;
    public static final int POSITION_COMPONENT_COUNT = 3;
    public static final int COLOR_COMPONENT_COUNT = 3;
    public static final int VECTOR_COMPONENT_COUNT = 3;
    public static final int PARTICLE_START_TIME_COMPONENT_COUNT = 1;

    public static final float EYE_X = 0.0f;
    public static final float EYE_Y = 0.0f;
    public static final float EYE_Z = -0.5f;
    public static final float LOOK_X = 0.0f;
    public static final float LOOK_Y = 0.0f;
    public static final float LOOK_Z = -5.0f;
    public static final float UP_X = 0.0f;
    public static final float UP_Y = 1.0f;
    public static final float UP_Z = 0.0f;

    public static final String U_MATRIX = "u_Matrix";
    public static final String U_COLOR = "u_Color";
    public static final String U_TEXTURE_UNIT = "u_TextureUnit";
    public static final String U_TIME = "u_Time";
    public static final String A_DIRECTION_VECTOR = "a_DirectionVector";
    public static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";
    public static final String A_POSITION = "a_Position";
    public static final String A_COLOR = "a_Color";
    public static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    public static final int TOTAL_COMPONENT_COUNT = POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT + VECTOR_COMPONENT_COUNT + PARTICLE_START_TIME_COMPONENT_COUNT;
    public static final int STRIDE = TOTAL_COMPONENT_COUNT * BYTES_PER_FLOAT;

    private Constants() {
        throw new AssertionError();
    }
}
