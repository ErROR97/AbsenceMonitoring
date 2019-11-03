package com.example.absencemonitoring.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absencemonitoring.Handlers.ApiHandler;
import com.example.absencemonitoring.Handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.fragments.RecoveryFragment;

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    TextView loginButton;
    TextView recoveryBtn;
    EditText passWord , personalId;
    FrameLayout containerFramelayout;
    RecoveryFragment recoveryFragment;
    ApiHandler apiHandler;
    UserDetails userDetails;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        userDetails = new UserDetails(activity);
        apiHandler = new ApiHandler(activity);

        if (userDetails.getUserLogin()) {
            String role = null;
            try {
                role = userDetails.getUserInfo().getString("role");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (role.trim().equals("master")) {
                startActivity(new Intent(LoginActivity.this, MasterDashboardActivity.class));
            }
            else if (role.trim().equals("employee")){
                startActivity(new Intent(LoginActivity.this, EmployeeDashboardActivity.class));
            }
            else if (role.trim().equals("guard")){
                startActivity(new Intent(LoginActivity.this, GuardDashboardActivity.class));
            }

            apiHandler = new ApiHandler(activity);
            finish();
        }

/*
        apiHandler.getUserInfo(userDetails.getUserDetails(), new ApiHandler.responseListenerGetInfo() {
            @Override
            public void onRecived(String response) {
                if (userDetails.getUserLogin() && response.trim().equals("Success")) {
                    String role = null;
                    try {
                        role = userDetails.getUserInfo().getString("role");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (role.trim().equals("master")) {
                        startActivity(new Intent(LoginActivity.this, MasterDashboardActivity.class));
                    }
                    else if (role.trim().equals("employee")){
                        startActivity(new Intent(LoginActivity.this, EmployeeDashboardActivity.class));
                    }
                    else if (role.trim().equals("guard")){
                        startActivity(new Intent(LoginActivity.this, GuardDashboardActivity.class));
                    }

                    apiHandler = new ApiHandler(activity);
                    finish();
                }
            }
        });
*/

        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.btn_login);
        recoveryBtn = findViewById(R.id.btn_recovery_password);
        personalId = findViewById(R.id.et_personal_code);
        passWord = findViewById(R.id.et_password);
        containerFramelayout = findViewById(R.id.container_fragment);
        recoveryFragment = new RecoveryFragment();


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiHandler.logIn(personalId.getText().toString(), passWord.getText().toString(), new ApiHandler.responseListenerLogin() {
                    @Override
                    public void onRecived(String response) {
                        if (response.trim().split("_")[0].equals("loginSuccess")) {
                            if (response.trim().split("_")[1].equals("master")) {
                                startActivity(new Intent(LoginActivity.this, MasterDashboardActivity.class));
                            }
                            else if (response.trim().split("_")[1].equals("employee")){
                                startActivity(new Intent(LoginActivity.this, EmployeeDashboardActivity.class));
                            }
                            else if (response.trim().split("_")[1].equals("guard")){
                                startActivity(new Intent(LoginActivity.this, GuardDashboardActivity.class));
                            }

                            apiHandler = new ApiHandler(activity);
                            finish();
                        } else {
                            Toast.makeText(activity, response,Toast.LENGTH_LONG).show();
                        }
                    }
                });
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