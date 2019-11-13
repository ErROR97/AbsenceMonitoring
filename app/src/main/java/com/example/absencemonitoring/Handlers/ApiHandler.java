package com.example.absencemonitoring.Handlers;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.absencemonitoring.Utils.DateTime;
import com.example.absencemonitoring.instances.Furlough;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHandler {

    private Activity activity;
    private UserDetails userDetails;
    private String urlLogin = "http://matingrimes.ir/office/login.php";
    private String urlReqLeave = "http://matingrimes.ir/office/reqLeave.php";
    private String urlgetNotifLeave = "http://matingrimes.ir/office/notifLeave.php";
    private String urlAcceptRejectReqLeave = "http://matingrimes.ir/office/acceptRejectReqLeave.php";
    private String urlAcceptControlReqLeave = "http://matingrimes.ir/office/controlReqLeave.php";
    private String urlUpdateArchive = "http://matingrimes.ir/office/updateArchive.php";

    public ApiHandler(Activity activity) {
        this.activity = activity;
    }


    public void logIn(final String personalId, final String password,final responseListenerLogin responseListenerLogin) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                        if (response.trim().split("_")[0].equals("success")) {
                            userDetails = new UserDetails(activity);
                            userDetails.saveUserDetails(personalId,true);
                            try {
                                userDetails.saveUserInfo(new JSONObject(response.trim().split("_")[1]));
                            } catch (JSONException e) { e.printStackTrace(); }
                            responseListenerLogin.onRecived("success");
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


    public void reqLeave(final String fullName,final String personalId, final String personalIdmaster, final String leavetype, final String startTime, final String timeLeave, final String startDate, final String descriptionLeave,final String currentDate,final responseListenerReqLeave responseListenerReqLeave) {
        final StringRequest request = new StringRequest(Request.Method.POST, urlReqLeave,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            responseListenerReqLeave.onRecived("success");
                        } else {
                            responseListenerReqLeave.onRecived("error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    responseListenerReqLeave.onRecived("NoConnectionError");
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fullName", fullName.trim());
                params.put("personalId", personalId.trim());
                params.put("personalIdmaster", personalIdmaster.trim());
                params.put("leavetype", leavetype.trim());
                params.put("startTime", startTime.trim());
                params.put("timeLeave", timeLeave.trim());
                params.put("startDate", startDate.trim());
                params.put("descriptionLeave", descriptionLeave.trim());
                params.put("currentdate", currentDate.trim());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }


    public void getControlReqLeave(final String personalIdmaster, final responseListenerControlReqLeave responseListenerControlReqLeave){
        final StringRequest request = new StringRequest(Request.Method.POST, urlAcceptControlReqLeave,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().split("_")[0].equals("success")){
                            JSONArray jsonArray = null;

                            try {
                                jsonArray = new JSONArray(response.trim().split("_")[1]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            List<Furlough> FurloughList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Furlough furlough = new Furlough();
                                try {
                                    furlough.setTimeLeave(jsonArray.getJSONObject(i).get("timeleave").toString());
                                    furlough.setName(jsonArray.getJSONObject(i).get("fullname").toString());
                                    furlough.setDescriptionLeave(jsonArray.getJSONObject(i).get("descriptionLeave").toString());
                                    furlough.setLeaveType(jsonArray.getJSONObject(i).get("leavetype").toString());
                                    furlough.setPersonalIdemployee(jsonArray.getJSONObject(i).get("personalIdemployee").toString());
                                    furlough.setPersonalIdMaster(jsonArray.getJSONObject(i).get("personalIdmaster").toString());
                                    furlough.setStartDate(jsonArray.getJSONObject(i).get("startdate").toString());
                                    furlough.setStartTime(jsonArray.getJSONObject(i).get("starttime").toString());
                                    furlough.setId(Integer.parseInt(jsonArray.getJSONObject(i).get("id").toString()));
                                    furlough.setStatusArchive(jsonArray.getJSONObject(i).get("statusArchive").toString());
                                    furlough.setCurrentDate(jsonArray.getJSONObject(i).get("currentdate").toString());
                                    furlough.setStatusLeave(jsonArray.getJSONObject(i).get("status").toString());
                                    furlough.setStarted(DateTime.checkFurloughIsStarted(furlough.getStartDate(), furlough.getStartTime()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                FurloughList.add(furlough);
                            }
                            responseListenerControlReqLeave.onRevived(FurloughList);
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
                params.put("personalIdmaster", personalIdmaster.trim());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    public void acceptRejectReqLeave(final boolean status, final String personalIdemployee, final String personalIdmaster, final String leaveType,final String description,final String descriptionLeave, final String currentDate, final responseListenerAcceptRejectReqLeave responseListenerAcceptRejectReqLeave) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlAcceptRejectReqLeave,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            responseListenerAcceptRejectReqLeave.onRecived("success");
                        } else {
                            responseListenerAcceptRejectReqLeave.onRecived("error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    responseListenerAcceptRejectReqLeave.onRecived("NoConnectionError");
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("personalIdemployee", personalIdemployee.trim());
                params.put("personalIdmaster", personalIdmaster.trim());
                params.put("currentDate", currentDate.trim());
                params.put("description", description.trim());
                params.put("descriptionLeave", descriptionLeave.trim());
                params.put("leaveType", leaveType.trim());
                params.put("status",String.valueOf(status));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    public void getNotifReqLeave(final String personalIdmaster, final responseListenerNotifReqLeave responseListenerNotifReqLeave){
        final StringRequest request = new StringRequest(Request.Method.POST, urlgetNotifLeave,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        List<Furlough> FurloughList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Furlough furlough = new Furlough();
                            try {
                                furlough.setTimeLeave(jsonArray.getJSONObject(i).get("timeleave").toString());
                                furlough.setName(jsonArray.getJSONObject(i).get("fullname").toString());
                                furlough.setDescriptionLeave(jsonArray.getJSONObject(i).get("descriptionLeave").toString());
                                furlough.setLeaveType(jsonArray.getJSONObject(i).get("leavetype").toString());
                                furlough.setPersonalIdemployee(jsonArray.getJSONObject(i).get("personalIdemployee").toString());
                                furlough.setPersonalIdMaster(jsonArray.getJSONObject(i).get("personalIdmaster").toString());
                                furlough.setStartDate(jsonArray.getJSONObject(i).get("startdate").toString());
                                furlough.setStartTime(jsonArray.getJSONObject(i).get("starttime").toString());
                                furlough.setId(Integer.parseInt(jsonArray.getJSONObject(i).get("id").toString()));
                                furlough.setCurrentDate(jsonArray.getJSONObject(i).get("currentdate").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            FurloughList.add(furlough);
                        }
                        responseListenerNotifReqLeave.onRevived(FurloughList);
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
                params.put("personalIdmaster", personalIdmaster.trim());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    public void updateStatusArchive(final String id, final responseListenerUpdateArchive responseListenerUpdateArchive) {
        final StringRequest request = new StringRequest(Request.Method.POST, urlUpdateArchive,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        if (response.trim().equals("success")) {
                            responseListenerUpdateArchive.onRecived("success");
                        } else {
                            responseListenerUpdateArchive.onRecived("error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    responseListenerUpdateArchive.onRecived("NoConnectionError");
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id", id.trim());
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


    public interface responseListenerReqLeave {
        void onRecived(String response);
    }

    public interface responseListenerAcceptRejectReqLeave {
        void onRecived(String response);
    }

    public interface responseListenerUpdateArchive {
        void onRecived(String response);
    }

    public interface responseListenerNotifReqLeave{
        void onRevived(List<Furlough> notifReqLeaveList);
    }

    public interface responseListenerControlReqLeave{
        void onRevived(List<Furlough> controlLeaveList);
    }

}

