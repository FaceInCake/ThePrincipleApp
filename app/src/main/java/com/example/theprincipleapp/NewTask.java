package com.example.theprincipleapp;

import static com.example.theprincipleapp.helpers.Util.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.theprincipleapp.db.Task;
import com.example.theprincipleapp.db.TaskTypeEnum;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.helpers.Util;
import com.google.android.material.snackbar.Snackbar;
import java.text.ParseException;
import java.util.Calendar;


public class NewTask extends AppCompatActivity {

    Button btnSubmit, btnCancel;
    EditText editTextDescription, editTextName, editTextLocation, editTextOpenDateDate,
            editTextOpenDateTime, editTextDueDateDate, editTextDueDateTime, editTextGrade;
    Spinner spinnerTaskType;
    final Calendar openDateCalendar = Calendar.getInstance();
    final Calendar dueDateCalendar = Calendar.getInstance();
    int cid;

    UserDatabase userdb = UserDatabase.UDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        editTextDescription = findViewById(R.id.editTextMultilineDescription);
        editTextName = findViewById(R.id.editTextName);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextOpenDateDate = findViewById(R.id.editTextOpenDate);
        editTextOpenDateTime = findViewById(R.id.editTextOpenTime);
        editTextDueDateDate = findViewById(R.id.editTextDueDate);
        editTextDueDateTime = findViewById(R.id.editTextDueTime);
        editTextGrade = findViewById(R.id.editTextGrade);

        spinnerTaskType = findViewById(R.id.spinnerTaskType);
        spinnerTaskType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TaskTypeEnum.values()));

        btnSubmit.setOnClickListener(this::submit);
        btnCancel.setOnClickListener(view -> finish());

        editTextOpenDateDate.setOnClickListener(view
            -> showDatePicker(this, openDateCalendar, editTextOpenDateDate));
        editTextOpenDateTime.setOnClickListener(view
            -> showTimePicker(this, openDateCalendar, editTextOpenDateTime));
        editTextDueDateDate.setOnClickListener(view
            -> showDatePicker(this, dueDateCalendar, editTextOpenDateDate));
        editTextDueDateTime.setOnClickListener(view
            -> showTimePicker(this, dueDateCalendar, editTextDueDateTime));
    }

    @Override
    protected void onStart() {
        super.onStart();
        cid = getIntent().getIntExtra("cid", -1);
        if (cid == -1) Util.alertError(this, R.string.err_invalid_class_for_task);
    }

    protected void submit (View v) {
        AsyncTask.execute(() -> {
            try {
                Task task = parseTask();
                userdb.taskDao().insert(task);
            } catch (Exception e) {
                String msg = e.getMessage();
                Snackbar.make(this, v, msg==null?"Error":msg, Snackbar.LENGTH_SHORT).show();
                return;
            }
            // alerting user that the new task was added
            runOnUiThread(() -> Toast.makeText(getApplicationContext(),
                "Task successfully added", Toast.LENGTH_SHORT).show());
            finish();
        });
    }

    protected Task parseTask () throws ParseException {
        Task task = new Task();
        task.name = editTextName.getText().toString();
        if (task.name.isEmpty())
            throw new ParseException("Task name cannot be empty", 0);
        int s = spinnerTaskType.getLastVisiblePosition();
        if (s <= 0 || s >= TaskTypeEnum.values().length)
            throw new ParseException("Please select a valid task type", 0);
        task.type = TaskTypeEnum.values()[s];
        task.open = openDateCalendar.getTime();
        task.due = dueDateCalendar.getTime();
        if (task.open.after(task.due))
            throw new ParseException("Due date must be after the open date", 0);
        try { task.grade = Float.parseFloat(editTextGrade.getText().toString()); }
        catch (NumberFormatException e) { throw new ParseException("Invalid task grade: "+e.getMessage(), 1); }
        if (task.grade < 0 || task.grade > 100)
            throw new ParseException("Task grade must be a valid percentage", 0);
        task.location = editTextLocation.getText().toString();
        // Location can be empty
        task.description = editTextDescription.getText().toString();
        // Description can be empty
        task.cid = cid;
        return task;
    }

}
