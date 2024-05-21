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
    TercihlerFragment tercihlerFragment;
    private ResourceDialogListener listener;
    public ResourcesDialog(@NonNull Context context, ResourceDialogListener listener) {
        super(context);
        this.listener = listener;
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
                System.out.println("Kandilli Resource selected");
                listener.onResourceSelected("Kandilli");
                dismiss();
            }
        });

        CardView cardFilter2 = findViewById(R.id.card_filter2);
        cardFilter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CSEM Resource selected");
                listener.onResourceSelected("CSEM");
                dismiss();
            }
        });

        CardView cardFilter3 = findViewById(R.id.card_filter3);
        cardFilter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("AFAD Resource selected");
                listener.onResourceSelected("AFAD");
                dismiss();
            }
        });

    }
}
