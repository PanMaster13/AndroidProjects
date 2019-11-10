package com.example.taskmanagementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Completed_TaskObjectAdapter extends RecyclerView.Adapter<Completed_TaskObjectAdapter.ViewHolder> {
    private static ArrayList<TaskObject> completed_list;
    private Context context;

    public Completed_TaskObjectAdapter(Context context, ArrayList<TaskObject> list) {
        this.context = context;
        Completed_TaskObjectAdapter.completed_list = list;
    }

    @NonNull
    @Override
    public Completed_TaskObjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_task_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TaskObject object = completed_list.get(position);

        holder.textView_title.setText(object.getTitle());
        holder.textView_dueDate.setText(object.getDue_date());
        holder.switch_status.setChecked(true);
        holder.switch_status.setEnabled(false);
    }

    @Override
    public int getItemCount() { return completed_list.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView imageView;
        private TextView textView_title, textView_dueDate;
        private Switch switch_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.completed_cardView);
            imageView = itemView.findViewById(R.id.completed_icon);
            textView_title = itemView.findViewById(R.id.completed_textView_title);
            textView_dueDate = itemView.findViewById(R.id.completed_textView_dueDate);
            switch_status = itemView.findViewById(R.id.completed_switch_status);
        }
    }

    public interface OnUpdateDB{
        void updateDB();
    }
}
