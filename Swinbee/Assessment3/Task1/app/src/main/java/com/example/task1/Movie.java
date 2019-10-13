package com.example.task1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Movie implements Parcelable {
    private String title;
    private String genre;
    private String duration;

    private int image;

    public Movie() {
    }

    public Movie(String title, String genre, String duration, int image) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.image = image;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        genre = in.readString();
        duration = in.readString();
        image = in.readInt();
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static ArrayList<Movie> createMovieList(){
        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
        movieArrayList.add(new Movie("Avengers: Endgame", "Action, Adventure, Sci-Fi", "3 hours", R.drawable.avengers));
        movieArrayList.add(new Movie("47 Meters Down", "Adventure, Drama, Horror", "2 hours 30 mins", R.drawable.meters));
        movieArrayList.add(new Movie("Spiderman: Far From Home", "Action, Adventure, Sci-Fi", "2 hours 15 mins", R.drawable.spiderman));
        movieArrayList.add(new Movie("Lion King", "Animation, Adventure, Drama", "1 hour 45 mins", R.drawable.lion_king));
        movieArrayList.add(new Movie("Fast & Furious: Hobbs & Shaw", "Action, Adventure", "2 hours", R.drawable.hobbs));

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
    }
}
