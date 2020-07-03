package com.my.mythings.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 文琳
 * @time 2020/6/16 17:26
 * @desc 物品
 */
public class Thing implements Parcelable {
    private int position;
    private String name;
    private String price;

    public Thing(int position, String name, String price) {
        this.position = position;
        this.name = name;
        this.price = price;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.position);
        dest.writeString(this.name);
        dest.writeString(this.price);
    }

    protected Thing(Parcel in) {
        this.position = in.readInt();
        this.name = in.readString();
        this.price = in.readString();
    }

    public static final Creator<Thing> CREATOR = new Creator<Thing>() {
        @Override
        public Thing createFromParcel(Parcel source) {
            return new Thing(source);
        }

        @Override
        public Thing[] newArray(int size) {
            return new Thing[size];
        }
    };
}
