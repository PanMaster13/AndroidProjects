package com.example.task1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.NewsViewHolder> {

    public List<NewsObject> newsList;
    private AdapterCallBack adapterCallBack;

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleText;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.newsImage);
            titleText = itemView.findViewById(R.id.newsTitle);
        }
    }

    public MyRecycleViewAdapter(List<NewsObject> newsList, AdapterCallBack adapterCallBack) {
        this.newsList = newsList;
        this.adapterCallBack = adapterCallBack;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.news_list_item, null);
        NewsViewHolder rcv = new NewsViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleViewAdapter.NewsViewHolder holder, final int position) {
        final NewsObject newsObject = newsList.get(position);
        holder.imageView.setImageResource(newsList.get(position).getImage());
        holder.titleText.setText(newsList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallBack.onMethodCallBack(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.newsList.size();
    }

    public interface AdapterCallBack{
        void onMethodCallBack(int position);
    }
}
