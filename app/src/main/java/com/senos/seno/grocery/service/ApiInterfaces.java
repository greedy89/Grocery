package com.senos.seno.grocery.service;

import com.senos.seno.grocery.model.Data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaces {
    @GET("api/grocery/all")
    Call<Data> getAll();
}
