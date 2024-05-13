package com.example.depremradari;

public class MyItem {
    private double mag;
    private String district;
    private String province;
    private String hours;
    private String depth;

    public MyItem(double mag, String district, String province, String hours, String depth) {
        this.mag = mag;
        this.district = district;
        this.province = province;
        this.hours = hours;
        this.depth = depth+" KM";
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
}
