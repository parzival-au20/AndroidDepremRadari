package com.example.depremradari;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DepremlerFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private MyAdapter adapter;
    private List<MyItem> itemList;
    private CardView[] filterCards;
    private Button[] filterButtons;
    private boolean[] buttonClicked = {false, false, false, false};
    private int[] backgroundColor = {R.color.green,
                                R.color.yellow,
                                 R.color.orange,
                                R.color.Depremred};
    ResourcesDialog resourcesDialog;
    TimeDialog timeDialog;
    private ResultsViewModel resultsViewModel;
    Dialog dialog;
    Button btnDialogCancel, BtnDialogLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Fragmentin bir options menuyu olduğunu belirtmek için

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_depremler, container, false);

        // Toolbar öğesini al
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        // Toolbar özelliklerini ayarla
        if (toolbar != null) {
            toolbar.setTitle("Deprem Radarı");
            toolbar.setBackgroundColor(getResources().getColor(R.color.headerColor));
        }

        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        /*resultsViewModel = new ViewModelProvider(requireActivity()).get(ResultsViewModel.class);

        List<KandilliParse.Result> depremDataList = resultsViewModel.getDepremDataList();
        System.out.println(depremDataList);*/
        /*itemList = new ArrayList<>();
        for (KandilliParse.Result result : depremDataList) {
            //itemList.add(new MyItem(result.getMag(), result.getTitle(), result.getTitle(), result.getDate(), result.getDepth()));
            System.out.println("Earthquake ID: " + result.get_id());
            System.out.println("Title: " + result.getTitle());
            System.out.println("Date: " + result.getDate());
            System.out.println("Magnitude: " + result.getMag());
            System.out.println("Depth: " + result.getDepth());
            System.out.println("Geojson Type: " + result.getGeojson().getType());
            System.out.println("Geojson Coordinates: " + Arrays.toString(result.getGeojson().getCoordinates()));
            System.out.println("--------------------------------------");
        }

        adapter = new MyAdapter(requireContext(), itemList);
        recyclerView.setAdapter(adapter);*/

        filterCards = new CardView[]{
                rootView.findViewById(R.id.card_filter1),
                rootView.findViewById(R.id.card_filter2),
                rootView.findViewById(R.id.card_filter3),
                rootView.findViewById(R.id.card_filter4)
        };

        filterButtons = new Button[]{
                rootView.findViewById(R.id.filter1),
                rootView.findViewById(R.id.filter2),
                rootView.findViewById(R.id.filter3),
                rootView.findViewById(R.id.filter4)
        };

        for (int i = 0; i < filterButtons.length; i++) {
            final int index = i;
            filterButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleButtonClick(index);
                }
            });
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.resource_data) {
            System.out.println("resource_data");
            if (resourcesDialog == null) {
                resourcesDialog = new ResourcesDialog(requireContext());
            }
            resourcesDialog.show();
            return true;
        } else if (id == R.id.edit_time) {
            System.out.println("edit_time");
            if (timeDialog == null) {
                timeDialog = new TimeDialog(requireContext());
            }
            timeDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleButtonClick(int index) {
        if (!buttonClicked[index]) {
            buttonClicked[index] = true; // Tıklanma durumunu true yap
            // Diğer butonların tıklanma durumunu sıfırla
            for (int i = 0; i < buttonClicked.length; i++) {
                if (i != index) {
                    buttonClicked[i] = false;
                    updateButtonAppearance(filterButtons[i], filterCards[i], false,backgroundColor[i]);
                }else {
                    updateButtonAppearance(filterButtons[i], filterCards[i], true,backgroundColor[i]);
                }
            }
            filterItems();
        } else {
            updateButtonAppearance(filterButtons[index], filterCards[index], false,backgroundColor[index]);
            for (int i = 0; i < buttonClicked.length; i++) {
                buttonClicked[i] = false;
            }
            adapter = new MyAdapter(requireContext(), itemList);
            recyclerView.setAdapter(adapter);
        }
    }

    private void filterItems() {
        List<MyItem> filteredList = new ArrayList<>();
        for (MyItem item : itemList) {
            //System.out.println(item.getMag());
            double magnitude = item.getMag();
            if ((buttonClicked[0] && magnitude < 2) ||
                    (buttonClicked[1] && (2 <= magnitude && magnitude < 3)) ||
                    (buttonClicked[2] && (3 <= magnitude && magnitude < 4)) ||
                    (buttonClicked[3] && magnitude >= 4)) {
                filteredList.add(item);
            }
        }
        adapter = new MyAdapter(requireContext(), filteredList);
        recyclerView.setAdapter(adapter);
    }

    private void updateButtonAppearance(Button button, CardView card, boolean isSelected, int backgroundColor) {
        // Seçili duruma göre kenarlık rengini ve kalınlığını güncelle
        if (isSelected) {
            card.setCardBackgroundColor(context.getResources().getColor(R.color.darkslategray));
        } else{
            card.setCardBackgroundColor(context.getResources().getColor(backgroundColor));
        }
    }

    /*public void setDepremlerList(List<KandilliParse.Result> depremlerList) {
        // Depremler listesini kullanarak fragment içinde yapılacak işlemleri gerçekleştirin
        depremDataList = depremlerList;
        System.out.println("set depremler list");
        System.out.println(depremlerList);
        itemList = new ArrayList<>();
        for (KandilliParse.Result result : depremDataList) {
            itemList.add(new MyItem(result.getMag(), result.getTitle(), result.getTitle(), result.getDate(), result.getDepth()));
            /*System.out.println("Earthquake ID: " + result.get_id());
            System.out.println("Title: " + result.getTitle());
            System.out.println("Date: " + result.getDate());
            System.out.println("Magnitude: " + result.getMag());
            System.out.println("Depth: " + result.getDepth());
            System.out.println("Geojson Type: " + result.getGeojson().getType());
            System.out.println("Geojson Coordinates: " + Arrays.toString(result.getGeojson().getCoordinates()));
            System.out.println("--------------------------------------");
        }
        adapter = new MyAdapter(requireContext(), itemList);
        recyclerView.setAdapter(adapter);*/
    }

