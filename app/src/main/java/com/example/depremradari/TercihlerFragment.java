package com.example.depremradari;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TercihlerFragment extends Fragment implements ResourceDialogListener, MagDialogListener, TimeDialogListener{

    private CardView[] navCards;
    ResourcesDialog resourcesDialog;
    MagFilterDialog magFilterDialog;
    TimeDialog timeDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tercihler, container, false);

        navCards = new CardView[]{
                rootView.findViewById(R.id.card_1),
                rootView.findViewById(R.id.card_2),
                rootView.findViewById(R.id.card_3),
                rootView.findViewById(R.id.card_4),
                rootView.findViewById(R.id.card_5),
        };

        // Toolbar öğesini al
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);


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
                if (resourcesDialog == null) {
                    resourcesDialog = new ResourcesDialog(requireContext(), this);
                }
                resourcesDialog.show();
                break;
            case 2:
                if (magFilterDialog == null) {
                    magFilterDialog = new MagFilterDialog(requireContext(), this);
                }
                magFilterDialog.show();
                break;
            case 3:
                if (timeDialog == null) {
                    timeDialog = new TimeDialog(requireContext(), this);
                }
                timeDialog.show();
                break;
            case 4:
                navigateToFragment(new Gizlilik());
                break;
            case 5:
                //navigateToFragment(new DepremSonrasi());
                break;
        }
    }

    private void navigateToFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResourceSelected(String resource) {
        // Tercihler için yapılan işlemler
        // Örneğin uygulama genelinde bir ayarı güncellemek
        SharedPreferences preferences = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        preferences.edit().putString("defaultResource", resource).apply();
    }
    @Override
    public void onMagSelected(String filter) {
        SharedPreferences preferences = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        preferences.edit().putString("DefaultMagnitudeFilter", filter).apply();
    }

    @Override
    public void onTimeSelected(int time) {
        SharedPreferences preferences = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        preferences.edit().putString("DefaultTimeFilter", String.valueOf(time)).apply();
    }
}