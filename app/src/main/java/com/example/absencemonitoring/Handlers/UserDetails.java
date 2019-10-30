package com.example.absencemonitoring.Handlers;

import android.content.Context;
import android.content.SharedPreferences;

public class UserDetails {
    private SharedPreferences preference;
    public static final  String preferenceName="UserData";

    public UserDetails(Context context) {
        preference=context.getSharedPreferences(preferenceName, context.MODE_PRIVATE);
    }

    public void saveUserDetails(String personalId,boolean login){
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("personalId",personalId);
        editor.putBoolean("flagLogin",login);
        editor.apply();
    }

    public void saveUserRole(String userRole){
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("role",userRole);
        editor.apply();
    }

    public String getUserDetails() {
        return preference.getString("personalId", "not found");
    }

    public boolean getUserLogin() {
        return preference.getBoolean("flagLogin", false);
    }
}
