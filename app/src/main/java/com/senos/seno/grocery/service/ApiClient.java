package com.senos.seno.grocery.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senos.seno.grocery.utility.StringCoverter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        //membuat interceptor
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader("X-Api-Key", "218B41B886DCFC41F90A15A8354080EC").build();
                return chain.proceed(newRequest);
            }
        };
        //membuat client http
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        //membuat gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(String.class, new StringCoverter());
        Gson gson = gsonBuilder.create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.137.1:8080/grocery/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        return retrofit;
    }
}
