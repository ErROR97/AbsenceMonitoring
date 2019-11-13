package com.example.absencemonitoring.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.example.absencemonitoring.Utils.Formating;
import com.google.android.material.snackbar.Snackbar;
import com.shawnlin.numberpicker.NumberPicker;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import ir.huri.jcal.JalaliCalendar;

public class EmployeeFurloughActivity extends AppCompatActivity {

    Activity activity;
    RelativeLayout employeeFurloughActivity;
    ImageView backImg;
    TextView emplooyeeName , EmplooyeeId;
    
    EditText descriptionLeave;
    TextView startTimeMinTxt, startTimeHourTxt;
    TextView amountTimeMinTxt, amountTimeHourTxt, amountTimeDayTxt;
    TextView startDateYearTxt, startDateMonthTxt, startDateDayTxt;
    
    CardView done;

    RelativeLayout startTimeEditContainer;
    RelativeLayout darkenBackground;
    NumberPicker startHourNumpic, startMinNumpic;
    CardView numpicStartTimeDialog;
    CardView confirmStartTimeCrd, cancelStartTimeCrd;
    String[] minArray, hourArray;

    RelativeLayout amountTimeEditContainer;
    CardView amountTimeNumpicDialog;
    NumberPicker amountDayNumpic, amountHourNumpic, amountMinNumpic;
    CardView confirmAmountTimeCrd, cancelAmountTimeCrd;
    String[] amountDayArray;

    RelativeLayout startDateEditContainer;
    CardView startDateNumpicDialog;
    NumberPicker startDateDayNumpic, startDateMonthNumpic, startDateYearNumpic;
    CardView confirmstartDateCrd, cancelStartDateCrd;
    String[] dayArray, monthArray, yearArray;

    CardView officialChkbx, personalChkbx, sickChkbx;
    ImageView imgOfficialChkbx, imgPersonalChkbx, imgSickChkbx;
    ProgressBar progressBar;
    TextView currentDateTxt;
    TextView confirmTxt;

    UserDetails userDetails;
    ApiHandler apiHandler;
    String type = "اداری";
    String inputError = "ورودی های زیر را کنترل کنید";
    String[] jalaliCalendar;
    boolean error = false;


