package com.example.theprincipleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.theprincipleapp.db.UserClass;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.helpers.Util;

public class ViewClass extends AppCompatActivity {
    InfoSection oName, oCode, oDesc, cProf, cStart, cEnd;

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

        AsyncTask.execute(() -> {
            int cid = getIntent().getIntExtra("cid", -1);
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
                //TODO: edit a class
                Toast.makeText(getApplicationContext(),"edit",Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_delete:
                //TODO: delete a class
                Toast.makeText(getApplicationContext(),"delete",Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}