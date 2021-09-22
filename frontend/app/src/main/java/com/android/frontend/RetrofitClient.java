package com.android.frontend;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//    http://localhost:3000/api/users

    String BASE_URL = "http://3.38.88.166:3000";    //"http://192.168.0.9:3000"; // 서버 url
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RetrofitInterface server = retrofit.create(RetrofitInterface.class);

}
