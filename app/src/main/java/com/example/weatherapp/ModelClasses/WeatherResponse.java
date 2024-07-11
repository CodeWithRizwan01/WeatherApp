package com.example.weatherapp.ModelClasses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("main")
    private Main main;
    @SerializedName("weather")
    private List<Weather> weather;
    @SerializedName("visibility")
    private int visibility;
    @SerializedName("name")
    private String name;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("sys")
    private Sys sys;
    public WeatherResponse(Main main, List<Weather> weather, int visibility, String name, Wind wind, Sys sys) {
        this.main = main;
        this.weather = weather;
        this.visibility = visibility;
        this.name = name;
        this.wind = wind;
        this.sys = sys;
    }
    public Wind getWind() {
        return wind;
    }
    public void setMain(Main main) {
        this.main = main;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public int getVisibility() {
        return visibility;
    }

    public String getName() {
        return name;
    }

    public Sys getSys() {
        return sys;
    }
}
