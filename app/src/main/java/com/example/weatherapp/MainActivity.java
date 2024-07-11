package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.weatherapp.ModelClasses.Sys;
import com.example.weatherapp.ModelClasses.Wind;
import com.example.weatherapp.ModelClasses.Main;
import com.example.weatherapp.ModelClasses.Weather;
import com.example.weatherapp.Interface.WeatherInterface;
import com.example.weatherapp.ModelClasses.WeatherResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText etSearch;
    AppCompatButton btnSearch;
    ConstraintLayout constraintLayout;
    LottieAnimationView ivFetchImage;

    TextView tvCity, tvDate,tvCurrentDay, tvTemp, tvDescription, tvFeel, tvWind, tvHumidity, tvPressure, tvMaxTemp, tvMinTemp,tvSunset,tvSunrise;
    String APIKEY = "f0cb7b359ba203f1f2ec3eee363973dd";
    private static final String BASE_URL = "https://api.openweathermap.org/";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.et_search);
        ivFetchImage = findViewById(R.id.fetch_image);
//        animationView = findViewById(R.id.lottie_stars);
        btnSearch = findViewById(R.id.btn_search);
        constraintLayout = findViewById(R.id.constraint);
        tvCity = findViewById(R.id.tv_city);
        tvDate = findViewById(R.id.tv_date);
        tvCurrentDay = findViewById(R.id.tv_day);
        tvTemp = findViewById(R.id.tv_temp);
        tvDescription = findViewById(R.id.tv_mist);
        tvFeel = findViewById(R.id.tv_feel_like);
        tvWind = findViewById(R.id.tv_wind_speed);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvPressure = findViewById(R.id.tv_air_pressure);
        tvMaxTemp = findViewById(R.id.tv_max_temp);
        tvMinTemp = findViewById(R.id.tv_min_temp);
        tvSunset = findViewById(R.id.tv_sunset);
        tvSunrise = findViewById(R.id.tv_sunrise);

        // code for the current date
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String currentDate = format.format(new Date());
        tvDate.setText(currentDate);

        // code for the current day of the week
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        String currentDay = dayFormat.format(new Date());
        tvCurrentDay.setText(currentDay);

        fetchWeather("Sadiqabad");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                if (TextUtils.isEmpty(etSearch.getText().toString())) {
                    etSearch.setError("Please Enter City");
                    return;
                }

                String City_Name = etSearch.getText().toString();
                etSearch.setText("");
                fetchWeather(City_Name);

            }
        });

    }
    void hideKeyBoard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(constraintLayout.getApplicationWindowToken(),0);
    }
    void fetchWeather(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherInterface weatherInterface = retrofit.create(WeatherInterface.class);
        Call<WeatherResponse> call = weatherInterface.getWeatherData(cityName, APIKEY, "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();

                    Main main = weatherResponse.getMain();
                    Wind wind = weatherResponse.getWind();
                    Sys sys = weatherResponse.getSys();

                    // Convert wind speed from m/s to km/h
                    double windSpeedMps = wind.getSpeed();
                    double windSpeedKmh = windSpeedMps * 3.6;
                    int windSpeed = (int) Math.round(windSpeedKmh);

                    tvCity.setText(weatherResponse.getName());


                    // Format sunrise and sunset times
                    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
                    String formattedSunrise = timeFormat.format(new Date(sys.getSunrise() * 1000));
                    String formattedSunset = timeFormat.format(new Date(sys.getSunset() * 1000));

                    // Display sunrise and sunset times
                    tvSunset.setText(formattedSunset);  // Display sunset time
                    tvSunrise.setText(formattedSunrise); // Display sunrise time

                    // Convert double to int for display
                    int temp = (int) Math.round(main.getTemp());
                    int feelsLike = (int) Math.round(main.getFeelsLike());
                    int tempMin = (int) Math.round(main.getTempMin());
                    int tempMax = (int) Math.round(main.getTempMax());

                    tvTemp.setText(String.valueOf(temp) + " \u00B0"+"C");
                    tvMaxTemp.setText(String.valueOf(tempMax) + " \u00B0");
                    tvMinTemp.setText(String.valueOf(tempMin) + " \u00B0");
                    tvFeel.setText(String.valueOf(feelsLike) + " \u00B0");
                    tvWind.setText(String.valueOf(windSpeed) + " km/h");
                    tvHumidity.setText(String.valueOf(main.getHumidity()) + " %");
                    tvPressure.setText(String.valueOf(main.getPressure()) + " hPa");

                    List<Weather> description = weatherResponse.getWeather();
                    if (!description.isEmpty()) {
                        tvDescription.setText(description.get(0).getDescription());

                        String weatherDescription = description.get(0).getDescription().toLowerCase();
                        int animationResource = R.raw.weather;
                        int backgroundResource = R.drawable.bg;


                        // Determine appropriate animation based on weather description
                        if (weatherDescription.contains("clear")) {
                            animationResource = R.raw.clear;
                            backgroundResource = R.drawable.night;
                        } else if (weatherDescription.contains("cloud")) {
                            animationResource = R.raw.cloud;
                            backgroundResource = R.drawable.clear;

                        } else if (weatherDescription.contains("Sunny")) {
                            animationResource = R.raw.clear;
                            backgroundResource = R.drawable.bg;

                        } else if (weatherDescription.contains("rain")) {
                            animationResource = R.raw.rain;
                            backgroundResource = R.drawable.rainy;

                        } else if (weatherDescription.contains("snow")) {
                            animationResource = R.raw.snow;
                            backgroundResource = R.drawable.snow;

                        } else if (weatherDescription.contains("haze")) {
                            animationResource = R.raw.haze;
                            backgroundResource = R.drawable.smoke;

                        } else if (weatherDescription.contains("mist") || weatherDescription.contains("smoke")) {
                            animationResource = R.raw.smoke;
                            backgroundResource = R.drawable.smoke;

                        }
                        // Set the LottieAnimationView with the appropriate animation
                        ivFetchImage.setAnimation(animationResource);
                        ivFetchImage.playAnimation();
                        // Set the background of the ConstraintLayout
                        constraintLayout.setBackgroundResource(backgroundResource);
                    }
                }

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable throwable) {
//                Log.e("WeatherApp", "Network error: " + throwable.getMessage());

            }
        });
    }
}