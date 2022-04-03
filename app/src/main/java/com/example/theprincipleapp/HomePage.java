package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.theprincipleapp.helpers.RecyclerAdapter;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> al_imageNames = new ArrayList<>();
    ArrayList<Integer> al_images = new ArrayList<>();
    RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        recyclerView = findViewById(R.id.recyclerView);
        al_imageNames.add("COMP 4110");
        al_imageNames.add("COMP 4200");
        al_imageNames.add("COMP 4250");
        al_imageNames.add("COMP 4540");
        al_imageNames.add("COMP 4990");

        al_images.add(R.drawable.image1);
        al_images.add(R.drawable.image2);
        al_images.add(R.drawable.image3);
        al_images.add(R.drawable.image4);
        al_images.add(R.drawable.image5);

     adapter = new RecyclerAdapter(HomePage.this, al_imageNames, al_images);
    recyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this));
     recyclerView.setAdapter(adapter);

    }
}

