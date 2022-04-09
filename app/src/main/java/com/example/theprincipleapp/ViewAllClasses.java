package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.db.Class;
import java.util.Calendar;


public class ViewAllClasses extends AppCompatActivity {
    RecyclerView recyclerViewAllCourses;
    TextView textViewTerm, textViewNoClassesToShow;
    Button btnPreviousTerm, btnNextTerm;
    ViewAllClassesRecyclerAdapter viewAllClassesRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_classes);

        recyclerViewAllCourses = findViewById(R.id.recyclerViewAllCourses);
        textViewTerm = findViewById(R.id.textViewTerm);
        btnPreviousTerm = findViewById(R.id.btnPreviousTerm);
        btnNextTerm = findViewById(R.id.btnNextTerm);
        textViewNoClassesToShow = findViewById(R.id.textViewNoClassesToShow);

        viewAllClassesRecyclerAdapter = new ViewAllClassesRecyclerAdapter(ViewAllClasses.this);
        recyclerViewAllCourses.setLayoutManager(new LinearLayoutManager(ViewAllClasses.this));
        recyclerViewAllCourses.setAdapter(viewAllClassesRecyclerAdapter);

        AsyncTask.execute(() -> {
            Calendar c = Calendar.getInstance();
            viewAllClassesRecyclerAdapter.classes = UserDatabase.UDB.userClassDao().getAllFrom(
                    c.get(Calendar.YEAR), Class.getTerm(c.get(Calendar.MONTH))
            );
            if (viewAllClassesRecyclerAdapter.classes.isEmpty()) {
                textViewNoClassesToShow.setVisibility(View.VISIBLE);
                recyclerViewAllCourses.setVisibility(View.GONE);
            }
        });

    }
}