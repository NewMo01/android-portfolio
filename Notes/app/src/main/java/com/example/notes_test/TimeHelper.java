package com.example.notes_test;

import android.annotation.SuppressLint;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {

    public static Calendar calendar = Calendar.getInstance();
    public static final int year = calendar.get(Calendar.YEAR);
    public static final int month = calendar.get(Calendar.MONTH);
    public static final int date = calendar.get(Calendar.DATE);


    @SuppressLint("SimpleDateFormat")
    public static long convertIntoMilli(String t) throws ParseException {
        String d = new SimpleDateFormat("MM/dd/yyyy ").format(new Date(year-1900,month,date));
        return new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(d + t).getTime() ;

    }


    public static String formatTime(Date t){
        @SuppressLint("SimpleDateFormat") Format f = new SimpleDateFormat("HH:mm:ss");
        return f.format(t);
    }


}
