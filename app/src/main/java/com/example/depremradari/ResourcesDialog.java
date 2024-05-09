package com.example.depremradari;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import java.util.Objects;

public class ResourcesDialog extends Dialog {

    public ResourcesDialog(@NonNull Context context) {
        super(context);
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
                // CardView'a tıklandığında yapılacak işlemler buraya yazılır
                // Örneğin, bir dialog göstermek veya yeni bir aktiviteye geçmek gibi
                System.out.println("Kandilli Res");
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
                dismiss();
            }
        });
    }
}
