package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewClass extends AppCompatActivity {

    EditText et_classCode, et_fullName, et_section, et_description, et_professor;
    Button button_ok, button_cancel;
    String classCode, fullName, description, professor;
    int section;

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

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classCode = et_classCode.getText().toString();
                fullName = et_fullName.getText().toString();
                description = et_description.getText().toString();
                professor = et_professor.getText().toString();
                section = Integer.parseInt(et_section.getText().toString());

                //TODO: Save Class changes
                finish();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Save Class changes
                finish();
            }
        });



    }
}