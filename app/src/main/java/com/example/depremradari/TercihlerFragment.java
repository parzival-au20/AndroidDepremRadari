package com.example.depremradari;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TercihlerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tercihler, container, false);

        // Toolbar öğesini al
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);

        // Toolbar özelliklerini ayarla
        if (toolbar != null) {
            toolbar.setTitle("Ayarlar");
            toolbar.setBackgroundColor(getResources().getColor(R.color.green));
        }

        return rootView;
    }
}