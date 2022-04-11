package com.example.theprincipleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.theprincipleapp.db.Task;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.helpers.Util;


public class ViewTask extends AppCompatActivity {
    InfoSection name,desc,type,open,close,local,grade;
    int tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        tid = getIntent().getIntExtra("tid", -1);
        if (tid == -1) Util.alertError(this, R.string.err_invalidTask);

        name = findViewById(R.id.taskName);
        desc = findViewById(R.id.taskDesc);
        type = findViewById(R.id.taskType);
        open = findViewById(R.id.taskOpen);
        close = findViewById(R.id.taskClose);
        local = findViewById(R.id.taskLocal);
        grade = findViewById(R.id.taskGrade);

        AsyncTask.execute(() -> {
            Task t = UserDatabase.UDB.taskDao().get(tid);
            runOnUiThread(() -> {
                name.setValue(t.name);
                desc.setValue(t.description);
                type.setValue(t.type.toString());
                open.setValue(t.open.toString());
                close.setValue(t.due.toString());
                local.setValue(t.location);
                grade.setValue(String.valueOf(t.grade));
            });
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_edit:
                Intent intent = new Intent(this, EditTask.class);
                intent.putExtra("tid", tid);
                startActivity(intent);
                return true;

            case R.id.action_delete:
                //TODO: delete a task
                Toast.makeText(getApplicationContext(),"delete",Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}