package com.example.absencemonitoring.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.NoticeFurloughAdapter;
import com.example.absencemonitoring.instances.Furlough;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeFurloughFragment extends Fragment {
    View view;
    RecyclerView rv;
    NoticeFurloughAdapter noticeFurloughAdapter;
    List<Furlough> list;

    public void init() {
        rv = view.findViewById(R.id.rv_notice_furlough);
        list = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice_furlough, container, false);

        init();

        list.add(new Furlough("متین براهویی", "استعلاجی", "0000", "0000", "۳ روز"));
        list.add(new Furlough("متین براهویی", "استعلاجی", "0000", "0000", "۳ روز"));
        list.add(new Furlough("متین براهویی", "استعلاجی", "0000", "0000", "۳ روز"));
        list.add(new Furlough("متین براهویی", "استعلاجی", "0000", "0000", "۳ روز"));
        list.add(new Furlough("متین براهویی", "استعلاجی", "0000", "0000", "۳ روز"));

        noticeFurloughAdapter = new NoticeFurloughAdapter(getActivity(), list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(noticeFurloughAdapter);

        return view;
    }
}
