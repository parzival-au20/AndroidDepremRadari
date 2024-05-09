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

import java.util.List;

public class ResultsViewModel extends ViewModel {
    private MutableLiveData<String> selectedResource = new MutableLiveData<>();
    private List<KandilliParse.Result> depremDataList;
    private MutableLiveData<String> selectedTime = new MutableLiveData<>();
    private MutableLiveData<List<KandilliParse.Result>> results = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private Retrofit retrofit;
    private KandilliApi kandilliApi;
    private String baseUrl = "https://api.orhanaydogdu.com.tr/";
    private Call<KandilliParse> kandilliParseCall;
    private KandilliParse kandilliParse;

    public void setDepremDataList(List<KandilliParse.Result> depremDataList) {
        this.depremDataList = depremDataList;
        System.out.println("ResultViewModel");
        System.out.println(depremDataList);
    }

    public List<KandilliParse.Result> getDepremDataList() {
        return depremDataList;
    }
    public LiveData<String> getSelectedResource() {
        return selectedResource;
    }

    public LiveData<String> getSelectedTime() {
        return selectedTime;
    }

    public LiveData<List<KandilliParse.Result>> getResults() {
        return results;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public void setSelectedResource(String resource) {
        selectedResource.setValue(resource);
    }

    public void setSelectedTime(String time) {
        selectedTime.setValue(time);
    }

    private void setRetrofitSettings(){
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
                        setDepremDataList(kandilliParse.getResult());
                        results.setValue(kandilliParse.getResult());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<KandilliParse> call, @NonNull Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
 /*   public void searchApi(String resource, String time) {
        // Veri çekme işlemleri burada gerçekleştirilir
        // Sonuçlar ve diğer durumlar LiveData'ya atanır
        ApiManager apiManager = ApiManager.getInstance(); // ApiManager sınıfından bir örnek alınır
        loading.setValue(true); // Veri çekme işlemi başladı, loading durumu true olarak ayarlandı

        Call<List<DepremDataKandilli>> call = apiManager.getDepremData(resource, time); // Retrofit çağrısı oluşturulur
        call.enqueue(new Callback<List<DepremDataKandilli>>() {
            @Override
            public void onResponse(Call<List<DepremDataKandilli>> call, Response<List<DepremDataKandilli>> response) {
                if (response.isSuccessful()) {
                    List<DepremDataKandilli> dataList = response.body();
                    results.setValue(dataList); // Veri başarıyla alındı, results MutableLiveData'ya atanıyor
                    loading.setValue(false); // Veri çekme işlemi tamamlandı, loading durumu false olarak ayarlandı
                } else {
                    errorMessage.setValue("Veri çekme işlemi başarısız oldu."); // API'den başarısız bir yanıt alındı, hata mesajı errorMessage MutableLiveData'ya atanıyor
                    loading.setValue(false); // Veri çekme işlemi tamamlandı, loading durumu false olarak ayarlandı
                }
            }

            @Override
            public void onFailure(Call<List<DepremDataKandilli>> call, Throwable t) {
                errorMessage.setValue("Veri çekme işlemi başarısız oldu: " + t.getMessage()); // Veri çekme işlemi başarısız oldu, hata mesajı errorMessage MutableLiveData'ya atanıyor
                loading.setValue(false); // Veri çekme işlemi tamamlandı, loading durumu false olarak ayarlandı
            }
        });
    }*/
}

