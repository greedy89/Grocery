package com.senos.seno.grocery.service;

import com.senos.seno.grocery.model.Barang;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaces {
    @GET("api/grocery/all")
    Call<Barang> getAll();
}
