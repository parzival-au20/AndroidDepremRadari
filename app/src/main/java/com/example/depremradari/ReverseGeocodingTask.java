package com.example.depremradari;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReverseGeocodingTask extends AsyncTask<Double, Void, String[]> {
    private final OnGeocodingResultListener listener;
    private DepremlerFragment depremlerFragment;
    private Context context;
    public ReverseGeocodingTask(OnGeocodingResultListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected String[] doInBackground(Double... params) {
        double latitude = params[0];
        double longitude = params[1];
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String fullAddress = address.getAddressLine(0);

                String regex = "(.*), (\\d{5}) ([^/]+)/([^,]+)";
                String specialCaseRegex = "(.*), (\\d{5}) ([^/]+)/([^/]+)/([^,]+)";
                String province = "";
                String district = "";

                Pattern pattern = Pattern.compile(regex);
                Pattern specialCasePattern = Pattern.compile(specialCaseRegex);

                Matcher matcher = pattern.matcher(fullAddress);
                Matcher specialCaseMatcher = specialCasePattern.matcher(fullAddress);

                if (specialCaseMatcher.find()) {
                    String subDistrict = specialCaseMatcher.group(3);
                    district = specialCaseMatcher.group(4);
                    province = specialCaseMatcher.group(5);
                    district = subDistrict + "/" + district;
                } else if (matcher.find()) {
                    province = matcher.group(4);
                    district = matcher.group(3);
                }

                return new String[]{province, district};
            } else {
                return new String[]{"No address found", ""};
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{"Geocoder service not available", ""};
        }
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (listener != null) {
            listener.onGeocodingResult(result);
        }
    }

    interface OnGeocodingResultListener {
        void onGeocodingResult(String[] result);
    }
}
