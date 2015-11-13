package com.example.insy4308.mavblaster.mavUtilities;

import android.content.Intent;

import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;
import static com.example.insy4308.mavblaster.mavUtilities.Departments.*;

public enum Categories {
    CATEGORY1(CAT1),
    CATEGORY2(CAT2),
    CATEGORY3(CAT3),
    CATEGORY4(CAT4),
    CATEGORY5(CAT5);

    private int categoryCode;
    private static final String name = Categories.class.getName();

    private Categories(int i){
        categoryCode = i;
    }
    public void attachCatTo(Intent intent) {
        intent.putExtra(name, ordinal());
    }
    public static Categories detachCatFrom(Intent intent) {
        if (!intent.hasExtra(name)) throw new IllegalStateException();
        return values()[intent.getIntExtra(name, -1)];
    }
    public int getCategoryCode() {
        return categoryCode;
    }
}

