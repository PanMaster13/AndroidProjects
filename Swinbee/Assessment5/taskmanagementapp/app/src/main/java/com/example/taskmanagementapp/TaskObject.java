package com.example.taskmanagementapp;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskObject implements Parcelable {
    private int id;
    private String title, due_date, details, priority, completion_status;

    public TaskObject(int id, String title, String due_date, String details, String priority, String completion_status) {
        this.id = id;
        this.title = title;
        this.due_date = due_date;
        this.details = details;
        this.priority = priority;
        this.completion_status = completion_status;
    }

    public TaskObject(String title, String due_date, String details, String priority, String completion_status) {
        this.title = title;
        this.due_date = due_date;
        this.details = details;
        this.priority = priority;
        this.completion_status = completion_status;
    }

    protected TaskObject(Parcel in) {
        id = in.readInt();
        title = in.readString();
        due_date = in.readString();
        details = in.readString();
        priority = in.readString();
        completion_status = in.readString();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getDetails() {
        return details;
    }

    public String getPriority() {
        return priority;
    }

    public String getCompletion_status() {
        return completion_status;
    }

    public static final Creator<TaskObject> CREATOR = new Creator<TaskObject>() {
        @Override
        public TaskObject createFromParcel(Parcel in) {
            return new TaskObject(in);
        }

        @Override
        public TaskObject[] newArray(int size) {
            return new TaskObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(due_date);
        parcel.writeString(details);
        parcel.writeString(priority);
        parcel.writeString(completion_status);
    }
}
