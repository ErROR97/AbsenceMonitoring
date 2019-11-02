package com.example.absencemonitoring.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absencemonitoring.Utils.DateTime;
import com.example.absencemonitoring.Handlers.ApiHandler;
import com.example.absencemonitoring.Handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.Utils.InputFilterMinMax;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

public class EmployeeFurloughActivity extends AppCompatActivity {

    Activity activity;
    RelativeLayout employeeFurloughActivity;
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
    CardView officialChkbx, personalChkbx, sickChkbx;
    ImageView imgOfficialChkbx, imgPersonalChkbx, imgSickChkbx;
    ProgressBar progressBar;
    TextView confirmTxt;
    String type = "اداری";
    String inputError = "ورودی های زیر را کنترل کنید";
    boolean error = false;


    @SuppressLint("SetTextI18n")
    private void init() {
        userDetails = new UserDetails(activity);
        apiHandler = new ApiHandler(activity);


        employeeFurloughActivity = findViewById(R.id.activity_employee_furlough);

        officialChkbx = findViewById(R.id.chkbx_official);
        personalChkbx = findViewById(R.id.chkbx_personal);
        sickChkbx = findViewById(R.id.chkbx_sick);

        imgOfficialChkbx = findViewById(R.id.img_chkbx_official);
        imgPersonalChkbx = findViewById(R.id.img_chkbx_personal);
        imgSickChkbx = findViewById(R.id.img_chkbx_sick);


        minStartTime = findViewById(R.id.et_min_start_time);
        hourStartTime = findViewById(R.id.et_hour_start_time);

        minDurationTime = findViewById(R.id.et_min_duration_time);
        hourDurationTime = findViewById(R.id.et_hour_duration_time);
        dayDurationTime = findViewById(R.id.et_day_duration_time);

        dayStartDate = findViewById(R.id.et_day_start_date);
        monthStartDate = findViewById(R.id.et_month_start_date);
        yearStartDate = findViewById(R.id.et_year_start_date);

        descriptionLeave = findViewById(R.id.et_subject);

        backImg = findViewById(R.id.img_back);
        emplooyeeName = findViewById(R.id.txt_employee_name);
        EmplooyeeId = findViewById(R.id.txt_employee_situation);

        done = findViewById(R.id.btn_done);

        progressBar = findViewById(R.id.progressbar);
        confirmTxt = findViewById(R.id.txt_confirm);

        minStartTime.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "59")});
        hourStartTime.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "23")});

        minDurationTime.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "59")});
        hourDurationTime.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "23")});
        hourDurationTime.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "30")});

        dayStartDate.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "31")});
        monthStartDate.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
        yearStartDate.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "99")});





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

        officialChkbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "اداری";
                officialChkbx.setAlpha(1);
                imgOfficialChkbx.setImageResource(R.drawable.ic_tick);
                imgOfficialChkbx.setBackgroundResource(R.drawable.background_check_box_on);

                personalChkbx.setAlpha(.7f);
                imgPersonalChkbx.setImageResource(0);
                imgPersonalChkbx.setBackgroundResource(R.drawable.background_check_box_off);

                sickChkbx.setAlpha(.7f);
                imgSickChkbx.setImageResource(0);
                imgSickChkbx.setBackgroundResource(R.drawable.background_check_box_off);

            }
        });


        personalChkbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = "شخصی";

                personalChkbx.setAlpha(1);
                imgPersonalChkbx.setImageResource(R.drawable.ic_tick);
                imgPersonalChkbx.setBackgroundResource(R.drawable.background_check_box_on);

                officialChkbx.setAlpha(.7f);
                imgOfficialChkbx.setImageResource(0);
                imgOfficialChkbx.setBackgroundResource(R.drawable.background_check_box_off);

                sickChkbx.setAlpha(.7f);
                imgSickChkbx.setImageResource(0);
                imgSickChkbx.setBackgroundResource(R.drawable.background_check_box_off);

            }
        });


        sickChkbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = "استعلاجی";

                sickChkbx.setAlpha(1);
                imgSickChkbx.setImageResource(R.drawable.ic_tick);
                imgSickChkbx.setBackgroundResource(R.drawable.background_check_box_on);

                officialChkbx.setAlpha(.7f);
                imgOfficialChkbx.setImageResource(0);
                imgOfficialChkbx.setBackgroundResource(R.drawable.background_check_box_off);

                personalChkbx.setAlpha(.7f);
                imgPersonalChkbx.setImageResource(0);
                imgPersonalChkbx.setBackgroundResource(R.drawable.background_check_box_off);
            }
        });





        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (minStartTime.length() == 0) {
                    inputError+= "\n-دقیقه زمان شروع";
                    error = true;
                }
                if (hourStartTime.length() == 0) {
                    inputError += "\n-ساعت زمان شروع";
                    error = true;
                }
                if (minDurationTime.length() == 0) {
                    inputError += "\n-دقیقه مدت زمان";
                    error = true;
                }
                if (hourDurationTime.length() == 0) {
                    inputError += "\n-ساعت مدت زمان";
                    error = true;
                }
                if (dayDurationTime.length() == 0) {
                    inputError += "\n-روز مدت زمان";
                    error = true;
                }
                if (dayStartDate.length() == 0) {
                    inputError += "\n-روز تاریخ شروع";
                    error = true;
                }
                if (monthStartDate.length() == 0) {
                    inputError += "\n-ماه تاریخ شروع";
                    error = true;
                }
                if (yearStartDate.length() == 0) {
                    inputError += "\n-سال تاریخ شروع";
                    error = true;
                }

                if (error) {
                    final Snackbar snackbar = Snackbar.make(employeeFurloughActivity, inputError, Snackbar.LENGTH_INDEFINITE);


                    ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.light_yellow));
                    TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setMaxLines(9);
                    textView.setTextSize(14);
                    textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.iransansmobile_light));
                    textView.setTextColor(getResources().getColor(R.color.red));

                    snackbar.setActionTextColor(getResources().getColor(R.color.light_yellow));
                    TextView snackbarActionView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_action);
                    snackbarActionView.setTextSize(18);
                    snackbarActionView.setBackground(getResources().getDrawable(R.drawable.background_ripple_button));
                    snackbarActionView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.iransansmobile_medium));

                    snackbar.setAction("باشه", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    }).show();

                    inputError = "ورودی های زیر را کنترل کنید";
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    confirmTxt.setVisibility(View.INVISIBLE);
                    done.setEnabled(false);
                    apiHandler.reqLeave(emplooyeeName.getText().toString(), userDetails.getUserDetails(), "9537063", type,
                            DateTime.timeToString(0, Integer.parseInt(hourStartTime.getText().toString()), Integer.parseInt(minStartTime.getText().toString())),
                            DateTime.timeToString(Integer.parseInt(dayDurationTime.getText().toString()), Integer.parseInt(hourDurationTime.getText().toString()), Integer.parseInt(minDurationTime.getText().toString())),
                            DateTime.dateToString(Integer.parseInt(yearStartDate.getText().toString()), Integer.parseInt(monthStartDate.getText().toString()), Integer.parseInt(dayStartDate.getText().toString())), descriptionLeave.getText().toString(),
                            new ApiHandler.responseListenerReqLeave() {
                                @Override
                                public void onRecived(String response) {
                                    if (response.trim().equals("success")) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        confirmTxt.setVisibility(View.VISIBLE);
                                        done.setEnabled(true);

                                        setResult(3);
                                        finish();
                                    }
                                }
                            });
                }

            }
        });
    }

    public int getpixel(int dp) {
        return dp * (int) getApplicationContext().getResources().getDisplayMetrics().density;
    }

    public int getdp(int pixel) {
        return pixel / (int) getApplicationContext().getResources().getDisplayMetrics().density;
    }

}
