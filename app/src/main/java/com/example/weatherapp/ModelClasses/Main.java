package com.example.weatherapp.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp")
    private double temp;

    @SerializedName("temp_min")
    private double tempMin;

    @SerializedName("temp_max")
    private double tempMax;

    @SerializedName("humidity")
    private int humidity;

    @SerializedName("pressure")
    private int pressure;

    @SerializedName("feels_like")
    private double feelsLike;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Main(double temp, double tempMin, double tempMax, int humidity, int pressure, double feelsLike) {
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = humidity;
        this.pressure = pressure;
        this.feelsLike = feelsLike;
    }
}
