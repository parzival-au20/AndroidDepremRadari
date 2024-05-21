package com.example.depremradari;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class TatbikatSorularContent extends Fragment {

    String[] cevaplar = {"A","A","B","B","B","A","A","A","B","B"};
    TextView textSoru;
    TextView textCevapA;
    TextView textCevapB;
    String content;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_soru_content, container, false);

        CardView Card_A = rootView.findViewById(R.id.card_4);
        CardView Card_B = rootView.findViewById(R.id.card_5);
        textSoru = rootView.findViewById(R.id.soru);
        textCevapA = rootView.findViewById(R.id.cevapA);
        textCevapB = rootView.findViewById(R.id.cevapB);

        Bundle args = getArguments();
        if (args != null) {
            content = args.getString("content");
            assert content != null;
            getSoru(Integer.parseInt(content));
        }
        Toolbar toolbar = rootView.findViewById(R.id.toolbarInfo);

        if (getActivity() instanceof AppCompatActivity) {

            toolbar.setNavigationOnClickListener(view -> {
                // Fragment'tan önceki ekrana dön
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack(); // BackStack'i yönet
                }
            });
        }

        Card_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cevaplar[Integer.parseInt(content) - 1].equals("A")) {
                    Card_A.setCardBackgroundColor(getResources().getColor(R.color.green));
                    Card_B.setCardBackgroundColor(getResources().getColor(R.color.Depremred));
                } else {
                    Card_A.setCardBackgroundColor(getResources().getColor(R.color.Depremred));
                    Card_B.setCardBackgroundColor(getResources().getColor(R.color.green));
                }
            }
        });
        Card_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cevaplar[Integer.parseInt(content) - 1].equals("B")) {
                    Card_A.setCardBackgroundColor(getResources().getColor(R.color.Depremred));
                    Card_B.setCardBackgroundColor(getResources().getColor(R.color.green));
                } else {
                    Card_A.setCardBackgroundColor(getResources().getColor(R.color.green));
                    Card_B.setCardBackgroundColor(getResources().getColor(R.color.Depremred));
                }
            }
        });
        return rootView;

    }

    private void getSoru(int cardIndex){
        Context context = getContext();

        switch (cardIndex) {
            case 1:
                textSoru.setText(R.string.Soru1);
                textCevapA.setText(R.string.Soru1_A);
                textCevapB.setText(R.string.Soru1_B);
                break;
            case 2:
                textSoru.setText(R.string.Soru2);
                textCevapA.setText(R.string.Soru2_A);
                textCevapB.setText(R.string.Soru2_B);
                break;
            case 3:
                textSoru.setText(R.string.Soru3);
                textCevapA.setText(R.string.Soru3_A);
                textCevapB.setText(R.string.Soru3_B);
                break;
            case 4:
                textSoru.setText(R.string.Soru4);
                textCevapA.setText(R.string.Soru4_A);
                textCevapB.setText(R.string.Soru4_B);
                break;
            case 5:
                textSoru.setText(R.string.Soru5);
                textCevapA.setText(R.string.Soru5_A);
                textCevapB.setText(R.string.Soru5_B);
                break;
            case 6:
                textSoru.setText(R.string.Soru6);
                textCevapA.setText(R.string.Soru6_A);
                textCevapB.setText(R.string.Soru6_B);
                break;
            case 7:
                textSoru.setText(R.string.Soru7);
                textCevapA.setText(R.string.Soru7_A);
                textCevapB.setText(R.string.Soru7_B);
                break;
            case 8:
                textSoru.setText(R.string.Soru8);
                textCevapA.setText(R.string.Soru8_A);
                textCevapB.setText(R.string.Soru8_B);
                break;
            case 9:
                textSoru.setText(R.string.Soru9);
                textCevapA.setText(R.string.Soru9_A);
                textCevapB.setText(R.string.Soru9_B);
                break;
            case 10:
                textSoru.setText(R.string.Soru10);
                textCevapA.setText(R.string.Soru10_A);
                textCevapB.setText(R.string.Soru10_B);
                break;
        }
    }

}