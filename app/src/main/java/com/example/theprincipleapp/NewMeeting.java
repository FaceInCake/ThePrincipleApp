package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewMeeting extends AppCompatActivity {

    Button btn_ok, btn_cancel;
    Spinner spinnerMeetingType, spinnerWeekday;
    EditText et_section, et_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);

        btn_ok = findViewById(R.id.btn_okmeeting);
        btn_cancel = findViewById(R.id.btn_cancelmeeting);
        spinnerMeetingType = findViewById(R.id.spinner_meetingtype);
        spinnerWeekday = findViewById(R.id.spinner_meetingweekday);
        et_section = findViewById(R.id.et_section);
        et_location = findViewById(R.id.et_location);

        btn_ok.setOnClickListener(view -> {

        });

        btn_cancel.setOnClickListener(view -> {

        });

    }
}