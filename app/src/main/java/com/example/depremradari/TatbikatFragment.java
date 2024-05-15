package com.example.depremradari;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public class TatbikatFragment extends Fragment {

    private CardView[] navCards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tatbikat, container, false);

        navCards = new CardView[]{
                rootView.findViewById(R.id.card_1),
                rootView.findViewById(R.id.card_2),
                rootView.findViewById(R.id.card_3),
                rootView.findViewById(R.id.card_4)
        };

        // Toolbar öğesini al
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);

        // Toolbar özelliklerini ayarla
        if (toolbar != null) {
            toolbar.setTitle("Deprem Tatbikatı");
            toolbar.setBackgroundColor(getResources().getColor(R.color.orange));
        }
        for (int i = 0; i < navCards.length; i++) {

            int finalI = i;
            // Lambda ifadesi ile tüm kartlara OnClickListener ata
            navCards[i].setOnClickListener(v -> openNewFragmentOrActivity(finalI + 1));
        }

        return rootView;
    }

    private void openNewFragmentOrActivity(int cardIndex) {
        switch (cardIndex) {
            case 1:
                navigateToFragment(new TatbikatVideo());
                break;
            case 2:
                navigateToFragment(new TatbikatTest());
                break;
            case 3:
                navigateToFragment(new TatbikatCanta());
                break;
            case 4:
                navigateToFragment(new TatbikatInfo());
                break;
        }
    }

    private void navigateToFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }
}