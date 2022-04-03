package com.example.theprincipleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewClass extends AppCompatActivity {

    TextView tv_classCode, tv_fullName, tv_section, tv_description, tv_professor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class);

        tv_classCode = findViewById(R.id.tv_classcodeinfo);
        tv_fullName = findViewById(R.id.tv_fullnameinfo);
        tv_section = findViewById(R.id.tv_sectioninfo);
        tv_description = findViewById(R.id.tv_descriptioninfo);
        tv_professor = findViewById(R.id.tv_professorinfo);



        //TODO: Display Class info in TextViews




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add:
                //TODO: add a class
                Toast.makeText(getApplicationContext(),"add",Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_edit:
                //TODO: edit a class
                Toast.makeText(getApplicationContext(),"edit",Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_view:
                //TODO: view meetings
                Toast.makeText(getApplicationContext(),"view",Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_delete:
                //TODO: delete a class
                Toast.makeText(getApplicationContext(),"delete",Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}