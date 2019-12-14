package com.example.absencemonitoring.interfaces;

import android.view.MotionEvent;
import android.view.View;

public interface SwipeFragmentListener {
    void onSwipe(View view, MotionEvent event, SwipeEndFragmentListener swipeEndFragmentListener);
    void onCloseMenu();
}
