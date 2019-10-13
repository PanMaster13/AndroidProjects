package com.example.task1;

import android.content.Context;
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
    private AdapterCallBack adapterCallBack;

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

    public MoviesAdapter (List<Movie> movieList, Context context){
        this.movieList = movieList;
        try {
            this.adapterCallBack = ((AdapterCallBack) context);
        } catch (ClassCastException e){
            throw new ClassCastException("Activity must implement AdapterCallBack.");
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.image.setImageResource(movie.getImage());
        holder.duration.setText(movie.getDuration());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallBack.onMethodCallBack(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface  AdapterCallBack{
        void onMethodCallBack(int position);
    }
}
