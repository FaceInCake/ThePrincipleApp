package com.example.theprincipleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.helpers.TaskAdapter;


public class ViewAllTasks extends AppCompatActivity {
    RecyclerView recView;
    TaskAdapter adapter;
    int cid;

    @SuppressLint("NotifyDataSetChanged") // Whole set is changed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_tasks);

        cid = getIntent().getIntExtra("cid", -1);
        recView = findViewById(R.id.vat_recView);
        adapter = new TaskAdapter(this);
        recView.setAdapter(adapter);

        AsyncTask.execute(() -> {
            adapter.tasks = cid < 0
            ?   UserDatabase.UDB.taskDao().getAll()
            :   UserDatabase.UDB.taskDao().getFrom(cid);
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (cid < 0) return false;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_viewallclasses, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i = new Intent(this, NewTask.class);
                i.putExtra("cid", cid);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
