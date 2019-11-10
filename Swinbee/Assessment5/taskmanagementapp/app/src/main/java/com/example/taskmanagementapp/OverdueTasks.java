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

public class OverdueTasks extends AppCompatActivity implements TaskObjectAdapter.OnUpdateDB {

    private DbHandler handler;
    private BottomNavigationView bottomNavigationView;
    private ArrayList<TaskObject> overdueList;
    ArrayList<TaskObject> overdue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overdue_tasks);

        handler = new DbHandler(this);
        addDatabase();

        bottomNavigationView = findViewById(R.id.bottom_nav_overdue);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_completed:
                        Intent intent_completed = new Intent(OverdueTasks.this, CompletedTasks.class);
                        startActivity(intent_completed);
                        break;
                    case R.id.nav_home:
                        Intent intent_home = new Intent(OverdueTasks.this, MainActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.nav_overdue:
                        Toast.makeText(OverdueTasks.this, "You are already at this page.", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    private void addDatabase(){
        overdueList = handler.getOverdueTaskObjects();
        overdue = new ArrayList<>();

        for (TaskObject object: overdueList){
            if (object.getCompletion_status().equals("Pending")){
                overdue.add(object);
            }
        }

        sortDate(overdue);
        RecyclerView recyclerView = findViewById(R.id.overdue_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TaskObjectAdapter adapter = new TaskObjectAdapter(this, overdue);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateDB() {
        overdue.clear();
        addDatabase();
    }

    private void sortDate(ArrayList<TaskObject> list){
        Collections.sort(list, new Comparator<TaskObject>() {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            @Override
            public int compare(TaskObject object1, TaskObject object2) {
                try {
                    return format.parse(object1.getDue_date()).compareTo(format.parse(object2.getDue_date()));
                } catch (ParseException e){
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}
