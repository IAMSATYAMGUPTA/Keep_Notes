package com.example.keep_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;

public class updateactivity extends AppCompatActivity {
    Button save;
    EditText titleinput,descriptioninput;
    DBHelper DB;
    Bundle bundle;
    String uptitle,updes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateactivity);
        titleinput = findViewById(R.id.updatetitltinput);
        descriptioninput = findViewById(R.id.updateDescription);
        save = findViewById(R.id.updatesavenotes);

        DB = new DBHelper(this);

        Bundle bundle;
        bundle = new Bundle();
        if(bundle != null) {
            bundle = getIntent().getExtras();
            uptitle = bundle.getString("utitle");
            updes = bundle.getString("udescription");
            titleinput.setText(uptitle);
            descriptioninput.setText(updes);
            titleinput.setEnabled(false);

        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String updatetitle = titleinput.getText().toString();
                String updatedescription = descriptioninput.getText().toString();
                long createdtime = System.currentTimeMillis();
                String formattime = DateFormat.getDateTimeInstance().format(createdtime);
                Boolean checkupdatedata = DB.updateuserdetail(uptitle, updatedescription,formattime);
                if(checkupdatedata==true) {
                    Toast.makeText(getApplicationContext(), "Entry Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "New Entry Not Updated", Toast.LENGTH_SHORT).show();}
            }
        });



    }
}