package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.instances.Furlough;

public class MasterFurloughActivity extends AppCompatActivity {

    Furlough furlough;

    private void init() {

        furlough = new Furlough();
        Intent intent = new Intent();
        furlough.setName(intent.getStringExtra("fullName"));
        furlough.setPersonalIdemployee(intent.getStringExtra("personalIdemployee"));
        furlough.setLeaveType(intent.getStringExtra("leaveType"));
        furlough.setStartTime(intent.getStringExtra("startTime"));
        furlough.setTimeLeave(intent.getStringExtra("timeLeave"));
        furlough.setDescriptionLeave(intent.getStringExtra("descriptionLeave"));
        furlough.setId(Integer.parseInt(intent.getStringExtra("id")));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_furlough);

        init();
    }


}
