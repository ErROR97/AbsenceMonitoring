package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.huri.jcal.JalaliCalendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.ReserveSportTimeAdapter;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.instances.Sport;
import com.example.absencemonitoring.interfaces.SendSelectedReserverSportTimeListener;
import com.example.absencemonitoring.utils.Formating;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SportReserveActivity extends AppCompatActivity implements SendSelectedReserverSportTimeListener {

    RelativeLayout root;

    ImageView backImg;
    TextView chooseDateTxt;

    RecyclerView volleyballReserveSportTimeRv, footballReserveSportTimeRv, swimmingReserveSportTimeRv;

    TextView volleyballCapacityTxt, footballCapacityTxt, swimmingCapacityTxt;

    CardView sendVolleyballReqBtn, sendFootballReqBtn, sendSwimmingReqBtn;
    ProgressBar sendVolleyballReqProgressbar, sendFootballReqProgressbar, sendSwimmingReqProgressbar;
    TextView sendVolleyballReqTxt, sendFootballReqTxt, sendSwimmingReqTxt;

    TextView emptyListVolleyballTxt, emptyListFootballTxt, emptyListSwimmingTxt;

    CardView DialogFocusContainer, DaysOfWeekDialog;
    TextView[] DayOfWeekTxts;

    ProgressBar mainProgressbar;



    List<Sport> volleyballList, footballList, swimmingList;
    List<Sport> volleyballListSelected, footballListSelected, swimmingListSelected;
    String[] jalaliCalendar;
    String[] weekDays =  {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
    String[] persianWeekDays = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه"};



    ReserveSportTimeAdapter volleyballReserveSportTimeAdapter, footballReserverSportTimeAdapter, swimmingReserverSportItemAdapter;



    ApiHandler apiHandler;
    UserDetails userDetails;



    int weekDay;
    int choosenWeekDay;
    String fullName;
    String personalId;



    @SuppressLint("SetTextI18n")
    private void init() {

        root = findViewById(R.id.root);

        chooseDateTxt = findViewById(R.id.txt_choose_date);
        backImg = findViewById(R.id.img_back);

        volleyballReserveSportTimeRv = findViewById(R.id.rv_reserve_sport_time_volleyball);
        footballReserveSportTimeRv = findViewById(R.id.rv_reserve_sport_time_football);
        swimmingReserveSportTimeRv = findViewById(R.id.rv_reserve_sport_time_swimming);

        volleyballCapacityTxt = findViewById(R.id.txt_capacity_volleyball);
        footballCapacityTxt = findViewById(R.id.txt_capacity_football);
        swimmingCapacityTxt = findViewById(R.id.txt_capacity_swimming);

        sendVolleyballReqBtn = findViewById(R.id.btn_send_volleyball_req);
        sendFootballReqBtn = findViewById(R.id.btn_send_football_req);
        sendSwimmingReqBtn = findViewById(R.id.btn_send_swimming_req);

        sendVolleyballReqProgressbar = findViewById(R.id.progressbar_send_volleyball_req);
        sendFootballReqProgressbar = findViewById(R.id.progressbar_send_football_req);
        sendSwimmingReqProgressbar = findViewById(R.id.progressbar_send_swimming_req);

        sendVolleyballReqTxt = findViewById(R.id.txt_send_volleyball_req);
        sendFootballReqTxt = findViewById(R.id.txt_send_football_req);
        sendSwimmingReqTxt = findViewById(R.id.txt_send_swimming_req);

        emptyListVolleyballTxt = findViewById(R.id.txt_empty_list_volleyball);
        emptyListFootballTxt = findViewById(R.id.txt_empty_list_football);
        emptyListSwimmingTxt = findViewById(R.id.txt_empty_list_swimming);

        DialogFocusContainer = findViewById(R.id.container_dialog_focus);
        DaysOfWeekDialog = findViewById(R.id.container_week);

        DayOfWeekTxts = new TextView[7];
        DayOfWeekTxts[0] = findViewById(R.id.txt_saturday);
        DayOfWeekTxts[1] = findViewById(R.id.txt_sunday);
        DayOfWeekTxts[2] = findViewById(R.id.txt_monday);
        DayOfWeekTxts[3] = findViewById(R.id.txt_tuesday);
        DayOfWeekTxts[4] = findViewById(R.id.txt_wednesday);
        DayOfWeekTxts[5] = findViewById(R.id.txt_thursday);
        DayOfWeekTxts[6] = findViewById(R.id.txt_friday);

        mainProgressbar = findViewById(R.id.progressbar_main);



        jalaliCalendar = new JalaliCalendar(new GregorianCalendar()).toString().split("-");



        apiHandler = new ApiHandler(this);
        userDetails = new UserDetails(this);



        weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        choosenWeekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        try {
            fullName = userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName");
            personalId = userDetails.getUserInfo().getString("personalId");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        chooseDateTxt.setText(persianWeekDays[weekDay]
                + " "
                + Formating.englishDigitsToPersian(jalaliCalendar[0])
                + "/"
                + Formating.englishDigitsToPersian(jalaliCalendar[1])
                + "/"
                + Formating.englishDigitsToPersian(jalaliCalendar[2]));

        handleDaysOfWeekContainer();
        getTimes();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_reserve);

        init();



        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        chooseDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(true);
            }
        });



        sendVolleyballReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (volleyballListSelected.size() > 0) {
                    sendReqBtnEnable(sendVolleyballReqBtn, sendVolleyballReqProgressbar, sendVolleyballReqTxt, false);

                    String json = new Gson().toJson(volleyballListSelected);

                    apiHandler.reqSport(personalId, fullName, json, weekDays[choosenWeekDay], new ApiHandler.ResponseListenerReqSport() {
                        @Override
                        public void onRecieved(String response) {
                            getTimes();
                            volleyballListSelected = new ArrayList<>();
                            sendReqBtnEnable(sendVolleyballReqBtn, sendVolleyballReqProgressbar, sendVolleyballReqTxt, true);
                        }
                    });
                } else {
                    showSnackbar("چیزی برای ثبت انتخاب نکرده اید");
                }
            }
        });



        sendFootballReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (footballListSelected.size() > 0) {
                    sendReqBtnEnable(sendFootballReqBtn, sendFootballReqProgressbar, sendFootballReqTxt, false);
                    String json = new Gson().toJson(footballListSelected);

                    apiHandler.reqSport(personalId, fullName, json, weekDays[choosenWeekDay], new ApiHandler.ResponseListenerReqSport() {
                        @Override
                        public void onRecieved(String response) {
                            getTimes();
                            footballListSelected = new ArrayList<>();
                            sendReqBtnEnable(sendFootballReqBtn, sendFootballReqProgressbar, sendFootballReqTxt, true);

                        }
                    });
                } else {
                    showSnackbar("چیزی برای ثبت انتخاب نکرده اید");
                }
            }
        });



        sendSwimmingReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swimmingListSelected.size() > 0) {
                    sendReqBtnEnable(sendSwimmingReqBtn, sendSwimmingReqProgressbar, sendSwimmingReqTxt, false);
                    String json = new Gson().toJson(swimmingListSelected);

                    apiHandler.reqSport(personalId, fullName, json, weekDays[choosenWeekDay], new ApiHandler.ResponseListenerReqSport() {
                        @Override
                        public void onRecieved(String response) {
                            getTimes();
                            swimmingListSelected = new ArrayList<>();
                            sendReqBtnEnable(sendSwimmingReqBtn, sendSwimmingReqProgressbar, sendSwimmingReqTxt, true);
                        }
                    });
                } else {
                    showSnackbar("چیزی برای ثبت انتخاب نکرده اید");
                }
            }
        });



        for (int i = 0; i < DayOfWeekTxts.length; i++) {
            final int finalI = i;
            DayOfWeekTxts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DayOfWeekTxts[choosenWeekDay].setBackgroundResource(0);
                    choosenWeekDay = finalI;
                    DayOfWeekTxts[choosenWeekDay].setBackgroundResource(R.drawable.background_date_reserve_sport_form);
                    chooseDateTxt.setText(DayOfWeekTxts[choosenWeekDay].getText());

                    volleyballListSelected = new ArrayList<>();
                    footballListSelected = new ArrayList<>();
                    swimmingListSelected = new ArrayList<>();

                    showDialog(false);
                    getTimes();
                }
            });
        }
    }





    @SuppressLint("SetTextI18n")
    public void handleDaysOfWeekContainer() {
        for (int i = weekDay; i < 7 + weekDay; i++) {
            String[] jalaliCalendar = new JalaliCalendar(new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + i - weekDay - weekDay)).toString().split("-");

            DayOfWeekTxts[i - weekDay].setText(persianWeekDays[i - weekDay]
                    + " "
                    + Formating.englishDigitsToPersian(jalaliCalendar[0])
                    + "/"
                    + Formating.englishDigitsToPersian(jalaliCalendar[1])
                    + "/"
                    + Formating.englishDigitsToPersian(jalaliCalendar[2]));
            if (i == 2 * (weekDay)) {
                DayOfWeekTxts[i - weekDay].setBackgroundResource(R.drawable.background_date_reserve_sport_form);
            }
        }
    }





    private void showDialog(boolean check) {
        if (check) {
            DialogFocusContainer.setVisibility(View.VISIBLE);
            DialogFocusContainer.setClickable(true);
            DaysOfWeekDialog.setVisibility(View.VISIBLE);
        } else {
            DialogFocusContainer.setVisibility(View.INVISIBLE);
            DialogFocusContainer.setClickable(false);
            DaysOfWeekDialog.setVisibility(View.INVISIBLE);
        }
    }





    private void checkForEmptyList(List<Sport> list, TextView emptyText, CardView btn) {
        if (list.size() == 0) {
            emptyText.setVisibility(View.VISIBLE);
            btn.setEnabled(false);
            btn.setAlpha(.7f);
        } else {
            emptyText.setVisibility(View.INVISIBLE);
            btn.setEnabled(true);
            btn.setAlpha(1);
        }
    }


    private void sendReqBtnEnable(CardView btn, ProgressBar progressBar, TextView text, boolean check) {
        if (check) {
            btn.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
            text.setVisibility(View.VISIBLE);
        } else {
            btn.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            text.setVisibility(View.INVISIBLE);
        }
    }





    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(root, message, Snackbar.LENGTH_LONG);
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines(9);
        textView.setTextSize(14);
        textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.iransansmobile_medium));
        textView.setTextColor(getResources().getColor(R.color.black));

        snackbar.show();
    }





    public void getTimes() {
        DialogFocusContainer.setVisibility(View.VISIBLE);
        mainProgressbar.setVisibility(View.VISIBLE);
        volleyballCapacityTxt.setText("-");
        footballCapacityTxt.setText("-");
        swimmingCapacityTxt.setText("-");

        volleyballList = new ArrayList<>();
        footballList = new ArrayList<>();
        swimmingList = new ArrayList<>();

        apiHandler.getSport(new ApiHandler.ResponseListenerGetSport() {
            @Override
            public void onRecieved(List<Sport> sportLIst) {
                DialogFocusContainer.setVisibility(View.INVISIBLE);
                mainProgressbar.setVisibility(View.INVISIBLE);

                for (int i = 0; i < sportLIst.size(); i++) {
                    if (sportLIst.get(i).getType().equals("volleyball") && sportLIst.get(i).getDate().toString().contains(weekDays[choosenWeekDay])) {
                        volleyballList.add(sportLIst.get(i));
                    } else if (sportLIst.get(i).getType().equals("football") && sportLIst.get(i).getDate().toString().contains(weekDays[choosenWeekDay])) {
                        footballList.add(sportLIst.get(i));
                    } else if (sportLIst.get(i).getType().equals("swimming") && sportLIst.get(i).getDate().toString().contains(weekDays[choosenWeekDay])) {
                        swimmingList.add(sportLIst.get(i));
                    }
                }

                checkForEmptyList(volleyballList, emptyListVolleyballTxt, sendVolleyballReqBtn);
                checkForEmptyList(footballList, emptyListFootballTxt, sendFootballReqBtn);
                checkForEmptyList(swimmingList, emptyListSwimmingTxt, sendSwimmingReqBtn);


                volleyballReserveSportTimeAdapter = new ReserveSportTimeAdapter(SportReserveActivity.this, volleyballList, choosenWeekDay, "volleyball");
                footballReserverSportTimeAdapter = new ReserveSportTimeAdapter(SportReserveActivity.this, footballList, choosenWeekDay, "football");
                swimmingReserverSportItemAdapter = new ReserveSportTimeAdapter(SportReserveActivity.this, swimmingList, choosenWeekDay, "swimming");

                volleyballReserveSportTimeRv.setLayoutManager(new LinearLayoutManager(SportReserveActivity.this));
                footballReserveSportTimeRv.setLayoutManager(new LinearLayoutManager(SportReserveActivity.this));
                swimmingReserveSportTimeRv.setLayoutManager(new LinearLayoutManager(SportReserveActivity.this));

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
    public void onSelectedSend(List<Sport> list, String type, String capcity) {
        if (type.equals("volleyball")) {
            volleyballListSelected = list;
            volleyballCapacityTxt.setText(Formating.englishDigitsToPersian(capcity));
        } else if (type.equals("football")) {
            footballListSelected = list;
            footballCapacityTxt.setText(Formating.englishDigitsToPersian(capcity));
        } else {
            swimmingListSelected = list;
            swimmingCapacityTxt.setText(Formating.englishDigitsToPersian(capcity));
        }
    }


    @Override
    public void onBackPressed() {
        if (DaysOfWeekDialog.getVisibility() == View.VISIBLE) {
            showDialog(false);
        } else {
            super.onBackPressed();
        }
    }
}
