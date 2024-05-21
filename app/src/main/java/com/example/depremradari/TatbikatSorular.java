package com.example.depremradari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class TatbikatSorular extends Fragment {

    private CardView[] navCards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sorular, container, false);

        navCards = new CardView[]{
                rootView.findViewById(R.id.card_1),
                rootView.findViewById(R.id.card_2),
                rootView.findViewById(R.id.card_3),
                rootView.findViewById(R.id.card_4),
                rootView.findViewById(R.id.card_5),
                rootView.findViewById(R.id.card_6),
                rootView.findViewById(R.id.card_7),
                rootView.findViewById(R.id.card_8),
                rootView.findViewById(R.id.card_9),
                rootView.findViewById(R.id.card_10),
        };

        Toolbar toolbar = rootView.findViewById(R.id.toolbarInfo);

        if (getActivity() instanceof AppCompatActivity) {

            toolbar.setNavigationOnClickListener(view -> {
                // Fragment'tan önceki ekrana dön
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack(); // BackStack'i yönet
                }
            });
        }

        for (int i = 0; i < navCards.length; i++) {

            int finalI = i+1;
            // Lambda ifadesi ile tüm kartlara OnClickListener ata
            navCards[i].setOnClickListener(v -> openNewFragmentOrActivity(finalI));
        }


        return rootView;

    }



    private void openNewFragmentOrActivity(int cardIndex) {
        Fragment fragment = new TatbikatSorularContent();
        Bundle bundle = new Bundle();
        bundle.putString("content", String.valueOf(cardIndex));
        fragment.setArguments(bundle);
        navigateToFragment(fragment);
    }

    private void navigateToFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutSorular, fragment)
                .addToBackStack(null)
                .commit();
    }
}