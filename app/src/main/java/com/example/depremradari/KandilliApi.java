package com.example.depremradari;

import retrofit2.Call;
import retrofit2.http.GET;

public interface KandilliApi {
    @GET("deprem/kandilli/live?limit=20")
    Call<KandilliParse> getResult();
}
