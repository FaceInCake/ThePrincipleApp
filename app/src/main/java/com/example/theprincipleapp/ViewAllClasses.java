package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import com.example.theprincipleapp.db.Class;
import com.example.theprincipleapp.db.Course;
import com.example.theprincipleapp.db.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewAllClasses extends AppCompatActivity {
    RecyclerView recyclerViewAllCourses;
    TextView textViewTerm;
    Button btnPreviousTerm, btnNextTerm;

    ViewAllClassesRecyclerAdapter viewAllClassesRecyclerAdapter;
    UserDatabase userdb = UserDatabase.UDB;

    List<Pair<Course, Integer>> cardData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_classes);

        recyclerViewAllCourses = findViewById(R.id.recyclerViewAllCourses);
        textViewTerm = findViewById(R.id.textViewTerm);
        btnPreviousTerm = findViewById(R.id.btnPreviousTerm);
        btnNextTerm = findViewById(R.id.btnNextTerm);

//         Getting all the class ids and related course and storing them as a pair
        //TODO: Initialize view as empty, query classes, populate view when query complete
//        for(Class _class:
//            cardData.add(Pair.create(userdb.courseDao().get(_class.oid), _class.cid));
//        }

        viewAllClassesRecyclerAdapter = new ViewAllClassesRecyclerAdapter(this, cardData);
        recyclerViewAllCourses.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAllCourses.setAdapter(viewAllClassesRecyclerAdapter);
    }
}