package com.newsnap.services;

import retrofit.Endpoint;
import retrofit.RestAdapter;

/**
 * Created by Mark on 2/5/2015.
 */
public class ServiceGenerator {

    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, Endpoint endpoint) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        return adapter.create(serviceClass);
    }
}