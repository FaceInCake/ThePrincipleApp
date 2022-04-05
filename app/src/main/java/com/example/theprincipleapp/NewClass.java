package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.theprincipleapp.db.Class;
import com.example.theprincipleapp.db.Course;

public class NewClass extends AppCompatActivity {

    EditText et_classCode, et_fullName, et_section, et_description, et_professor;
    Button button_ok, button_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        et_classCode = findViewById(R.id.et_classcode);
        et_fullName = findViewById(R.id.et_fullname);
        et_section = findViewById(R.id.et_section);
        et_description = findViewById(R.id.et_description);
        et_professor = findViewById(R.id.et_professor);
        button_ok = findViewById(R.id.button_ok);
        button_cancel = findViewById(R.id.button_cancel);

        button_ok.setOnClickListener(this::insertClass);
        button_cancel.setOnClickListener(view -> finish());
    }

    void insertClass (View v) {

        // TODO: Save
        finish();
    }
}