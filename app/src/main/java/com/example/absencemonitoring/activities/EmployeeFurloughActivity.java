package com.example.absencemonitoring.activities;

import android.os.Bundle;
import android.view.View;

import com.example.absencemonitoring.R;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeFurloughActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_furlough);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }
}
