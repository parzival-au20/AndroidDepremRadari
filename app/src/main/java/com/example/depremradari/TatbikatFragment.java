package com.example.depremradari;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public class TatbikatFragment extends Fragment {

    private CardView[] navCards;
    private Fragment activeChildFragment;
    private Fragment tatbikatVideoFragment;
    private Fragment tatbikatTestFragment;
    private Fragment tatbikatCantaFragment;
    private Fragment tatbikatInfoFragment;
    private Fragment tatbikatFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tatbikatVideoFragment = new TatbikatVideo();
        tatbikatTestFragment = new TatbikatTest();
        tatbikatCantaFragment = new TatbikatCanta();
        tatbikatInfoFragment = new TatbikatInfo(); // Initialize this
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


        for (int i = 0; i < navCards.length; i++) {

            int finalI = i;
            // Lambda ifadesi ile tüm kartlara OnClickListener ata
            navCards[i].setOnClickListener(v -> openNewFragmentOrActivity(finalI + 1));
        }

        return rootView;
    }

    private void openNewFragmentOrActivity(int cardIndex) {
        Fragment newFragment;
        switch (cardIndex) {
            case 1:
                newFragment = new TatbikatVideo();
                break;
            case 2:
                newFragment = new TatbikatTest();
                break;
            case 3:
                newFragment = new TatbikatCanta();
                break;
            case 4:
                newFragment = new TatbikatInfo();
                break;
            default:
                newFragment = null;
                break;
        }

        if (newFragment != null) {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayoutTatbikat, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    /*private void openNewFragmentOrActivity(int cardIndex) {
        Fragment newFragment;
        switch (cardIndex) {
            case 1:
                newFragment = tatbikatVideoFragment;
                break;
            case 2:
                newFragment = tatbikatTestFragment;
                break;
            case 3:
                newFragment = tatbikatCantaFragment;
                break;
            case 4:
                newFragment = tatbikatInfoFragment;
                break;
            default:
                return;
        }

        if (newFragment != null && newFragment != activeChildFragment) {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            if (activeChildFragment != null) {
                transaction.hide(activeChildFragment);
            }
            if (!newFragment.isAdded()) {
                transaction.add(R.id.frameLayoutTatbikat, newFragment);
            } else {
                transaction.show(newFragment);
            }
            transaction.addToBackStack(null);
            transaction.commit();
            activeChildFragment = newFragment;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        System.out.println(activeChildFragment);
        // Fragment geri döndüğünde aktif child fragment'ı güncelle
        if (activeChildFragment != null) {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.show(activeChildFragment);
            System.out.println(activeChildFragment);
            transaction.commit();
        }
    }*/


    /*private void openNewFragmentOrActivity(int cardIndex) {
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
    }*/
}