package com.example.theprincipleapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theprincipleapp.db.UserDatabase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import com.example.theprincipleapp.helpers.HomeRecAdapter;


public class HomePage extends AppCompatActivity {
    RecyclerView recyclerView;
    HomeRecAdapter adapter;
    Button btn_classes, btn_calendar, btn_tasks;

    @SuppressLint("NotifyDataSetChanged") // Entire set is changed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        UserDatabase.createDatabase(this);

        recyclerView = findViewById(R.id.home_recview);
        btn_classes = findViewById(R.id.home_btn_allClasses);
        btn_calendar =  findViewById(R.id.home_btn_calendar);
        btn_tasks = findViewById(R.id.home_btn_allTasks);



        btn_classes.setOnClickListener(view -> {
            Intent i = new Intent(this, ViewAllClasses.class);
            startActivity(i);
        });

        // View calendar

        // View all tasks
        btn_tasks.setOnClickListener(view ->
            startActivity(new Intent(this, ViewAllTasks.class))
        );

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new HomeRecAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this));
        recyclerView.setAdapter(adapter);
        AsyncTask.execute(() -> {
            adapter.classes = UserDatabase.UDB.userClassDao().getAll();
            runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
            });
        });
    }
}
