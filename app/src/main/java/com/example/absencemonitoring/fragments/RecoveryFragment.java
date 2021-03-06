package com.example.absencemonitoring.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.absencemonitoring.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RecoveryFragment extends Fragment {
    View view;
    ImageView backImg;
    fragmentRemoveListener fragmentRemoveListener;


    public void init() {
        backImg = view.findViewById(R.id.img_back);
        fragmentRemoveListener = (fragmentRemoveListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recovery, container, false);

        init();

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentRemoveListener.onFragmentRemoved();
            }
        });

        return view;
    }

    public interface fragmentRemoveListener {
        void onFragmentRemoved();
    }

}
