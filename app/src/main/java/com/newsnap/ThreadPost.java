package com.newsnap;

/**
 * Created by Mark on 1/20/2015.
 */
public class ThreadPost {

    private String name;
    private String email;
    private String title;
    private String body;

    public ThreadPost(String name, String email, String title, String body) {
        this.name = name;
        this.email = email;
        this.title = title;
        this.body = body;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
