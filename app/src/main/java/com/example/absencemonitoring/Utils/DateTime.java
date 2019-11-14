package com.example.absencemonitoring.Utils;

import android.util.Log;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

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
        if (amounts[0].equals("00")) {
            return "ساعتی";
        } else {
            return "روزانه";
        }
    }


    public static int checkFurloughIsStarted(String startDate, String startTime, String amount) {
        String[] startDates = startDate.split("/");
        String[] startTimes = startTime.split(":");
        String[] endDate = DateTime.calculateEndDate(startDate, amount).split("/");
        String[] endTime = DateTime.calculateEndTime(startTime, amount).split(":");


        JalaliCalendar jalaliCalendarStart = new JalaliCalendar(Integer.parseInt(startDates[0]), Integer.parseInt(startDates[1]), Integer.parseInt(startDates[2]));
        JalaliCalendar jalaliCalendarEnd = new JalaliCalendar(Integer.parseInt(endDate[0]), Integer.parseInt(endDate[1]), Integer.parseInt(endDate[2]));

        Calendar calendar = Calendar.getInstance();

        Calendar startDateMiladi = jalaliCalendarStart.toGregorian();
        startDateMiladi.set(Calendar.HOUR, Integer.parseInt(startTimes[1]));
        startDateMiladi.set(Calendar.MINUTE, Integer.parseInt(startTimes[2]));

        Calendar endDateMiladi = jalaliCalendarEnd.toGregorian();
        endDateMiladi.set(Calendar.HOUR, Integer.parseInt(endTime[0]));
        endDateMiladi.set(Calendar.MINUTE, Integer.parseInt(endTime[1]));


        long differenceStart = TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis() - startDateMiladi.getTimeInMillis());
        long differenceEnd = TimeUnit.MILLISECONDS.toSeconds(endDateMiladi.getTimeInMillis() - calendar.getTimeInMillis());

        if (differenceStart < 0) {
            return 0;
        } else if (differenceStart > 0 && differenceEnd > 0) {
            return 1;
        } else {
            return 2;
        }
    }



    public static String calculateAmountIsDayOrHour(String amount) {
        String[] amounts = amount.split(":");
        if (amounts[0].equals("00")) {
            return String.format("%02d", Integer.parseInt(amounts[1])) + ":" + String.format("%02d", Integer.parseInt(amounts[2])) + " ساعت";
        } else {
            return amounts[0] + " روز";
        }
    }



    public static String calculateEndDate(String startDate, String amountDate) {

        String[] starts = startDate.split("/");
        String[] amounts = amountDate.split(":");

        JalaliCalendar jalaliCalendar = new JalaliCalendar(Integer.parseInt(starts[0]), Integer.parseInt(starts[1]), Integer.parseInt(starts[2]));
        Calendar calendar = jalaliCalendar.toGregorian();
        calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(amounts[0]));
        jalaliCalendar = new JalaliCalendar(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));

        return jalaliCalendar.toString().split("-")[0]
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

        return String.format("%02d", days) + ":" + String.format("%02d", hours) + ":" + String.format("%02d", minutes);
    }

    public static String calculatePassedTime(String startDate, String startTime, String amountTime) {
        String[] endDate = DateTime.calculateEndDate(startDate, amountTime).split("/");
        String[] endTime = DateTime.calculateEndTime(startTime, amountTime).split(":");
        JalaliCalendar jalaliCalendar = new JalaliCalendar(Integer.parseInt(endDate[0]), Integer.parseInt(endDate[1]), Integer.parseInt(endDate[2]));
        Calendar miladiEndDate = jalaliCalendar.toGregorian();
        miladiEndDate.set(Calendar.HOUR, Integer.parseInt(endTime[0]));
        miladiEndDate.set(Calendar.MINUTE, Integer.parseInt(endTime[1]));

        Calendar now = Calendar.getInstance();
        long difference = TimeUnit.MILLISECONDS.toMinutes(now.getTimeInMillis() - miladiEndDate.getTimeInMillis());

        long days = difference / 24 / 60;
        long hours = difference / 60 % 24;
        long minutes = difference % 60;

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

        return  String.format("%02d", days) + ":" + String.format("%02d", hours) + ":" + String.format("%02d", minutes);
    }

    public static boolean checkForTimeInputValidation(int year, int month, int day, int hour, int min) {
        JalaliCalendar jalaliCalendar = new JalaliCalendar(new GregorianCalendar());

        if (year < Integer.parseInt(jalaliCalendar.toString().split("-")[0])) {
            return  false;
        }

        if (year == Integer.parseInt(jalaliCalendar.toString().split("-")[0])
                && month <  Integer.parseInt(jalaliCalendar.toString().split("-")[1])) {
            return  false;
        }

        if (year == Integer.parseInt(jalaliCalendar.toString().split("-")[0])
                && month ==  Integer.parseInt(jalaliCalendar.toString().split("-")[1])
                && day <  Integer.parseInt(jalaliCalendar.toString().split("-")[2])) {
            return  false;
        }

        if (year == Integer.parseInt(jalaliCalendar.toString().split("-")[0])
                && month ==  Integer.parseInt(jalaliCalendar.toString().split("-")[1])
                && day ==  Integer.parseInt(jalaliCalendar.toString().split("-")[2])
                && hour < Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            return false;
        }
        if (year == Integer.parseInt(jalaliCalendar.toString().split("-")[0])
                && month ==  Integer.parseInt(jalaliCalendar.toString().split("-")[1])
                && day ==  Integer.parseInt(jalaliCalendar.toString().split("-")[2])
                && hour == Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                && min < Calendar.getInstance().get(Calendar.MINUTE)) {
            return false;
        }

        return true;
    }



    public static boolean isPersianLeapYear(int persianYear) {
        return ceil((38D + (ceil(persianYear - 474L, 2820L) + 474L)) * 682D, 2816D) < 682L;
    }

    public static long ceil(double double1, double double2) {
        return (long) (double1 - double2 * Math.floor(double1 / double2));
    }
}
