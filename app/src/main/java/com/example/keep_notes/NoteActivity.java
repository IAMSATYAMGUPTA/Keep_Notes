package com.example.keep_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;

public class NoteActivity extends AppCompatActivity {
    Button save;
    EditText titleinput,descriptioninput;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        titleinput = findViewById(R.id.titltinput);
        descriptioninput = findViewById(R.id.Description);
        save = findViewById(R.id.savenotes);

        DB = new DBHelper(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleinput.getText().toString();
                String description = descriptioninput.getText().toString();
                long createdtime = System.currentTimeMillis();
                String formattime = DateFormat.getDateTimeInstance().format(createdtime);
                Boolean checkinsertdata  = DB.insertuserdata(title, description,formattime);
                if(checkinsertdata==true)
                {
                    Toast.makeText(NoteActivity.this, "Notes Saved", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(NoteActivity.this, "Notes are not Saved", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }
}