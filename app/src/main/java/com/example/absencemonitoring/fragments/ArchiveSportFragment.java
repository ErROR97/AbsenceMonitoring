package com.example.absencemonitoring.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.ArchiveSportAdapter;
import com.example.absencemonitoring.instances.Sport;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArchiveSportFragment extends Fragment {
    View view;
    RecyclerView rv;
    ArchiveSportAdapter archiveSportAdapter;
    List<Sport> list;


    private void init() {
        rv = view.findViewById(R.id.rv_archive_sport);
        list = new ArrayList<>();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_archive_sport, container, false);

        init();

        list.add(new Sport("والیبال", "۹۸/۰۸/۰۹", "۲۱:۰۰", "۲۳:۰۰", "۲۱", getResources().getColor(R.color.light_yellow), R.drawable.ic_volleyball));
        list.add(new Sport("فوتبال", "۹۸/۰۸/۰۹", "۲۱:۰۰", "۲۳:۰۰", "۲۱", getResources().getColor(R.color.green), R.drawable.ic_football));
        list.add(new Sport("استخر", "۹۸/۰۸/۰۹", "۲۱:۰۰", "۲۳:۰۰", "۲۱", getResources().getColor(R.color.light_blue), R.drawable.ic_swimming));

        archiveSportAdapter = new ArchiveSportAdapter(getActivity(), list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv.setAdapter(archiveSportAdapter);

        return view;
    }
}
