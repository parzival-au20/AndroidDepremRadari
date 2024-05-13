package com.example.depremradari;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import java.util.Objects;

public class ResourcesDialog extends Dialog {

    DepremlerFragment depremlerFragment;
    public ResourcesDialog(@NonNull Context context, DepremlerFragment depremlerFragment) {
        super(context);
        this.depremlerFragment = depremlerFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resources_dialog_box); // Özelleştirilmiş XML dosyası burada atanır
        Objects.requireNonNull(getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        // Diğer ayarlamaları burada yapabilirsiniz

        CardView cardFilter1 = findViewById(R.id.card_filter1);
        cardFilter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Kandilli Res");
                depremlerFragment.resultsViewModel.setRetrofitSettings("Kandilli");
                depremlerFragment.kandilliLiveData = depremlerFragment.resultsViewModel.getResults();
                depremlerFragment.setKandilliLiveData();
                dismiss();
            }
        });

// card_filter2 için tıklama dinleyicisi
        CardView cardFilter2 = findViewById(R.id.card_filter2);
        cardFilter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CSEM Res");
                dismiss();
            }
        });

        CardView cardFilter3 = findViewById(R.id.card_filter3);
        cardFilter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("AFAD Res");
                depremlerFragment.resultsViewModel.setRetrofitSettings("AFAD");
                depremlerFragment.afadLiveData = depremlerFragment.resultsViewModel.getAfadData();
                depremlerFragment.setAfadLiveData();
                dismiss();
            }
        });
    }
}
