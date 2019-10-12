package com.example.task1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private List<Movie> movieList;

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        public TextView title, genre, duration;
        public ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            genre = itemView.findViewById(R.id.movie_genre);
            duration = itemView.findViewById(R.id.movie_duration);
            image = itemView.findViewById(R.id.movie_image);
        }
    }

    public MoviesAdapter (List<Movie> movieList){
        this.movieList = movieList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.image.setImageResource(movie.getImage());
        holder.duration.setText(movie.getDuration());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


}
