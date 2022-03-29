package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewClass extends AppCompatActivity {

    TextView tv_classCode, tv_fullName, tv_section, tv_description, tv_professor;
    Button btn_edit, btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class);

        tv_classCode = findViewById(R.id.tv_classcodeinfo);
        tv_fullName = findViewById(R.id.tv_fullnameinfo);
        tv_section = findViewById(R.id.tv_sectioninfo);
        tv_description = findViewById(R.id.tv_descriptioninfo);
        tv_professor = findViewById(R.id.tv_professorinfo);

        btn_edit = findViewById(R.id.button_editclass);
        btn_delete = findViewById(R.id.button_deleteclass);

        //TODO: Display Class info in TextViews


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Edit Class
                finish();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Delete Class
                finish();
            }
        });
    }
}