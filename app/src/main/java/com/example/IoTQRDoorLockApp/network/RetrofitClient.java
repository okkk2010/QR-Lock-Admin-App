package com.example.IoTQRDoorLockApp.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String LOCAL_URL = "http://10.0.0.2:8080/";
    private static final String HEROKU_URL = "https://qr-door-lock-923b9e990317.herokuapp.com/";
    private static final String BASE_URL = HEROKU_URL;

    private static Retrofit retrofitInstance;

    public static Retrofit getInstance() {
        if(retrofitInstance == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitInstance;
    }
}
