package com.example.absencemonitoring.utils;

import android.annotation.SuppressLint;

import java.util.Arrays;


public class Formating {

    @SuppressLint("DefaultLocale")
    public static String cleanTimeOrDateFormat(String input, String separator) {
        String[] temp = input.split(separator);
        String result = String.format("%02d", Integer.parseInt(temp[0]));

        for (int i = 1; i < temp.length; i++) {
            result += separator + String.format("%02d", Integer.parseInt(temp[i]));
        }
        return englishDigitsToPersian(result);
    }

    public static String englishDigitsToPersian(String input) {
        char[] persianChars = {'۰','۱','۲','۳','۴','۵','۶','۷','۸','۹'};

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                builder.append(persianChars[(int) (input.charAt(i)) - 48]);
            } else {
                builder.append(input.charAt(i));
            }
        }
        return builder.toString();
    }

    public static String persianDigitsToEnglish(String input) {
        char[] persianChars = {'۰','۱','۲','۳','۴','۵','۶','۷','۸','۹'};

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            int index = Arrays.binarySearch(persianChars, input.charAt(i));
            if (index != -1) {
                builder.append(Arrays.binarySearch(persianChars, input.charAt(i)));
            } else {
                builder.append(input.charAt(i));
            }
        }
        return builder.toString();
    }
}
