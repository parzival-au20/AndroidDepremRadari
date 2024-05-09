package com.example.depremradari;

public class MyItem {
    private double mag;
    private String district;
    private String province;
    private String hours;
    private double depth;

    public MyItem(double mag, String district, String province, String hours, double depth) {
        this.mag = mag;
        this.district = district;
        this.province = province;
        this.hours = hours;
        this.depth = depth;
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
    public double getDepth() {
        return depth;
    }
}
