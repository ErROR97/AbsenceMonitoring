package com.example.absencemonitoring.Utils;

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

    public static String calculateAmountIsDayOrHour(String amount) {
        String[] amounts = amount.split(":");
        if (amounts[0].equals("0")) {
            return amounts[1] + " ساعت";
        } else {
            return amounts[0] + " روز";
        }
    }
}
