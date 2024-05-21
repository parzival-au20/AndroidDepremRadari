package com.example.depremradari;

import android.os.Parcel;
import android.os.Parcelable;

public class MyItem implements Parcelable {
    private double mag;
    private String district;
    private String province;
    private String hours;
    private String depth;
    private double latitude;
    private double longitude;

    public MyItem(double mag, String district, String province, String hours, String depth, double latitude, double longitude) {
        this.mag = mag;
        this.district = district;
        this.province = province;
        this.hours = hours;
        this.depth = depth+" KM";
        this.latitude = latitude;
        this.longitude = longitude;
    }
    protected MyItem(Parcel in) {
        mag = in.readDouble();
        district = in.readString();
        province = in.readString();
        hours = in.readString();
        depth = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }
    public static final Creator<MyItem> CREATOR = new Creator<MyItem>() {
        @Override
        public MyItem createFromParcel(Parcel in) {
            return new MyItem(in);
        }

        @Override
        public MyItem[] newArray(int size) {
            return new MyItem[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mag);
        dest.writeString(district);
        dest.writeString(province);
        dest.writeString(hours);
        dest.writeString(depth);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public double getMag() {
        return mag;
    }

    public String getDistrict() {
        return district;
    }

    public String getProvince() {
        return province;
    }
    public String getHours() {
        return hours;
    }
    public String getDepth() {
        return depth;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
