package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.theprincipleapp.db.Class;
import com.example.theprincipleapp.db.Course;
import com.example.theprincipleapp.db.Task;
import com.example.theprincipleapp.db.UserClass;
import com.example.theprincipleapp.db.UserDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewClass extends AppCompatActivity {

    EditText et_classCode, et_fullName, et_description, et_professor;
    Button button_ok, button_cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        et_classCode = findViewById(R.id.et_classcode);
        et_fullName = findViewById(R.id.et_fullname);
        et_description = findViewById(R.id.et_description);
        et_professor = findViewById(R.id.et_professor);
        button_ok = findViewById(R.id.button_ok);
        button_cancel = findViewById(R.id.button_cancel);

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
            c.start = Calendar.getInstance().getTime();
            c.end = Calendar.getInstance().getTime();
            userClass.cls = c;

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {


                    UserDatabase.UDB.userClassDao().insert(userClass);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Course successfully added", Toast.LENGTH_LONG).show();
                            et_professor.setText("");
                            et_fullName.setText("");
                            et_description.setText("");
                            et_classCode.setText("");
                        }
                    });
                }
            });
        });
        button_cancel.setOnClickListener(view -> finish());
    }

    void insertClass (View v) {

        // TODO: Save
        finish();
    }
}