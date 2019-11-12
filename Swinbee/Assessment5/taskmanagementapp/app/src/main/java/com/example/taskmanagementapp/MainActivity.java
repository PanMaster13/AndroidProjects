package com.example.taskmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.util.Collections;
import java.util.Comparator;
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

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void addDatabase(){
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
        sortTaskByDates(pending);

        pending.addAll(overdue);

        RecyclerView recyclerView = findViewById(R.id.task_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TaskObjectAdapter adapter = new TaskObjectAdapter(this, pending);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateDB() {
        pending.clear();
        addDatabase();
    }

    private void sortTaskByDates(ArrayList<TaskObject> taskList){
        final SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        for(TaskObject object: taskList){
            Date date = null;
            try {
                date = format.parse(object.getDue_date());
            } catch (ParseException e){
                e.printStackTrace();
            }
            if (new Date().before(date)){
                Collections.sort(taskList, new Comparator<TaskObject>() {
                    @Override
                    public int compare(TaskObject object1, TaskObject object2) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        try {
                            return dateFormat.parse(object1.getDue_date()).compareTo(dateFormat.parse(object2.getDue_date()));
                        } catch (ParseException e){
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
            }
        }
    }
}
