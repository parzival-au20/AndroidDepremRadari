package com.example.depremradari;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ResultsViewModel extends ViewModel {
    private MutableLiveData<List<KandilliParse.Result>> kandilliData = new MutableLiveData<>();
    private MutableLiveData<List<AfadParse.Data>> afadData = new MutableLiveData<>();
    private MutableLiveData<CsemParse> CsemData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private Retrofit retrofitKandilli;
    private Retrofit retrofitAfad;
    private Retrofit retrofitCSEM;
    private ApiInterface kandilliApi;
    private ApiInterface afadApi;
    private ApiInterface CSEMApi;
    private String baseUrlKandilli = "https://api.orhanaydogdu.com.tr/";
    private String baseUrlAFAD = "https://deprem.afad.gov.tr/";
    private String baseUrlCSEM = "https://www.seismicportal.eu/";
    private Call<KandilliParse> kandilliParseCall;
    private Call<List<AfadParse.Data>> afadParseCall;
    private Call<CsemParse> CSEMParseCall;
    private KandilliParse kandilliParse;
    private CsemParse csemParse;
    private List<AfadParse.Data> afadParse;

    public LiveData<List<KandilliParse.Result>> getResults() {
        return kandilliData;
    }
    public LiveData<List<AfadParse.Data>> getAfadData() { return afadData; }
    public LiveData<CsemParse> getCSEMData() { return CsemData; }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public void setRetrofitSettings(String Resource){
        String currentDate = getCurrentTime();
        String oneDayAgoDate = getOneDayAgo();


        if(Objects.equals(Resource, "Kandilli")){
            retrofitKandilli = new Retrofit.Builder()
                    .baseUrl(baseUrlKandilli)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            kandilliApi = retrofitKandilli.create(ApiInterface.class);
            kandilliParseCall = kandilliApi.getKandilliData();
            fetchDataFromKandilli();
            System.out.println("burası kandilli setRetrosettings");
        }
        else if (Objects.equals(Resource, "AFAD")) {
            retrofitAfad = new Retrofit.Builder()
                    .baseUrl(baseUrlAFAD)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            afadApi = retrofitAfad.create(ApiInterface.class);

            afadParseCall = afadApi.getAfadData(
                    oneDayAgoDate, currentDate, "timedesc", 200);
            fetchDataFromAFAD(afadApi, afadParseCall, afadData);
            System.out.println("burası afad setRetrosettings");
            System.out.println(getAfadData());
        }
        else{
            retrofitCSEM = new Retrofit.Builder()
                    .baseUrl(baseUrlCSEM)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CSEMApi = retrofitCSEM.create(ApiInterface.class);
            CSEMParseCall = CSEMApi.getCSEMData(
                    200,
                    oneDayAgoDate, currentDate,
                    34, 45,
                    24,46,"json"
            );
            //fetchDataFromCSEM();
            fetchDataFromAFAD(CSEMApi, CSEMParseCall, CsemData);
            System.out.println("burası Csem setRetrosettings");
            System.out.println(getCSEMData());
        }

    }

    public <T> void fetchDataFromAFAD(ApiInterface apiInterface, Call<T> callResource, MutableLiveData<T> data) {
        callResource.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if(response.isSuccessful()){
                    T responseData = response.body();
                    if (responseData != null) {
                        data.setValue(responseData);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
    public void fetchDataFromKandilli() {
        kandilliParseCall.enqueue(new Callback<KandilliParse>() {
            @Override
            public void onResponse(@NonNull Call<KandilliParse> call, @NonNull Response<KandilliParse> response) {
                if(response.isSuccessful()){
                    kandilliParse = response.body();
                    if (kandilliParse != null) {
                        kandilliData.setValue(kandilliParse.getResult());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<KandilliParse> call, @NonNull Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
    /*public void fetchDataFromCSEM() {
        CSEMParseCall.enqueue(new Callback<List<CsemParse.Feature>>() {
            @Override
            public void onResponse(@NonNull Call<List<CsemParse.Feature>> call, @NonNull Response<List<CsemParse.Feature>> response) {
                if(response.isSuccessful()){
                    csemParse = (CsemParse) response.body();
                    if (csemParse != null) {
                        CsemData.setValue(csemParse.getFeatures());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CsemParse.Feature>> call, @NonNull Throwable t) {
                System.out.println(t.toString());
            }
        });
    }*/

    public String toISOString(Date formattedDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

// Date nesnesini belirli bir formata çevir

        return dateFormat.format(formattedDate);
    }

    public String getCurrentTime(){
        Calendar currentTime = Calendar.getInstance();
        Date currentDate = currentTime.getTime();

        return toISOString(currentDate);
    }
    public String getOneDayAgo(){
        Calendar oneDayAgo = Calendar.getInstance();
        oneDayAgo.add(Calendar.DAY_OF_MONTH, -1); // Bir gün önceye git
        oneDayAgo.add(Calendar.HOUR_OF_DAY, -3);
        Date oneDayAgoDate = oneDayAgo.getTime(); // 3 saat geriye git

        return toISOString(oneDayAgoDate);
    }

}

