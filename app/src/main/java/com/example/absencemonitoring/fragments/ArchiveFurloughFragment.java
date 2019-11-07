package com.example.absencemonitoring.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.absencemonitoring.Handlers.ApiHandler;
import com.example.absencemonitoring.Handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.ArchiveFurloughAdapter;
import com.example.absencemonitoring.instances.Furlough;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArchiveFurloughFragment extends Fragment {
    View view;
    RecyclerView rv;
    ArchiveFurloughAdapter archiveFurloughAdapter;
    UserDetails userDetails;
    ApiHandler apiHandler;
    List<Furlough> list;


    private void init() {
        rv = view.findViewById(R.id.rv_archive_furlough);

        userDetails = new UserDetails(getActivity());
        apiHandler = new ApiHandler(getActivity());

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_archive_furlough, container, false);

        init();

        return view;
    }
}
