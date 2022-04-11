package com.example.theprincipleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.example.theprincipleapp.db.UserClass;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.helpers.Util;

public class ViewClass extends AppCompatActivity {
    InfoSection oName, oCode, oDesc, cProf, cStart, cEnd;
    Button btnAllTasks, btnAllMeetings;
    int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class);

        oName = findViewById(R.id.courseName);
        oCode = findViewById(R.id.courseCode);
        oDesc = findViewById(R.id.courseDesc);
        cProf = findViewById(R.id.classProf);
        cStart = findViewById(R.id.classStart);
        cEnd = findViewById(R.id.classEnd);
        btnAllTasks = findViewById(R.id.btn_newTask);
        btnAllMeetings = findViewById(R.id.btn_newMeeting);

        btnAllTasks.setOnClickListener(view -> {
            Intent i = new Intent(this, ViewAllTasks.class);
            i.putExtra("cid", cid);
            startActivity(i);
        });

        btnAllMeetings.setOnClickListener(view -> {
            Intent i = new Intent(this, ViewAllMeetings.class);
            i.putExtra("cid", cid);
            startActivity(i);
        });

        AsyncTask.execute(() -> {
            cid = getIntent().getIntExtra("cid", -1);
            if (cid < 0) Util.alertError(this, R.string.err_invalidClass);
            UserClass c = UserDatabase.UDB.userClassDao().get(cid);
            if (c == null) Util.alertError(this, R.string.err_invalidClass);

            runOnUiThread(() -> {
                oName.setValue(c.course.full_name);
                oCode.setValue(c.course.code);
                oDesc.setValue(c.course.description);
                cProf.setValue(c.cls.professor);
                cStart.setValue(c.cls.start.toString());
                cEnd.setValue(c.cls.end.toString());
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
                Intent i = new Intent(this, EditClass.class);
                i.putExtra("cid", cid);
                startActivity(i);
                return true;
            case R.id.action_delete:
                AsyncTask.execute(() -> {
                    UserClass c = UserDatabase.UDB.userClassDao().get(cid);
                    if (c == null) Util.alertError(this, R.string.err_invalidClass);
                    UserDatabase.UDB.userClassDao().delete(c);
                    runOnUiThread(() -> {
                         Toast.makeText(getApplicationContext(),"Class successfully deleted",Toast.LENGTH_LONG).show();
                         finish();
                    });
                });
                    return true;



                    }
        return super.onOptionsItemSelected(item);
    }
}