package com.example.depremradari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TatbikatInfo extends Fragment {

    private CardView[] navCards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        navCards = new CardView[]{
                rootView.findViewById(R.id.card_1),
                rootView.findViewById(R.id.card_2),
                rootView.findViewById(R.id.card_3),
        };


        // Toolbar öğesini al
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);

        if (getActivity() instanceof AppCompatActivity) {

            toolbar.setNavigationOnClickListener(view -> {
                // Fragment'tan önceki ekrana dön
                // Fragment'tan önceki ekrana dön
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack(); // BackStack'i yönet
                }
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
                navigateToFragment(new DepremOncesi());
                break;
            case 2:
                navigateToFragment(new DepremAninda());
                break;
            case 3:
                navigateToFragment(new DepremSonrasi());
                break;
        }
    }

    private void navigateToFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutTatbikat, fragment)
                .addToBackStack(null)
                .commit();
    }
}