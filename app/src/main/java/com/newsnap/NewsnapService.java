package com.newsnap;

import com.google.gson.JsonElement;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by Mark on 1/20/2015.
 */
public interface NewsnapService {

    @GET("/")
    @Headers("Accept: application/json")
    public void getThreads(Callback<List<Thread>> callback);

    @GET("/{thread}")
    @Headers("Accept: application/json")
    public void getThread(@Path("thread") String threadId, Callback<List<ThreadPost>> callback);

}
