package com.example.absencemonitoring.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.absencemonitoring.instances.ControlFurlough;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.ControlAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ControlFragment extends Fragment {
    View view;
    RecyclerView rv;
    ControlAdapter controlAdapter;
    List<ControlFurlough> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_control, container, false);

        list = new ArrayList<>();
        rv = view.findViewById(R.id.rv_control_furlough);

        list.add(new ControlFurlough("متین براهویی", "استعلاجی", "000000", "000000", "۱۱", "۱۵", "۴۳", 10));
        list.add(new ControlFurlough("متین براهویی", "استعلاجی", "000000", "000000", "۱۱", "۱۵", "۴۳", 10));
        list.add(new ControlFurlough("متین براهویی", "استعلاجی", "000000", "000000", "۱۱", "۱۵", "۴۳", 10));
        list.add(new ControlFurlough("متین براهویی", "استعلاجی", "000000", "000000", "۱۱", "۱۵", "۴۳", 10));
        list.add(new ControlFurlough("متین براهویی", "استعلاجی", "000000", "000000", "۱۱", "۱۵", "۴۳", 10));
        list.add(new ControlFurlough("متین براهویی", "استعلاجی", "000000", "000000", "۱۱", "۱۵", "۴۳", 10));
        list.add(new ControlFurlough("متین براهویی", "استعلاجی", "000000", "000000", "۱۱", "۱۵", "۴۳", 10));

        controlAdapter = new ControlAdapter(getActivity(), list);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(controlAdapter);

        return view;
    }
}
