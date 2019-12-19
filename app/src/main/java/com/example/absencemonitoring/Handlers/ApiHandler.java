package com.example.absencemonitoring.handlers;

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
import com.example.absencemonitoring.instances.Furlough;
import com.example.absencemonitoring.instances.FurloughArchive;
import com.example.absencemonitoring.instances.Sport;


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
    private String urlGetLeaveArchive = "http://matingrimes.ir/office/getLeaveArchive.php";
    private String urlGetSport = "http://matingrimes.ir/office/getSport.php";
    private String urlReqSport = "http://matingrimes.ir/office/sportReq.php";
    private String urlInsertTimeSport = "http://matingrimes.ir/office/insertTimeSport.php";
    private String urlArchiveReqLeaveEmployee = "http://matingrimes.ir/office/archiveReqLeaveEmployee.php";
    private String urlNotifReqEmployee = "http://matingrimes.ir/office/notifReqEmployee.php";
    private String urlControlReqLeaveEmployee = "http://matingrimes.ir/office/controlReqLeaveEmployee.php";



    public ApiHandler(Activity activity) {
        this.activity = activity;
    }


    public void logIn(final String personalId, final String password,final ResponseListenerLogin responseListenerLogin) {

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


    public void reqLeave(final String fullName,final String personalId, final String personalIdmaster, final String leavetype, final String startTime, final String timeLeave, final String startDate, final String descriptionLeave,final String currentDate,final ResponseListenerReqLeave responseListenerReqLeave) {
        final StringRequest request = new StringRequest(Request.Method.POST, urlReqLeave,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("bozekohi", "onRecived: " + response);
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


    public void getControlReqLeave(final String personalIdmaster, final ResponseListenerControlReqLeave responseListenerControlReqLeave){
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
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                FurloughList.add(furlough);
                            }
                            responseListenerControlReqLeave.onRevived(FurloughList);
                        } else {
                            responseListenerControlReqLeave.onRevived(new ArrayList<Furlough>());
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

    public void acceptRejectReqLeave(final boolean status, final String personalIdemployee, final String personalIdmaster, final String leaveType,final String description,final String descriptionLeave, final String currentDate, final ResponseListenerAcceptRejectReqLeave responseListenerAcceptRejectReqLeave) {

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

    public void getNotifReqLeave(final String personalIdmaster, final ResponseListenerNotifReqLeave responseListenerNotifReqLeave){
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

    public void getLeaveArchive(final String personalIdmaster, final ResponseListenerLeaveArchive responseListenerLeaveArchive){
        final StringRequest request = new StringRequest(Request.Method.POST, urlGetLeaveArchive,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;

                        if (response.trim().split("_")[0].equals("success")) {
                            try {
                                jsonArray = new JSONArray(response.trim().split("_")[1]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            List<Furlough> FurloughList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Furlough furlough = new Furlough();
                                try {
                                    furlough.setId(Integer.parseInt(jsonArray.getJSONObject(i).get("id").toString()));
                                    furlough.setName(jsonArray.getJSONObject(i).get("fullname").toString());
                                    furlough.setPersonalIdemployee(jsonArray.getJSONObject(i).get("personalIdemployee").toString());
                                    furlough.setPersonalIdMaster(jsonArray.getJSONObject(i).get("personalIdmaster").toString());
                                    furlough.setLeaveType(jsonArray.getJSONObject(i).get("leavetype").toString());
                                    furlough.setStartTime(jsonArray.getJSONObject(i).get("starttime").toString());
                                    furlough.setTimeLeave(jsonArray.getJSONObject(i).get("timeleave").toString());
                                    furlough.setStartDate(jsonArray.getJSONObject(i).get("startdate").toString());
                                    furlough.setDescription(jsonArray.getJSONObject(i).get("description").toString());
                                    furlough.setDescriptionLeave(jsonArray.getJSONObject(i).get("descriptionLeave").toString());
                                    furlough.setCurrentDate(jsonArray.getJSONObject(i).get("currentdate").toString());
                                    furlough.setStatusArchive(jsonArray.getJSONObject(i).get("statusArchive").toString());
                                    furlough.setStatusLeave(jsonArray.getJSONObject(i).get("status").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                FurloughList.add(furlough);
                            }
                            responseListenerLeaveArchive.onRevived(FurloughList);
                            responseListenerLeaveArchive.onMessage("success");
                        } else {
                            responseListenerLeaveArchive.onMessage("error");
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


    public void getNotifReqEmployee(final String personalId, final ResponseListenerNotifReqLeaveEmployee responseListenerNotifReqLeaveEmployee){
        final StringRequest request = new StringRequest(Request.Method.POST, urlNotifReqEmployee,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;

                        if (response.trim().split("_")[0].equals("success")) {
                            try {
                                jsonArray = new JSONArray(response.trim().split("_")[1]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            List<Furlough> FurloughList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Furlough furlough = new Furlough();
                                try {
                                    furlough.setId(Integer.parseInt(jsonArray.getJSONObject(i).get("id").toString()));
                                    furlough.setName(jsonArray.getJSONObject(i).get("id").toString());
                                    furlough.setPersonalIdemployee(jsonArray.getJSONObject(i).get("id").toString());
                                    furlough.setPersonalIdMaster(jsonArray.getJSONObject(i).get("id").toString());
                                    furlough.setLeaveType(jsonArray.getJSONObject(i).get("leavetype").toString());
                                    furlough.setStartTime(jsonArray.getJSONObject(i).get("starttime").toString());
                                    furlough.setTimeLeave(jsonArray.getJSONObject(i).get("timeleave").toString());
                                    furlough.setStartDate(jsonArray.getJSONObject(i).get("startdate").toString());
                                    furlough.setCurrentDate(jsonArray.getJSONObject(i).get("currentdate").toString());
                                    furlough.setDescriptionLeave(jsonArray.getJSONObject(i).get("descriptionLeave").toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                FurloughList.add(furlough);
                            }
                            responseListenerNotifReqLeaveEmployee.onRevived(FurloughList);
                            responseListenerNotifReqLeaveEmployee.onMessage("success");
                        } else {
                            responseListenerNotifReqLeaveEmployee.onMessage("error");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    responseListenerNotifReqLeaveEmployee.onMessage("NoConnectionError");
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("personalIdemployee", personalId.trim());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    public void getArchiveEmployee(final String personalId, final ResponseListenerArchiveReqLeaveEmployee responseListenerArchiveReqLeaveEmployee){
        final StringRequest request = new StringRequest(Request.Method.POST, urlArchiveReqLeaveEmployee,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;

                        if (response.trim().split("_")[0].equals("success")) {
                            try {
                                jsonArray = new JSONArray(response.trim().split("_")[1]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            List<FurloughArchive> FurloughArchiveList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                FurloughArchive furloughArchive = new FurloughArchive();
                                try {
                                    furloughArchive.setId(Integer.parseInt(jsonArray.getJSONObject(i).get("id").toString()));
                                    furloughArchive.setFullName(jsonArray.getJSONObject(i).get("id").toString());
                                    furloughArchive.setPersonalIdEmployee(jsonArray.getJSONObject(i).get("id").toString());
                                    furloughArchive.setPersonalIdMaster(jsonArray.getJSONObject(i).get("id").toString());
                                    furloughArchive.setLeaveType(jsonArray.getJSONObject(i).get("leavetype").toString());
                                    furloughArchive.setStartTime(jsonArray.getJSONObject(i).get("starttime").toString());
                                    furloughArchive.setTimeLeave(jsonArray.getJSONObject(i).get("timeleave").toString());
                                    furloughArchive.setStartDate(jsonArray.getJSONObject(i).get("startdate").toString());
                                    furloughArchive.setDescription(jsonArray.getJSONObject(i).get("description").toString());

                                    furloughArchive.setCurrentDate(jsonArray.getJSONObject(i).get("currentdate").toString());
                                    furloughArchive.setDescriptionLeave(jsonArray.getJSONObject(i).get("descriptionLeave").toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                FurloughArchiveList.add(furloughArchive);
                            }
                            responseListenerArchiveReqLeaveEmployee.onRevived(FurloughArchiveList);
                            responseListenerArchiveReqLeaveEmployee.onMessage("success");
                        } else {
                            responseListenerArchiveReqLeaveEmployee.onMessage("error");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    responseListenerArchiveReqLeaveEmployee.onMessage("NoConnectionError");
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("personalIdemployee", personalId.trim());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    public void getControllEmployee(final String personalId, final ResponseListenerControlReqLeaveEmployee responseListenerControlReqLeaveEmployee){
        final StringRequest request = new StringRequest(Request.Method.POST, urlControlReqLeaveEmployee,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;

                        if (response.trim().split("_")[0].equals("success")) {
                            try {
                                jsonArray = new JSONArray(response.trim().split("_")[1]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            List<FurloughArchive> FurloughArchiveList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                FurloughArchive furloughArchive = new FurloughArchive();
                                try {
                                    furloughArchive.setId(Integer.parseInt(jsonArray.getJSONObject(i).get("id").toString()));
                                    furloughArchive.setFullName(jsonArray.getJSONObject(i).get("id").toString());
                                    furloughArchive.setPersonalIdEmployee(jsonArray.getJSONObject(i).get("id").toString());
                                    furloughArchive.setPersonalIdMaster(jsonArray.getJSONObject(i).get("id").toString());
                                    furloughArchive.setLeaveType(jsonArray.getJSONObject(i).get("leavetype").toString());
                                    furloughArchive.setStartTime(jsonArray.getJSONObject(i).get("starttime").toString());
                                    furloughArchive.setTimeLeave(jsonArray.getJSONObject(i).get("timeleave").toString());
                                    furloughArchive.setStartDate(jsonArray.getJSONObject(i).get("startdate").toString());
                                    furloughArchive.setDescription(jsonArray.getJSONObject(i).get("description").toString());

                                    furloughArchive.setCurrentDate(jsonArray.getJSONObject(i).get("currentdate").toString());
                                    furloughArchive.setDescriptionLeave(jsonArray.getJSONObject(i).get("descriptionLeave").toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                FurloughArchiveList.add(furloughArchive);
                            }
                            responseListenerControlReqLeaveEmployee.onRevived(FurloughArchiveList);
                            responseListenerControlReqLeaveEmployee.onMessage("success");
                        } else {
                            responseListenerControlReqLeaveEmployee.onMessage("error");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    responseListenerControlReqLeaveEmployee.onMessage("NoConnectionError");
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("personalIdemployee", personalId.trim());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }


    public void getSport(final ResponseListenerGetSport responseListenerGetSport){
        final StringRequest request = new StringRequest(Request.Method.POST, urlGetSport,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;

                        if (response.trim().split("_")[0].equals("success")) {
                            try {
                                jsonArray = new JSONArray(response.trim().split("_")[1]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            List<Sport> sportList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Sport sport = new Sport();
                                try {
                                    sport.setId(Integer.parseInt(jsonArray.getJSONObject(i).get("id").toString()));
                                    sport.setCode(jsonArray.getJSONObject(i).get("code").toString());
                                    sport.setType(jsonArray.getJSONObject(i).get("type").toString());
                                    sport.setTime(jsonArray.getJSONObject(i).get("time").toString());
                                    sport.setDate(new JSONObject(jsonArray.getJSONObject(i).get("date").toString()));
                                    sport.setPersonalIds(new JSONObject(jsonArray.getJSONObject(i).get("personalid").toString()));
                                    sport.setCapacity(new JSONObject(jsonArray.getJSONObject(i).get("capacity").toString()));
                                    sport.setStatus(new JSONObject(jsonArray.getJSONObject(i).get("status").toString()));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                sportList.add(sport);
                            }
                            responseListenerGetSport.onRecieved(sportList);
                            responseListenerGetSport.onMessage("success");
                        } else {
                            responseListenerGetSport.onMessage("error");
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
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }

    public void reqSport(final String personalId, final String fullName, final String jsonArray, final String date, final ResponseListenerReqSport responseListenerReqSport) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlReqSport,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                        if (response.trim().equals("success")) {
                            responseListenerReqSport.onRecieved("success");
                        } else {
                            responseListenerReqSport.onRecieved(response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    responseListenerReqSport.onRecieved("NoConnectionError");
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("personalId", personalId.trim());
                params.put("fullName", fullName.trim());
                params.put("post", jsonArray.trim());
                params.put("date", date.trim());
                return params;


            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

    }

    public void insertTimeSport(final String type, final String capacity, final String time, final String date, final ResponseListenerInserTimeSport responseListenerInserTimeSport) {

        final StringRequest request = new StringRequest(Request.Method.POST, urlInsertTimeSport,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                        if (response.trim().equals("success")) {
                            responseListenerInserTimeSport.onRecieved("success");
                        } else {
                            responseListenerInserTimeSport.onRecieved(response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    responseListenerInserTimeSport.onRecieved("NoConnectionError");
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type", type.trim());
                params.put("capacity", capacity.trim());
                params.put("time", time.trim());
                params.put("date", date.trim());
                return params;


            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

    }

    public void updateStatusArchive(final int id, final ResponseListenerUpdateArchive responseListenerUpdateArchive) {
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
                params.put("id", String.valueOf(id));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);
    }


    public interface ResponseListenerLogin {
        void onRecived(String response);
    }


    public interface ResponseListenerReqLeave {
        void onRecived(String response);
    }

    public interface ResponseListenerAcceptRejectReqLeave {
        void onRecived(String response);
    }

    public interface ResponseListenerUpdateArchive {
        void onRecived(String response);
    }

    public interface ResponseListenerNotifReqLeave {
        void onRevived(List<Furlough> notifReqLeaveList);
    }

    public interface ResponseListenerNotifReqLeaveEmployee {

        void onRevived(List<Furlough> notifReqLeaveEmployeeList);
        void onMessage(String error);

    }

    public interface ResponseListenerArchiveReqLeaveEmployee {

        void onRevived(List<FurloughArchive> archiveReqLeaveEmployeeList);
        void onMessage(String error);

    }

    public interface ResponseListenerControlReqLeaveEmployee {

        void onRevived(List<FurloughArchive> archiveReqLeaveEmployeeList);
        void onMessage(String error);

    }

    public interface ResponseListenerLeaveArchive {
        void onRevived(List<Furlough> leaveArchiveList);

        void onMessage(String error);
    }

    public interface ResponseListenerControlReqLeave {
        void onRevived(List<Furlough> controlLeaveList);
    }

    public interface ResponseListenerInserTimeSport {
        void onRecieved(String response);
    }

    public interface ResponseListenerGetSport {
        void onRecieved(List<Sport> sportLIst);
        void onMessage(String response);
    }

    public interface ResponseListenerReqSport {
        void onRecieved(String response);
    }




}

