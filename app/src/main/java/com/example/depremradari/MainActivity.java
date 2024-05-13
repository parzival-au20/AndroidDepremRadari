package com.example.depremradari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setItemIconTintList(null);
        ColorStateList colorStateList1 = ContextCompat.getColorStateList( this,R.color.active_color_1);
        ColorStateList colorStateList2 = ContextCompat.getColorStateList( this,R.color.active_color_2);
        ColorStateList colorStateList3 = ContextCompat.getColorStateList( this,R.color.active_color_3);
        ColorStateList colorStateList4 = ContextCompat.getColorStateList( this,R.color.active_color_4);
        bottomNavigationView.setItemTextColor(createColorStateList(getResources().getColor(R.color.Depremred), getResources().getColor(R.color.black)));
        DepremlerFragment depremlerFragment = new DepremlerFragment();


        loadFragment(depremlerFragment, false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.navDepremler){
                    loadFragment(new DepremlerFragment(), false);
                    bottomNavigationView.setItemActiveIndicatorColor(colorStateList1);
                    bottomNavigationView.setItemTextColor(createColorStateList(getResources().getColor(R.color.Depremred), getResources().getColor(R.color.black)));
                } else if (itemId == R.id.navHarita) {
                    loadFragment(new HaritaFragment(), false);
                    bottomNavigationView.setItemActiveIndicatorColor(colorStateList2);
                    bottomNavigationView.setItemTextColor(createColorStateList(getResources().getColor(R.color.blue), getResources().getColor(R.color.black)));
                }else if (itemId == R.id.navHazirlik) {
                    loadFragment(new TatbikatFragment(), false);
                    bottomNavigationView.setItemActiveIndicatorColor(colorStateList3);
                    bottomNavigationView.setItemTextColor(createColorStateList(getResources().getColor(R.color.orange), getResources().getColor(R.color.black)));
                }else {
                    loadFragment(new TercihlerFragment(), false);
                    bottomNavigationView.setItemActiveIndicatorColor(colorStateList4);
                    bottomNavigationView.setItemTextColor(createColorStateList(getResources().getColor(R.color.green), getResources().getColor(R.color.black)));
                }
                return true;
            }
        });
    }
    private void loadFragment(Fragment fragment, boolean isAppInitilazed){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitilazed){
            fragmentTransaction.add(R.id.frameLayout, fragment);
        }else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }
        fragmentTransaction.commit();
    }

    private ColorStateList createColorStateList(int activeColor, int inactiveColor) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_checked}
        };
        int[] colors = new int[]{
                activeColor,
                inactiveColor
        };
        return new ColorStateList(states, colors);
    }

}