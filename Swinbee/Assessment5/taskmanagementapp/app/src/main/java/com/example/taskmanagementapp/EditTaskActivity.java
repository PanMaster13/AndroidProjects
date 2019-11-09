package com.example.taskmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity {

    private EditText editText_title_edit, editText_details_edit;
    private TextView textView_date_edit;
    private CheckBox priority_box_edit;
    private Calendar calendar;
    private DbHandler handler;
    private String priority;
    private int objectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        handler = new DbHandler(this);
        editText_title_edit = findViewById(R.id.title_editBox_edit);
        textView_date_edit = findViewById(R.id.dueDate_box_edit);
        editText_details_edit = findViewById(R.id.details_editBox_edit);
        priority_box_edit = findViewById(R.id.priority_box_edit);
        calendar = Calendar.getInstance();

        Intent intent = getIntent();
        TaskObject object = intent.getParcelableExtra("OBJECT");
        objectID = object.getId();
        editText_title_edit.setText(object.getTitle());
        textView_date_edit.setText(object.getDue_date());
        editText_details_edit.setText(object.getDetails());
        priority = object.getPriority();

        textView_date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        textView_date_edit.setText(format.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker();
                dialog.show();
            }
        });

        priority_box_edit.setChecked(priority.equals("first"));
        priority_box_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                priority = isChecked ? "first" : "second";
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
        if (editText_title_edit.getText().toString().isEmpty()){
            editText_title_edit.setError("Required");
        }
        if (textView_date_edit.getText().toString().isEmpty()){
            textView_date_edit.setError("Required");
        }

        if ((editText_title_edit.getText().length() != 0) && (textView_date_edit.getText().length() != 0)){
            TaskObject object = new TaskObject(objectID, editText_title_edit.getText().toString(), textView_date_edit.getText().toString(), editText_details_edit.getText().toString(), priority, "Pending");
            handler.updateTaskObject(object);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}