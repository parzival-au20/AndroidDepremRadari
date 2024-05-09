package com.example.depremradari;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Retrofit retrofit;
    private KandilliApi kandilliApi;
    private String baseUrl = "https://api.orhanaydogdu.com.tr/";
    private Call<KandilliParse> kandilliParseCall;
    private KandilliParse kandilliParse;
    private ResultsViewModel resultsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setRetrofitSettings();
        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setItemIconTintList(null);
        ColorStateList colorStateList1 = ContextCompat.getColorStateList( this,R.color.active_color_1);
        ColorStateList colorStateList2 = ContextCompat.getColorStateList( this,R.color.active_color_2);
        ColorStateList colorStateList3 = ContextCompat.getColorStateList( this,R.color.active_color_3);
        ColorStateList colorStateList4 = ContextCompat.getColorStateList( this,R.color.active_color_4);
        bottomNavigationView.setItemTextColor(createColorStateList(getResources().getColor(R.color.Depremred), getResources().getColor(R.color.black)));
        DepremlerFragment depremlerFragment = new DepremlerFragment();
        resultsViewModel = new ViewModelProvider(this).get(ResultsViewModel.class);
        List<KandilliParse.Result> depremDataList = resultsViewModel.getDepremDataList();
        LiveData<List<KandilliParse.Result>> depremDataList1 = resultsViewModel.getResults();
        System.out.println(depremDataList);
        System.out.println(depremDataList1);
        depremDataList1.observe(this, new Observer<List<KandilliParse.Result>>() {
            @Override
            public void onChanged(List<KandilliParse.Result> results) {
                // Veri değiştiğinde burası çalışacak
                System.out.println(results);
            }
        });
        System.out.println("Activity");

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
                    loadFragment(new HazirlikFragment(), false);
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
    /*private void setRetrofitSettings(){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        kandilliApi = retrofit.create(KandilliApi.class);
        kandilliParseCall = kandilliApi.getResult();

        kandilliParseCall.enqueue(new Callback<KandilliParse>() {
            @Override
            public void onResponse(@NonNull Call<KandilliParse> call, @NonNull Response<KandilliParse> response) {
                if(response.isSuccessful()){
                    kandilliParse = response.body();
                    if (kandilliParse != null) {
                        resultsViewModel.setDepremDataList(kandilliParse.getResult());
                    }
                    //System.out.println(kandilliParse.getResult());
                    /*for (KandilliParse.Result result : kandilliParse.getResult()) {
                        System.out.println("Earthquake ID: " + result.get_id());
                        System.out.println("Title: " + result.getTitle());
                        System.out.println("Date: " + result.getDate());
                        System.out.println("Magnitude: " + result.getMag());
                        System.out.println("Depth: " + result.getDepth());
                        System.out.println("Geojson Type: " + result.getGeojson().getType());
                        System.out.println("Geojson Coordinates: " + Arrays.toString(result.getGeojson().getCoordinates()));
                        System.out.println("--------------------------------------");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<KandilliParse> call, @NonNull Throwable t) {
                System.out.println(t.toString());
            }
        });
    }*/

}