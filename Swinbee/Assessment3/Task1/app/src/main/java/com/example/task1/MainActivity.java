package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Movie> movieList = new ArrayList<>();
    private  RecyclerView recyclerView;
    private  MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.movie_recycle_view);
        moviesAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);

        Movie movie = new Movie("Avengers: Endgame", "Action, Adventure, Sci-Fi", "3 hours", R.drawable.avengers);
        movieList.add(movie);
        movie = new Movie("$7 Meters Down", "Adventure, Drama, Horror", "2 hours 30 mins", R.drawable.meters);
        movieList.add(movie);

        moviesAdapter.notifyDataSetChanged();
    }
}
