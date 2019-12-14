package com.example.absencemonitoring.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.ArchiveFurloughAdapter;
import com.example.absencemonitoring.instances.Furlough;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArchiveFurloughFragment extends Fragment {
    private View view;
    private RecyclerView rv;
    private ArchiveFurloughAdapter archiveFurloughAdapter;
    private CardView filterContainer, sortContainer, filterItemsContainer, sortItemsContaienr;
    private LinearLayout filterSortItemsContaienr;
    String firstname, lastname;
    List<Furlough> list;

    private ApiHandler apiHandler;
    private UserDetails userDetails;

    private void init() {
        rv = view.findViewById(R.id.rv_archive_furlough);
        list = new ArrayList<>();

        filterContainer = view.findViewById(R.id.container_filter);
        sortContainer = view.findViewById(R.id.container_sort);
        filterSortItemsContaienr = view.findViewById(R.id.container_items_filter_sort);
        filterItemsContainer = view.findViewById(R.id.contaiener_items_filter);
        sortItemsContaienr = view.findViewById(R.id.container_items_sort);

        userDetails = new UserDetails(getActivity());
        apiHandler = new ApiHandler(getActivity());

        try {
            firstname = userDetails.getUserInfo().getString("firstName");
            lastname = userDetails.getUserInfo().getString("lastName");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            apiHandler.getLeaveArchive(userDetails.getUserInfo().getString("personalIdmaster"), new ApiHandler.ResponseListenerLeaveArchive() {
                @Override
                public void onRevived(List<Furlough> leaveArchiveList) {
                    try {
                        if (userDetails.getUserInfo().getString("role").equals("master")) {
                            archiveFurloughAdapter = new ArchiveFurloughAdapter(getActivity(), leaveArchiveList);
                            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv.setAdapter(archiveFurloughAdapter);
                        } else {
                            for (int i = 0; i < leaveArchiveList.size(); i++) {
                                if (leaveArchiveList.get(i).getName().equals(firstname + " " + lastname)) {
                                    list.add(leaveArchiveList.get(i));
                                }
                            }
                            archiveFurloughAdapter = new ArchiveFurloughAdapter(getActivity(), list);
                            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv.setAdapter(archiveFurloughAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onMessage(String error) {

                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_archive_furlough, container, false);

        init();

        filterContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterItemsContainer.getVisibility() == View.INVISIBLE) {
                    filterItemsContainer.setVisibility(View.VISIBLE);
                    sortItemsContaienr.setVisibility(View.INVISIBLE);
                    filterSortItemsContaienr.setVisibility(View.VISIBLE);
                } else {
                    filterItemsContainer.setVisibility(View.INVISIBLE);
                    filterSortItemsContaienr.setVisibility(View.INVISIBLE);
                }
            }
        });

        sortContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortItemsContaienr.getVisibility() == View.INVISIBLE) {
                    sortItemsContaienr.setVisibility(View.VISIBLE);
                    filterItemsContainer.setVisibility(View.INVISIBLE);
                    filterSortItemsContaienr.setVisibility(View.VISIBLE);
                } else {
                    sortItemsContaienr.setVisibility(View.INVISIBLE);
                    filterSortItemsContaienr.setVisibility(View.INVISIBLE);
                }
            }
        });

        return view;
    }
}
