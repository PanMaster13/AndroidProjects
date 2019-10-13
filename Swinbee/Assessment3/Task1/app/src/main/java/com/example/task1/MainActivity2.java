package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private TextView movie_title, movie_genre, movie_duration;
    private ImageView movie_image;
    private EditText date;
    private Movie movie_selected;
    private Spinner time_spinner;
    public static final String[] MONTHS = {"January", "Febuary", "March", "April", "May", "Jun", "July", "August", "September", "October", "November", "December"};

    private String timeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        date = findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        initAndSet();
    }

    // Performs basic initialisation and sets views accordingly
    private void initAndSet(){
        Intent intent = getIntent();
        movie_selected = intent.getParcelableExtra("selected");

        movie_title = findViewById(R.id.selected_movie_title);
        movie_genre = findViewById(R.id.selected_movie_genre);
        movie_duration = findViewById(R.id.selected_movie_duration);
        movie_image = findViewById(R.id.selected_movie_image);
        time_spinner = findViewById(R.id.timeSpinner);

        movie_title.setText(movie_selected.getTitle());
        movie_genre.setText(movie_selected.getGenre());
        movie_duration.setText(movie_selected.getDuration());
        movie_image.setImageResource(movie_selected.getImage());


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, movie_selected.getShowtimes(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner.setAdapter(adapter);

        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                timeValue = (String)adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                timeValue = (String)adapterView.getItemAtPosition(0);
            }
        });
    }

    private void showDatePickerDialog(){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                calendar.getInstance().get(Calendar.YEAR),
                calendar.getInstance().get(Calendar.MONTH),
                calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

        String dateValue = dayOfMonth + " " + MONTHS[month] + " " + year;
        date.setText(dateValue);
    }
}
