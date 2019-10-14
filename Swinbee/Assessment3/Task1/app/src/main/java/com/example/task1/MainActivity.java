package com.example.task1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.AdapterCallBack {

    // Used in main layout
    private  ArrayList<Movie> movieList;
    private  RecyclerView recyclerView;
    private  MoviesAdapter moviesAdapter;
    private  Movie selected_movie;
    static final int RETURNED_MOVIE_REQUEST = 1;

    // Used in summary layout
    private TextView summary_title, summary_date, summary_time, adult_text, kids_text;
    private ImageView summary_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = Movie.createMovieList();
        setRecyclerView();
    }

    private void setRecyclerView(){
        recyclerView = findViewById(R.id.movie_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        moviesAdapter = new MoviesAdapter(movieList, this);
        recyclerView.setAdapter(moviesAdapter);
    }

    // This function is called whenever a view in the RecycleView is pressed
    @Override
    public void onMethodCallBack(int position) {
        selected_movie = movieList.get(position);
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("selected", selected_movie);
        startActivityForResult(intent, RETURNED_MOVIE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setContentView(R.layout.activity_main_summary);
        Movie returned_movie = data.getParcelableExtra("returned");

        summary_title = findViewById(R.id.summary_title);
        summary_image = findViewById(R.id.summary_image);
        summary_date = findViewById(R.id.summary_date);
        summary_time = findViewById(R.id.summary_time);
        adult_text = findViewById(R.id.summary_adult);
        kids_text = findViewById(R.id.summary_kids);

        summary_title.setText(returned_movie.getTitle());
        summary_image.setImageResource(returned_movie.getImage());
        summary_date.setText(returned_movie.getDate());
        summary_time.setText(returned_movie.getSelected_time());
        adult_text.setText(String.valueOf(returned_movie.getAdult_tickets()));
        kids_text.setText(String.valueOf(returned_movie.getKids_tickets()));
    }
}
