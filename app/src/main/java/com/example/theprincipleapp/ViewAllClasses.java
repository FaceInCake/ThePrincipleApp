package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.theprincipleapp.db.Course;
import com.example.theprincipleapp.db.UserClass;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.db.Class;

import java.util.ArrayList;
import java.util.List;


public class ViewAllClasses extends AppCompatActivity {
    RecyclerView recyclerViewAllCourses;
    TextView textViewTerm, textViewNoClassesToShow;
    Button btnPreviousTerm, btnNextTerm;

    ViewAllClassesRecyclerAdapter viewAllClassesRecyclerAdapter;
    UserDatabase userDB = UserDatabase.UDB;

    List<Pair<Course, Class>> cardData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_classes);

        recyclerViewAllCourses = findViewById(R.id.recyclerViewAllCourses);
        textViewTerm = findViewById(R.id.textViewTerm);
        btnPreviousTerm = findViewById(R.id.btnPreviousTerm);
        btnNextTerm = findViewById(R.id.btnNextTerm);
        textViewNoClassesToShow = findViewById(R.id.textViewNoClassesToShow);

        // TODO: Add a way to get which term and term year


        viewAllClassesRecyclerAdapter = new ViewAllClassesRecyclerAdapter(ViewAllClasses.this, cardData);
        recyclerViewAllCourses.setLayoutManager(new LinearLayoutManager(ViewAllClasses.this));
        recyclerViewAllCourses.setAdapter(viewAllClassesRecyclerAdapter);

        updateViewClasses();
    }

    private void updateViewClasses() {
        List<UserClass> userClasses = userDB.userClassDao().getAll();
        if (userClasses.size() > 0) {
            cardData.clear();
            // Getting all the user's courses and classes
            for (UserClass userClass : userClasses)
                cardData.add(Pair.create(userClass.course, userClass.cls));

            viewAllClassesRecyclerAdapter.notifyDataSetChanged();

            // hiding the "no classes to show" message, and showing the class card(s)
            textViewNoClassesToShow.setVisibility(View.GONE);
            recyclerViewAllCourses.setVisibility(View.VISIBLE);
        }
    }
}