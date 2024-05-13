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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class DepremlerFragment extends Fragment{

    public RecyclerView recyclerView;
    private Context context;
    public MyAdapter adapter;
    public List<MyItem> itemList;
    private CardView[] filterCards;
    private Button[] filterButtons;
    private ImageView resourceImage;
    private boolean[] buttonClicked = {false, false, false, false};
    private int[] backgroundColor = {R.color.green,
                                R.color.yellow,
                                 R.color.orange,
                                R.color.Depremred};
    ResourcesDialog resourcesDialog;
    TimeDialog timeDialog;
    public ResultsViewModel resultsViewModel;
    Dialog dialog;
    Button btnDialogCancel, BtnDialogLogout;
    SimpleDateFormat format;
    public LiveData<List<KandilliParse.Result>> kandilliLiveData;
    public LiveData<List<AfadParse.Data>> afadLiveData;
    private FrameLayout frameLayout;
    private View loadingView;
    private View rootView;
    private String selectedResource ;
    private String selectedTime;

    public String getSelectedResource() {
        return selectedResource;
    }

    public void setSelectedResource(String selectedResource) {
        this.selectedResource = selectedResource;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        frameLayout = new FrameLayout(inflater.getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        loadingView = inflater.inflate(R.layout.loading_animation, frameLayout, false);
        rootView = inflater.inflate(R.layout.fragment_depremler, frameLayout, false);

        showLoading();

        resultsViewModel = new ViewModelProvider(this).get(ResultsViewModel.class);
        resultsViewModel.setRetrofitSettings("Kandilli");
        kandilliLiveData = resultsViewModel.getResults();

        setToolbar();
        resourceImage = frameLayout.findViewById(R.id.resourceImage);
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

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        //setAfadLiveData();
        setKandilliLiveData();


        for (int i = 0; i < filterButtons.length; i++) {
            final int index = i;
            filterButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleButtonClick(index);
                }
            });
        }

        return frameLayout;
    }

    public void setToolbar(){
        // Toolbar ayarlamadan önce Context'in varlığını kontrol et
        Context context = getContext();
        if (context == null) {
            return;  // Context yoksa işlem yapma
        }

        Toolbar toolbar = frameLayout.findViewById(R.id.toolbar);
        // Toolbar özelliklerini ayarla
        if (toolbar != null) {
            toolbar.setTitle("Deprem Radarı");
            toolbar.setBackgroundColor(getResources().getColor(R.color.headerColor));
        }

        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
    }

    public void showLoading(){

        if (!isAdded()) {  // Fragment hala Activity'ye bağlı mı kontrol et
            return;
        }
        if (loadingView.getParent() != null) {
            ((ViewGroup) loadingView.getParent()).removeView(loadingView);
        }
        frameLayout.addView(loadingView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Yükleme görünümünü kaldır
                frameLayout.removeView(loadingView);
                // Ana içerik görünümünü ekle
                if (rootView.getParent() != null) {
                    ((ViewGroup) rootView.getParent()).removeView(rootView);
                }
                frameLayout.addView(rootView);
                setToolbar();
                resourceImage = frameLayout.findViewById(R.id.resourceImage);
                updateResImg();
            }
        }, 3000);
    }

    private void updateResImg(){
        if(Objects.equals(selectedResource, "AFAD")){
            resourceImage.setImageResource(R.drawable.afad);
        } else if (Objects.equals(selectedResource, "Kandilli")) {
            resourceImage.setImageResource(R.drawable.kandilli);
        }else {
            resourceImage.setImageResource(R.drawable.csem);
        }
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
                resourcesDialog = new ResourcesDialog(requireContext(), this);
            }
            resourcesDialog.show();
            timeDialog = new TimeDialog(requireContext(), this);
            return true;
        } else if (id == R.id.edit_time) {
            System.out.println("edit_time");
            if (timeDialog == null) {
                timeDialog = new TimeDialog(requireContext(), this);
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

    private String[] processCity(String cityString) {
        String[] parts = cityString.split("\\("); // Şehir adı ve ilçe bilgisini ayır
        String district = parts[0].trim().replaceAll("-", " "); // Şehir adını al ve tireleri boşlukla değiştir
        String province = ""; // İl bilgisi için boş bir string tanımla

        // Eğer ilçe ve il bilgisi varsa, ilçe bilgisini ve parantez içindeki il bilgisini al
        if (parts.length > 1) {
            province = parts[1].length() > 1 ? parts[1].replaceAll("\\)", "") : ""; // Parantez içindeki il bilgisini al
        }
        return new String[] { district, province };
    }

    private String formatTime(String timeStamp, String Resource) {

        if(Objects.equals(Resource, "AFAD")){
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("TSI"));
        }else {
            format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        }

        try {
            Date logTime = format.parse(timeStamp);
            Date currentTime = new Date();
            // Zaman farkını hesapla (milisaniye cinsinden)
            long timeDifference = currentTime.getTime() - logTime.getTime();
            //System.out.println(timeDifference);
            if (timeDifference < 0) {
                timeDifference = Math.abs(timeDifference);
            }
            // Dakika cinsine dönüştür
            double minutesDifference = Math.floor((double)timeDifference / (1000 * 60));

            // Farka göre zamanı formatla
            if (minutesDifference < 60) {
                // 1 saatten az ise dakika cinsinden göster
                return (long) minutesDifference + " dakika önce";
            } else {
                // 1 saatten fazla ise saat cinsinden göster
                long hoursDifference = (long) Math.floor(minutesDifference / 60);
                return hoursDifference + " saat önce";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setAfadLiveData(){
        resourceImage.setImageResource(R.drawable.afad);
        for (int i = 0; i < buttonClicked.length; i++) {
            buttonClicked[i] = false;
            updateButtonAppearance(filterButtons[i], filterCards[i], false, backgroundColor[i]);
        }
        showLoading();
        setSelectedResource("AFAD");
        //resourceImage.setImageResource(R.drawable.afad);
        afadLiveData.observe(getViewLifecycleOwner(), new Observer<List<AfadParse.Data>>() {
            @Override
            public void onChanged(List<AfadParse.Data> afadData) {
                itemList = new ArrayList<>();
                for (AfadParse.Data data : afadData) {
                    if(data.getDistrict() != null || data.getProvince()!= null){
                        String date = formatTime(data.getDate(), "AFAD");
                        itemList.add(new MyItem(Double.valueOf(data.getMagnitude()), data.getDistrict(), data.getProvince(), date, String.valueOf(data.getDepth())));
                    }
                }
                adapter = new MyAdapter(requireContext(), itemList);
                recyclerView.setAdapter(adapter);
            }
        });
    }
    public void setKandilliLiveData(){
        resourceImage.setImageResource(R.drawable.kandilli);

        for (int i = 0; i < buttonClicked.length; i++) {
            buttonClicked[i] = false;
            updateButtonAppearance(filterButtons[i], filterCards[i], false, backgroundColor[i]);
        }
        showLoading();
        setSelectedResource("Kandilli");
        kandilliLiveData.observe(getViewLifecycleOwner(), new Observer<List<KandilliParse.Result>>() {
            @Override
            public void onChanged(List<KandilliParse.Result> results) {
                itemList = new ArrayList<>();
                for (KandilliParse.Result result : results) {
                    String[] cityInfo = processCity(result.getTitle());
                    String district = cityInfo[0]; // İlçe bilgisini alır
                    String province = cityInfo[1];
                    String date = formatTime(result.getDate(), "Kandilli");
                    itemList.add(new MyItem(result.getMag(), district, province, date, String.valueOf(result.getDepth())));
                }
                adapter = new MyAdapter(requireContext(), itemList);
                recyclerView.setAdapter(adapter);

            }
        });
    }

}

