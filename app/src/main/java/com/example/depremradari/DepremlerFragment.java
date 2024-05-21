package com.example.depremradari;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepremlerFragment extends Fragment implements ResourceDialogListener,TimeDialogListener {

    public RecyclerView recyclerView;
    private Context context;
    public MyAdapter adapter;
    public List<MyItem> itemList;
    private String defaultFilterIndex;
    private String defaultTime;
    public CardView[] filterCards;
    public Button[] filterButtons;
    private ImageView resourceImage;
    public boolean[] buttonClicked = {false, false, false, false};
    public int[] backgroundColor = {R.color.green,
            R.color.yellow,
            R.color.orange,
            R.color.Depremred};
    ResourcesDialog resourcesDialog;
    TimeDialog timeDialog;
    public ResultsViewModel resultsViewModel;
    SimpleDateFormat format;
    public LiveData<List<KandilliParse.Result>> kandilliLiveData;
    public LiveData<List<AfadParse.Data>> afadLiveData;
    public LiveData<CsemParse> csemLiveData;
    private FrameLayout frameLayout;
    private View loadingView;
    private View rootView;
    private String selectedResource;

    public String selectedTime;
    private List<MyItem> filteredList;
    private List<MyItem> filteredFinalList;

    public void setSelectedResource(String selectedResource) {
        this.selectedResource = selectedResource;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        filteredList = new ArrayList<>();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        frameLayout = new FrameLayout(inflater.getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        loadingView = inflater.inflate(R.layout.loading_animation, frameLayout, false);
        rootView = inflater.inflate(R.layout.fragment_depremler, frameLayout, false);
        showLoading();

        resourceImage = frameLayout.findViewById(R.id.resourceImage);
        resultsViewModel = new ViewModelProvider(this).get(ResultsViewModel.class);
        SharedPreferences preferences = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String defaultResource = preferences.getString("defaultResource", "Kandilli");

        SharedPreferences preferences_filter = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        defaultFilterIndex = preferences.getString("DefaultMagnitudeFilter", "Filter4").split("")[6];

        SharedPreferences preferences_time = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        defaultTime = preferences.getString("DefaultTimeFilter", "24");
        setSelectedTime(defaultTime);
        setToolbar();

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
        //setKandilliLiveData();

        onResourceSelected(defaultResource);

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

    @Override
    public void onResourceSelected(String resource) {
        resultsViewModel.setRetrofitSettings(resource);
        if ("Kandilli".equals(resource)) {
            kandilliLiveData = resultsViewModel.getResults();
            setKandilliLiveData();
        } else if ("AFAD".equals(resource)) {
            afadLiveData = resultsViewModel.getAfadData();
            setAfadLiveData();
        } else if ("CSEM".equals(resource)) {
            csemLiveData = resultsViewModel.getCSEMData();
            setCSEMLiveData();
        }
    }

    public void setToolbar() {
        // Toolbar ayarlamadan önce Context'in varlığını kontrol et
        Context context = getContext();
        if (context == null) {
            return;  // Context yoksa işlem yapma
        }
        Toolbar toolbar = frameLayout.findViewById(R.id.toolbar);

        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
    }

    public void showLoading() {

        if (!isAdded()) {  // Fragment hala Activity'ye bağlı mı kontrol et
            return;
        }
        if (loadingView.getParent() != null) {
            ((ViewGroup) loadingView.getParent()).removeView(loadingView);
        }
        frameLayout.addView(loadingView);
        new Handler().postDelayed(() -> {
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
        }, 3000);
    }

    private void updateResImg() {
        if (Objects.equals(selectedResource, "AFAD")) {
            resourceImage.setImageResource(R.drawable.afad);
        } else if (Objects.equals(selectedResource, "Kandilli")) {
            resourceImage.setImageResource(R.drawable.kandilli);
        } else {
            resourceImage.setImageResource(R.drawable.csem);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        //setToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.resource_data) {
            if (resourcesDialog == null) {
                resourcesDialog = new ResourcesDialog(requireContext(), this);
            }
            resourcesDialog.show();
            timeDialog = new TimeDialog(requireContext(), this, selectedTime);
            return true;
        } else if (id == R.id.edit_time) {
            if (timeDialog == null) {
                timeDialog = new TimeDialog(requireContext(), this, selectedTime);
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
                    updateButtonAppearance(filterButtons[i], filterCards[i], false, backgroundColor[i]);
                } else {
                    updateButtonAppearance(filterButtons[i], filterCards[i], true, backgroundColor[i]);
                }
            }
            filterItems();
            filterItemsByHour(Integer.parseInt(selectedTime), filteredList);
        } else {
            updateButtonAppearance(filterButtons[index], filterCards[index], false, backgroundColor[index]);
            Arrays.fill(buttonClicked, false);
            adapter = new MyAdapter(requireContext(), filterItemsByHour(Integer.parseInt(selectedTime), itemList));
            recyclerView.setAdapter(adapter);
        }
    }

    private void filterItems() {
        filteredList = new ArrayList<>();
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
        /*adapter = new MyAdapter(requireContext(), filteredList);
        recyclerView.setAdapter(adapter);*/
    }

    public void updateButtonAppearance(Button button, CardView card, boolean isSelected, int backgroundColor) {
        // Seçili duruma göre kenarlık rengini ve kalınlığını güncelle
        if (isSelected) {
            card.setCardBackgroundColor(context.getResources().getColor(R.color.darkslategray));
        } else {
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
        return new String[]{district, province};
    }

    private String formatTime(String timeStamp, String Resource) {

        if (Objects.equals(Resource, "AFAD")) {
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("TSI"));
        } else {
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
            double minutesDifference = Math.floor((double) timeDifference / (1000 * 60));

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

    public void setAfadLiveData() {
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
                System.out.println(afadData);
                for (AfadParse.Data data : afadData) {
                    if (data.getDistrict() != null || data.getProvince() != null) {
                        String date = formatTime(data.getDate(), "AFAD");
                        itemList.add(new MyItem(Double.valueOf(data.getMagnitude()),
                                data.getDistrict(),
                                data.getProvince(), date,
                                String.valueOf(data.getDepth()),
                                Double.valueOf(data.getLatitude()), Double.valueOf(data.getLongitude())));
                    }
                }
                adapter = new MyAdapter(requireContext(), itemList);
                recyclerView.setAdapter(adapter);
                startDefaultFilterIndex();
            }
        });
    }

    public void setCSEMLiveData() {
        resourceImage.setImageResource(R.drawable.csem);
        for (int i = 0; i < buttonClicked.length; i++) {
            buttonClicked[i] = false;
            updateButtonAppearance(filterButtons[i], filterCards[i], false, backgroundColor[i]);
        }
        showLoading();
        setSelectedResource("CSEM");
        //resourceImage.setImageResource(R.drawable.afad);
        csemLiveData.observe(getViewLifecycleOwner(), new Observer<CsemParse>() {
            @Override
            public void onChanged(CsemParse csemData) {
                itemList = new ArrayList<>();
                String[] city;
                String District;
                String Province;
                String date;
                System.out.println("------------------");
                for (CsemParse.Feature data : csemData.getFeatures()) {

                    double latitude = data.getProperties().getLat();
                    double longitude = data.getProperties().getLon();
                    city = findLocation(latitude, longitude);
                    if(Objects.equals(city[0], "") && Objects.equals(city[1], "")){
                        city[1] = data.getProperties().getFlynnRegion();
                    }
                    if (data.getProperties().getFlynnRegion() != null /* data.getProperties().getFlynnRegion()!= null*/) {
                        date = formatTime(data.getProperties().getTime(), "AFAD");
                        itemList.add(new MyItem(data.getProperties().getMag(),
                                city[0],
                                city[1], date,
                                String.valueOf(data.getProperties().getDepth()),
                                data.getProperties().getLat(), data.getProperties().getLon()));
                    }
                }
                adapter = new MyAdapter(requireContext(), itemList);
                recyclerView.setAdapter(adapter);
                startDefaultFilterIndex();
            }
        });
    }

    public void setKandilliLiveData() {
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
                    itemList.add(new MyItem(result.getMag(), district, province, date, String.valueOf(result.getDepth()), result.getGeojson().getCoordinates()[1], result.getGeojson().getCoordinates()[0]));
                }
                /*adapter = new MyAdapter(requireContext(), itemList);
                recyclerView.setAdapter(adapter);*/
                startDefaultFilterIndex();
            }
        });
    }

    @Override
    public void onTimeSelected(int hours) {
        boolean state = false;
        for (boolean button : buttonClicked) {
            if (button) {
                state = button;
                break;
            }
        }
        if (filteredList.size() == 0 && !state) {
            filterItemsByHour(hours, itemList);
        } else {
            filterItemsByHour(hours, filteredList);
        }
    }

    private List<MyItem> filterItemsByHour(int time, List<MyItem> itemList) {
        filteredFinalList = new ArrayList<>();
        setSelectedTime(String.valueOf(time));

        for (MyItem item : itemList) {
            String itemTime = item.getHours(); // Varsayalım ki MyItem sınıfında zamanı temsil eden bir metot var
            String[] timeArray = itemTime.split(" ");
            int hour = Integer.parseInt(timeArray[0]);
            String state = timeArray[1];

            if ((Objects.equals(state, "dakika") || (hour <= 1 && Objects.equals(state, "saat") && time == 1))) {
                filteredFinalList.add(item);
            } else if ((hour <= 12 && Objects.equals(state, "saat")) && time == 12) {
                filteredFinalList.add(item);
            } else if ((hour <= 24 && Objects.equals(state, "saat")) && time == 24) {
                filteredFinalList.add(item);
            }
        }
        adapter = new MyAdapter(getContext(), filteredFinalList);
        recyclerView.setAdapter(adapter);
        return filteredFinalList;
    }

    public void startDefaultFilterIndex() {
        if (!defaultFilterIndex.equals("4")) {
            handleButtonClick(Integer.parseInt(defaultFilterIndex));
        } else {
            filterItemsByHour(Integer.parseInt(selectedTime), itemList);
        }
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public List<MyItem> getItemList() {
        return itemList != null ? itemList : new ArrayList<>();
    }

    public List<MyItem> getFilteredFinalList() {
        return filteredFinalList != null ? filteredFinalList : new ArrayList<>();
    }

    private String[] findLocation(double latitude ,double longitude){
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());

        try {
            // Enlem ve boylam bilgilerini adres bilgilerine çevirin
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String fullAddress = address.getAddressLine(0);

                String regex = "(.*), (\\d{5}) ([^/]+)/([^,]+)";
                String specialCaseRegex = "(.*), (\\d{5}) ([^/]+)/([^/]+)/([^,]+)";
                String regex2 = "(\\d{5}) ([^/]+)/([^/]+)/([^,]+), Türkiye";
                String specialCaseRegex2 = "(\\d{5}) ([^/]+)/([^,]+), Türkiye";
                String province = "";
                String district = "";

                Pattern pattern = Pattern.compile(regex);
                Pattern specialCasePattern = Pattern.compile(specialCaseRegex);
                Pattern pattern2 = Pattern.compile(regex2);
                Pattern specialCasePattern2 = Pattern.compile(specialCaseRegex2);

                Matcher matcher = pattern.matcher(fullAddress);
                Matcher specialCaseMatcher = specialCasePattern.matcher(fullAddress);
                Matcher matcher2 = pattern2.matcher(fullAddress);
                Matcher specialCaseMatcher2 = specialCasePattern2.matcher(fullAddress);


                if (specialCaseMatcher.find()) {
                    String subDistrict = specialCaseMatcher.group(3);
                    district = specialCaseMatcher.group(4);
                    province = specialCaseMatcher.group(5);
                    district = subDistrict + "/" + district;
                } else if (matcher.find()) {
                    province = matcher.group(4);
                    district = matcher.group(3);
                }
                else if (matcher2.find()) {
                    province = matcher2.group(4);
                    district = matcher2.group(2) + "/" + matcher2.group(3);
                } else if (specialCaseMatcher2.find()) {
                    province = specialCaseMatcher2.group(3);
                    district = specialCaseMatcher2.group(2);
                }
                if(district=="" || province == ""){
                    System.out.println(fullAddress);
                }
                return new String[]{district, province};
            } else {
                System.out.println("No address found for the provided coordinates.");
                return new String[]{"", ""};
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{"No address found", ""};
        }
    }




}

