package com.example.theprincipleapp;

import static com.example.theprincipleapp.helpers.Util.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.theprincipleapp.db.Task;
import com.example.theprincipleapp.db.TaskTypeEnum;
import com.example.theprincipleapp.helpers.Util;
import com.google.android.material.snackbar.Snackbar;
import java.util.Arrays;
import java.util.Locale;


public class EditTask extends NewTask {
    TextView textViewHeader;
    int tid, cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        tid = getIntent().getIntExtra("tid", -1);
        if (tid == -1) Util.alertError(this, R.string.err_invalid_task);

        super.onCreate(savedInstanceState);

        textViewHeader = findViewById(R.id.textViewHeader);
        textViewHeader.setText("Edit Task");
        btnSubmit.setText("Save Changes");

        AsyncTask.execute(() -> {
            Task oldTask = userdb.taskDao().get(tid);
            if (oldTask == null)
                runOnUiThread(() -> Util.alertError(this, R.string.err_invalid_task));
            else {
                cid = oldTask.cid;
                getIntent().putExtra("cid", cid); // Prevents super.onStart from erroring
                openDateCalendar.setTime(oldTask.open);
                dueDateCalendar.setTime(oldTask.due);
                editTextDescription.setText(oldTask.description);
                editTextName.setText(oldTask.name);
                editTextLocation.setText(oldTask.location);
                editTextGrade.setText(String.format(Locale.getDefault(), "%f", oldTask.grade));
                editTextOpenDateDate.setText(dateFormat.format(openDateCalendar.getTime()));
                editTextOpenDateTime.setText(timeFormat.format(openDateCalendar.getTime()));
                editTextDueDateDate.setText(dateFormat.format(dueDateCalendar.getTime()));
                editTextDueDateTime.setText(timeFormat.format(dueDateCalendar.getTime()));
                spinnerTaskType.setSelection(Arrays.asList(TaskTypeEnum.values()).indexOf(oldTask.type));
            }
        });
    }

    @Override
    protected void submit (View v) {
        AsyncTask.execute(() -> {
            try {
                Task task = parseTask();
                task.cid = cid;
                task.tid = tid;
                userdb.taskDao().update(task);
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
}