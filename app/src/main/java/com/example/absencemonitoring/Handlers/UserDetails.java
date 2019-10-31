package com.example.absencemonitoring.Handlers;

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

    public void saveUserInfo(String firstName , String lastName , String nationalId ,String phoneNumber ,String email ,String role ,String address){
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("firstName",firstName);
        editor.putString("lastName",lastName);
        editor.putString("nationalId",nationalId);
        editor.putString("phoneNumber",phoneNumber);
        editor.putString("email",email);
        editor.putString("role",role);
        editor.putString("address",address);
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
            jsonObject.put("firstName",preference.getString("firstName", "not found"));
            jsonObject.put("lastName",preference.getString("lastName", "not found"));
            jsonObject.put("nationalId",preference.getString("nationalId", "not found"));
            jsonObject.put("phoneNumber",preference.getString("phoneNumber", "not found"));
            jsonObject.put("email",preference.getString("email", "not found"));
            jsonObject.put("role",preference.getString("role", "not found"));
            jsonObject.put("address",preference.getString("address", "not found"));
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
