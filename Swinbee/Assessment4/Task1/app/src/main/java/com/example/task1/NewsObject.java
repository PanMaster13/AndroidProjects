package com.example.task1;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsObject implements Parcelable {

    private int image;
    private String category, title, url;

    public NewsObject(int image, String category, String title, String url) {
        this.image = image;
        this.category = category;
        this.title = title;
        this.url = url;
    }

    protected NewsObject(Parcel in) {
        image = in.readInt();
        category = in.readString();
        title = in.readString();
        url = in.readString();
    }

    public static final Creator<NewsObject> CREATOR = new Creator<NewsObject>() {
        @Override
        public NewsObject createFromParcel(Parcel in) {
            return new NewsObject(in);
        }

        @Override
        public NewsObject[] newArray(int size) {
            return new NewsObject[size];
        }
    };

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(image);
        parcel.writeString(category);
        parcel.writeString(title);
        parcel.writeString(url);
    }
}
