package com.lolipoplls.lecture4;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
    private String food;
    private int qty;
    private double price;

    public Order(String food, int qty, double price) {
        this.food = food;
        this.qty = qty;
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return food  + " | Qty:" + qty +
                " | price: RM" + price;
    }

    protected Order(Parcel in) {
        food = in.readString();
        qty = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.food);
        dest.writeInt(this.qty);
        dest.writeDouble(this.price);
    }
}
