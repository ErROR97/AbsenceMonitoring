package com.example.absencemonitoring.activities;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.utils.DateTime;
import com.example.absencemonitoring.utils.Formating;
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
    // views def--------------------------------------------------------------------------
    RelativeLayout root;

    ImageView backImg;
    TextView currentDateTxt;

    TextView emplooyeeNameTxt, EmplooyeeIdTxt;

    CardView officialChkbxContainer, personalChkbxContainer, sickChkbxContainer;
    ImageView OfficialChkbxImg, PersonalChkbxImg, SickChkbxImg;

    TextView startTimeMinTxt, startTimeHourTxt;
    CardView startTimeEditBtn;

    TextView amountTimeMinTxt, amountTimeHourTxt, amountTimeDayTxt;
    CardView amountTimeEditBtn;

    TextView startDateYearTxt, startDateMonthTxt, startDateDayTxt;
    CardView startDateEditBtn;

    EditText leaveDescriptionEt;

    CardView doneBtn;
    TextView doneTxt;

    ProgressBar progressBar;

    RelativeLayout DialogFocusContainer;

    CardView startTimeNumpicDialog;
    NumberPicker startHourNumpic, startMinNumpic;
    CardView confirmStartTimeBtn, cancelStartTimeBtn;

    CardView amountTimeNumpicDialog;
    NumberPicker amountTimeDayNumpic, amountTimeHourNumpic, amountTimeMinNumpic;
    CardView confirmAmountTimeBtn, cancelAmountTimeBtn;

    CardView startDateNumpicDialog;
    NumberPicker startDateDayNumpic, startDateMonthNumpic, startDateYearNumpic;
    CardView confirmstartDateBtn, cancelStartDateBtn;
    // end of views def---------------------------------------------------------------------------


    // lists def-------------------------------------------------------------------
    String[] jalaliCalendar;
    String[] minArray, hourArray;
    String[] amountDayArray;
    String[] monthArray, yearArray;
    // end of lists def-------------------------------------------------------------------------


    // handlers def--------------------------------------------------------------------------------------
    UserDetails userDetails;
    ApiHandler apiHandler;
    // end of handlers def----------------------------------------------------------------------------


    String type = "اداری";




    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void init() {
        // views init--------------------------------------------------------------------------
        root = findViewById(R.id.activity_employee_furlough);

        backImg = findViewById(R.id.img_back);
        currentDateTxt = findViewById(R.id.txt_current_date);

        emplooyeeNameTxt = findViewById(R.id.txt_employee_name);
        EmplooyeeIdTxt = findViewById(R.id.txt_employee_situation);


        officialChkbxContainer = findViewById(R.id.chkbx_official);
        personalChkbxContainer = findViewById(R.id.chkbx_personal);
        sickChkbxContainer = findViewById(R.id.chkbx_sick);
        OfficialChkbxImg = findViewById(R.id.img_chkbx_official);
        PersonalChkbxImg = findViewById(R.id.img_chkbx_personal);
        SickChkbxImg = findViewById(R.id.img_chkbx_sick);

        startTimeMinTxt = findViewById(R.id.txt_start_time_min);
        startTimeHourTxt = findViewById(R.id.txt_start_time_hour);
        startTimeEditBtn = findViewById(R.id.container_edit_start_time);

        doneBtn = findViewById(R.id.btn_done);
        doneTxt = findViewById(R.id.txt_confirm);

        progressBar = findViewById(R.id.progressbar);

        amountTimeMinTxt = findViewById(R.id.txt_amount_time_min);
        amountTimeHourTxt = findViewById(R.id.txt_amount_time_hour);
        amountTimeDayTxt = findViewById(R.id.txt_amount_time_day);
        amountTimeEditBtn = findViewById(R.id.container_edit_amount_time);

        startDateYearTxt = findViewById(R.id.txt_start_date_year);
        startDateMonthTxt = findViewById(R.id.txt_start_date_month);
        startDateDayTxt = findViewById(R.id.txt_start_date_day);
        startDateEditBtn = findViewById(R.id.container_edit_start_date);

        leaveDescriptionEt = findViewById(R.id.et_subject);

        DialogFocusContainer = findViewById(R.id.darken_background);

        startTimeNumpicDialog = findViewById(R.id.dialog_numpic_start_time);
        startHourNumpic = findViewById(R.id.numpic_start_time_hour);
        startMinNumpic = findViewById(R.id.numpic_start_time_min);
        confirmStartTimeBtn = findViewById(R.id.crd_confirm_start_time_dialog);
        cancelStartTimeBtn = findViewById(R.id.crd_cancel_start_time_dialog);

        amountTimeNumpicDialog = findViewById(R.id.dialog_numpic_amount_time);
        amountTimeDayNumpic = findViewById(R.id.numpic_amount_time_day);
        amountTimeHourNumpic = findViewById(R.id.numpic_amount_time_hour);
        amountTimeMinNumpic = findViewById(R.id.numpic_amount_time_min);
        confirmAmountTimeBtn = findViewById(R.id.crd_confirm_amount_time_dialog);
        cancelAmountTimeBtn = findViewById(R.id.crd_cancel_amount_time_dialog);

        startDateNumpicDialog = findViewById(R.id.dialog_numpic_start_date);
        startDateYearNumpic = findViewById(R.id.numpic_start_date_year);
        startDateMonthNumpic = findViewById(R.id.numpic_start_date_month);
        startDateDayNumpic = findViewById(R.id.numpic_start_date_day);
        confirmstartDateBtn = findViewById(R.id.crd_confirm_start_date_dialog);
        cancelStartDateBtn = findViewById(R.id.crd_cancel_start_date_dialog);
        // end of views init--------------------------------------------------------------------------



        // lists init---------------------------------------------------------------------
        jalaliCalendar = new JalaliCalendar(new GregorianCalendar()).toString().split("-");
        minArray = displayValueOfNumpic(60, 0);
        hourArray = displayValueOfNumpic(24, 0);
        amountDayArray = displayValueOfNumpic(31, 0);
        monthArray = displayValueOfNumpic(12, 1);
        yearArray = displayValueOfNumpic(15, Integer.parseInt(jalaliCalendar[0]));
        // end of lists init---------------------------------------------------------------------



        // handlers init----------------------------------------------------------------------------------
        userDetails = new UserDetails(this);
        apiHandler = new ApiHandler(this);
        // end of handlers init----------------------------------------------------------------------------------



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

        amountTimeMinNumpic.setMinValue(0);
        amountTimeMinNumpic.setMaxValue(59);
        amountTimeMinNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        amountTimeMinNumpic.setDisplayedValues(minArray);
        amountTimeMinNumpic.setValue(0);

        amountTimeHourNumpic.setMinValue(0);
        amountTimeHourNumpic.setMaxValue(23);
        amountTimeHourNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        amountTimeHourNumpic.setDisplayedValues(hourArray);
        amountTimeHourNumpic.setValue(0);

        amountTimeDayNumpic.setMinValue(0);
        amountTimeDayNumpic.setMaxValue(30);
        amountTimeDayNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        amountTimeDayNumpic.setDisplayedValues(amountDayArray);
        amountTimeDayNumpic.setValue(1);

        startDateYearNumpic.setMinValue(Integer.parseInt(jalaliCalendar[0]));
        startDateYearNumpic.setMaxValue(Integer.parseInt(jalaliCalendar[0]) + 10);
        startDateYearNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        startDateYearNumpic.setDisplayedValues(yearArray);

        startDateMonthNumpic.setMinValue(1);
        startDateMonthNumpic.setMaxValue(12);
        startDateMonthNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        startDateMonthNumpic.setDisplayedValues(monthArray);
        startDateMonthNumpic.setValue(Integer.parseInt(jalaliCalendar[1]));


        checkForLeapYearInJalaliCalendar();
        numberOfMonthDaysInJalaliCalendar();

        startDateDayNumpic.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/iransansmobile_light.ttf"));
        startDateDayNumpic.setValue(Integer.parseInt(jalaliCalendar[2]));


        currentDateTxt.setText(startDateYearTxt.getText() + "/" + startDateMonthTxt.getText() + "/" + startDateDayTxt.getText());


        try {
            emplooyeeNameTxt.setText(userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName"));
            EmplooyeeIdTxt.setText(Formating.englishDigitsToPersian(userDetails.getUserInfo().getString("personalId")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_furlough);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();



        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        officialChkbxContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxHandler("اداری");
            }
        });



        personalChkbxContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxHandler("شخصی");
            }
        });



        sickChkbxContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxHandler("استعلاجی");
            }
        });



        startTimeEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerDialogs("time", true);
            }
        });



        cancelStartTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerDialogs("time", false);
                startHourNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(startTimeHourTxt.getText().toString())));
                startMinNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(startTimeMinTxt.getText().toString())));
            }
        });



        confirmStartTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DateTime.checkForTimeInputValidation(startDateYearNumpic.getValue(), startDateMonthNumpic.getValue(), startDateDayNumpic.getValue(), startHourNumpic.getValue(), startMinNumpic.getValue())) {
                    startTimeMinTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", startMinNumpic.getValue())));
                    startTimeHourTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", startHourNumpic.getValue())));
                    handlerDialogs("time", false);
                } else {
                    snackbarHandler("ورودی نباید از زمان حال کمتر باشد");
                }
            }
        });



        amountTimeEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerDialogs("amount", true);
            }
        });



        cancelAmountTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerDialogs("amount", false);
                amountTimeDayNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(amountTimeDayTxt.getText().toString())));
                amountTimeHourNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(amountTimeHourTxt.getText().toString())));
                amountTimeMinNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(amountTimeMinTxt.getText().toString())));
            }
        });



        confirmAmountTimeBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                amountTimeMinTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", amountTimeMinNumpic.getValue())));
                amountTimeHourTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", amountTimeHourNumpic.getValue())));
                amountTimeDayTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", amountTimeDayNumpic.getValue())));
                handlerDialogs("amount", false);
            }
        });



        startDateEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerDialogs("date", true);
            }
        });



        cancelStartDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               handlerDialogs("date", false);
                startDateYearNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(startDateYearTxt.getText().toString())));
                startDateMonthNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(startDateMonthTxt.getText().toString())));
                startDateDayNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(startDateDayTxt.getText().toString())));
            }
        });



        confirmstartDateBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                if (DateTime.checkForTimeInputValidation(startDateYearNumpic.getValue(), startDateMonthNumpic.getValue(), startDateDayNumpic.getValue(), startHourNumpic.getValue(), startMinNumpic.getValue())) {
                    startDateYearTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", startDateYearNumpic.getValue())));
                    startDateMonthTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", startDateMonthNumpic.getValue())));
                    startDateDayTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", startDateDayNumpic.getValue())));
                    handlerDialogs("date", false);
                } else {
                    snackbarHandler("ورودی نباید از زمان حال کمتر باشد");
                }
            }
        });



        startDateYearNumpic.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                checkForLeapYearInJalaliCalendar();
            }
        });



        startDateMonthNumpic.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberOfMonthDaysInJalaliCalendar();
            }
        });





        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handleProgressbar(true);
                try {
                    String fullName = emplooyeeNameTxt.getText().toString();
                    String personalId = userDetails.getUserInfo().getString("personalId");
                    String persoanlIdMaster = userDetails.getUserInfo().getString("personalIdmaster");
                    String startTime = "00" + ":" + Formating.persianDigitsToEnglish(startTimeHourTxt.getText().toString()) + ":" + Formating.persianDigitsToEnglish(startTimeMinTxt.getText().toString());
                    String amountTime = Formating.persianDigitsToEnglish(amountTimeDayTxt.getText().toString()) + ":" + Formating.persianDigitsToEnglish(amountTimeHourTxt.getText().toString()) + ":" + Formating.persianDigitsToEnglish(amountTimeMinTxt.getText().toString());
                    String startDate = Formating.persianDigitsToEnglish(startDateYearTxt.getText().toString()) + "/" + Formating.persianDigitsToEnglish(startDateMonthTxt.getText().toString()) + "/" + Formating.persianDigitsToEnglish(startDateDayTxt.getText().toString());
                    String leaveDescription = leaveDescriptionEt.getText().toString();
                    String currentDate= Formating.persianDigitsToEnglish(currentDateTxt.getText().toString());

                    apiHandler.reqLeave(fullName, personalId, persoanlIdMaster, type, startTime, amountTime, startDate, leaveDescription, currentDate,
                            new ApiHandler.ResponseListenerReqLeave() {
                                @Override
                                public void onRecived(String response) {
                                    if (response.trim().equals("success")) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        doneTxt.setVisibility(View.VISIBLE);
                                        doneBtn.setEnabled(true);
                                        setResult(3);
                                        finish();
                                    } else if (response.trim().equals("error_already")) {
                                        snackbarHandler("شما از قبل یک درخواست برای بررسی دارید");
                                        handleProgressbar(false);
                                    } else if (response.trim().equals("NoConnectionError")) {
                                        snackbarHandler("لطفا اینترنت خود را چک کنید");
                                        handleProgressbar(false);
                                    } else {
                                        snackbarHandler("خطا در برقراری ارتباط با سرور");
                                        handleProgressbar(false);
                                    }
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    // end of on create-----------------------------------------------------------------------------------------------





    private void checkboxHandler(String type) {
        officialChkbxContainer.setAlpha(.7f);
        OfficialChkbxImg.setImageResource(0);
        OfficialChkbxImg.setBackgroundResource(R.drawable.background_check_box_off);

        personalChkbxContainer.setAlpha(.7f);
        PersonalChkbxImg.setImageResource(0);
        PersonalChkbxImg.setBackgroundResource(R.drawable.background_check_box_off);

        sickChkbxContainer.setAlpha(.7f);
        SickChkbxImg.setImageResource(0);
        SickChkbxImg.setBackgroundResource(R.drawable.background_check_box_off);

        if (type.equals("اداری")) {
            this.type = "اداری";
            officialChkbxContainer.setAlpha(1);
            OfficialChkbxImg.setImageResource(R.drawable.ic_tick);
            OfficialChkbxImg.setBackgroundResource(R.drawable.background_check_box_on);
        } else if (type.equals("شخصی")) {
            this.type = "شخصی";
            personalChkbxContainer.setAlpha(1);
            PersonalChkbxImg.setImageResource(R.drawable.ic_tick);
            PersonalChkbxImg.setBackgroundResource(R.drawable.background_check_box_on);
        } else {
            this.type = "استعلاجی";
            sickChkbxContainer.setAlpha(1);
            SickChkbxImg.setImageResource(R.drawable.ic_tick);
            SickChkbxImg.setBackgroundResource(R.drawable.background_check_box_on);
        }
    }





    private void handlerDialogs(String type, boolean check) {
        switch (type) {
            case "time":
                if (check) {
                    DialogFocusContainer.setVisibility(View.VISIBLE);
                    DialogFocusContainer.setClickable(true);
                    startTimeNumpicDialog.setVisibility(View.VISIBLE);
                } else {
                    DialogFocusContainer.setVisibility(View.GONE);
                    DialogFocusContainer.setClickable(false);
                    startTimeNumpicDialog.setVisibility(View.GONE);
                }
                break;
            case "amount":
                if (check) {
                    DialogFocusContainer.setVisibility(View.VISIBLE);
                    DialogFocusContainer.setClickable(true);
                    amountTimeNumpicDialog.setVisibility(View.VISIBLE);
                } else {
                    DialogFocusContainer.setVisibility(View.GONE);
                    DialogFocusContainer.setClickable(false);
                    amountTimeNumpicDialog.setVisibility(View.GONE);
                }
                break;
            case "date":
                if (check) {
                    DialogFocusContainer.setVisibility(View.VISIBLE);
                    DialogFocusContainer.setClickable(true);
                    startDateNumpicDialog.setVisibility(View.VISIBLE);
                } else {
                    DialogFocusContainer.setVisibility(View.GONE);
                    DialogFocusContainer.setClickable(false);
                    startDateNumpicDialog.setVisibility(View.GONE);
                }
                break;
        }
    }


    private void handleProgressbar(boolean check) {
        if (check) {
            progressBar.setVisibility(View.VISIBLE);
            doneTxt.setVisibility(View.INVISIBLE);
            doneBtn.setEnabled(false);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            doneTxt.setVisibility(View.VISIBLE);
            doneBtn.setEnabled(true);
        }
    }





    private void snackbarHandler(String message) {
        Snackbar snackbar = Snackbar.make(root, message, Snackbar.LENGTH_LONG);
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextSize(14);
        textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.iransansmobile_light));
        textView.setTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }





    private void numberOfMonthDaysInJalaliCalendar() {
        int[] array = {1, 5, 9, 13, 17, 22, 26, 30};

        if (startDateMonthNumpic.getValue() <= 6) {
            startDateDayNumpic.setMaxValue(31);
            String[] displayedNumbers = new String[31 + 1];
            for (int i = 0; i < displayedNumbers.length; i++) {
                displayedNumbers[i] = Formating.englishDigitsToPersian(String.valueOf(1 + i));
            }
            startDateDayNumpic.setDisplayedValues(displayedNumbers);
        } else if (startDateMonthNumpic.getValue() > 6 && startDateMonthNumpic.getValue() <= 11) {
            startDateDayNumpic.setMaxValue(30);
            String[] displayedNumbers = new String[30 + 1];
            for (int i = 0; i < displayedNumbers.length; i++) {
                displayedNumbers[i] = Formating.englishDigitsToPersian(String.valueOf(1 + i));
            }
            startDateDayNumpic.setDisplayedValues(displayedNumbers);
        } else if (startDateMonthNumpic.getValue() == 12 && !Arrays.asList(array).contains(startDateYearNumpic.getValue() % 33)) {
            startDateDayNumpic.setMaxValue(29);
        } else if (startDateMonthNumpic.getValue() == 12 && Arrays.asList(array).contains(startDateYearNumpic.getValue() % 33)) {
            startDateDayNumpic.setMaxValue(30);
        }
    }





    public void checkForLeapYearInJalaliCalendar() {

        if (startDateMonthNumpic.getValue() == 12 && !DateTime.isPersianLeapYear(startDateYearNumpic.getValue())) {
            startDateDayNumpic.setMaxValue(29);
        } else if (startDateMonthNumpic.getValue() == 12 && DateTime.isPersianLeapYear(startDateYearNumpic.getValue())) {
            startDateDayNumpic.setMaxValue(30);
        }
    }





    public String[] displayValueOfNumpic(int num, int start) {
        String[] temp = new String[num];
        for (int i = 0; i < temp.length ; i++) {
            temp[i] = Formating.englishDigitsToPersian(String.format("%02d", i + start));
        }
        return temp;
    }


    @Override
    public void onBackPressed() {
        if (startTimeNumpicDialog.getVisibility() == View.VISIBLE || amountTimeNumpicDialog.getVisibility() == View.VISIBLE || startDateNumpicDialog.getVisibility() == View.VISIBLE) {
            DialogFocusContainer.setVisibility(View.GONE);
            DialogFocusContainer.setClickable(false);


            startTimeNumpicDialog.setVisibility(View.GONE);
            startHourNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(startTimeHourTxt.getText().toString())));
            startMinNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(startTimeMinTxt.getText().toString())));

            amountTimeNumpicDialog.setVisibility(View.GONE);
            amountTimeDayNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(amountTimeDayTxt.getText().toString())));
            amountTimeHourNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(amountTimeHourTxt.getText().toString())));
            amountTimeMinNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(amountTimeMinTxt.getText().toString())));

            startDateNumpicDialog.setVisibility(View.GONE);
            startDateYearNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(startDateYearTxt.getText().toString())));
            startDateMonthNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(startDateMonthTxt.getText().toString())));
            startDateDayNumpic.setValue(Integer.parseInt(Formating.persianDigitsToEnglish(startDateDayTxt.getText().toString())));
        } else {
            super.onBackPressed();
        }
    }
}
