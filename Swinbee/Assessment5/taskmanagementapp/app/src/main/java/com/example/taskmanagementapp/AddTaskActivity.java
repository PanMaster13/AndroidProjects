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
                Date deadline = format.parse(textView_date.getText().toString());
                Calendar calculator = Calendar.getInstance();
                calculator.setTime(deadline);
                calculator.add(Calendar.DATE, -1);
                date = calculator.getTime();
            } catch (ParseException e){
                e.printStackTrace();
            }
            handler.addTaskObject(object);
            setAlarm(date, editText_title.getText().toString());
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    public void setAlarm(Date due_date, String task_title){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, due_date.getDate());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent("android.intent.action.DISPLAY_NOTIFICATION");
        intent.putExtra("title", task_title);
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcastIntent);
    }
}
