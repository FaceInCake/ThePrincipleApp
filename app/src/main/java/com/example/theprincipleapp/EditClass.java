package com.example.theprincipleapp;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theprincipleapp.db.Class;
import com.example.theprincipleapp.db.Course;
import com.example.theprincipleapp.db.UserClass;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.helpers.Util;
import com.google.android.material.snackbar.Snackbar;
import java.text.ParseException;


public class EditClass extends NewClass {
    TextView tv_header;
    int cid, oid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        cid = getIntent().getIntExtra("cid", -1);
        if (cid < 0) Util.alertError(this, R.string.err_invalidClass);

        // Super NewClass inits most things
        super.onCreate(savedInstanceState);

        tv_header = findViewById(R.id.tv_newclass);
        tv_header.setText(R.string.edit_class);
        button_ok.setText(R.string.save_changes);

        AsyncTask.execute(() -> {
            UserClass c = UserDatabase.UDB.userClassDao().get(cid);
            if (c == null) runOnUiThread(() -> Util.alertError(this, R.string.err_invalidClass));
            else {
                oid = c.cls.oid;
                et_classCode.setText(c.course.code);
                et_fullName.setText(c.course.full_name);
                et_description.setText(c.course.description);
                et_professor.setText(c.cls.professor);
                start.setTime(c.cls.start);
                end.setTime(c.cls.end);
                et_startDate.setText(Util.sdf.format(start.getTime()));
                et_endDate.setText(Util.sdf.format(end.getTime()));
            }
        });
    }

    @Override
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
        c.cid = cid;
        c.oid = oid;
        o.oid = oid;
        AsyncTask.execute(() -> {
            try {
                UserDatabase.UDB.courseDao().update(o);
                UserDatabase.UDB.classDao().update(c);
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
}