package com.example.ecommerceapp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apicontroller {

    static final String url = "http://192.168.43.231/ecomapi/";
    private static Apicontroller clientObject;
    private static Retrofit retrofit;

    Apicontroller() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public static synchronized Apicontroller getInstance() {
        if (clientObject == null) {
            clientObject = new Apicontroller();
        }
        return clientObject;
    }
    public apiset getapi() {
        return retrofit.create(apiset.class);
    }
}