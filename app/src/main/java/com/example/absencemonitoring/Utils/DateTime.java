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
            return String.format("%02d", Integer.parseInt(amounts[1])) + ":" + String.format("%02d", Integer.parseInt(amounts[2])) + " ساعت";
        } else {
            return amounts[0] + " روز";
        }
    }

    public static String calculateEndTime(String startTime, String amountTime) {
        String starts[] = startTime.split(":");
        String amounts[] = amountTime.split(":");
        int endHour = Integer.parseInt(starts[1]) + Integer.parseInt(amounts[1]);
        int endMin = Integer.parseInt(starts[2]) + Integer.parseInt(amounts[2]);
        if (endMin >= 60) {
            endHour++;
            endMin -= 60;
        }
        String endTime = String.format("%02d", endHour) + ":" + String.format("%02d", endMin);
        return endTime;
    }
}
