package com.newsnap.services;

import com.newsnap.items.ThreadPost;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Mark on 1/20/2015.
 */
public interface NewsnapService {

    @GET("/")
    @Headers("Accept: application/json")
    public void getThreads(Callback<List<com.newsnap.items.Thread>> callback);

    @GET("/{thread}")
    @Headers("Accept: application/json")
    public void getThread(@Path("thread") String threadId, Callback<List<ThreadPost>> callback);

    @FormUrlEncoded
    @POST("/mobile")
    public void createNewThread(
            @Field("op-name") String name, @Field("op-email") String email,
            @Field("title") String title, @Field("news") String news,
            Callback<Response> responseCallback);

    @FormUrlEncoded
    @POST("/mobile/{id}")
    public void createNewReply(
            @Path("id") String id, @Field("replier-name") String name,
            @Field("replier-email") String email, @Field("reply") String reply,
            Callback<Response> responseCallback);

}
