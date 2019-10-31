package com.example.absencemonitoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.fragments.RecoveryFragment;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    TextView loginButton;
    TextView recoveryBtn;
    FrameLayout containerFramelayout;
    RecoveryFragment recoveryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.btn_login);
        recoveryBtn = findViewById(R.id.btn_recovery_password);
        containerFramelayout = findViewById(R.id.container_fragment);

        recoveryFragment = new RecoveryFragment();


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MasterDashboardActivity.class));
            }
        });

        recoveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerFramelayout.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.container_fragment, recoveryFragment).addToBackStack("fragment").commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().beginTransaction().remove(recoveryFragment).commit();
        containerFramelayout.setVisibility(View.INVISIBLE);
    }
}
