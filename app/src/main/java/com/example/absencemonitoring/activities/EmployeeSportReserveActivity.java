package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.absencemonitoring.R;

public class EmployeeSportReserveActivity extends AppCompatActivity {
    ImageView backImg;

    private void init() {
        backImg = findViewById(R.id.img_back);
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
    }
}
