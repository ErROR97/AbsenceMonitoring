package com.example.absencemonitoring;

public class DateTime {

    public static String dateToString(int yDate, int mDate, int dDate) {
        return yDate + "/" + mDate + "/" + dDate;
    }

    public static String timeToString(int amountTime, int hTime, int mTime) {
        return amountTime + ":" + hTime + ":" + mTime;
    }

    public static String[] stringToTime(String string) {
        return string.split(":");
    }

    public static String[] stringToDate(String string) {
        return string.split("/");
    }
}
