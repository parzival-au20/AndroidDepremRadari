package com.example.depremradari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class KapalıAlan extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kapali_alan, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbarInfo);

        if (getActivity() instanceof AppCompatActivity) {

            toolbar.setNavigationOnClickListener(view -> {
                // Fragment'tan önceki ekrana dön
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack(); // BackStack'i yönet
                }
            });
        }
        return rootView;

    }
}