    @SuppressLint("SetTextI18n")
    private void init() {
        jalaliCalendar = new JalaliCalendar(new GregorianCalendar()).toString().split("-");

        userDetails = new UserDetails(activity);
        apiHandler = new ApiHandler(activity);

        minArray = displayValueOfNumpic(60, 0);
        hourArray = displayValueOfNumpic(24, 0);
        amountDayArray = displayValueOfNumpic(31, 0);
        monthArray = displayValueOfNumpic(12, 1);
        yearArray = displayValueOfNumpic(15, Integer.parseInt(jalaliCalendar[0]));

//        esfandLengthinLeapYear(Integer.parseInt(jalaliCalendar[0]), Integer.parseInt(jalaliCalendar[1]));


        darkenBackground = findViewById(R.id.darken_background);
        employeeFurloughActivity = findViewById(R.id.activity_employee_furlough);

        officialChkbx = findViewById(R.id.chkbx_official);
        personalChkbx = findViewById(R.id.chkbx_personal);
        sickChkbx = findViewById(R.id.chkbx_sick);
        imgOfficialChkbx = findViewById(R.id.img_chkbx_official);
        imgPersonalChkbx = findViewById(R.id.img_chkbx_personal);
        imgSickChkbx = findViewById(R.id.img_chkbx_sick);

        startTimeMinTxt = findViewById(R.id.txt_start_time_min);
        startTimeHourTxt = findViewById(R.id.txt_start_time_hour);

        amountTimeMinTxt = findViewById(R.id.txt_amount_time_min);
        amountTimeHourTxt = findViewById(R.id.txt_amount_time_hour);
        amountTimeDayTxt = findViewById(R.id.txt_amount_time_day);

        startDateYearTxt = findViewById(R.id.txt_start_date_year);
        startDateMonthTxt = findViewById(R.id.txt_start_date_month);
        startDateDayTxt = findViewById(R.id.txt_start_date_day);


        startTimeEditContainer = findViewById(R.id.container_edit_start_time);
        numpicStartTimeDialog = findViewById(R.id.dialog_numpic_start_time);
        startHourNumpic = findViewById(R.id.numpic_start_time_hour);
        startMinNumpic = findViewById(R.id.numpic_start_time_min);
        confirmStartTimeCrd = findViewById(R.id.crd_confirm_start_time_dialog);
        cancelStartTimeCrd = findViewById(R.id.crd_cancel_start_time_dialog);

        amountTimeEditContainer = findViewById(R.id.container_edit_amount_time);
        amountTimeNumpicDialog = findViewById(R.id.dialog_numpic_amount_time);
        amountDayNumpic = findViewById(R.id.numpic_amount_time_day);
        amountHourNumpic = findViewById(R.id.numpic_amount_time_hour);
        amountMinNumpic = findViewById(R.id.numpic_amount_time_min);
        confirmAmountTimeCrd = findViewById(R.id.crd_confirm_amount_time_dialog);
        cancelAmountTimeCrd = findViewById(R.id.crd_cancel_amount_time_dialog);

        startDateEditContainer = findViewById(R.id.container_edit_start_date);
        startDateNumpicDialog = findViewById(R.id.dialog_numpic_start_date);
        startDateYearNumpic = findViewById(R.id.numpic_start_date_year);
        startDateMonthNumpic = findViewById(R.id.numpic_start_date_month);
        startDateDayNumpic = findViewById(R.id.numpic_start_date_day);
        confirmstartDateCrd = findViewById(R.id.crd_confirm_start_date_dialog);
        cancelStartDateCrd = findViewById(R.id.crd_cancel_start_date_dialog);

        startTimeMinTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE))));
        startTimeHourTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY))));

        startDateYearTxt.setText(Formating.englishDigitsToPersian(jalaliCalendar[0]));
        startDateMonthTxt.setText(Formating.englishDigitsToPersian(jalaliCalendar[1]));
        startDateDayTxt.setText(Formating.englishDigitsToPersian(jalaliCalendar[2]));

        startMinNumpic.setMinValue(0);
        startMinNumpic.setMaxValue(59);
        startMinNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        startMinNumpic.setDisplayedValues(minArray);
        startMinNumpic.setValue(Calendar.getInstance().get(Calendar.MINUTE));

        startHourNumpic.setMinValue(0);
        startHourNumpic.setMaxValue(23);
        startHourNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        startHourNumpic.setDisplayedValues(hourArray);
        startHourNumpic.setValue(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

        amountMinNumpic.setMinValue(0);
        amountMinNumpic.setMaxValue(59);
        amountMinNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        amountMinNumpic.setDisplayedValues(minArray);
        amountMinNumpic.setValue(0);

        amountHourNumpic.setMinValue(0);
        amountHourNumpic.setMaxValue(23);
        amountHourNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        amountHourNumpic.setDisplayedValues(hourArray);
        amountHourNumpic.setValue(0);

        amountDayNumpic.setMinValue(0);
        amountDayNumpic.setMaxValue(30);
        amountDayNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        amountDayNumpic.setDisplayedValues(amountDayArray);
        amountDayNumpic.setValue(1);

        startDateYearNumpic.setMinValue(Integer.parseInt(jalaliCalendar[0]));
        startDateYearNumpic.setMaxValue(Integer.parseInt(jalaliCalendar[0]) + 10);
        startDateYearNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        startDateYearNumpic.setDisplayedValues(yearArray);

        startDateMonthNumpic.setMinValue(1);
        startDateMonthNumpic.setMaxValue(12);
        startDateMonthNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        startDateMonthNumpic.setDisplayedValues(monthArray);
        startDateMonthNumpic.setValue(Integer.parseInt(jalaliCalendar[1]));


        checkPersianLeapYear();
        numberOfDaysInMonthPersian();

        startDateDayNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        startDateDayNumpic.setValue(Integer.parseInt(jalaliCalendar[2]));


        descriptionLeave = findViewById(R.id.et_subject);

        backImg = findViewById(R.id.img_back);
        emplooyeeName = findViewById(R.id.txt_employee_name);
        EmplooyeeId = findViewById(R.id.txt_employee_situation);

        done = findViewById(R.id.btn_done);

        progressBar = findViewById(R.id.progressbar);
        confirmTxt = findViewById(R.id.txt_confirm);

        currentDateTxt = findViewById(R.id.txt_current_date);
        JalaliCalendar jalaliCalendar = new JalaliCalendar(new GregorianCalendar());
        currentDateTxt.setText(jalaliCalendar.toString().split("-")[0] + "/" + jalaliCalendar.toString().split("-")[1] + "/"
        + jalaliCalendar.toString().split("-")[2]);


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



        startTimeEditContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkenBackground.setVisibility(View.VISIBLE);
                numpicStartTimeDialog.setVisibility(View.VISIBLE);
            }
        });

        confirmStartTimeCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DateTime.checkForTimeInputValidation(
                        Integer.parseInt(Formating.persianDigitsToEnglish(startDateYearTxt.getText().toString()))
                        , Integer.parseInt(Formating.persianDigitsToEnglish(startDateMonthTxt.getText().toString()))
                        , Integer.parseInt(Formating.persianDigitsToEnglish(startDateDayTxt.getText().toString()))
                        , startHourNumpic.getValue()
                        , startMinNumpic.getValue())) {

                    startTimeMinTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", startMinNumpic.getValue())));
                    startTimeHourTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", startHourNumpic.getValue())));
                    numpicStartTimeDialog.setVisibility(View.GONE);
                    darkenBackground.setVisibility(View.GONE);
                } else {

                    Snackbar snackbar = Snackbar.make(employeeFurloughActivity, "ورودی نباید از زمان حال کمتر باشد", Snackbar.LENGTH_LONG);
                    ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
                    TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextSize(14);
                    textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.iransansmobile_light));
                    textView.setTextColor(getResources().getColor(R.color.white));

                    snackbar.show();

                }
            }
        });

        cancelStartTimeCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numpicStartTimeDialog.setVisibility(View.GONE);
                darkenBackground.setVisibility(View.GONE);
            }
        });



        amountTimeEditContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkenBackground.setVisibility(View.VISIBLE);
                amountTimeNumpicDialog.setVisibility(View.VISIBLE);
            }
        });

        confirmAmountTimeCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountTimeMinTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", amountMinNumpic.getValue())));
                amountTimeHourTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", amountHourNumpic.getValue())));
                amountTimeDayTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", amountDayNumpic.getValue())));
                amountTimeNumpicDialog.setVisibility(View.GONE);
                darkenBackground.setVisibility(View.GONE);
            }
        });

        cancelAmountTimeCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountTimeNumpicDialog.setVisibility(View.GONE);
                darkenBackground.setVisibility(View.GONE);
            }
        });

        startDateEditContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkenBackground.setVisibility(View.VISIBLE);
                startDateNumpicDialog.setVisibility(View.VISIBLE);
            }
        });

        confirmstartDateCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DateTime.checkForTimeInputValidation(
                        startDateYearNumpic.getValue()
                        , startDateMonthNumpic.getValue()
                        , startDateDayNumpic.getValue()
                        , Integer.parseInt(Formating.persianDigitsToEnglish(startTimeHourTxt.getText().toString()))
                        , Integer.parseInt(Formating.persianDigitsToEnglish(startTimeMinTxt.getText().toString())))) {

                    startDateYearTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", startDateYearNumpic.getValue())));
                    startDateMonthTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", startDateMonthNumpic.getValue())));
                    startDateDayTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", startDateDayNumpic.getValue())));
                    startDateNumpicDialog.setVisibility(View.GONE);
                    darkenBackground.setVisibility(View.GONE);
                } else {

                    Snackbar snackbar = Snackbar.make(employeeFurloughActivity, "ورودی نباید از زمان حال کمتر باشد", Snackbar.LENGTH_LONG);
                    ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
                    TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextSize(14);
                    textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.iransansmobile_light));
                    textView.setTextColor(getResources().getColor(R.color.white));

                    snackbar.show();

                }
            }
        });

        cancelStartDateCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateNumpicDialog.setVisibility(View.GONE);
                darkenBackground.setVisibility(View.GONE);
            }
        });

        startDateYearNumpic.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                checkPersianLeapYear();
            }
        });

        startDateMonthNumpic.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberOfDaysInMonthPersian();
            }
        });





        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                confirmTxt.setVisibility(View.INVISIBLE);
                done.setEnabled(false);
                apiHandler.reqLeave(emplooyeeName.getText().toString(), userDetails.getUserDetails(), "9537063", type,
                        "00" + ":" + Formating.persianDigitsToEnglish(startTimeHourTxt.getText().toString()) + ":" + Formating.persianDigitsToEnglish(startTimeMinTxt.getText().toString()),
                        Formating.persianDigitsToEnglish(amountTimeDayTxt.getText().toString()) + ":" + Formating.persianDigitsToEnglish(amountTimeHourTxt.getText().toString()) + ":" + Formating.persianDigitsToEnglish(amountTimeMinTxt.getText().toString()),
                        Formating.persianDigitsToEnglish(startDateYearTxt.getText().toString()) + "/" + Formating.persianDigitsToEnglish(startDateMonthTxt.getText().toString()) + "/" + Formating.persianDigitsToEnglish(startDateDayTxt.getText().toString()),
                        descriptionLeave.getText().toString(),
                        currentDateTxt.getText().toString(),
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
        });
    }


    public String[] displayValueOfNumpic(int num, int start) {
        String[] temp = new String[num];
        for (int i = 0; i < temp.length ; i++) {
            temp[i] = Formating.englishDigitsToPersian(String.format("%02d", i + start));
        }
        return temp;
    }

    public void esfandLengthinLeapYear(int year, int month) {
        String[] JalaliCalendar = new JalaliCalendar(new GregorianCalendar()).toString().split("-");
        if (month <= 6) {
            dayArray = displayValueOfNumpic(32, 1);
        } else {
            if (month == 12 && DateTime.isPersianLeapYear(year)) {
                dayArray = displayValueOfNumpic(30, 1);
            } else {
                dayArray = displayValueOfNumpic(29, 1);
            }
        }
    }

    private void numberOfDaysInMonthPersian() {
        int[] array = {1, 5, 9, 13, 17, 22, 26, 30};

        if (startDateMonthNumpic.getValue() <= 6) {
            startDateDayNumpic.setMaxValue(31);
            String[] displayedNumbers = new String[31 + 1];
            for (int i = 0; i < displayedNumbers.length; i++) {
                displayedNumbers[i] = String.valueOf(Formating.englishDigitsToPersian(String.valueOf(1 + i)));
            }
            startDateDayNumpic.setDisplayedValues(displayedNumbers);
        } else if (startDateMonthNumpic.getValue() > 6 && startDateMonthNumpic.getValue() <= 11) {
            startDateDayNumpic.setMaxValue(30);
            String[] displayedNumbers = new String[30 + 1];
            for (int i = 0; i < displayedNumbers.length; i++) {
                displayedNumbers[i] = String.valueOf(Formating.englishDigitsToPersian(String.valueOf(1 + i)));
            }
            startDateDayNumpic.setDisplayedValues(displayedNumbers);
        } else if (startDateMonthNumpic.getValue() == 12 && !Arrays.asList(array).contains(startDateYearNumpic.getValue() % 33)) {
            startDateDayNumpic.setMaxValue(29);
        } else if (startDateMonthNumpic.getValue() == 12 && Arrays.asList(array).contains(startDateYearNumpic.getValue() % 33)) {
            startDateDayNumpic.setMaxValue(30);
        }
    }

    public void checkPersianLeapYear() {

        if (startDateMonthNumpic.getValue() == 12 && !DateTime.isPersianLeapYear(startDateYearNumpic.getValue())) {
            startDateDayNumpic.setMaxValue(29);
        } else if (startDateMonthNumpic.getValue() == 12 && DateTime.isPersianLeapYear(startDateYearNumpic.getValue())) {
            startDateDayNumpic.setMaxValue(30);
        }
    }

    public int getpixel(int dp) {
        return dp * (int) getApplicationContext().getResources().getDisplayMetrics().density;
    }

    public int getdp(int pixel) {
        return pixel / (int) getApplicationContext().getResources().getDisplayMetrics().density;
    }

}
