package com.example.absencemonitoring.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.utils.Formating;
import com.shawnlin.numberpicker.NumberPicker;

public class addReserveSportTimeFragment extends Fragment {
    private View view;
    private CardView volleyballContainer, footballContainer, swimmingContainer;
    private CardView editStartTimeCrd, editEndTimeCrd;
    private CardView numpickTimeDialog, confirmTimeCrd, cancelTimeCrd;
    private TextView startTimeMinTxt, startTimeHourTxt, endTimeMinTxt, endTimeHourTxt;
    private NumberPicker hour, min;
    String[] minArray, hourArray;


    boolean onStart = false;

    public void init() {
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

        footballContainer.setAlpha(.5f);
        swimmingContainer.setAlpha(.5f);

        minArray = displayValueOfNumpic(60, 0);
        hourArray = displayValueOfNumpic(24, 0);

        hour.setMinValue(0);
        hour.setMaxValue(23);

        min.setMinValue(0);
        min.setMaxValue(59);

        hour.setDisplayedValues(hourArray);
        min.setDisplayedValues(minArray);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_reserve_sport_time, container, false);
        init();

        editStartTimeCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numpickTimeDialog.setVisibility(View.VISIBLE);
                onStart = true;
            }
        });

        editEndTimeCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numpickTimeDialog.setVisibility(View.VISIBLE);
                onStart = false;
            }
        });

        confirmTimeCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStart) {
                    startTimeMinTxt.setText(String.valueOf(min.getValue()));
                    startTimeHourTxt.setText(String.valueOf(hour.getValue()));
                } else {
                    endTimeMinTxt.setText(String.valueOf(min.getValue()));
                    endTimeHourTxt.setText(String.valueOf(hour.getValue()));
                }
                numpickTimeDialog.setVisibility(View.INVISIBLE);
            }
        });

        cancelTimeCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numpickTimeDialog.setVisibility(View.INVISIBLE);
            }
        });


        return view;
    }


    public String[] displayValueOfNumpic(int num, int start) {
        String[] temp = new String[num];
        for (int i = 0; i < temp.length ; i++) {
            temp[i] = Formating.englishDigitsToPersian(String.format("%02d", i + start));
        }
        return temp;
    }
}


