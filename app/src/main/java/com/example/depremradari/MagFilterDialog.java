package com.example.depremradari;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import java.util.Objects;

public class MagFilterDialog extends Dialog {


    private MagDialogListener listener;
    private CardView[] filterCards;
    private Button[] filterButtons;
    private Button okButton;
    public boolean[] buttonClicked = {false, false, false, false, false};
    public int[] backgroundColor = {R.color.green,
            R.color.yellow,
            R.color.orange,
            R.color.Depremred,
            R.color.blue_ };
    private SharedPreferences preferences;

    public MagFilterDialog(@NonNull Context context, MagDialogListener listener) {
        super(context);
        this.listener = listener;
        this.preferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mag_filter_dialog); // Özelleştirilmiş XML dosyası burada atanır
        Objects.requireNonNull(getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        // Diğer ayarlamaları burada yapabilirsiniz

        okButton = findViewById(R.id.tamam);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        filterCards = new CardView[]{
                findViewById(R.id.card_filter1),
                findViewById(R.id.card_filter2),
                findViewById(R.id.card_filter3),
                findViewById(R.id.card_filter4),
                findViewById(R.id.card_filter5),
        };

        filterButtons = new Button[]{
                findViewById(R.id.filter1),
                findViewById(R.id.filter2),
                findViewById(R.id.filter3),
                findViewById(R.id.filter4),
                findViewById(R.id.filter5),
        };

        initializeButtonStates();

        for (int i = 0; i < filterButtons.length; i++) {
            final int index = i;
            filterButtons[i].setOnClickListener(v -> {
                updateButtonSelection(index);
                // Save the selected filter
                preferences.edit().putInt("selectedFilter", index).apply();
                if (listener != null) {
                    listener.onMagSelected("Filter" + index);
                }
                //dismiss();
            });
        }
    }
    private void initializeButtonStates() {
        int selectedFilter = preferences.getInt("selectedFilter", 4); // Default to first filter
        updateButtonSelection(selectedFilter);
    }
    private void updateButtonSelection(int selectedIndex) {
        for (int i = 0; i < buttonClicked.length; i++) {
            buttonClicked[i] = (i == selectedIndex);
            filterButtons[i].setSelected(buttonClicked[i]);
            if (buttonClicked[i]) {
                filterCards[i].setCardBackgroundColor(getContext().getResources().getColor(R.color.darkslategray));
            } else {
                filterCards[i].setCardBackgroundColor(getContext().getResources().getColor(backgroundColor[i]));
            }
        }
    }
}
