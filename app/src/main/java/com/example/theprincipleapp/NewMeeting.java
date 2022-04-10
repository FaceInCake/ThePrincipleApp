package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.theprincipleapp.db.MeetingTypeEnum;
import com.example.theprincipleapp.db.Weekday;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewMeeting extends AppCompatActivity {

    Button btn_ok, btn_cancel;
    Spinner spinnerMeetingType, spinnerWeekday;
    EditText et_section, et_location, et_startdate, et_starttime, et_enddate, et_endtime;

    int section;
    String location;
    Weekday weekday;
    MeetingTypeEnum meetingTypeEnum;

    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    Date startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);


        spinnerMeetingType = findViewById(R.id.spinner_meetingtype);
        spinnerWeekday = findViewById(R.id.spinner_meetingweekday);
        et_section = findViewById(R.id.et_section);
        et_location = findViewById(R.id.et_location);
        btn_ok = findViewById(R.id.btn_okmeeting);
        btn_cancel = findViewById(R.id.btn_cancelmeeting);

        et_startdate = findViewById(R.id.et_startdate);
        et_starttime = findViewById(R.id.et_starttime);
        et_enddate = findViewById(R.id.et_enddate);
        et_endtime = findViewById(R.id.et_endtime);



        spinnerMeetingType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MeetingTypeEnum.values()));
        spinnerWeekday.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Weekday.values()));


        btn_ok.setOnClickListener(view -> {
            int meetingSpinnerPosition = spinnerMeetingType.getLastVisiblePosition();
            int weekdaySpinnerPosition = spinnerWeekday.getLastVisiblePosition();


            startDate = startCalendar.getTime();
            endDate = endCalendar.getTime();
            section = Integer.parseInt(et_section.getText().toString());
            location = et_location.getText().toString();
            meetingTypeEnum = MeetingTypeEnum.values()[meetingSpinnerPosition];
            weekday = Weekday.values()[weekdaySpinnerPosition];



            // TODO: save new meeting to database



            finish();

        });

        btn_cancel.setOnClickListener(view -> {
            finish();
        });


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