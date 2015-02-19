package com.newsnap.endpoint;

import retrofit.Endpoint;

/**
 * Created by Mark on 2/18/2015.
 */
public class NewsnapEndpoint implements Endpoint {

    @Override
    public String getUrl() {
        return "http://newsnap.herokuapp.com";
    }

    @Override
    public String getName() {
        return "Newsnap";
    }
}
