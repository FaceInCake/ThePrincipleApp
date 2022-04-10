package com.example.theprincipleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.db.Class;
import com.example.theprincipleapp.helpers.Term;

import java.util.Calendar;
import java.util.Locale;


public class ViewAllClasses extends AppCompatActivity {
    RecyclerView recyclerViewAllCourses;
    TextView textViewTerm, textViewNoClassesToShow;
    ImageButton btnPreviousTerm, btnNextTerm;
    ViewAllClassesRecyclerAdapter viewAllClassesRecyclerAdapter;
    int curYear;
    Term curTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_classes);

        recyclerViewAllCourses = findViewById(R.id.recyclerViewAllCourses);
        textViewTerm = findViewById(R.id.textViewTerm);
        btnPreviousTerm = findViewById(R.id.btnPreviousTerm);
        btnNextTerm = findViewById(R.id.btnNextTerm);
        textViewNoClassesToShow = findViewById(R.id.textViewNoClassesToShow);
        {
            Calendar c = Calendar.getInstance();
            curYear = c.get(Calendar.YEAR);
            curTerm = Class.getTerm(c.get(Calendar.MONTH));
        }
        btnPreviousTerm.setOnClickListener(view -> {
            switch (curTerm) {
                case WINTER: curTerm = Term.FALL; curYear--; break;
                case SUMMER: curTerm = Term.WINTER; break;
                case FALL: curTerm = Term.SUMMER; break;
            }
            updateList();
        });
        btnNextTerm.setOnClickListener(view -> {
            switch (curTerm) {
                case WINTER: curTerm = Term.SUMMER; break;
                case SUMMER: curTerm = Term.FALL; break;
                case FALL: curTerm = Term.WINTER; curYear++; break;
            }
            updateList();
        });

        viewAllClassesRecyclerAdapter = new ViewAllClassesRecyclerAdapter(ViewAllClasses.this);
        recyclerViewAllCourses.setLayoutManager(new LinearLayoutManager(ViewAllClasses.this));
        recyclerViewAllCourses.setAdapter(viewAllClassesRecyclerAdapter);

        updateList();
    }

    @SuppressLint("NotifyDataSetChanged") // Whole dataset is changed
    private void updateList () {
        AsyncTask.execute(() -> {
            viewAllClassesRecyclerAdapter.classes = UserDatabase.UDB.userClassDao()
                .getAllFrom(curYear, curTerm);
            runOnUiThread(() -> {
                if (viewAllClassesRecyclerAdapter.classes.isEmpty()) {
                    textViewNoClassesToShow.setVisibility(View.VISIBLE);
                    recyclerViewAllCourses.setVisibility(View.GONE);
                } else {
                    textViewNoClassesToShow.setVisibility(View.GONE);
                    recyclerViewAllCourses.setVisibility(View.VISIBLE);
                }
                textViewTerm.setText(String.format(Locale.getDefault(), "%s %d", curTerm.toString(), curYear));
                viewAllClassesRecyclerAdapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_viewallclasses, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add:
                Intent i = new Intent(this, NewClass.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}