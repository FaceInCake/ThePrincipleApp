package com.example.theprincipleapp;

import static com.example.theprincipleapp.helpers.Util.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theprincipleapp.db.Meeting;
import com.example.theprincipleapp.db.MeetingTypeEnum;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.db.Weekday;
import com.example.theprincipleapp.db.Weekdays;
import com.example.theprincipleapp.helpers.Util;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EditMeeting extends NewMeeting {
    int mid;
    TextView tv_meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Super NewMeeting inits most things
        super.onCreate(savedInstanceState);

        tv_meeting = findViewById(R.id.tv_newmeeting);
        tv_meeting.setText("Edit Meeting");
        btn_ok.setText("Save Changes");

        mid = getIntent().getIntExtra("mid", -1);
        if (mid < 0) Util.alertError(this, R.string.err_invalidMeeting);

        AsyncTask.execute(() -> {
            Meeting oldMeeting = UserDatabase.UDB.meetingDao().get(mid);
            if (oldMeeting == null) runOnUiThread(() ->
                    Util.alertError(this, R.string.err_invalidMeeting));
            else {
                cid = oldMeeting.cid;
                getIntent().putExtra("cid", cid); // Stops NewMeeting from erroring
                et_section.setText(String.valueOf(oldMeeting.section));
                et_location.setText(oldMeeting.location);
                startCalendar.setTime(oldMeeting.start);
                endCalendar.setTime(oldMeeting.end);
                et_startdate.setText(dateFormat.format(startCalendar.getTime()));
                et_enddate.setText(dateFormat.format(endCalendar.getTime()));
                et_starttime.setText(timeFormat.format(startCalendar.getTime()));
                et_endtime.setText(timeFormat.format(endCalendar.getTime()));

                Weekdays weekdays;
                weekdays = oldMeeting.weekdays;
                if (weekdays.contains(Weekday.SUNDAY)) cb_sunday.setChecked(true);
                if (weekdays.contains(Weekday.MONDAY)) cb_monday.setChecked(true);
                if (weekdays.contains(Weekday.TUESDAY)) cb_tuesday.setChecked(true);
                if (weekdays.contains(Weekday.WEDNESDAY)) cb_wednesday.setChecked(true);
                if (weekdays.contains(Weekday.THURSDAY)) cb_thursday.setChecked(true);
                if (weekdays.contains(Weekday.FRIDAY)) cb_friday.setChecked(true);
                if (weekdays.contains(Weekday.SATURDAY)) cb_saturday.setChecked(true);

                spinnerMeetingType.setSelection(Arrays.asList(MeetingTypeEnum.values()).indexOf(oldMeeting.type));
            }
        });
    }

    @Override
    protected void attemptInsertion(View view) {
        AsyncTask.execute(() -> {
            try {
                Meeting meeting = parseMeeting();
                meeting.mid = mid;
                meeting.cid = cid;
                UserDatabase.UDB.meetingDao().update(meeting);
            } catch (Exception e) {
                String msg = e.getMessage();
                Snackbar.make(this, view, msg==null?"Error":msg, Snackbar.LENGTH_SHORT).show();
                return;
            }
            runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                    "Meeting successfully added", Toast.LENGTH_SHORT).show()
            );
            finish();
        });
    }
}