package com.example.absencemonitoring.activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.Utils.DateTime;
import com.example.absencemonitoring.Utils.Formating;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class FurloughDetailsActivity extends AppCompatActivity {
    TextView nameTxt, personalIdTxt, typeTxt, daysTxt, hoursTxt, minsTxt, startDateTxt, startTimeTxt, endDateTxt, endTimeTxt;

    String remaindTime = "";
    String StartDate = "";
    String StartTime = "";
    String amountTime = "";

    Handler handler;
    Runnable runnable;

    public void init() {

        handler = new Handler();

        nameTxt = findViewById(R.id.txt_name);
        personalIdTxt = findViewById(R.id.txt_personal_code);
        typeTxt = findViewById(R.id.txt_type);
        daysTxt = findViewById(R.id.txt_days);
        hoursTxt = findViewById(R.id.txt_hours);
        minsTxt = findViewById(R.id.txt_mins);
        startDateTxt = findViewById(R.id.txt_start_date);
        startTimeTxt = findViewById(R.id.txt_start_time);
        endDateTxt = findViewById(R.id.txt_end_date);
        endTimeTxt = findViewById(R.id.txt_end_time);

        StartDate = getIntent().getStringExtra("startDate");
        StartTime = getIntent().getStringExtra("startTime");
        amountTime = getIntent().getStringExtra("amountTime");

        nameTxt.setText(getIntent().getStringExtra("name"));
        personalIdTxt.setText(Formating.englishDigitsToPersian(getIntent().getStringExtra("personalId")));
        typeTxt.setText(getIntent().getStringExtra("type"));


        if (getIntent().getBooleanExtra("isStarted", false)) {
            remaindTime = DateTime.calculateRemainingTime(StartDate, StartTime, amountTime);
        } else {
            remaindTime = String.format("%02d", Integer.parseInt(amountTime.split(":")[0]))
                    + ":" + String.format("%02d", Integer.parseInt(amountTime.split(":")[1]))
                    + ":" + String.format("%02d", Integer.parseInt(amountTime.split(":")[2]));
        }

        daysTxt.setText(Formating.englishDigitsToPersian(remaindTime.split(":")[0]));
        hoursTxt.setText(Formating.englishDigitsToPersian(remaindTime.split(":")[1]));
        minsTxt.setText(Formating.englishDigitsToPersian(remaindTime.split(":")[2]));

        startDateTxt.setText(Formating.cleanTimeOrDateFormat(getIntent().getStringExtra("startDate"), "/"));
        startTimeTxt.setText(Formating.cleanTimeOrDateFormat(StartTime.split(":")[1] + ":" + StartTime.split(":")[2], ":"));

        if (DateTime.checkForDailyOrHourly(amountTime).equals("روزانه")) {
            endDateTxt.setText(Formating.cleanTimeOrDateFormat(DateTime.calculateEndDate(StartDate, amountTime), "/"));
            endTimeTxt.setText(Formating.cleanTimeOrDateFormat(StartTime.split(":")[1] + ":" + StartTime.split(":")[2], ":"));
        } else {
            endDateTxt.setText(Formating.cleanTimeOrDateFormat(StartDate, "/"));
            endTimeTxt.setText(Formating.cleanTimeOrDateFormat(DateTime.calculateEndTime(StartTime, amountTime), ":"));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furlough_details);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();
        updateemainingTime();


    }

    private void updateemainingTime() {

        runnable = new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                int secs = calendar.get(Calendar.SECOND);

                if (secs == 0) {
                    if (DateTime.checkFurloughIsStarted(StartDate, StartTime)) {
                        remaindTime = DateTime.calculateRemainingTime(StartDate, StartTime, amountTime);
                        daysTxt.setText(Formating.englishDigitsToPersian(remaindTime.split(":")[0]));
                        hoursTxt.setText(Formating.englishDigitsToPersian(remaindTime.split(":")[1]));
                        minsTxt.setText(Formating.englishDigitsToPersian(remaindTime.split(":")[2]));
                    }
                }
                handler.postDelayed(runnable, 1000);
            }
        };
        runnable.run();
    }
}
