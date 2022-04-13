package com.example.theprincipleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.theprincipleapp.db.Meeting;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.helpers.Util;

public class ViewMeeting extends AppCompatActivity {
    InfoSection type,sec,wds,start,end,local;
    int mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);

        mid = getIntent().getIntExtra("mid", -1);
        if (mid < 0) Util.alertError(this, R.string.err_invalidMeeting);

        type = findViewById(R.id.meetType);
        sec = findViewById(R.id.meetSection);
        wds = findViewById(R.id.meetWeekdays);
        start = findViewById(R.id.meetStart);
        end = findViewById(R.id.meetEnd);
        local = findViewById(R.id.meetLocal);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AsyncTask.execute(() -> {
            Meeting m = UserDatabase.UDB.meetingDao().get(mid);
            runOnUiThread(() -> {
                type.setValue(m.type.toString());
                sec.setValue(String.valueOf(m.section));
                wds.setValue(m.weekdays.abbreviate());
                start.setValue(m.start.toString());
                end.setValue(m.end.toString());
                if (m.location.isEmpty())
                     local.setVisibility(View.GONE);
                else local.setValue(m.location);
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
                Intent intent = new Intent(this, EditMeeting.class);
                intent.putExtra("mid", mid);
                startActivity(intent);
                return true;
            case R.id.action_delete:
                AsyncTask.execute(() -> {
                    Meeting m = UserDatabase.UDB.meetingDao().get(mid);
                    UserDatabase.UDB.meetingDao().delete(m);
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(),"Meeting successfully deleted",Toast.LENGTH_LONG).show();
                        finish();
                    });
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}