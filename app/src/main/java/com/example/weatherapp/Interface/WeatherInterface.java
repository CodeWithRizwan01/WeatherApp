package com.example.weatherapp.Interface;

import com.example.weatherapp.ModelClasses.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInterface {
    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeatherData(
            @Query("q") String q,
            @Query("appid") String APIKey,
            @Query("units") String units
    );
}
