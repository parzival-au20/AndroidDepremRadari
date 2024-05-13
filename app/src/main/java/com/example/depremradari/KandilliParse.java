package com.example.depremradari;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class KandilliParse {
    @SerializedName("result")
    @Expose
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
    public class Result {
        @SerializedName("_id")
        @Expose
        private String _id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("mag")
        @Expose
        private double mag;
        @SerializedName("depth")
        @Expose
        private double depth;
        /*@SerializedName("geojson")
        @Expose
        private GeoJson geojson;*/

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getMag() {
            return mag;
        }

        public void setMag(double mag) {
            this.mag = mag;
        }

        public double getDepth() {
            return depth;
        }

        public void setDepth(double depth) {
            this.depth = depth;
        }

       /* public GeoJson getGeojson() {
            return geojson;
        }

        public void setGeojson(GeoJson geojson) {
            this.geojson = geojson;
        }*/



        // Getter ve setter metotları
    }

   /* public class GeoJson {
        @SerializedName("coordinates")
        @Expose
        private double[] coordinates;

        // Getter ve setter metotları
        public double[] getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(double[] coordinates) {
            this.coordinates = coordinates;
        }
    }*/



}
