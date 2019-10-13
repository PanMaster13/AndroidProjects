package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.AdapterCallBack {
    private  ArrayList<Movie> movieList;
    private  RecyclerView recyclerView;
    private  MoviesAdapter moviesAdapter;
    private  Movie selected_movie;

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
        startActivity(intent);
    }
}
