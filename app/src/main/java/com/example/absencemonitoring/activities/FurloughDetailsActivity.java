package com.example.absencemonitoring.activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.utils.DateTime;
import com.example.absencemonitoring.utils.Formating;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FurloughDetailsActivity extends AppCompatActivity {
    TextView nameTxt, personalIdTxt, typeTxt, daysTxt, hoursTxt, minsTxt, startDateTxt, startTimeTxt, endDateTxt, endTimeTxt;
    TextView remainderLbl, minsLbl, hoursLbl, daysLbl, finishFurloughLbl;
    RelativeLayout remainderContainer;
    CardView finishFurloughCrd;
    ProgressBar progressBar;

    CardView statusTxtConainer;
    TextView statusTxt;

    String remaindTime = "";
    String StartDate = "";
    String StartTime = "";
    String amountTime = "";

    ApiHandler apiHandler;
    Handler handler;
    Runnable runnable;

    public void init() {

        apiHandler = new ApiHandler(this);
        handler = new Handler();

        remainderContainer = findViewById(R.id.container_remainder);

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

        statusTxt = findViewById(R.id.txt_status);
        statusTxtConainer = findViewById(R.id.container_txt_status);

        remainderLbl = findViewById(R.id.lbl_remainder);
        minsLbl = findViewById(R.id.lbl_mins);
        hoursLbl = findViewById(R.id.lbl_hours);
        daysLbl = findViewById(R.id.lbl_days);
        finishFurloughLbl = findViewById(R.id.lbl_finish_furlough);

        progressBar = findViewById(R.id.progressbar);
        finishFurloughCrd = findViewById(R.id.crd_finish_furlough);
        finishFurloughCrd.setEnabled(false);

        StartDate = getIntent().getStringExtra("startDate");
        StartTime = getIntent().getStringExtra("startTime");
        amountTime = getIntent().getStringExtra("amountTime");

        nameTxt.setText(getIntent().getStringExtra("name"));
        personalIdTxt.setText(Formating.englishDigitsToPersian(getIntent().getStringExtra("personalId")));
        typeTxt.setText(getIntent().getStringExtra("type"));


        if (getIntent().getIntExtra("isStarted", 0) == 1) {
            remaindTime = DateTime.calculateRemainingTime(StartDate, StartTime, amountTime);
        } else if (getIntent().getIntExtra("isStarted", 0) == 0) {
            remaindTime = String.format("%02d", Integer.parseInt(amountTime.split(":")[0]))
                    + ":" + String.format("%02d", Integer.parseInt(amountTime.split(":")[1]))
                    + ":" + String.format("%02d", Integer.parseInt(amountTime.split(":")[2]));
        } else {
            remaindTime = DateTime.calculatePassedTime(StartDate, StartTime, amountTime);
            remainderContainer.setBackground(getResources().getDrawable(R.drawable.background_red_radius_10dp));
            remainderLbl.setText("زمان گذشته");
            daysTxt.setTextColor(getResources().getColor(R.color.yellow));
            hoursTxt.setTextColor(getResources().getColor(R.color.yellow));
            minsTxt.setTextColor(getResources().getColor(R.color.yellow));
            daysLbl.setTextColor(getResources().getColor(R.color.light_yellow));
            hoursLbl.setTextColor(getResources().getColor(R.color.light_yellow));
            minsLbl.setTextColor(getResources().getColor(R.color.light_yellow));
            finishFurloughCrd.setEnabled(true);
            finishFurloughCrd.setAlpha(1);
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

        statusTxt.setText(getIntent().getStringExtra("status"));

        if (getIntent().getStringExtra("status").equals("شروع نشده")) {
            statusTxtConainer.setCardBackgroundColor(getResources().getColor(R.color.yellow));
        } else if (getIntent().getStringExtra("status").equals("در حال انجام")) {
            statusTxtConainer.setCardBackgroundColor(getResources().getColor(R.color.light_green));
        } else {
            statusTxtConainer.setCardBackgroundColor(getResources().getColor(R.color.red));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furlough_details);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();
        updateemainingTime();


        finishFurloughCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                finishFurloughLbl.setVisibility(View.INVISIBLE);
                Log.i("gavaznekohi", "onClick: " + getIntent().getStringExtra("id"));
                apiHandler.updateStatusArchive(getIntent().getIntExtra("id", 0), new ApiHandler.ResponseListenerUpdateArchive() {
                    @Override
                    public void onRecived(String response) {
                        if (response.equals("success")) {
                            setResult(6);
                            progressBar.setVisibility(View.INVISIBLE);
                            finishFurloughLbl.setVisibility(View.VISIBLE);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void updateemainingTime() {

        runnable = new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                int secs = calendar.get(Calendar.SECOND);

                if (secs == 0) {
                    if (DateTime.checkFurloughIsStarted(StartDate, StartTime, amountTime) == 1) {
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
