package com.example.insy4308.mavblaster.mavUtilities;

import android.content.Intent;

import static com.example.insy4308.mavblaster.mavUtilities.Constants.*;

public enum Departments {
    INFO_SYS(INSY),
    FINANCE(FINA),
    MANAGEMENT(MANA),
    ACCOUNTING(ACCT),
    ECONOMY(ECON),
    MARKETING(MKGT);

    private int departmentCode;
    private static final String name = Departments.class.getName();

    private Departments(int i){
        departmentCode = i;
    }
    public void attachDeptTo(Intent intent){
        intent.putExtra(name, ordinal());
    }
    public static Departments detachDeptFrom(Intent intent){
        if(!intent.hasExtra(name)) throw new IllegalStateException();
        return values()[intent.getIntExtra(name, -1)];
    }
    public int getDepartmentCode() {
        return departmentCode;
    }

    public String getDepartmentUrl(int i) {
        return SET_URLS[departmentCode-1][i - 1];
    }
}
