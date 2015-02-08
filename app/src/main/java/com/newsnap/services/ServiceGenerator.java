package com.newsnap.services;

import retrofit.RestAdapter;

/**
 * Created by Mark on 2/5/2015.
 */
public class ServiceGenerator {

    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .build();

        return adapter.create(serviceClass);
    }
}