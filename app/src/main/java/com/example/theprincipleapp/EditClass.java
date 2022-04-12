package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.theprincipleapp.db.UserClass;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.helpers.Util;

import java.util.Calendar;
import java.util.Date;


public class EditClass extends AppCompatActivity {

    EditText et_classCode, et_fullName, et_description, et_professor, et_start, et_end;
    Button button_ok, button_cancel;
    TextView tv_header;
    Calendar cal;
    Date start, end;
    String classCode, fullName, description, professor;
    int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        tv_header = findViewById(R.id.tv_newclass);
        et_classCode = findViewById(R.id.et_classcode);
        et_fullName = findViewById(R.id.et_fullname);
        et_description = findViewById(R.id.et_description);
        et_professor = findViewById(R.id.et_professor);
        et_start = findViewById(R.id.et_startDate);
        et_end = findViewById(R.id.et_endDate);
        button_ok = findViewById(R.id.button_ok);
        button_cancel = findViewById(R.id.button_cancel);

        tv_header.setText(R.string.edit_class);
        button_ok.setText(R.string.save_changes);

        cal = Calendar.getInstance();

        AsyncTask.execute(() -> {
            cid = getIntent().getIntExtra("cid", -1);
            if (cid < 0) runOnUiThread(() -> Util.alertError(this, R.string.err_invalidClass));
            UserClass c = UserDatabase.UDB.userClassDao().get(cid);
            if (c == null) runOnUiThread(() -> Util.alertError(this, R.string.err_invalidClass));
            else {
                et_classCode.setText(c.course.code);
                et_fullName.setText(c.course.full_name);
                et_description.setText(c.course.description);
                et_professor.setText(c.cls.professor);
                start = c.cls.start;
                end = c.cls.end;
                et_start.setText(NewClass.sdf.format(start));
                et_end.setText(NewClass.sdf.format(end));
            }
        });

        et_start.setOnClickListener(view -> updateDate(start, et_start));
        et_end.setOnClickListener(view -> updateDate(end, et_end));

        button_ok.setOnClickListener(view -> {

            classCode = et_classCode.getText().toString();
            fullName = et_fullName.getText().toString();
            description = et_description.getText().toString();
            professor = et_professor.getText().toString();

            AsyncTask.execute(() -> {
                cid = getIntent().getIntExtra("cid", -1);
                if (cid < 0) runOnUiThread(() -> Util.alertError(this, R.string.err_invalidClass));
                UserClass c = UserDatabase.UDB.userClassDao().get(cid);
                if (c == null) runOnUiThread(() -> Util.alertError(this, R.string.err_invalidClass));
                else {
                    c.cls.professor = professor;
                    c.cls.start = start;
                    c.cls.end = end;
                    c.course.code = classCode;
                    c.course.full_name = fullName;
                    c.course.description = description;

                    UserDatabase.UDB.userClassDao().update(c);

                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), "Course successfully updated", Toast.LENGTH_LONG).show()
                    );
                }
            });
            finish();
        });
    }

    public void updateDate (Date date, EditText et) {
        cal.setTime(date);
        new DatePickerDialog(
            this,
            (datePicker, y, m, d) -> {
                cal.set(y, m, d);
                date.setTime(cal.getTime().getTime());
                et.setText(NewClass.sdf.format(date));
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

}