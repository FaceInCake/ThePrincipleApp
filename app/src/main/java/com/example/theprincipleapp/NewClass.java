package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.theprincipleapp.db.Class;
import com.example.theprincipleapp.db.Course;
import com.example.theprincipleapp.db.UserDatabase;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class NewClass extends AppCompatActivity {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    protected EditText et_classCode, et_fullName, et_description, et_professor, et_startDate, et_endDate;
    protected Calendar c;
    protected Date start, end;
    protected Button button_ok, button_cancel;

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

        button_ok.setOnClickListener(this::attemptInsertion);
        button_cancel.setOnClickListener(view -> finish());
    }

    protected Course parseCourse () throws ParseException {
        Course course = new Course();
        course.full_name = et_fullName.getText().toString();
        if (course.full_name.isEmpty())
            throw new ParseException("Course name cannot be empty", 0);
        course.code = et_classCode.getText().toString();
        if (course.code.isEmpty())
            throw new ParseException("Course code cannot be empty", 0);
        course.description = et_description.getText().toString();
        // Description can be empty
        return course;
    }

    protected Class parseClass () throws ParseException {
        Class c = new Class();
        c.professor = et_professor.getText().toString();
        if (c.professor.isEmpty()) c.professor = "TBD";
        try { c.start = sdf.parse(et_startDate.getText().toString());
        } catch (ParseException e) {
            throw new ParseException("Invalid start date", 0);
        }
        try { c.end = sdf.parse(et_endDate.getText().toString());
        } catch (ParseException e) {
            throw new ParseException("Invalid end date", 0);
        }
        if (c.start.after(c.end))
            throw new ParseException("End date must take place after the start date", 0);
        return c;
    }

    protected void attemptInsertion (View v) {
        Course o; Class c;
        try {
            o = parseCourse();
            c = parseClass();
        } catch (ParseException e) {
            String msg = e.getMessage();
            Snackbar.make(this, v, msg==null?"Error":msg, Snackbar.LENGTH_SHORT).show();
            return;
        }
        AsyncTask.execute(() -> {
            try {
                c.oid = (int) UserDatabase.UDB.courseDao().insert(o);
                UserDatabase.UDB.classDao().insert(c);
            } catch (SQLiteConstraintException e) {
                // Only possible constraint exception is a non-unique course code
                Snackbar.make(this, v, "Course code must be unique", Snackbar.LENGTH_SHORT).show();
                return;
            }
            runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                "Course successfully added", Toast.LENGTH_SHORT).show());
            finish();
        });
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