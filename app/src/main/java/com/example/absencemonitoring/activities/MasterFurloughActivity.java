package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.Utils.SolarCalendar;
import com.example.absencemonitoring.instances.Furlough;

import java.util.Calendar;

public class MasterFurloughActivity extends AppCompatActivity {

    Furlough furlough;
    TextView typeTxt, nameTxt, amountTypeTxt, amountTxt, dayOrHourTxt, startDateOrTimeLbl, startDateOrTimeTxt, endDateOrTimeLbl, endDateOrTimeTxt;
    TextView currentDateTxt, furloughCodeTxt, decriptionTxt;

    String dayOrTime = "";
    String amount = "";


    private void init() {

        furlough = new Furlough();
        furlough.setName(getIntent().getStringExtra("fullName"));
        furlough.setPersonalIdemployee(getIntent().getStringExtra("personalIdemployee"));
        furlough.setLeaveType(getIntent().getStringExtra("leaveType"));
        furlough.setStartTime(getIntent().getStringExtra("startTime"));
        furlough.setTimeLeave(getIntent().getStringExtra("timeLeave"));
        furlough.setStartDate(getIntent().getStringExtra("startDate"));
        furlough.setDescriptionLeave(getIntent().getStringExtra("descriptionLeave"));
        furlough.setId(getIntent().getIntExtra("id", 0));
        furlough.setCurrentDate(getIntent().getStringExtra("currentDate"));

        amount = getIntent().getStringExtra("dayOrTime").split(" ")[0];
        dayOrTime = getIntent().getStringExtra("dayOrTime").split(" ")[1];


        typeTxt = findViewById(R.id.txt_type);
        nameTxt = findViewById(R.id.txt_name);
        amountTypeTxt = findViewById(R.id.txt_amount_type);
        amountTxt = findViewById(R.id.txt_amount);
        dayOrHourTxt = findViewById(R.id.txt_day_or_hour);
        startDateOrTimeLbl = findViewById(R.id.lbl_start_date_or_time);
        startDateOrTimeTxt = findViewById(R.id.txt_start_date_or_time);
        endDateOrTimeLbl = findViewById(R.id.lbl_end_date_or_time);
        endDateOrTimeTxt = findViewById(R.id.txt_end_date_or_time);
        currentDateTxt = findViewById(R.id.txt_current_date);
        furloughCodeTxt = findViewById(R.id.txt_furlough_code);
        decriptionTxt = findViewById(R.id.txt_description);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_furlough);

        init();

        currentDateTxt.setText(furlough.getCurrentDate());
        furloughCodeTxt.setText(String.valueOf(furlough.getId()));

        typeTxt.setText("درخواست مرخصی " + furlough.getLeaveType());
        amountTxt.setText(amount);

        nameTxt.setText(furlough.getName());
        if (dayOrTime.equals("روز")) {
            amountTypeTxt.setText("روزانه");
            dayOrHourTxt.setText("روز");
            startDateOrTimeLbl.setText("از تاریخ");
            endDateOrTimeLbl.setText("تا تاریخ");
            startDateOrTimeTxt.setText(furlough.getStartDate());
//            endDateOrTimeTxt.setText(SolarCalendar.getCurrentShamsidate(Calendar.getInstance().add(Calendar.DATE, 5)));
        } else {
            amountTypeTxt.setText("ساعتی");
            dayOrHourTxt.setText("ساعت");
            startDateOrTimeLbl.setText("از ساعت");
            endDateOrTimeLbl.setText("تا ساعت");
        }



    }


}
