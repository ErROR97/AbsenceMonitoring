package com.example.absencemonitoring.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.NoticeSportAdapter;
import com.example.absencemonitoring.instances.Sport;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeSportFragment extends Fragment {
    View view;
    RecyclerView rv;
    List<Sport> list;
    NoticeSportAdapter noticeSportAdapter;

    public void init() {

        rv = view.findViewById(R.id.rv_notice_sport);
        list = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice_sport, container, false);

        init();

        list.add(new Sport("والیبال", "۹۸/۰۸/۰۳", "۲۱:۰۰", "۲۳:۰۰", "۱۹", 1, R.drawable.ic_volleyball));
        list.add(new Sport("والیبال", "۹۸/۰۸/۰۳", "۲۱:۰۰", "۲۳:۰۰", "۱۹", 1, R.drawable.ic_volleyball));

        noticeSportAdapter = new NoticeSportAdapter(getActivity(), list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(noticeSportAdapter);

        return  view;
    }
}
