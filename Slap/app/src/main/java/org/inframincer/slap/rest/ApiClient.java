package org.inframincer.slap.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yoon on 2017. 10. 7..
 */

public class ApiClient {

    public static final String BASE_URL = "http://openapi.seoul.go.kr:8088/";
    private static Retrofit sRetrofit = null;

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}
