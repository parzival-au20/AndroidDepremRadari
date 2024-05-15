package com.example.depremradari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class DepremSonrasi extends Fragment {

    private CardView[] navCards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_deprem_sonrasi, container, false);

        navCards = new CardView[]{
                rootView.findViewById(R.id.card_1),
                rootView.findViewById(R.id.card_2),
                rootView.findViewById(R.id.card_3),
        };

        // Toolbar öğesini al
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);

            toolbar.setNavigationOnClickListener(view -> {
                // Fragment'tan önceki ekrana dön
                activity.onBackPressed();
            });
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
                navigateToFragment(new KapalıAlan());
                break;
            case 2:
                navigateToFragment(new Acik_Alan());
                break;
            case 3:
                navigateToFragment(new Enkazda());
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