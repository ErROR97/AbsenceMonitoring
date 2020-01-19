package com.example.absencemonitoring.interfaces;

import com.example.absencemonitoring.instances.Sport;

import java.util.List;

public interface SendSelectedReserverSportTimeListener {
    void onSelectedSend(List<Sport> list, String type, String capacity);
}
