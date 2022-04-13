package com.example.theprincipleapp.helpers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import com.example.theprincipleapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Util {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    @UiThread
    public static void alertError (Activity act, int message_id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(act);
        adb.setTitle(R.string.error);
        adb.setIcon(R.mipmap.ic_launcher_round);
        adb.setCancelable(false);
        adb.setMessage(message_id);
        adb.setNeutralButton(R.string.okay, (d,i) -> act.finish());
        adb.setOnDismissListener(d -> act.finish());
        AlertDialog ad = adb.create();
        ad.show();
    }

    public static String toTitleCase (String string) {
        if (string == null) return null;
        boolean whiteSpace = true;
        StringBuilder builder = new StringBuilder(string);
        final int len = builder.length();
        for (int i=0; i<len; ++i) {
            char c = builder.charAt(i);
            if (Character.isWhitespace(c))
                whiteSpace = true;
            else {
                if (whiteSpace) {
                    builder.setCharAt(i, Character.toTitleCase(c));
                    whiteSpace = false;
                } else builder.setCharAt(i, Character.toLowerCase(c));
            }
        }
        return builder.toString();
    }

    public static void mapDate (Calendar cal, EditText view, int y, int m, int d) {
        cal.set(y, m, d);
        view.setText(dateFormat.format(cal.getTime()));
    }

    public static void mapTime (Calendar cal, EditText view, TimePicker timePicker) {
        cal.set(Calendar.MINUTE, timePicker.getMinute());
        cal.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        view.setText(timeFormat.format(cal.getTime()));
    }

    public static void showDatePicker (Context context, Calendar cal, EditText view) {
        new DatePickerDialog(
                context,
                (dp,y,m,d) -> mapDate(cal, view, y, m, d),
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    public static void showTimePicker (Context context, Calendar cal, EditText view) {
        new TimePickerDialog(
                context,
                (tp,i,j) -> mapTime(cal, view, tp),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.HOUR),
                false
        ).show();
    }

}
