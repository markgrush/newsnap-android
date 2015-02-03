package com.newsnap;

/**
 * Created by Mark on 1/24/2015.
 */
public class Thread {

    private String threadId;
    private String createdAt;
    private String title;
    private String name;
    private String email;

    public Thread(String threadId, String createdAt, String title, String name, String email) {

        this.threadId = threadId;
        this.createdAt = createdAt;
        this.title = title;
        this.name = name;
        this.email = email;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
