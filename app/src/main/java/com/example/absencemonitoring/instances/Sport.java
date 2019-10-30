package com.example.absencemonitoring.instances;

import android.graphics.drawable.Drawable;

public class Sport {
    private String type;
    private String date;
    private String startTime;
    private String endTime;
    private String Number;
    private int color;
    private int icon;

    public Sport(String type, String date, String startTime, String endTime, String number, int color, int icon) {
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.Number = number;
        this.color = color;
        this.icon = icon;
    }



    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getNumber() {
        return Number;
    }

    public int getColor() {
        return color;
    }

    public int getIcon() {
        return icon;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setNumber(String number) {
        this.Number = number;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
