package com.example.deliveryapp_finalproject_androidmobiledev;

public class Tip {
    private String title;
    private String content;


    public Tip() {}

    public Tip(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
