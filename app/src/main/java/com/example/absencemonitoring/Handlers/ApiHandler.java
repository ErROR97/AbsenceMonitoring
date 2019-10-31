package com.example.absencemonitoring.Handlers;

import android.app.Activity;
import android.util.Log;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ApiHandler {
    private Activity activity;
    private UserDetails userDetails;
    private String urlLogin = "http://matingrimes.ir/office/login.php";
    private String urlgetUserInfo = "http://matingrimes.ir/office/getUserInfo.php";

    public ApiHandler(Activity activity) {
        this.activity = activity;
    }

    public void logIn(final String personalId, final String password,final responseListenerLogin responseListenerLogin) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().split("_")[0].equals("loginSuccess")) {
                            userDetails = new UserDetails(activity);
                            userDetails.saveUserDetails(personalId,true);
                            userDetails.saveUserRole(response.trim().split("_")[1]);
                            responseListenerLogin.onRecived(response);
                            activity.finish();
                        } else {
                            responseListenerLogin.onRecived(response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    responseListenerLogin.onRecived("NoConnectionError");
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("personalId", personalId.trim());
                params.put("password", password.trim());
                return params;


            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

    }

    public void getUserInfo(final String personalId,final responseListenerGetInfo responseListenerGetInfo){
        final StringRequest request = new StringRequest(Request.Method.POST, urlgetUserInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("khar", "onResponse: " + response);
                        if (response.trim().split("_")[0].equals("Success")) {
                            userDetails = new UserDetails(activity);
                            userDetails.saveUserInfo(response.trim().split("_")[1],response.trim().split("_")[2],response.trim().split("_")[3],response.trim().split("_")[4],response.trim().split("_")[5],response.trim().split("_")[6],response.trim().split("_")[7]);
                            responseListenerGetInfo.onRecived("Success");
                        } else {
                            responseListenerGetInfo.onRecived("Error");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {

                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("personalId", personalId.trim());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    public interface responseListenerLogin {
        void onRecived(String response);
    }

    public interface responseListenerGetInfo {
        void onRecived(String response);
    }

}

