package com.example.absencemonitoring.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absencemonitoring.DateTime;
import com.example.absencemonitoring.Handlers.ApiHandler;
import com.example.absencemonitoring.Handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.notifReqLeave;

import org.json.JSONException;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class EmployeeFurloughActivity extends AppCompatActivity {

    Activity activity;
    ImageView backImg;
    TextView emplooyeeName , EmplooyeeId;
    EditText minStartTime, hourStartTime;
    EditText minDurationTime, hourDurationTime, dayDurationTime;
    EditText dayStartDate, monthStartDate, yearStartDate;
    EditText descriptionLeave;
    UserDetails userDetails;
    ApiHandler apiHandler;
    CardView done;
    DateTime dateTime;

    @SuppressLint("SetTextI18n")
    private void init() {
        userDetails = new UserDetails(activity);
        apiHandler = new ApiHandler(activity);


        minStartTime = findViewById(R.id.et_min_start_time);
        hourStartTime = findViewById(R.id.et_hour_start_time);

        minDurationTime = findViewById(R.id.et_min_duration_time);
        hourDurationTime = findViewById(R.id.et_hour_duration_time);
        dayDurationTime = findViewById(R.id.et_day_duration_time);

        dayStartDate = findViewById(R.id.et_day_start_date);
        monthStartDate = findViewById(R.id.et_month_start_date);
        yearStartDate = findViewById(R.id.et_year_start_date);

        descriptionLeave = findViewById(R.id.et_description_leave);

        backImg = findViewById(R.id.img_back);
        emplooyeeName = findViewById(R.id.txt_employee_name);
        EmplooyeeId = findViewById(R.id.txt_employee_situation);

        done = findViewById(R.id.btn_done);

        try {
            emplooyeeName.setText(userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName"));
            EmplooyeeId.setText(userDetails.getUserInfo().getString("personalId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_furlough);
        activity = this;

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!minStartTime.getText().toString().equals("")){
                    if ((Integer.parseInt(minStartTime.getText().toString()) > 0) && (Integer.parseInt(minStartTime.getText().toString()) < 60)) {
                        if ((Integer.parseInt(hourStartTime.getText().toString()) > -1) && (Integer.parseInt(hourStartTime.getText().toString()) < 24)) {
                            if ((Integer.parseInt(minDurationTime.getText().toString()) > 0) && (Integer.parseInt(minDurationTime.getText().toString()) < 60)) {
                                if ((Integer.parseInt(hourDurationTime.getText().toString()) > -1) && (Integer.parseInt(hourDurationTime.getText().toString()) < 24)) {
                                    if ((Integer.parseInt(dayDurationTime.getText().toString()) > -1)) {
                                        if ((Integer.parseInt(dayStartDate.getText().toString()) > 0) && (Integer.parseInt(dayStartDate.getText().toString()) < 32)) {
                                            if ((Integer.parseInt(monthStartDate.getText().toString()) > 0) && (Integer.parseInt(monthStartDate.getText().toString()) < 13)){
                                                if ((Integer.parseInt(yearStartDate.getText().toString()) > -1)) {
                                                    if (!descriptionLeave.getText().toString().equals(""))
                                                    {
                                                        apiHandler.reqLeave(userDetails.getUserDetails(),
                                                                "9537063",
                                                                "مرخصی اداری",
                                                                DateTime.timeToString(0, Integer.parseInt(hourStartTime.getText().toString()), Integer.parseInt(minStartTime.getText().toString())),
                                                                DateTime.timeToString(Integer.parseInt(dayDurationTime.getText().toString()), Integer.parseInt(hourDurationTime.getText().toString()), Integer.parseInt(minDurationTime.getText().toString())),
                                                                DateTime.dateToString(Integer.parseInt(yearStartDate.getText().toString()), Integer.parseInt(monthStartDate.getText().toString()), Integer.parseInt(dayStartDate.getText().toString())), descriptionLeave.getText().toString(),
                                                                new ApiHandler.responseListenerReqLeave() {
                                                                    @Override
                                                                    public void onRecived(String response) {
                                                                        if (response.trim().equals("Success"))
                                                                            Toast.makeText(activity, "Success",Toast.LENGTH_LONG).show();
                                                                    }
                                                                }
                                                        );
                                                        apiHandler.getNotifReqLeave("9537063", new ApiHandler.responseListenerNotifReqLeave() {
                                                            @Override
                                                            public void onRevived(List<notifReqLeave> notifReqLeaveList) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

    }
}
