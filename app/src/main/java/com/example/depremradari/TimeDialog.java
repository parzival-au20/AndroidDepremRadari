package com.example.depremradari;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import java.util.Objects;

public class TimeDialog extends Dialog {

    public TimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_dialog_box); // Özelleştirilmiş XML dosyası burada atanır
        Objects.requireNonNull(getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        // Diğer ayarlamaları burada yapabilirsiniz
        RadioGroup radioGroup = findViewById(R.id.radio_group);

        // RadioGroup'a tıklama dinleyici ekle
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Seçilen radio butonunu bul
            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                if (checkedId == R.id.radio_button_1) {
                    System.out.println("Son 1 Saat seçildi");
                    dismiss();
                } else if (checkedId == R.id.radio_button_2) {
                    System.out.println("Son 12 Saat seçildi");
                    dismiss();
                } else if (checkedId == R.id.radio_button_3) {
                    System.out.println("Son 24 Saat seçildi");
                    dismiss();
                }
            }
        });
    }
}
