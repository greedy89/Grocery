package com.senos.seno.grocery.service;

import com.senos.seno.grocery.model.Barang;
import com.senos.seno.grocery.model.ReturnValue;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterfaces {
    @GET("api/grocery/all")
    Call<Barang> getAll();

    @Multipart
    @POST("api/grocery/add")
    Call<ReturnValue>tambahBarang(
            @Part("codebarcode") RequestBody codebarcode,
            @Part("namabarang") RequestBody namabarang,
            @Part("hargabarang") RequestBody hargabarang,
            @Part("hargabeli") RequestBody hargabeli);

    @POST("api/grocery/delete")
    Call<Barang>deleteBarang(@Part("id")String id);

    @GET("apil/grocery/detail")
    Call<Barang>detailBarang(@Query("id")String id);
}
