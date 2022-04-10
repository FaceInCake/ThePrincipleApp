package com.example.theprincipleapp.helpers;

import android.app.Activity;
import androidx.appcompat.app.AlertDialog;
import com.example.theprincipleapp.R;

public class Util {

    public static void alertError (Activity act, int message_id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(act);
        adb.setTitle(R.string.error);
        adb.setIcon(R.mipmap.ic_launcher_round);
        adb.setCancelable(false);
        adb.setMessage(message_id);
        adb.setNeutralButton(R.string.okay, null);
        adb.setOnDismissListener(null);
        adb.create().show();
        act.finish();
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

}
