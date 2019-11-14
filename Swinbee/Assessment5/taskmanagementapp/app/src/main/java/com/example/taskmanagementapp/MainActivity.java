package com.example.taskmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TaskObjectAdapter.OnUpdateDB {

    private DbHandler handler;
    private BottomNavigationView bottomNavigationView;
    private ArrayList<TaskObject> pendingList;
    private ArrayList<TaskObject> overdueList;
    ArrayList<TaskObject> pending;
    ArrayList<TaskObject> overdue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new DbHandler(this);
        addDatabase();

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_completed:
                        Intent intent_completed = new Intent(MainActivity.this, CompletedTasks.class);
                        startActivity(intent_completed);
                        break;
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "You are already at this page.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_overdue:
                        Intent intent_overdue = new Intent(MainActivity.this, OverdueTasks.class);
                        startActivity(intent_overdue);
                        break;
                }
                return true;
            }
        });
    }

    private void addDatabase(){
        int dueInOneDayCounter = 0;
        pendingList = handler.getAllPending();
        overdueList = handler.getOverdueTaskObjects();

        pending = new ArrayList<>();
        overdue = new ArrayList<>();

        for(TaskObject object: pendingList){
            if (object.getCompletion_status().equals("Pending")){
                pending.add(object);
            }
        }

        for(TaskObject object: overdueList){
            if (object.getCompletion_status().equals("Pending")){
                overdue.add(object);
            }
        }

        pending.addAll(overdue);

        for (int x = 0; x < pending.size(); x++){
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            try{
                Date date = format.parse(pending.get(x).getDue_date());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                long day_difference = (calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / (24 * 60 * 60 * 1000);
                long hours_difference = (calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / (60 * 60 * 1000);
                if ((day_difference < 1) && (hours_difference > 0)){
                    dueInOneDayCounter++;
                }
            } catch (ParseException e){
                e.printStackTrace();
            }
        }

        RecyclerView recyclerView = findViewById(R.id.task_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TaskObjectAdapter adapter = new TaskObjectAdapter(this, pending);
        recyclerView.setAdapter(adapter);

        if (dueInOneDayCounter > 0){
        }
    }

    @Override
    public void updateDB() {
        pending.clear();
        overdue.clear();
        addDatabase();
    }
}
