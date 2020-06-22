package com.example.paragonapp;

public class PhotoUpload {
    private String weeklyName;
    private String weeklyImageUrl;


    public PhotoUpload() {
        // empty constructor needed
    }

    public PhotoUpload(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        weeklyImageUrl = imageUrl;
        weeklyName = name;
    }

    public String getWeeklyName() {
        return weeklyName;
    }

    public void setWeeklyName(String name) {
        weeklyName = name;
    }

    public String getWeeklyImageUrl() {
        return weeklyImageUrl;
    }

    public void setWeeklyImageUrl(String imageUrl) {
        weeklyImageUrl = imageUrl;

    }
}