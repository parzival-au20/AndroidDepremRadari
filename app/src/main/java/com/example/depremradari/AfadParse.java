package com.example.depremradari;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AfadParse {

    @SerializedName("data")
    @Expose
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("eventID")
        @Expose
        private String eventID;

        @SerializedName("latitude")
        @Expose
        private String latitude;

        @SerializedName("longitude")
        @Expose
        private String longitude;

        @SerializedName("depth")
        @Expose
        private String depth;

        @SerializedName("magnitude")
        @Expose
        private String magnitude;

        @SerializedName("province")
        @Expose
        private String province;

        @SerializedName("district")
        @Expose
        private String district;

        @SerializedName("date")
        @Expose
        private String date;



        // Getter ve setter metotları

        public String getEventID() {
            return eventID;
        }

        public void setEventID(String eventID) {
            this.eventID = eventID;
        }


        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getDepth() {
            return depth;
        }

        public void setDepth(String depth) {
            this.depth = depth;
        }

        public String getMagnitude() {
            return magnitude;
        }

        public void setMagnitude(String magnitude) {
            this.magnitude = magnitude;
        }


        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }


        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}

