package com.example.depremradari;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimeDialog extends Dialog {

    private DepremlerFragment depremlerFragment;
    public TimeDialog(@NonNull Context context,  DepremlerFragment depremlerFragment) {
        super(context);
        this.depremlerFragment = depremlerFragment;
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
                    filterItemsByHour(1);
                    dismiss();
                } else if (checkedId == R.id.radio_button_2) {
                    filterItemsByHour(12);
                    dismiss();
                } else if (checkedId == R.id.radio_button_3) {
                    filterItemsByHour(24);
                    dismiss();
                }
            }
        });
    }

    private void filterItemsByHour(int time) {
        List<MyItem> filteredList = new ArrayList<>();
        for (MyItem item : depremlerFragment.itemList) {
            String itemTime = item.getHours(); // Varsayalım ki MyItem sınıfında zamanı temsil eden bir metot var
            String[] timeArray = itemTime.split(" ");
            int hour = Integer.parseInt(timeArray[0]);
            String state = timeArray[1];

            if ( (Objects.equals(state, "dakika") || (hour <= 1 && Objects.equals(state, "saat"))) && time==1 ) {
                filteredList.add(item);
            } else if ((hour <= 12 && Objects.equals(state, "saat")) && time==12) {
                filteredList.add(item);
            } else if ((hour <= 24 && Objects.equals(state, "saat")) && time == 24) {
                filteredList.add(item);
            }
        }
        depremlerFragment.adapter = new MyAdapter(depremlerFragment.getContext(), filteredList);
        depremlerFragment.recyclerView.setAdapter(depremlerFragment.adapter);
    }

}
