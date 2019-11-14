package com.example.taskmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editText_title, editText_details;
    private TextView textView_date;
    private CheckBox priority_box;
    private Calendar calendar;
    private DbHandler handler;
    private String priority = "second";
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Task");
        setContentView(R.layout.activity_add_task);

        textView_date = findViewById(R.id.dueDate_box);
        editText_title = findViewById(R.id.title_editBox);
        editText_details = findViewById(R.id.details_editBox);
        priority_box = findViewById(R.id.priority_box);
        calendar = Calendar.getInstance();

        handler = new DbHandler(this);

        textView_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        textView_date.setText(format.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker();
                datePickerDialog.show();
            }
        });

        priority_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                priority = isChecked ? "first" : "second"; // Shorthand for if-then-else statement
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.submit_form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (editText_title.getText().toString().isEmpty()){
            editText_title.setError("Required");
        }
        if (textView_date.getText().toString().isEmpty()){
            textView_date.setError("Required");
        }
        if ((editText_title.getText().length() != 0) && (textView_date.getText().length() != 0)){
            TaskObject object = new TaskObject(editText_title.getText().toString(), textView_date.getText().toString(), editText_details.getText().toString(), priority, "Pending");
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            try {
                date = format.parse(textView_date.getText().toString());
            } catch (ParseException e){
                e.printStackTrace();
            }
            handler.addTaskObject(object);
            String date[] = textView_date.getText().toString().split("-");
            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day-1);
            calendar.set(Calendar.HOUR_OF_DAY, 3);
            calendar.set(Calendar.MINUTE, 13);
            calendar.set(Calendar.SECOND, 0);
            Intent alarm_intent = new Intent("android.intent.action.DISPLAY_NOTIFICATION");
            PendingIntent broadcastIntent = PendingIntent.getBroadcast(this, 100, alarm_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcastIntent);

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
