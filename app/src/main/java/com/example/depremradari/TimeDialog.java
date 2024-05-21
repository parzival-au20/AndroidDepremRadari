package com.example.depremradari;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimeDialog extends Dialog {


    private TimeDialogListener listener;
    private SharedPreferences preferences;
    private String selectedTime;
    public TimeDialog(@NonNull Context context, TimeDialogListener listener, String selectedTime) {
        super(context);
        this.listener = listener;
        this.preferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        this.selectedTime = selectedTime;
    }
    public TimeDialog(@NonNull Context context, TimeDialogListener listener) {
        super(context);
        this.listener = listener;
        this.preferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_dialog_box); // Özelleştirilmiş XML dosyası burada atanır
        Objects.requireNonNull(getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        // Diğer ayarlamaları burada yapabilirsiniz
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        String defaultTimeFilter = preferences.getString("DefaultTimeFilter", "24");
        if(selectedTime == null){
            selectedTime = defaultTimeFilter;
        }
        switch (selectedTime) {
            case "1":
                radioGroup.check(R.id.radio_button_1);
                break;
            case "12":
                radioGroup.check(R.id.radio_button_2);
                break;
            case "24":
                radioGroup.check(R.id.radio_button_3);
                break;
        }
        // RadioGroup'a tıklama dinleyici ekle
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Seçilen radio butonunu bul
            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                if (checkedId == R.id.radio_button_1) {
                    //filterItemsByHour(1);
                    listener.onTimeSelected(1);
                    dismiss();
                } else if (checkedId == R.id.radio_button_2) {
                    //filterItemsByHour(12);
                    listener.onTimeSelected(12);
                    dismiss();
                } else if (checkedId == R.id.radio_button_3) {
                    //filterItemsByHour(24);
                    listener.onTimeSelected(24);
                    dismiss();
                }
            }
        });
    }


}
