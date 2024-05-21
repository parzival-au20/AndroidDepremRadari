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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private FragmentManager fragmentManager;
    private Fragment depremlerFragment;
    private Fragment haritaFragment;
    private Fragment tatbikatFragment;
    private Fragment tercihlerFragment;
    private Fragment activeFragment;
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
        //DepremlerFragment depremlerFragment = new DepremlerFragment();
        fragmentManager = getSupportFragmentManager();
        depremlerFragment = new DepremlerFragment();
        haritaFragment = new HaritaFragment();
        tatbikatFragment = new TatbikatFragment();
        tercihlerFragment = new TercihlerFragment();

        // Başlangıçta DepremlerFragment'i yükle
        fragmentManager.beginTransaction()
                .add(R.id.frameLayout, depremlerFragment, "depremlerFragment")
                .commit();
        activeFragment = depremlerFragment;

        //loadFragment(depremlerFragment, false,"depremlerFragment");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.navDepremler){
                    //loadFragment(new DepremlerFragment(), false, "depremlerFragment");
                    switchFragment(depremlerFragment, colorStateList1, R.color.Depremred);
                    return true;
                } else if (itemId == R.id.navHarita) {
                    DepremlerFragment depremlerFragment = (DepremlerFragment) getSupportFragmentManager().findFragmentByTag("depremlerFragment");
                    if (depremlerFragment != null) {
                        ArrayList<MyItem> earthquakeData = new ArrayList<>(depremlerFragment.getFilteredFinalList().isEmpty() ? depremlerFragment.getItemList() : depremlerFragment.getFilteredFinalList());
                        haritaFragment  = HaritaFragment.newInstance(earthquakeData);
                        //loadFragment(haritaFragment , false, "haritaFragment");
                    } /*else {
                        loadFragment(new HaritaFragment(), false,"haritaFragment");
                    }*/
                    switchFragment(haritaFragment, colorStateList2, R.color.blue);
                    return true;
                }else if (itemId == R.id.navHazirlik) {
                    //loadFragment(new TatbikatFragment(), false, "tatbikatFragment");
                    switchFragment(tatbikatFragment,colorStateList3, R.color.orange);
                    return true;
                }else if(itemId == R.id.navTercihler){
                    //loadFragment(new TercihlerFragment(), false,"tercihlerFragment");
                    switchFragment(tercihlerFragment, colorStateList4, R.color.green);
                    return true;
                }
                return false;
            }
        });
    }

    private void switchFragment(Fragment fragment, ColorStateList colorStateList, int activeColor) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        bottomNavigationView.setItemActiveIndicatorColor(colorStateList);
        bottomNavigationView.setItemTextColor(createColorStateList(getResources().getColor(activeColor), getResources().getColor(R.color.black)));

        if (fragment.isAdded()) {
            transaction.hide(activeFragment).show(fragment);
        } else {
            transaction.hide(activeFragment).add(R.id.frameLayout, fragment);
        }
        transaction.commit();
        activeFragment = fragment;
    }
    private void loadFragment(Fragment fragment, boolean isAppInitilazed, ColorStateList colorStateList, int activeColor){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        bottomNavigationView.setItemActiveIndicatorColor(colorStateList);
        bottomNavigationView.setItemTextColor(createColorStateList(getResources().getColor(activeColor), getResources().getColor(R.color.black)));
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