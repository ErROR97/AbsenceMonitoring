package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.huri.jcal.JalaliCalendar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.ReserveSportTimeAdapter;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.instances.ReserveSportTime;
import com.example.absencemonitoring.instances.Sport;
import com.example.absencemonitoring.interfaces.SendSelectedReserverSportTimeListener;
import com.example.absencemonitoring.utils.Formating;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class EmployeeSportReserveActivity extends AppCompatActivity implements SendSelectedReserverSportTimeListener {
    ImageView backImg;
    RecyclerView volleyballReserveSportTimeRv, footballReserveSportTimeRv, swimmingReserveSportTimeRv;
    CardView sendVolleyballReqBtn, sendFootballReqBtn, sendSwimmingReqBtn;
    TextView chooseDateTxt;
    CardView shadowContainer, weekContainer;
    TextView[] weekDayTxts;
    ProgressBar sendVolleyballReqProgressbar, sendFootballReqProgressbar, sendSwimmingReqProgressbar;
    TextView sendVolleyballReqTxt, sendFootballReqTxt, sendSwimmingReqTxt;

    List<Sport> volleyballList, footballList, swimmingList;
    List<Sport> volleyballListSelected, footballListSelected, swimmingListSelecrted;
    ReserveSportTimeAdapter volleyballReserveSportTimeAdapter, footballReserverSportTimeAdapter, swimmingReserverSportItemAdapter;
    ApiHandler apiHandler;
    UserDetails userDetails;
    String[] jalaliCalendar;
    int weekDay;
    int choosenWeekDay;


        String[] weekDays =  {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
        String[] persianWeekDays = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه"};



    @SuppressLint("SetTextI18n")
    private void init() {

        jalaliCalendar = new JalaliCalendar(new GregorianCalendar()).toString().split("-");
        weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        choosenWeekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        weekDayTxts = new TextView[7];

        sendVolleyballReqProgressbar = findViewById(R.id.progressbar_send_volleyball_req);
        sendFootballReqProgressbar = findViewById(R.id.progressbar_send_football_req);
        sendSwimmingReqProgressbar = findViewById(R.id.progressbar_send_swimming_req);

        sendVolleyballReqTxt = findViewById(R.id.txt_send_volleyball_req);
        sendFootballReqTxt = findViewById(R.id.txt_send_football_req);
        sendSwimmingReqTxt = findViewById(R.id.txt_send_swimming_req);



        apiHandler = new ApiHandler(this);
        userDetails = new UserDetails(this);

        chooseDateTxt = findViewById(R.id.txt_choose_date);
        chooseDateTxt.setText(persianWeekDays[weekDay]
                + " "
                + Formating.englishDigitsToPersian(jalaliCalendar[0])
                + "/"
                + Formating.englishDigitsToPersian(jalaliCalendar[1])
                + "/"
                + Formating.englishDigitsToPersian(jalaliCalendar[2]));


        backImg = findViewById(R.id.img_back);
        volleyballReserveSportTimeRv = findViewById(R.id.rv_reserve_sport_time_volleyball);
        footballReserveSportTimeRv = findViewById(R.id.rv_reserve_sport_time_football);
        swimmingReserveSportTimeRv = findViewById(R.id.rv_reserve_sport_time_swimming);

        sendVolleyballReqBtn = findViewById(R.id.btn_send_volleyball_req);
        sendFootballReqBtn = findViewById(R.id.btn_send_football_req);
        sendSwimmingReqBtn = findViewById(R.id.btn_send_swimming_req);

        weekContainer = findViewById(R.id.container_week);
        shadowContainer = findViewById(R.id.container_shadow);

        weekDayTxts[0] = findViewById(R.id.txt_saturday);
        weekDayTxts[1] = findViewById(R.id.txt_sunday);
        weekDayTxts[2] = findViewById(R.id.txt_monday);
        weekDayTxts[3] = findViewById(R.id.txt_tuesday);
        weekDayTxts[4] = findViewById(R.id.txt_wednesday);
        weekDayTxts[5] = findViewById(R.id.txt_thursday);
        weekDayTxts[6] = findViewById(R.id.txt_friday);

        handleWeekContainer();

        getTimes();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_sport_reserve);

        init();

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sendVolleyballReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVolleyballReqProgressbar.setVisibility(View.VISIBLE);
                sendVolleyballReqTxt.setVisibility(View.INVISIBLE);
                String json = new Gson().toJson(volleyballList);
                Log.i("gav", "onRecieved: " + json);

                try {
                    apiHandler.reqSport(userDetails.getUserInfo().getString("personalId")
                            , userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName")
                            , json
                            , weekDays[choosenWeekDay]
                            , new ApiHandler.ResponseListenerReqSport() {
                                @Override
                                public void onRecieved(String response) {
                                    getTimes();
                                    sendVolleyballReqProgressbar.setVisibility(View.INVISIBLE);
                                    sendVolleyballReqTxt.setVisibility(View.VISIBLE);
                                    Log.i("gav", "onRecieved: " + response);
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        sendFootballReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFootballReqProgressbar.setVisibility(View.VISIBLE);
                sendFootballReqTxt.setVisibility(View.INVISIBLE);
                String json = new Gson().toJson(volleyballList);
                try {
                    apiHandler.reqSport(userDetails.getUserInfo().getString("personalId")
                            , userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName")
                            , json
                            , weekDays[choosenWeekDay]
                            , new ApiHandler.ResponseListenerReqSport() {
                                @Override
                                public void onRecieved(String response) {
                                    getTimes();
                                    sendFootballReqProgressbar.setVisibility(View.INVISIBLE);
                                    sendFootballReqTxt.setVisibility(View.VISIBLE);
                                    Log.i("gav", "onRecieved: " + response);
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        sendSwimmingReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSwimmingReqProgressbar.setVisibility(View.VISIBLE);
                sendSwimmingReqTxt.setVisibility(View.INVISIBLE);
                String json = new Gson().toJson(volleyballList);
                try {
                    apiHandler.reqSport(userDetails.getUserInfo().getString("personalId")
                            , userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName")
                            , json
                            , weekDays[choosenWeekDay]
                            , new ApiHandler.ResponseListenerReqSport() {
                                @Override
                                public void onRecieved(String response) {
                                    getTimes();
                                    sendSwimmingReqProgressbar.setVisibility(View.INVISIBLE);
                                    sendSwimmingReqTxt.setVisibility(View.VISIBLE);
                                    Log.i("gav", "onRecieved: " + response);
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        chooseDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weekContainer.getVisibility() == View.INVISIBLE) {
                    weekContainer.setVisibility(View.VISIBLE);
                    shadowContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        for (int i = 0; i < weekDayTxts.length; i++) {
            final int finalI = i;
            weekDayTxts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weekDayTxts[choosenWeekDay].setBackgroundResource(0);
                    choosenWeekDay = finalI;
                    weekDayTxts[choosenWeekDay].setBackgroundResource(R.drawable.background_date_reserve_sport_form);
                    weekContainer.setVisibility(View.INVISIBLE);
                    shadowContainer.setVisibility(View.INVISIBLE);
                    chooseDateTxt.setText(weekDayTxts[choosenWeekDay].getText());
                    getTimes();
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    public void handleWeekContainer() {
        for (int i = weekDay; i < 7 + weekDay; i++) {
            String[] jalaliCalendar = new JalaliCalendar(new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + i - weekDay - weekDay)).toString().split("-");

            weekDayTxts[i - weekDay].setText(persianWeekDays[i - weekDay]
                    + " "
                    + Formating.englishDigitsToPersian(jalaliCalendar[0])
                    + "/"
                    + Formating.englishDigitsToPersian(jalaliCalendar[1])
                    + "/"
                    + Formating.englishDigitsToPersian(jalaliCalendar[2]));
            if (i == 2 * (weekDay)) {
                weekDayTxts[i - weekDay].setBackgroundResource(R.drawable.background_date_reserve_sport_form);
            }
        }
    }

    public void getTimes() {
        volleyballList = new ArrayList<>();
        footballList = new ArrayList<>();
        swimmingList = new ArrayList<>();

        apiHandler.getSport(new ApiHandler.ResponseListenerGetSport() {
            @Override
            public void onRecieved(List<Sport> sportLIst) {
                for (int i = 0; i < sportLIst.size(); i++) {
                    if (sportLIst.get(i).getType().equals("volleyball") && sportLIst.get(i).getDate().toString().contains(weekDays[choosenWeekDay])) {
                        volleyballList.add(sportLIst.get(i));
                    } else if (sportLIst.get(i).getType().equals("football") && sportLIst.get(i).getDate().toString().contains(weekDays[choosenWeekDay])) {
                        footballList.add(sportLIst.get(i));
                    } else if (sportLIst.get(i).getType().equals("swimming") && sportLIst.get(i).getDate().toString().contains(weekDays[choosenWeekDay])) {
                        swimmingList.add(sportLIst.get(i));
                    }
                }
                volleyballReserveSportTimeAdapter = new ReserveSportTimeAdapter(EmployeeSportReserveActivity.this, volleyballList, choosenWeekDay);
                footballReserverSportTimeAdapter = new ReserveSportTimeAdapter(EmployeeSportReserveActivity.this, footballList, choosenWeekDay);
                swimmingReserverSportItemAdapter = new ReserveSportTimeAdapter(EmployeeSportReserveActivity.this, swimmingList, choosenWeekDay);

                volleyballReserveSportTimeRv.setLayoutManager(new LinearLayoutManager(EmployeeSportReserveActivity.this));
                footballReserveSportTimeRv.setLayoutManager(new LinearLayoutManager(EmployeeSportReserveActivity.this));
                swimmingReserveSportTimeRv.setLayoutManager(new LinearLayoutManager(EmployeeSportReserveActivity.this));

                volleyballReserveSportTimeRv.setAdapter(volleyballReserveSportTimeAdapter);
                footballReserveSportTimeRv.setAdapter(footballReserverSportTimeAdapter);
                swimmingReserveSportTimeRv.setAdapter(swimmingReserverSportItemAdapter);

            }

            @Override
            public void onMessage(String response) {

            }
        });

    }

    @Override
    public void onSelectedSend(List<Sport> list) {
        if (list.get(0).getType().equals("volleyball")) {
            volleyballListSelected = list;
        } else if (list.get(0).getType().equals("football")) {
            footballListSelected = list;
        } else {
            swimmingListSelecrted = list;
        }
    }
}
