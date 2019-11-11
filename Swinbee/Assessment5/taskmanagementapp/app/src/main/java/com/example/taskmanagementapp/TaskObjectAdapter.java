package com.example.taskmanagementapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TaskObjectAdapter extends RecyclerView.Adapter<TaskObjectAdapter.ViewHolder> {

    private static ArrayList<TaskObject> objectList;
    private Context context;

    public TaskObjectAdapter(Context context, ArrayList<TaskObject> objectList) {
        this.context = context;
        TaskObjectAdapter.objectList = objectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TaskObject object = objectList.get(position);
        String object_date_list[] = object.getDue_date().split("-");

        holder.title_display.setText(object.getTitle());
        holder.calender_icon.setImageResource(R.drawable.time);
        holder.status_switch.setText(object.getCompletion_status());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        try {
            Date objectDate = simpleDateFormat.parse(object.getDue_date());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(objectDate);
            holder.date_day.setText(object_date_list[0]);
            holder.date_month.setText(object_date_list[1]);
            long date_difference = calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
            long days_difference = date_difference / (24 * 60 * 60 * 1000);
            long hours_difference = date_difference / (60 * 60 * 1000);
            if (hours_difference > 24){
                while (hours_difference > 24){
                    hours_difference = hours_difference - 24;
                }
            }
            String remaining_time_text;
            if (days_difference < 0 || hours_difference < 0){
                holder.title_icon.setImageResource(R.drawable.clipboard_red1);
                remaining_time_text = "0 hr";
                holder.remaining_time.setText(remaining_time_text);
                holder.date_day.setTextColor(Color.parseColor("#F64242"));
                holder.date_month.setTextColor(Color.parseColor("#F64242"));
                holder.title_display.setTextColor(Color.parseColor("#F64242"));
            } else if (days_difference <= 1){
                holder.title_icon.setImageResource(R.drawable.clipboard_orange1);
                remaining_time_text = days_difference + " days " + hours_difference + " hours";
                holder.remaining_time.setText(remaining_time_text);
                holder.date_day.setTextColor(Color.parseColor("#000000"));
                holder.date_month.setTextColor(Color.parseColor("#000000"));
                holder.title_display.setTextColor(Color.parseColor("#000000"));
            } else {
                holder.title_icon.setImageResource(R.drawable.clipboard_blue1);
                remaining_time_text = days_difference + " days " + hours_difference + " hours";
                holder.remaining_time.setText(remaining_time_text);
                holder.date_day.setTextColor(Color.parseColor("#000000"));
                holder.date_month.setTextColor(Color.parseColor("#000000"));
                holder.title_display.setTextColor(Color.parseColor("#000000"));
            }
        } catch (ParseException e){
            e.printStackTrace();
        }

        if (object.getPriority().equals("second")){
            holder.cardView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else if (object.getPriority().equals("first")){
            holder.cardView.setBackgroundColor(Color.parseColor("#FCFFA3"));
        }

        // Sends object to edit page
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("OBJECT", object);
                context.startActivity(intent);
            }
        });

        // Deletes object
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final DbHandler handler = new DbHandler(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Alert").setMessage("Confirm Deletion of this Task?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handler.deleteTaskObject(objectList.get(holder.getAdapterPosition()));
                        objectList.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        ((OnUpdateDB)holder.itemView.getContext()).updateDB();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        holder.status_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DbHandler handler = new DbHandler(context);
                if (b) {
                    handler.updateTaskObject(new TaskObject(object.getId(), object.getTitle(), object.getDue_date(), object.getDetails(), object.getPriority(), "Completed"));
                }
                ((OnUpdateDB)holder.itemView.getContext()).updateDB();
            }
        });
    }

    @Override
    public int getItemCount() { return objectList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView date_day, date_month, title_display, remaining_time;
        private ImageView title_icon, calender_icon;
        private Switch status_switch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            date_day = itemView.findViewById(R.id.date_day);
            date_month = itemView.findViewById(R.id.date_month);
            title_display = itemView.findViewById(R.id.title_display);
            remaining_time = itemView.findViewById(R.id.remaining_time);
            title_icon = itemView.findViewById(R.id.title_icon);
            calender_icon = itemView.findViewById(R.id.calender_icon);
            status_switch = itemView.findViewById(R.id.switch_status);
        }
    }

    public interface OnUpdateDB{
        void updateDB();
    }
}
