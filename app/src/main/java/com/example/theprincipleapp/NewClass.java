package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.theprincipleapp.db.Class;
import com.example.theprincipleapp.db.Course;
import com.example.theprincipleapp.db.Task;
import com.example.theprincipleapp.db.UserClass;
import com.example.theprincipleapp.db.UserDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewClass extends AppCompatActivity {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    EditText et_classCode, et_fullName, et_description, et_professor, et_startDate, et_endDate;
    Calendar c;
    Date start, end;
    Button button_ok, button_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        et_classCode = findViewById(R.id.et_classcode);
        et_fullName = findViewById(R.id.et_fullname);
        et_description = findViewById(R.id.et_description);
        et_professor = findViewById(R.id.et_professor);
        et_startDate = findViewById(R.id.et_startDate);
        et_endDate = findViewById(R.id.et_endDate);
        button_ok = findViewById(R.id.button_ok);
        button_cancel = findViewById(R.id.button_cancel);

        c = Calendar.getInstance();
        start = c.getTime();
        c.roll(Calendar.MONTH, 4);
        end = c.getTime();

        et_startDate.setOnClickListener(view -> updateDate(start, et_startDate));
        et_endDate.setOnClickListener(view -> updateDate(end, et_endDate));

        button_ok.setOnClickListener(view -> {

            UserClass userClass = new UserClass();

            List<Task> tasks = new ArrayList<>();

            Course course = new Course();
            course.full_name = et_fullName.getText().toString();
            course.code = et_classCode.getText().toString();
            course.description = et_description.getText().toString();

            userClass.course = course;
            userClass.tasks = tasks;
            userClass.meetings = null;

            Class c = new Class();
            c.professor = et_professor.getText().toString();
            c.start = start;
            c.end = end;
            userClass.cls = c;

            AsyncTask.execute(() -> {
                UserDatabase.UDB.userClassDao().insert(userClass);
                runOnUiThread(() ->
                    Toast.makeText(getApplicationContext(),"Course successfully added", Toast.LENGTH_LONG).show()
                );
            });
            finish();
        });
        button_cancel.setOnClickListener(view -> finish());
    }

    public void updateDate (Date date, EditText et) {
        c.setTime(date);
        new DatePickerDialog(
            this,
            (datePicker, y, m, d) -> {
                c.set(y, m, d);
                date.setTime(c.getTime().getTime());
                et.setText(sdf.format(date));
            },
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

}