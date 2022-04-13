package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theprincipleapp.db.Task;
import com.example.theprincipleapp.db.TaskTypeEnum;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.helpers.Util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class EditTask extends AppCompatActivity {
    Button btnSubmit, btnCancel;
    EditText editTextDescription, editTextName, editTextLocation, editTextOpenDateDate,
            editTextOpenDateTime, editTextDueDateDate, editTextDueDateTime, editTextGrade;
    TextView textViewHeader;
    Spinner spinnerTaskType;

    final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
    final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    final Calendar openDateCalendar = Calendar.getInstance();
    final Calendar dueDateCalendar = Calendar.getInstance();

    Integer tid;
    UserDatabase userdb = UserDatabase.UDB;
    Task oldTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        tid = getIntent().getIntExtra("tid", -1);
        if (tid == -1) Util.alertError(this, R.string.err_invalid_task);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please select a valid task type.");
        AlertDialog dialog = alertDialogBuilder.create();

        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        textViewHeader = findViewById(R.id.textViewHeader);
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


        textViewHeader.setText("Edit Task");
        btnSubmit.setText("Save Changes");


        AsyncTask.execute(() -> {
            oldTask = userdb.taskDao().get(tid);
            if (oldTask == null) runOnUiThread(() -> Util.alertError(this, R.string.err_invalid_task));
            else {
                openDateCalendar.setTime(oldTask.open);
                dueDateCalendar.setTime(oldTask.due);
                editTextDescription.setText(oldTask.description);
                editTextName.setText(oldTask.name);
                editTextLocation.setText(oldTask.location);
                editTextGrade.setText(String.format(Locale.getDefault(), "%2.0f", oldTask.grade));
                editTextOpenDateDate.setText(dateFormat.format(openDateCalendar.getTime()));
                editTextOpenDateTime.setText(timeFormat.format(openDateCalendar.getTime()));
                editTextDueDateDate.setText(dateFormat.format(dueDateCalendar.getTime()));
                editTextDueDateTime.setText(timeFormat.format(dueDateCalendar.getTime()));
                spinnerTaskType.setSelection(Arrays.asList(TaskTypeEnum.values()).indexOf(oldTask.type));
            }
        });

        btnSubmit.setOnClickListener(view -> {
            int spinnerSelectedPosition = spinnerTaskType.getLastVisiblePosition();

            if (spinnerSelectedPosition == 0){
                dialog.show();

            } else {
                AsyncTask.execute(() -> {

                    // Editing the old task retrieved earlier
                    oldTask.location = editTextLocation.getText().toString();
                    oldTask.name = editTextName.getText().toString();
                    oldTask.description = editTextDescription.getText().toString();
                    oldTask.grade = Float.parseFloat(editTextGrade.getText().toString());
                    oldTask.type = TaskTypeEnum.values()[spinnerSelectedPosition];
                    oldTask.open =  openDateCalendar.getTime();
                    oldTask.due = dueDateCalendar.getTime();
                    oldTask.type = TaskTypeEnum.values()[spinnerSelectedPosition];

                    // inserting said task
                    userdb.taskDao().update(oldTask);

                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(),"Task successfully updated", Toast.LENGTH_LONG).show();
                    });
                });
                finish();
            }
        });

        btnCancel.setOnClickListener(view -> finish());


        // -------------------------- Listeners for choosing open date and time --------------------------
        DatePickerDialog.OnDateSetListener openDateDate = (view, year, month, day) -> {
            openDateCalendar.set(Calendar.YEAR, year);
            openDateCalendar.set(Calendar.MONTH,month);
            openDateCalendar.set(Calendar.DAY_OF_MONTH,day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
            editTextOpenDateDate.setText(dateFormat.format(openDateCalendar.getTime()));
        };

        TimePickerDialog.OnTimeSetListener openDateTime = (timePicker, i, i1) -> {
            openDateCalendar.set(Calendar.MINUTE, timePicker.getMinute());
            openDateCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            editTextOpenDateTime.setText(dateFormat.format(openDateCalendar.getTime()));
        };

        editTextOpenDateDate.setOnClickListener(view -> new DatePickerDialog(this, openDateDate, openDateCalendar.get(Calendar.YEAR), openDateCalendar.get(Calendar.MONTH), openDateCalendar.get(Calendar.DAY_OF_MONTH)).show());

        editTextOpenDateTime.setOnClickListener(view -> new TimePickerDialog(this, openDateTime, openDateCalendar.get(Calendar.MINUTE), openDateCalendar.get(Calendar.HOUR), false).show());
        // -------------------------- END Listeners for choosing open date and time --------------------------


        // -------------------------- Listeners for choosing due date and time --------------------------
        DatePickerDialog.OnDateSetListener dueDateDate = (view, year, month, day) -> {
            dueDateCalendar.set(Calendar.YEAR, year);
            dueDateCalendar.set(Calendar.MONTH,month);
            dueDateCalendar.set(Calendar.DAY_OF_MONTH,day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
            editTextDueDateDate.setText(dateFormat.format(dueDateCalendar.getTime()));
        };

        TimePickerDialog.OnTimeSetListener dueDateTime = (timePicker, i, i1) -> {
            dueDateCalendar.set(Calendar.MINUTE, timePicker.getMinute());
            dueDateCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            editTextDueDateTime.setText(dateFormat.format(dueDateCalendar.getTime()));
        };

        editTextDueDateDate.setOnClickListener(view -> new DatePickerDialog(this, dueDateDate, dueDateCalendar.get(Calendar.YEAR), dueDateCalendar.get(Calendar.MONTH), dueDateCalendar.get(Calendar.DAY_OF_MONTH)).show());

        editTextDueDateTime.setOnClickListener(view -> new TimePickerDialog(this, dueDateTime, dueDateCalendar.get(Calendar.MINUTE), dueDateCalendar.get(Calendar.HOUR), false).show());
        // -------------------------- End Listeners for choosing due date and time --------------------------

    }
}