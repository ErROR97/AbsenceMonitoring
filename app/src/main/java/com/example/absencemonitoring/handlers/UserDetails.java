package com.example.absencemonitoring.handlers;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetails {
    private SharedPreferences preference;
    public static final  String preferenceName="UserData";

    public UserDetails(Context context) {
        preference=context.getSharedPreferences(preferenceName, context.MODE_PRIVATE);
    }

    public void saveUserInfo(JSONObject userInfo){
        SharedPreferences.Editor editor = preference.edit();
        try {
            editor.putString("personalId",userInfo.getString("personalId"));
            editor.putString("personalIdmaster",userInfo.getString("personalIdmaster"));
            editor.putString("firstName",userInfo.getString("firstname"));
            editor.putString("lastName",userInfo.getString("lastname"));
            editor.putString("nationalId",userInfo.getString("nationalId"));
            editor.putString("phoneNumber",userInfo.getString("phonenumber"));
            editor.putString("email",userInfo.getString("email"));
            editor.putString("role",userInfo.getString("role"));
            editor.putString("department",userInfo.getString("department"));
            editor.putString("address",userInfo.getString("address"));
            editor.putString("forgetCode",userInfo.getString("forgetcode"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.apply();
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

    public JSONObject getUserInfo() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("personalId",preference.getString("personalId", "not found"));
            jsonObject.put("personalIdmaster",preference.getString("personalIdmaster", "not found"));
            jsonObject.put("firstName",preference.getString("firstName", "not found"));
            jsonObject.put("lastName",preference.getString("lastName", "not found"));
            jsonObject.put("nationalId",preference.getString("nationalId", "not found"));
            jsonObject.put("phoneNumber",preference.getString("phoneNumber", "not found"));
            jsonObject.put("email",preference.getString("email", "not found"));
            jsonObject.put("role",preference.getString("role", "not found"));
            jsonObject.put("address",preference.getString("address", "not found"));
            jsonObject.put("department",preference.getString("department", "not found"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public boolean getUserLogin() {
        return preference.getBoolean("flagLogin", false);
    }

    public void deleteUser(){
        SharedPreferences.Editor editor = preference.edit();
        editor.clear();
        editor.apply();
    }
}
