package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    TextView movie_title, movie_genre, movie_duration;
    ImageView movie_image;
    EditText date;
    Movie movie_selected;

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

        movie_title.setText(movie_selected.getTitle());
        movie_genre.setText(movie_selected.getGenre());
        movie_duration.setText(movie_selected.getDuration());
        movie_image.setImageResource(movie_selected.getImage());
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
        calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

        String dateValue = dayOfMonth + "/" + month + "/" + year;
        date.setText(dateValue);
    }
}
