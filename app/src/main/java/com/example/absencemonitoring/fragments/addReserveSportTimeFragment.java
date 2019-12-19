package com.example.absencemonitoring.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.ApiHandler.ResponseListenerInserTimeSport;
import com.example.absencemonitoring.interfaces.DismissShadowListener;
import com.example.absencemonitoring.utils.Formating;
import com.shawnlin.numberpicker.NumberPicker;

public class addReserveSportTimeFragment extends Fragment {
    private ApiHandler apiHandler;
    private CardView cancelBtn;
    private CardView cancelTimeCrd;
    private int capacity = 12;
    private CardView confirmBtn;
    private CardView confirmTimeCrd;
    private DismissShadowListener dismissShadowListener;
    private CardView editEndTimeCrd;
    private CardView editStartTimeCrd;
    private TextView endTimeHourTxt;
    private TextView endTimeMinTxt;
    private CardView footballContainer;
    private NumberPicker hour;
    private String[] hourArray;
    private NumberPicker min;
    private String[] minArray;
    private CardView numpickTimeDialog;
    private boolean onStart = false;
    private TextView startTimeHourTxt;
    private TextView startTimeMinTxt;
    private CardView swimmingContainer;
    private String type = "volleyball";
    private View view;
    private CardView volleyballContainer;
    TextView[] weekDayTxts;
    String[] weekDays = {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
    String[] chosenWeekDays = {"", "", "", "", "", "", ""};
    ProgressBar progressBar;

    public void init() {
        apiHandler = new ApiHandler(getActivity());
        dismissShadowListener = (DismissShadowListener) getActivity();

        weekDayTxts = new TextView[7];

        volleyballContainer = view.findViewById(R.id.container_volleyball);
        footballContainer = view.findViewById(R.id.container_football);
        swimmingContainer = view.findViewById(R.id.container_swimming);

        hour = view.findViewById(R.id.numpic_time_hour);
        min = view.findViewById(R.id.numpic_time_min);

        editStartTimeCrd = view.findViewById(R.id.crd_edit_start_time);
        editEndTimeCrd = view.findViewById(R.id.crd_edit_end_time);
        numpickTimeDialog = view.findViewById(R.id.dialog_numpic_time);
        confirmTimeCrd = view.findViewById(R.id.crd_confirm_time_dialog);
        cancelTimeCrd = view.findViewById(R.id.crd_cancel_time_dialog);
        startTimeMinTxt = view.findViewById(R.id.txt_start_time_min);
        startTimeHourTxt = view.findViewById(R.id.txt_start_time_hour);
        endTimeMinTxt = view.findViewById(R.id.txt_end_time_min);
        endTimeHourTxt = view.findViewById(R.id.txt_end_time_hour);

        confirmBtn = view.findViewById(R.id.btn_confirm);
        cancelBtn = view.findViewById(R.id.btn_cancel);

        weekDayTxts[0] = view.findViewById(R.id.txt_saturday);
        weekDayTxts[1] = view.findViewById(R.id.txt_sunday);
        weekDayTxts[2] = view.findViewById(R.id.txt_monday);
        weekDayTxts[3] = view.findViewById(R.id.txt_tuesday);
        weekDayTxts[4] = view.findViewById(R.id.txt_wednesday);
        weekDayTxts[5] = view.findViewById(R.id.txt_thursday);
        weekDayTxts[6] = view.findViewById(R.id.txt_friday);

        progressBar = view.findViewById(R.id.progressbar);

        footballContainer.setAlpha(0.5f);
        swimmingContainer.setAlpha(0.5f);
        minArray = displayValueOfNumpic(60, 0);
        hourArray = displayValueOfNumpic(24, 0);
        hour.setMinValue(0);
        hour.setMaxValue(23);
        hour.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iransansmobile_light.ttf"));
        hour.setDisplayedValues(hourArray);

        min.setMinValue(0);
        min.setMaxValue(59);
        min.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iransansmobile_light.ttf"));
        min.setDisplayedValues(minArray);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_reserve_sport_time, container, false);
        init();


        volleyballContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                type = "volleyball";
                capacity = 12;
                volleyballContainer.setAlpha(1.0f);
                footballContainer.setAlpha(0.5f);
                swimmingContainer.setAlpha(0.5f);
            }
        });



        footballContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                type = "football";
                capacity = 12;
                footballContainer.setAlpha(1.0f);
                volleyballContainer.setAlpha(0.5f);
                swimmingContainer.setAlpha(0.5f);
            }
        });



        swimmingContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                type = "swimming";
                capacity = 50;
                swimmingContainer.setAlpha(1.0f);
                footballContainer.setAlpha(0.5f);
                volleyballContainer.setAlpha(0.5f);
            }
        });


        for (int i = 0; i < weekDayTxts.length; i++) {
            final int finalI = i;
            weekDayTxts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chosenWeekDays[finalI].equals("")) {
                        chosenWeekDays[finalI] = weekDays[finalI];
                        weekDayTxts[finalI].setBackground(getResources().getDrawable(R.drawable.background_choose_week_day_spotman_on));
                    } else {
                        chosenWeekDays[finalI] = "";
                        weekDayTxts[finalI].setBackground(getResources().getDrawable(R.drawable.background_choose_week_day_spotman_off));
                    }
                }
            });
        }


        editStartTimeCrd.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                numpickTimeDialog.setVisibility(View.VISIBLE);
                onStart = true;
            }
        });



        editEndTimeCrd.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                numpickTimeDialog.setVisibility(View.VISIBLE);
                onStart = false;
            }
        });



        confirmTimeCrd.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (onStart) {
                    startTimeMinTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", new Object[]{Integer.valueOf(min.getValue())})));
                    startTimeHourTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", new Object[]{Integer.valueOf(hour.getValue())})));
                } else {
                    endTimeMinTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", new Object[]{Integer.valueOf(min.getValue())})));
                    endTimeHourTxt.setText(Formating.englishDigitsToPersian(String.format("%02d", new Object[]{Integer.valueOf(hour.getValue())})));
                }
                numpickTimeDialog.setVisibility(View.INVISIBLE);
            }
        });



        cancelTimeCrd.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                numpickTimeDialog.setVisibility(View.INVISIBLE);
            }
        });



        confirmBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String chosenWeekDaysStr = "";
                for (int i = 0; i < chosenWeekDays.length; i++) {
                    if(!chosenWeekDays[i].equals(""))
                        chosenWeekDaysStr += chosenWeekDays[i] + "-";
                }

                if (!chosenWeekDaysStr.equals("")) {
                    chosenWeekDaysStr = chosenWeekDaysStr.substring(0, chosenWeekDaysStr.length() - 1);
                    String time = Formating.persianDigitsToEnglish(startTimeHourTxt.getText().toString())
                            + ":"
                            + Formating.persianDigitsToEnglish(startTimeMinTxt.getText().toString())
                            + "-"
                            + Formating.persianDigitsToEnglish(endTimeHourTxt.getText().toString())
                            + ":"
                            + Formating.persianDigitsToEnglish(endTimeMinTxt.getText().toString());


                    apiHandler.insertTimeSport(type, String.valueOf(capacity), time, chosenWeekDaysStr, new ResponseListenerInserTimeSport() {
                        public void onRecieved(String response) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (response.equals("success")) {
                                getActivity().getSupportFragmentManager().beginTransaction().remove(addReserveSportTimeFragment.this).commit();
                                dismissShadowListener.onShadowDismissed();
                            } else {
                                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "حداقل باید یک روز از ایام هفته را انتخاب کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });



        this.cancelBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(addReserveSportTimeFragment.this).commit();
                dismissShadowListener.onShadowDismissed();
            }
        });




        return view;
    }

    public String[] displayValueOfNumpic(int num, int start) {
        String[] temp = new String[num];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = Formating.englishDigitsToPersian(String.format("%02d", new Object[]{Integer.valueOf(i + start)}));
        }
        return temp;
    }
}
