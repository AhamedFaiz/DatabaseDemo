package com.example.databasedemo.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class States implements Parcelable {
    private String state_name;
    private List<String> districts;

    public States(String state_name, List<String> districts) {
        this.state_name = state_name;
        this.districts = districts;
    }

    public States() {
    }

    protected States(Parcel in) {
        state_name = in.readString();
        districts = in.createStringArrayList();
    }

    public static final Creator<States> CREATOR = new Creator<States>() {
        @Override
        public States createFromParcel(Parcel in) {
            return new States(in);
        }

        @Override
        public States[] newArray(int size) {
            return new States[size];
        }
    };

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public List<String> getDistricts() {
        return districts;
    }

    public void setDistricts(List<String> districts) {
        this.districts = districts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(state_name);
        parcel.writeStringList(districts);
    }
}
