package com.example.theprincipleapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.theprincipleapp.db.Class;
import com.example.theprincipleapp.db.Course;
import com.example.theprincipleapp.db.UserClass;
import com.example.theprincipleapp.db.UserDatabase;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
            String res = attemptInsertion();
            if (res == null)
                Toast.makeText(getApplicationContext(),
                    "Course successfully added", Toast.LENGTH_SHORT).show();
            else
                Snackbar.make(this, view, res, Snackbar.LENGTH_SHORT).show();
        });
        button_cancel.setOnClickListener(view -> finish());
    }

    /**
     * Parses the current state of the editable fields and inserts the course and class
     * @return null on success, error message on error
     */
    @Nullable private String attemptInsertion () {
        Course course = new Course();
        course.full_name = et_fullName.getText().toString();
        if (course.full_name.isEmpty()) return "Course name cannot be empty";
        course.code = et_classCode.getText().toString();
        if (course.code.isEmpty()) return "Course code cannot be empty";
        course.description = et_description.getText().toString();
        // Description can be empty
        Class c = new Class();
        c.professor = et_professor.getText().toString();
        if (c.professor.isEmpty()) c.professor = "TBD";
        try { c.start = sdf.parse(et_startDate.getText().toString());
        } catch (ParseException e) { return "Invalid start date"; }
        try { c.end = sdf.parse(et_endDate.getText().toString());
        } catch (ParseException e) { return "Invalid end date"; }
        if (c.start.after(c.end)) return "End date must take place after the start date";

        AsyncTask.execute(() -> {
            c.oid = (int) UserDatabase.UDB.courseDao().insert(course);
            UserDatabase.UDB.classDao().insert(c);
        });
        finish();
        return null;
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