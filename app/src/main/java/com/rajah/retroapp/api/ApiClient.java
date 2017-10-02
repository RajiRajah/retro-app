package com.rajah.retroapp.api;

import com.rajah.retroapp.BuildConfig;
import com.rajah.retroapp.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rajah on 8/25/2017.
 */

public class ApiClient {

    public static Retrofit retrofit;

    private static OkHttpClient.Builder okHttpClientBuilder;
    private static HttpLoggingInterceptor loggingInterceptor;


    public static Retrofit getApiClient(){
        if (retrofit == null){
//            create instance of Httpclient
            okHttpClientBuilder = new OkHttpClient.Builder();
            loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            if(BuildConfig.DEBUG){
                okHttpClientBuilder.addInterceptor(loggingInterceptor);
            }
//            instance of retrofit
            retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).
                    addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();
        }
        return retrofit;
    }
}
