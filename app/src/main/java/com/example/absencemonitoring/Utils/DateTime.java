package com.example.absencemonitoring.Utils;

import android.util.Log;
import android.util.TimeUtils;

import com.example.absencemonitoring.instances.Furlough;

import java.sql.Time;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.Duration;

import ir.huri.jcal.JalaliCalendar;

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



    public static String checkForDailyOrHourly(String amount) {
        String[] amounts = amount.split(":");
        if (amounts[0].equals("0")) {
            return "ساعتی";
        } else {
            return "روزانه";
        }
    }


    public static boolean checkFurloughIsStarted(String startDate, String startTime) {
        String[] starts = startDate.split("/");
        starts[0] = String.valueOf(Integer.parseInt(starts[0]) + 1300);
        String[] startTimes = startTime.split(":");
        JalaliCalendar jalaliCalendar = new JalaliCalendar(Integer.parseInt(starts[0]), Integer.parseInt(starts[1]), Integer.parseInt(starts[2]));

        Calendar calendar = Calendar.getInstance();

        Calendar startDateMiladi = jalaliCalendar.toGregorian();
        startDateMiladi.set(Calendar.HOUR, Integer.parseInt(startTimes[1]));
        startDateMiladi.set(Calendar.MINUTE, Integer.parseInt(startTimes[2]));
        long difference = TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis() - startDateMiladi.getTimeInMillis());
        if (difference < 0) {
            return false;
        } else {
            return true;
        }
    }



    public static String calculateAmountIsDayOrHour(String amount) {
        String[] amounts = amount.split(":");
        if (amounts[0].equals("0")) {
            return String.format("%02d", Integer.parseInt(amounts[1])) + ":" + String.format("%02d", Integer.parseInt(amounts[2])) + " ساعت";
        } else {
            return amounts[0] + " روز";
        }
    }



    public static String calculateEndDate(String startDate, String amountDate) {

        String[] starts = startDate.split("/");
        String[] amounts = amountDate.split(":");

        JalaliCalendar jalaliCalendar = new JalaliCalendar(Integer.parseInt(starts[0]) + 1300, Integer.parseInt(starts[1]), Integer.parseInt(starts[2]));
        Calendar calendar = jalaliCalendar.toGregorian();
        calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(amounts[0]));
        jalaliCalendar = new JalaliCalendar(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));

        return String.valueOf(Integer.parseInt(jalaliCalendar.toString().split("-")[0]) - 1300)
                + "/" + jalaliCalendar.toString().split("-")[1]
                + "/" + jalaliCalendar.toString().split("-")[2];
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



    public static String calculateRemainingTime(String d,String t, String a) {
        String[] starts = d.split("/");
        starts[0] = String.valueOf(Integer.parseInt(starts[0]) + 1300);
        String[] startTimes = t.split(":");
        String[] amounts = a.split(":");

        JalaliCalendar jalaliCalendar = new JalaliCalendar(Integer.parseInt(starts[0]), Integer.parseInt(starts[1]), Integer.parseInt(starts[2]));

        Calendar calendar = Calendar.getInstance();

        Calendar startDate = jalaliCalendar.toGregorian();
        startDate.set(Calendar.HOUR, Integer.parseInt(startTimes[1]));
        startDate.set(Calendar.MINUTE, Integer.parseInt(startTimes[2]));

        long difference = TimeUnit.MILLISECONDS.toMinutes(calendar.getTimeInMillis() - startDate.getTimeInMillis());

        long days = Long.parseLong(amounts[0]) - difference / 24 / 60;
        long hours = Long.parseLong(amounts[1]) - difference / 60 % 24;
        long minutes = Long.parseLong(amounts[2]) - difference % 60;

        if (hours < 0) {
            days--;
            hours = 24 - difference / 60 % 24;
        }

        if (minutes < 0) {
            hours--;
            minutes = 60 - difference % 60;
            if (hours < 0) {
                days--;
                hours = 23;
            }
        }
        Log.i("goorekhar", "calculateRemainingTime: " + days + " " + hours + " " + minutes);

        return String.format("%02d", days) + ":" + String.format("%02d", hours) + ":" + String.format("%02d", minutes);
    }
}
