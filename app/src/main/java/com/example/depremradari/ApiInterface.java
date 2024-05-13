package com.example.depremradari;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("deprem/kandilli/live?limit=200")
    Call<KandilliParse> getKandilliData();

    @GET("apiv2/event/filter")
    Call<List<AfadParse.Data>> getAfadData(
            @Query("start") String startTime,
            @Query("end") String endTime,
            @Query("orderBy") String orderBy,
            @Query("limit") int limit
    );
}
