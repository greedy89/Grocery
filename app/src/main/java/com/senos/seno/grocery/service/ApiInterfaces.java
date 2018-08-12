package com.senos.seno.grocery.service;

import com.senos.seno.grocery.model.Data;
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
    Call<Data> getAll();

    @Multipart
    @POST("api/grocery/add")
    Call<ReturnValue>tambahBarang(
            @Part("codebarcode") RequestBody codebarcode,
            @Part("namabarang") RequestBody namabarang,
            @Part("hargajual") RequestBody hargabarang,
            @Part("modal") RequestBody hargabeli,
            @Part("tanggal") RequestBody tanggal,
            @Part("status") RequestBody status);

    @Multipart
    @POST("api/grocery/update")
    Call<ReturnValue>updateBarang(
            @Part("id") RequestBody id,
            @Part("codebarcode") RequestBody codebarcode,
            @Part("namabarang") RequestBody namabarang,
            @Part("hargajual") RequestBody hargabarang,
            @Part("modal") RequestBody hargabeli,
            @Part("tanggal") RequestBody tanggal,
            @Part("status") RequestBody status);

    @Multipart
    @POST("api/grocery/delete")
    Call<ReturnValue>deleteBarang(@Part("id")RequestBody id);

    @GET("apil/grocery/detail")
    Call<Data>detailBarang(@Query("id")String id);
}
