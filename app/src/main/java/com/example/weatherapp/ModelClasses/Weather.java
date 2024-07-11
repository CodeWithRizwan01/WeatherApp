package com.example.weatherapp.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public Weather(String description) {
        this.description = description;
    }
}
