package com.example.depremradari;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CsemParse {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("metadata")
    @Expose
    private Metadata metadata;

    @SerializedName("features")
    @Expose
    private List<Feature> features;

    // Getter ve Setter metodları

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public static class Metadata {
        @SerializedName("count")
        @Expose
        private int count;

        // Getter ve Setter metodları
    }

    public static class Feature {
        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("geometry")
        @Expose
        private Geometry geometry;

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("properties")
        @Expose
        private Properties properties;

        // Getter ve Setter metodları

        public Properties getProperties() {
            return properties;
        }

        public void setProperties(Properties properties) {
            this.properties = properties;
        }
    }

    public static class Geometry {
        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("coordinates")
        @Expose
        private double[] coordinates;

        // Getter ve Setter metodları
    }


    public static class Properties {
        @SerializedName("source_id")
        @Expose
        private String sourceId;
        @SerializedName("source_catalog")
        @Expose
        private String sourceCatalog;
        @SerializedName("lastupdate")
        @Expose
        private String lastUpdate;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("flynn_region")
        @Expose
        private String flynnRegion;
        @SerializedName("lat")
        @Expose
        private double lat;
        @SerializedName("lon")
        @Expose
        private double lon;
        @SerializedName("depth")
        @Expose
        private double depth;
        @SerializedName("evtype")
        @Expose
        private String evType;
        @SerializedName("auth")
        @Expose
        private String auth;
        @SerializedName("mag")
        @Expose
        private double mag;
        @SerializedName("magtype")
        @Expose
        private String magType;
        @SerializedName("unid")
        @Expose
        private String unid;

        // Getters and Setters
        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getSourceCatalog() {
            return sourceCatalog;
        }

        public void setSourceCatalog(String sourceCatalog) {
            this.sourceCatalog = sourceCatalog;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFlynnRegion() {
            return flynnRegion;
        }

        public void setFlynnRegion(String flynnRegion) {
            this.flynnRegion = flynnRegion;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getDepth() {
            return depth;
        }

        public void setDepth(double depth) {
            this.depth = depth;
        }

        public String getEvType() {
            return evType;
        }

        public void setEvType(String evType) {
            this.evType = evType;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public double getMag() {
            return mag;
        }

        public void setMag(double mag) {
            this.mag = mag;
        }

        public String getMagType() {
            return magType;
        }

        public void setMagType(String magType) {
            this.magType = magType;
        }

        public String getUnid() {
            return unid;
        }

        public void setUnid(String unid) {
            this.unid = unid;
        }
    }
}
