package com.pcsahu.shop;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceClass {

    private static final String USER_PREF = "SHOP";
    private SharedPreferences appShared;
    private SharedPreferences.Editor prefEditor;

    public SharedPreferenceClass(Context context){

        appShared = context.getSharedPreferences( USER_PREF,Context.MODE_PRIVATE );
        this.prefEditor =appShared.edit();

    }

    public float getValue(String key){
        return appShared.getFloat(key,0);

    }

    public void setValue(String key,float val){
        prefEditor.putFloat( key,val ).commit();

    }
}
