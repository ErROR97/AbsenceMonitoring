package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import ir.huri.jcal.JalaliCalendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.ControlAdapter;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.instances.Transport;
import com.example.absencemonitoring.utils.Formating;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class TransportReserveActivity extends AppCompatActivity {

    ImageView backImg;
    TextView openWeekdaysDialogTxt;

    CardView morningContainer, afternoonContainer;
    EditText morningAddressEt, afternoonAddressEt;
    CardView morningRequestBtn, afternoonRequestBtn;
    TextView morningBtnTxt, afternoonBtnTxt;
    ProgressBar morningBtnProgressbar, afternoonBtnProgressbar;

    CardView DialogFocusContainer, weekDaysDialogContainer;
    TextView[] DayOfWeekTxts;



    ApiHandler apiHandler;
    UserDetails userDetails;
    List <Transport> preTransportList;



    final String[] persianWeekDays = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه"};
    final String[] weekDays =  {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
    String fullName;
    String personalId;
    int weekDay;
    int choosenWeekDay;



    private void init() {
        backImg = findViewById(R.id.img_back);
        openWeekdaysDialogTxt = findViewById(R.id.txt_open_weekdays_dialog);

        morningContainer = findViewById(R.id.container_morning_trasnport);
        afternoonContainer = findViewById(R.id.container_afternoon_trasnport);


        morningAddressEt = findViewById(R.id.et_morning_address);
        afternoonAddressEt = findViewById(R.id.et_afternoon_address);

        morningRequestBtn = findViewById(R.id.btn_morning_request);
        afternoonRequestBtn = findViewById(R.id.btn_afternoon_request);

        morningBtnTxt = findViewById(R.id.txt_morning_btn);
        afternoonBtnTxt = findViewById(R.id.txt_afternoon_btn);

        morningBtnProgressbar = findViewById(R.id.progressbar_morning_btn);
        afternoonBtnProgressbar = findViewById(R.id.progressbar_afternoon_btn);

        DialogFocusContainer = findViewById(R.id.container_dialog_focus);
        weekDaysDialogContainer = findViewById(R.id.container_weekdays_dialog);



        DayOfWeekTxts = new TextView[7];
        DayOfWeekTxts[0] = findViewById(R.id.txt_saturday);
        DayOfWeekTxts[1] = findViewById(R.id.txt_sunday);
        DayOfWeekTxts[2] = findViewById(R.id.txt_monday);
        DayOfWeekTxts[3] = findViewById(R.id.txt_tuesday);
        DayOfWeekTxts[4] = findViewById(R.id.txt_wednesday);
        DayOfWeekTxts[5] = findViewById(R.id.txt_thursday);
        DayOfWeekTxts[6] = findViewById(R.id.txt_friday);


        apiHandler = new ApiHandler(this);
        userDetails = new UserDetails(this);



        weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        choosenWeekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        handleDaysOfWeekContainer();
        openWeekdaysDialogTxt.setText(DayOfWeekTxts[choosenWeekDay].getText());

        try {
            fullName = userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName");
            personalId = userDetails.getUserInfo().getString("personalId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        checkForPreReserves();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_reserve);

        init();



        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        openWeekdaysDialogTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(true);
            }
        });


        morningRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (morningAddressEt.getText().toString().length() != 0) {
                    morningBtnProgressbar.setVisibility(View.VISIBLE);
                    morningBtnTxt.setVisibility(View.INVISIBLE);
                    morningRequestBtn.setEnabled(false);
                    morningAddressEt.setEnabled(false);


                    Transport transport = new Transport(fullName, personalId, weekDays[choosenWeekDay], "morning", morningAddressEt.getText().toString());
                    apiHandler.insertTransportRequest(transport, new ApiHandler.ResponseListenerReqTransport() {
                        @Override
                        public void onRecieved(String response) {
                            if (response.equals("success")) {
                                morningBtnProgressbar.setVisibility(View.INVISIBLE);
                                morningBtnTxt.setVisibility(View.VISIBLE);
                                morningRequestBtn.setEnabled(true);
                                morningAddressEt.setEnabled(true);
                                checkForPreReserves();
                            }
                        }
                    });
                } else {
                    morningAddressEt.setError("can't be empty");
                }
            }
        });
        
        
        
        afternoonRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (afternoonAddressEt.getText().toString().length() != 0) {
                    afternoonAddressEt.setEnabled(false);
                    afternoonRequestBtn.setEnabled(false);
                    afternoonBtnProgressbar.setVisibility(View.VISIBLE);
                    afternoonBtnTxt.setVisibility(View.INVISIBLE);

                    Transport transport = new Transport(fullName, personalId, weekDays[choosenWeekDay], "afternoon", afternoonAddressEt.getText().toString());
                    apiHandler.insertTransportRequest(transport, new ApiHandler.ResponseListenerReqTransport() {
                        @Override
                        public void onRecieved(String response) {
                            if (response.equals("success")) {
                                afternoonBtnProgressbar.setVisibility(View.INVISIBLE);
                                afternoonBtnTxt.setVisibility(View.VISIBLE);
                                afternoonRequestBtn.setEnabled(true);
                                afternoonAddressEt.setEnabled(true);
                                checkForPreReserves();
                            }
                        }
                    });
                } else {
                    afternoonAddressEt.setError("can't be empty");
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
                    openWeekdaysDialogTxt.setText(DayOfWeekTxts[choosenWeekDay].getText());
                    showDialog(false);
                    checkForPreReserves();
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
            weekDaysDialogContainer.setVisibility(View.VISIBLE);
        } else {
            DialogFocusContainer.setVisibility(View.INVISIBLE);
            DialogFocusContainer.setClickable(false);
            weekDaysDialogContainer.setVisibility(View.INVISIBLE);
        }
    }


    private void checkForPreReserves() {
        preTransportList = new ArrayList<>();
        morningContainer.setAlpha(1);
        afternoonContainer.setAlpha(1);
        morningAddressEt.setEnabled(true);
        morningRequestBtn.setEnabled(true);
        afternoonRequestBtn.setEnabled(true);
        afternoonAddressEt.setEnabled(true);
        morningAddressEt.setText("");
        afternoonAddressEt.setText("");

        apiHandler.getTransport(new ApiHandler.ResponseListenerGetTransport() {
            @Override
            public void onRecieved(List<Transport> transportList) {
                for (int i = 0; i < transportList.size(); i++) {
                    if (transportList.get(i).getPersonalId().equals(personalId)) {
                        preTransportList.add(transportList.get(i));
                    }
                }
                showPreReserves();
            }

            @Override
            public void onMessage(String response) {

            }
        });

    }


    private void showPreReserves() {
        for (int i = 0; i < preTransportList.size(); i++) {
            if (Arrays.asList(weekDays).indexOf(preTransportList.get(i).getDate()) == choosenWeekDay) {
                if (preTransportList.get(i).getShift().equals("morning")) {
                    morningContainer.setAlpha(.5f);
                    morningAddressEt.setEnabled(false);
                    morningRequestBtn.setEnabled(false);
                    morningAddressEt.setText(preTransportList.get(i).getAddress());
                } else {
                    afternoonContainer.setAlpha(.5f);
                    afternoonAddressEt.setEnabled(false);
                    afternoonRequestBtn.setEnabled(false);
                    afternoonAddressEt.setText(preTransportList.get(i).getAddress());
                }
            }
        }
    }
}
