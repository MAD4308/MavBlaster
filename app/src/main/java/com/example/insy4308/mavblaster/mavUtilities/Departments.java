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
    private String departmentName;
    private static final String name = Departments.class.getName();

    Departments(int i){
        departmentCode = i;
        departmentName = DEPT_NAME[i-1];
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
    public String getDepartmentName(){

        return departmentName;
    }
    public String getDepartmentUrl(int i) {
        return SET_URLS[departmentCode-1][i - 1];
    }
}
