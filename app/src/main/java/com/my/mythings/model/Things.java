package com.my.mythings.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author 文琳
 * @time 2020/6/16 17:39
 * @desc 物品们
 */
public class Things implements Parcelable {
    private List<Thing> list;

    public Things(List<Thing> list) {
        this.list = list;
    }

    public List<Thing> getList() {
        return list;
    }

    public void setList(List<Thing> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    protected Things(Parcel in) {
        this.list = in.createTypedArrayList(Thing.CREATOR);
    }

    public static final Parcelable.Creator<Things> CREATOR = new Parcelable.Creator<Things>() {
        @Override
        public Things createFromParcel(Parcel source) {
            return new Things(source);
        }

        @Override
        public Things[] newArray(int size) {
            return new Things[size];
        }
    };
}
