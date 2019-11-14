package com.example.taskmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class CompletedTasks extends AppCompatActivity implements Completed_TaskObjectAdapter.OnUpdateDB{

    private DbHandler handler;
    private ArrayList<TaskObject> allTaskList;
    private BottomNavigationView bottomNavigationView;
    ArrayList<TaskObject> completedTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Completed Task");
        setContentView(R.layout.activity_completed_tasks);

        handler = new DbHandler(this);
        readDatabase();

        bottomNavigationView = findViewById(R.id.bottom_nav_completed);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_completed:
                        Toast.makeText(CompletedTasks.this, "You are already at this page.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_home:
                        Intent intent_home = new Intent(CompletedTasks.this, MainActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.nav_overdue:
                        Intent intent_overdue = new Intent(CompletedTasks.this, OverdueTasks.class);
                        startActivity(intent_overdue);
                        break;
                }
                return true;
            }
        });
    }

    private void readDatabase(){
        allTaskList = handler.getAllTaskObjects();
        completedTaskList = new ArrayList<>();

        for (TaskObject object: allTaskList){
            if (object.getCompletion_status().equals("Completed")){
                completedTaskList.add(object);
            }
        }

        RecyclerView completedView = findViewById(R.id.completed_recycle_view);
        completedView.setLayoutManager(new LinearLayoutManager(this));
        Completed_TaskObjectAdapter adapter = new Completed_TaskObjectAdapter(this, completedTaskList);
        completedView.setAdapter(adapter);
    }

    @Override
    public void updateDB() {
        completedTaskList.clear();
        readDatabase();
    }
}
