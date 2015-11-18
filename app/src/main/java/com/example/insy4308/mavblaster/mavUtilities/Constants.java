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

    public static final int INSY = 1;
    public static final int FINA = 2;
    public static final int MANA = 3;
    public static final int ACCT = 4;
    public static final int ECON = 5;
    public static final int MKGT = 6;

    public static final int CAT1 = 1;
    public static final int CAT2 = 2;
    public static final int CAT3 = 3;
    public static final int CAT4 = 4;
    public static final int CAT5 = 5;

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
    public static final String FB_IMAGE_URL = "http://students.uta.edu/ma/mar9680/blaster.png";
    public static final String QUIZ_URL_START = "https://api.quizlet.com/2.0/sets/";
    public static final String QUIZ_URL_END = "/?client_id=9BtdKcwDcH&whitespace=1";

    public static final String[] DEPT_NAME = {
            "Information Systems",
            "Finance",
            "Management",
            "Accounting",
            "Economics",
            "Marketing"};

    public static final String[][] CAT_NAME = {
            /*INSY*/new String[]{"INSY Category 1","INSY Category 2","INSY Category 3","INSY Category 4","INSY Category 5"},
            /*FINA*/new String[]{"FINA Category 1","FINA Category 2","FINA Category 3","FINA Category 4","FINA Category 5"},
            /*MANA*/new String[]{"MANA Category 1","MANA Category 2","MANA Category 3","MANA Category 4","MANA Category 5"},
            /*ACCT*/new String[]{"ACCT Category 1","ACCT Category 2","ACCT Category 3","ACCT Category 4","ACCT Category 5"},
            /*ECON*/new String[]{"ECON Category 1","ECON Category 2","ECON Category 3","ECON Category 4","ECON Category 5"},
            /*MARK*/new String[]{"MARK Category 1","MARK Category 2","MARK Category 3","MARK Category 4","MARK Category 5"}};

    public static final String[][] SET_URLS = {
        /*INSY*/new String[]{"6990638","45920741","52168328","80333650","3095340"},
        /*FINA*/new String[]{"59028934","67283930","52168328","80333650","3095340"},
        /*MANA*/new String[]{"27410096","45920741","52168328","80333650","3095340"},
        /*ACCT*/new String[]{"41305296","45920741","52168328","80333650","3095340"},
        /*ECON*/new String[]{"75478040","45920741","52168328","80333650","3095340"},
        /*MARK*/new String[]{"76457122","45920741","52168328","80333650","3095340"}};

    public static final int TOTAL_COMPONENT_COUNT = POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT + VECTOR_COMPONENT_COUNT + PARTICLE_START_TIME_COMPONENT_COUNT;
    public static final int STRIDE = TOTAL_COMPONENT_COUNT * BYTES_PER_FLOAT;

    private Constants() {
        throw new AssertionError();
    }
}
