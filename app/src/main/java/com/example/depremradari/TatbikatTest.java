package com.example.depremradari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class TatbikatTest extends Fragment {

    private CardView navCards;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbarInfo);
        navCards = rootView.findViewById(R.id.card_1);

        if (getActivity() instanceof AppCompatActivity) {

            toolbar.setNavigationOnClickListener(view -> {
                // Fragment'tan önceki ekrana dön
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack(); // BackStack'i yönet
                }
            });
        }

        navCards.setOnClickListener(v -> openNewFragmentOrActivity());

        return rootView;

    }

    private void openNewFragmentOrActivity() {
            Fragment fragment = new TatbikatSorular();
            navigateToFragment(fragment);
    }

    private void navigateToFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutTatbikat, fragment)
                .addToBackStack(null)
                .commit();
    }
}