package com.example.task1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Movie implements Parcelable {
    private String title;
    private String genre;
    private String duration;
    private String date;
    private int showtimes;
    private String selected_time;
    private int adult_tickets;
    private int kids_tickets;
    private int image;

    public Movie() {
    }

    public Movie(String title, String genre, String duration, int image, int showtimes) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.image = image;
        this.date = "";
        this.showtimes = showtimes;
        this.selected_time = "";
        this.adult_tickets = 0;
        this.kids_tickets = 0;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        genre = in.readString();
        duration = in.readString();
        image = in.readInt();
        date = in.readString();
        showtimes = in.readInt();
        selected_time = in.readString();
        adult_tickets = in.readInt();
        kids_tickets = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public int getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getShowtimes() {
        return showtimes;
    }

    public String getSelected_time() {
        return selected_time;
    }

    public void setSelected_time(String selected_time) {
        this.selected_time = selected_time;
    }

    public int getAdult_tickets() {
        return adult_tickets;
    }

    public void setAdult_tickets(int adult_tickets) {
        this.adult_tickets = adult_tickets;
    }

    public int getKids_tickets() {
        return kids_tickets;
    }

    public void setKids_tickets(int kids_tickets) {
        this.kids_tickets = kids_tickets;
    }

    public int getTotalTickets() { return adult_tickets + kids_tickets; }

    public static ArrayList<Movie> createMovieList(){ ;
        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
        movieArrayList.add(new Movie("Avengers: Endgame", "Action, Adventure, Sci-Fi", "3 hours", R.drawable.avengers, R.array.avengers));
        movieArrayList.add(new Movie("47 Meters Down", "Adventure, Drama, Horror", "2 hours 30 mins", R.drawable.meters, R.array.meters));
        movieArrayList.add(new Movie("Spiderman: Far From Home", "Action, Adventure, Sci-Fi", "2 hours 15 mins", R.drawable.spiderman, R.array.spiderman));
        movieArrayList.add(new Movie("Lion King", "Animation, Adventure, Drama", "1 hour 45 mins", R.drawable.lion_king, R.array.lion));
        movieArrayList.add(new Movie("Fast & Furious: Hobbs & Shaw", "Action, Adventure", "2 hours", R.drawable.hobbs, R.array.fastNfurious));

        return movieArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Creator<Movie> getCreator(){
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(title);
        parcel.writeString(genre);
        parcel.writeString(duration);
        parcel.writeInt(image);
        parcel.writeString(date);
        parcel.writeInt(showtimes);
        parcel.writeString(selected_time);
        parcel.writeInt(adult_tickets);
        parcel.writeInt(kids_tickets);
    }
}
