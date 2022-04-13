package com.example.theprincipleapp;

import static com.example.theprincipleapp.helpers.Util.*;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.theprincipleapp.db.Meeting;
import com.example.theprincipleapp.db.MeetingTypeEnum;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.db.Weekday;
import com.example.theprincipleapp.db.Weekdays;
import com.example.theprincipleapp.helpers.Util;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class NewMeeting extends AppCompatActivity {
    Button btn_ok, btn_cancel;
    Spinner spinnerMeetingType;
    EditText et_section, et_location, et_startdate, et_starttime, et_enddate, et_endtime;
    CheckBox cb_sunday, cb_monday, cb_tuesday, cb_wednesday, cb_thursday, cb_friday, cb_saturday;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();
    int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);

        spinnerMeetingType = findViewById(R.id.spinner_meetingtype);
        et_section = findViewById(R.id.et_section);
        et_location = findViewById(R.id.et_location);
        btn_ok = findViewById(R.id.btn_okmeeting);
        btn_cancel = findViewById(R.id.btn_cancelmeeting);

        et_startdate = findViewById(R.id.et_startdate);
        et_starttime = findViewById(R.id.et_starttime);
        et_enddate = findViewById(R.id.et_enddate);
        et_endtime = findViewById(R.id.et_endtime);

        cb_sunday = findViewById(R.id.cb_sunday);
        cb_monday = findViewById(R.id.cb_monday);
        cb_tuesday = findViewById(R.id.cb_tuesday);
        cb_wednesday = findViewById(R.id.cb_wednesday);
        cb_thursday = findViewById(R.id.cb_thursday);
        cb_friday = findViewById(R.id.cb_friday);
        cb_saturday = findViewById(R.id.cb_saturday);

        spinnerMeetingType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MeetingTypeEnum.values()));

        btn_ok.setOnClickListener(this::attemptInsertion);
        btn_cancel.setOnClickListener(view -> finish());
        et_startdate.setOnClickListener(view -> showDatePicker(this, startCalendar, et_startdate));
        et_starttime.setOnClickListener(view -> showTimePicker(this, startCalendar, et_starttime));
        et_enddate.setOnClickListener(view -> showDatePicker(this, endCalendar, et_enddate));
        et_endtime.setOnClickListener(view -> showTimePicker(this, endCalendar, et_endtime));
    }

    @Override
    protected void onStart() {
        super.onStart();
        cid = getIntent().getIntExtra("cid", -1);
        if (cid < 0) Util.alertError(this, R.string.err_invalidMeeting);
    }

    protected void attemptInsertion(View view) {
        AsyncTask.execute(() -> {
            try {
                Meeting meeting = parseMeeting();
                meeting.cid = cid;
                UserDatabase.UDB.meetingDao().insert(meeting);
            } catch (Exception e) {
                String msg = e.getMessage();
                Snackbar.make(this, view, msg==null?"Error":msg, Snackbar.LENGTH_SHORT).show();
                return;
            }
            runOnUiThread(() -> {
                Toast.makeText(getApplicationContext(), "Meeting successfully added", Toast.LENGTH_SHORT).show();
            });
            finish();
        });
    }

    protected Meeting parseMeeting () throws ParseException {
        Meeting meeting = new Meeting();
        try { meeting.section = Integer.parseInt(et_section.getText().toString());
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid section number: "+e.getMessage(), 0);
        }
        if (meeting.section < 0 || meeting.section > 999)
            throw new ParseException("Invalid section number", 0);
        int s = spinnerMeetingType.getLastVisiblePosition();
        if (s < 0 || s > MeetingTypeEnum.values().length)
            throw new ParseException("Please select a valid meeting type", 0);
        meeting.type = MeetingTypeEnum.values()[s];
        meeting.start = startCalendar.getTime();
        meeting.end = endCalendar.getTime();
        if (meeting.start.after(meeting.end))
            throw new ParseException("End date-time must be after start date-time", 0);
        meeting.location = et_location.getText().toString();
        // Can be empty
        Weekdays weekdays = new Weekdays();
        if (cb_sunday.isChecked()) weekdays.add(Weekday.SUNDAY);
        if (cb_monday.isChecked()) weekdays.add(Weekday.MONDAY);
        if (cb_tuesday.isChecked()) weekdays.add(Weekday.TUESDAY);
        if (cb_wednesday.isChecked()) weekdays.add(Weekday.WEDNESDAY);
        if (cb_thursday.isChecked()) weekdays.add(Weekday.THURSDAY);
        if (cb_friday.isChecked()) weekdays.add(Weekday.FRIDAY);
        if (cb_saturday.isChecked()) weekdays.add(Weekday.SATURDAY);
        meeting.weekdays = weekdays;
        return meeting;
    }
}