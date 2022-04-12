package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class NewMeeting extends AppCompatActivity {

    Button btn_ok, btn_cancel;
    Spinner spinnerMeetingType;
    EditText et_section, et_location, et_startdate, et_starttime, et_enddate, et_endtime;
    CheckBox cb_sunday, cb_monday, cb_tuesday, cb_wednesday, cb_thursday, cb_friday, cb_saturday;

    int section;
    String location;
    MeetingTypeEnum meetingTypeEnum;

    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    Date startDate, endDate;


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

        btn_ok.setOnClickListener(view -> {
            int meetingSpinnerPosition = spinnerMeetingType.getLastVisiblePosition();
            startDate = startCalendar.getTime();
            endDate = endCalendar.getTime();
            if(et_section.getText().toString().equals("")){
                section = -1;
            }else{
                section = Integer.parseInt(et_section.getText().toString());
            }
            location = et_location.getText().toString();
            meetingTypeEnum = MeetingTypeEnum.values()[meetingSpinnerPosition];

            Weekdays weekdays = new Weekdays();
            if(cb_sunday.isChecked())    weekdays.add(Weekday.SUNDAY);
            if(cb_monday.isChecked())    weekdays.add(Weekday.MONDAY);
            if(cb_tuesday.isChecked())   weekdays.add(Weekday.TUESDAY);
            if(cb_wednesday.isChecked()) weekdays.add(Weekday.WEDNESDAY);
            if(cb_thursday.isChecked())  weekdays.add(Weekday.THURSDAY);
            if(cb_friday.isChecked())    weekdays.add(Weekday.FRIDAY);
            if(cb_saturday.isChecked())  weekdays.add(Weekday.SATURDAY);

            AsyncTask.execute(() -> {
                int cid = getIntent().getIntExtra("cid", -1);
                if (cid < 0) Util.alertError(this, R.string.err_invalidMeeting);

                Meeting meeting = new Meeting();
                meeting.type = meetingTypeEnum;
                meeting.section = section;
                meeting.weekdays = weekdays;
                meeting.location = location;
                meeting.start = startDate;
                meeting.end = endDate;
                meeting.cid = cid;

                UserDatabase.UDB.meetingDao().insert(meeting);

                runOnUiThread(()->{
                    Toast.makeText(getApplicationContext(),"Meeting successfully added", Toast.LENGTH_LONG).show();
                });
                finish();
            });
        });

        btn_cancel.setOnClickListener(view -> finish());

        // start listeners
        DatePickerDialog.OnDateSetListener openDateDate = (view, year, month, day) -> {
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH,month);
            startCalendar.set(Calendar.DAY_OF_MONTH,day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
            et_startdate.setText(dateFormat.format(startCalendar.getTime()));
        };

        TimePickerDialog.OnTimeSetListener openDateTime = (timePicker, i, i1) -> {
            startCalendar.set(Calendar.MINUTE, timePicker.getMinute());
            startCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            et_starttime.setText(dateFormat.format(startCalendar.getTime()));
        };

        et_startdate.setOnClickListener(view -> new DatePickerDialog(this, openDateDate, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show());

        et_starttime.setOnClickListener(view -> new TimePickerDialog(this, openDateTime, startCalendar.get(Calendar.MINUTE), startCalendar.get(Calendar.HOUR), false).show());


        // end listeners
        DatePickerDialog.OnDateSetListener dueDateDate = (view, year, month, day) -> {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH,month);
            endCalendar.set(Calendar.DAY_OF_MONTH,day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
            et_enddate.setText(dateFormat.format(endCalendar.getTime()));
        };

        TimePickerDialog.OnTimeSetListener dueDateTime = (timePicker, i, i1) -> {
            endCalendar.set(Calendar.MINUTE, timePicker.getMinute());
            endCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            et_endtime.setText(dateFormat.format(endCalendar.getTime()));
        };

        et_enddate.setOnClickListener(view -> new DatePickerDialog(this, dueDateDate, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show());

        et_endtime.setOnClickListener(view -> new TimePickerDialog(this, dueDateTime, endCalendar.get(Calendar.MINUTE), endCalendar.get(Calendar.HOUR), false).show());
    }
}