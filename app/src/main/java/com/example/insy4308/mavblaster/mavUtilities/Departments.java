package com.example.insy4308.mavblaster.mavUtilities;

import android.content.Intent;


public enum Departments {
    INSY(Constants.INSY),
    FINA(Constants.FINA),
    MANA(Constants.MANA),
    ACCT(Constants.ACCT),
    ECON(Constants.ECON),
    MKTG(Constants.MKGT);

    private int departmentCode;
    private static final String name = Departments.class.getName();

    private Departments(int i){
        departmentCode = i;
    }
    public void attachTo(Intent intent){
        intent.putExtra(name, ordinal());
    }
    public static Departments detachFrom(Intent intent){
        if(!intent.hasExtra(name)) throw new IllegalStateException();
        return values()[intent.getIntExtra(name, -1)];
    }
    public int getDepartmentCode(){
        return departmentCode;
    }
}
