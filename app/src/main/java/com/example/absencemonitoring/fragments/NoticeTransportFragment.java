package com.example.absencemonitoring.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.NoticeTransportAdapter;
import com.example.absencemonitoring.instances.NoticeTransport;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeTransportFragment extends Fragment {
    View view;
    RecyclerView rv;
    List<NoticeTransport> list;
    NoticeTransportAdapter noticeTransportAdapter;

    public void init() {

        rv = view.findViewById(R.id.rv_notice_service);
        list = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice_transport, container, false);

        init();

        list.add(new NoticeTransport("صبح", "۹۸/۰۸/۰۳", "۶:۳۰", "خیابان دانشگاه، دانشگاه ۲۵"));
        list.add(new NoticeTransport("صبح", "۹۸/۰۸/۰۳", "۶:۳۰", "خیابان دانشگاه، دانشگاه ۲۵"));



        noticeTransportAdapter = new NoticeTransportAdapter(getActivity(), list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(noticeTransportAdapter);

        return  view;
    }
}
