package com.example.depremradari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class HaritaFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    FrameLayout map;
    private List<LatLng> earthquakeLocations;
    private List<String> earthquakeTitle;
    ArrayList<MyItem> earthquakeData;

    public static HaritaFragment newInstance(ArrayList<MyItem> DepremData) {
        HaritaFragment fragment = new HaritaFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("earthquake_data", DepremData);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_harita, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        earthquakeTitle = new ArrayList<>();
        if (getArguments() != null) {
            earthquakeData = getArguments().getParcelableArrayList("earthquake_data");
            earthquakeLocations = getLatLngList(earthquakeData);
        } else {
            earthquakeLocations = new ArrayList<>(); // Boş liste ile başlat
        }
        return rootView;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        View customInfoWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(customInfoWindow));
        addMarkers();
    }
    private void addMarkers() {
        int counter = 0;
        for (LatLng location: earthquakeLocations) {
            googleMap.addMarker(new MarkerOptions().position(location).title(earthquakeTitle.get(counter)));
            counter+=1;
        }
        if (!earthquakeLocations.isEmpty()) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(earthquakeLocations.get(0), 5));
        }
    }

    private List<LatLng> getLatLngList(List<MyItem> earthquakeData) {
        List<LatLng> locations = new ArrayList<>();
        for (MyItem item : earthquakeData) {
            locations.add(new LatLng(item.getLatitude(), item.getLongitude()));
            earthquakeTitle.add(new String(item.getProvince()+"\n"+item.getDistrict()+"\n("+item.getMag()+")"));
        }
        return locations;
    }
}