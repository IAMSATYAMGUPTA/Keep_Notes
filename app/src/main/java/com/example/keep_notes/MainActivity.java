package com.example.keep_notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    Button addnewnote;
    RecyclerView recyclerView;
    ArrayList<String> title, des,times;
    DBHelper DB;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addnewnote = findViewById(R.id.addnewbtn);
        recyclerView = findViewById(R.id.recycleview);

        addnewnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
                startActivity(intent);
            }
        });


    }
    private void displaydata()
    {
        Cursor cursor = DB.getdata();
        if(cursor.getCount()==0)
        {
            Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                title.add(cursor.getString(0));
                des.add(cursor.getString(1));
                times.add(cursor.getString(2));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        DB = new DBHelper(getApplicationContext());
        title = new ArrayList<>();
        des = new ArrayList<>();
        times = new ArrayList<>();

        Collections.sort(times);

        adapter = new MyAdapter(getApplicationContext(), title,des,times);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setAdapter(adapter);
        displaydata();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}