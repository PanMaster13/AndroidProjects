package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private TextView movie_title, movie_genre, movie_duration, dateText, adultText, kidsText;
    private ImageView movie_image, arrow_up_1, arrow_down_1, arrow_up_2, arrow_down_2;
    private Movie movie_selected;
    private Spinner time_spinner;
    private Button book_btn;
    public static final String[] MONTHS = {"January", "February", "March", "April", "May", "Jun", "July", "August", "September", "October", "November", "December"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        dateText = findViewById(R.id.date);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        initAndSet();
    }

    // Performs basic initialisation, sets views accordingly, and creates onClick listeners
    private void initAndSet(){
        Intent intent = getIntent();
        movie_selected = intent.getParcelableExtra("selected");

        movie_title = findViewById(R.id.selected_movie_title);
        movie_genre = findViewById(R.id.selected_movie_genre);
        movie_duration = findViewById(R.id.selected_movie_duration);
        movie_image = findViewById(R.id.selected_movie_image);
        time_spinner = findViewById(R.id.timeSpinner);

        arrow_down_1 = findViewById(R.id.arrowDown1);
        arrow_down_2 = findViewById(R.id.arrowDown2);
        arrow_up_1 = findViewById(R.id.arrowUp1);
        arrow_up_2 = findViewById(R.id.arrowUp2);
        adultText = findViewById(R.id.adult);
        kidsText = findViewById(R.id.kids);
        book_btn = findViewById(R.id.bookBtn);

        movie_title.setText(movie_selected.getTitle());
        movie_genre.setText(movie_selected.getGenre());
        movie_duration.setText(movie_selected.getDuration());
        movie_image.setImageResource(movie_selected.getImage());

        // Creating Spinner for time slot selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, movie_selected.getShowtimes(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner.setAdapter(adapter);
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                movie_selected.setSelected_time("Time: " + adapterView.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                movie_selected.setSelected_time("Time: " + adapterView.getItemAtPosition(0));
            }
        });

        // Decrease adult value
        arrow_down_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie_selected.setAdult_tickets(movie_selected.getAdult_tickets() - 1);
                movie_selected.setAdult_tickets(checkPersonValue(movie_selected.getAdult_tickets()));
                adultText.setText(String.valueOf(movie_selected.getAdult_tickets()));
            }
        });

        // Decrease kids value
        arrow_down_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie_selected.setKids_tickets(movie_selected.getKids_tickets() - 1);
                movie_selected.setKids_tickets(checkPersonValue(movie_selected.getKids_tickets()));
                kidsText.setText(String.valueOf(movie_selected.getKids_tickets()));
            }
        });

        // Increase adult value
        arrow_up_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie_selected.setAdult_tickets(movie_selected.getAdult_tickets() + 1);
                adultText.setText(String.valueOf(movie_selected.getAdult_tickets()));
            }
        });

        // Increase kids value
        arrow_up_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie_selected.setKids_tickets(movie_selected.getKids_tickets() + 1);
                kidsText.setText(String.valueOf(movie_selected.getKids_tickets()));
            }
        });

        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((movie_selected.getDate().matches("")) || (movie_selected.getTotalTickets() == 0)){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please ensure that a date is selected and at least there is 1 ticket reservation.", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("returned", movie_selected);
                    setResult(1, returnIntent);
                    finish();
                }
            }
        });
    }

    // Code for Date Picker Dialog
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
        movie_selected.setDate("Date: " + dateValue);
        dateText.setText(dateValue);
    }

    // Function that prevents negative ticket amount
    public int checkPersonValue(int personValue){
        if (personValue < 0){
            personValue = 0;
            return personValue;
        }
        else{
            return personValue;
        }
    }
}
