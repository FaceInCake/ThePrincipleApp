package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.theprincipleapp.db.UserClass;
import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.helpers.Util;


public class EditClass extends AppCompatActivity {

    EditText et_classCode, et_fullName, et_description, et_professor;
    Button button_ok, button_cancel;
    TextView tv_header;
    String classCode, fullName, description, professor;
    int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        tv_header = findViewById(R.id.tv_newclass);
        et_classCode = findViewById(R.id.et_classcode);
        et_fullName = findViewById(R.id.et_fullname);
        et_description = findViewById(R.id.et_description);
        et_professor = findViewById(R.id.et_professor);
        button_ok = findViewById(R.id.button_ok);
        button_cancel = findViewById(R.id.button_cancel);

        tv_header.setText("Edit Class");
        button_ok.setText("Save Changes");

        AsyncTask.execute(() -> {
            cid = getIntent().getIntExtra("cid", -1);
            if (cid < 0) Util.alertError(this, R.string.err_invalidClass);
            UserClass c = UserDatabase.UDB.userClassDao().get(cid);
            if (c == null) Util.alertError(this, R.string.err_invalidClass);

            et_classCode.setText(c.course.code);
            et_fullName.setText(c.course.full_name);
            et_description.setText(c.course.description);
            et_professor.setText(c.cls.professor);
        });

        button_ok.setOnClickListener(view -> {

            classCode = et_classCode.getText().toString();
            fullName = et_fullName.getText().toString();
            description = et_description.getText().toString();
            professor = et_professor.getText().toString();

            AsyncTask.execute(() -> {
                cid = getIntent().getIntExtra("cid", -1);
                if (cid < 0) Util.alertError(this, R.string.err_invalidClass);
                UserClass c = UserDatabase.UDB.userClassDao().get(cid);
                if (c == null) Util.alertError(this, R.string.err_invalidClass);

                c.cls.professor = professor;
                c.course.code = classCode;
                c.course.full_name = fullName;
                c.course.description = description;

                UserDatabase.UDB.userClassDao().update(c);

                runOnUiThread(() ->
                    Toast.makeText(getApplicationContext(),"Course successfully updated", Toast.LENGTH_LONG).show()
                );
            });
            finish();
        });
    }
}