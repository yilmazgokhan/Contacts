package com.yilmazgokhan.contact.RetrofitApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    /*For Emulator*/
//    private static final String BASE_URL = "http://10.0.2.2:5000/api/v1/";
    private static final String BASE_URL = "https://contact-keeper-api-v1.herokuapp.com/api/v1/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
