package com.twitter.challenge.network;

import com.android.volley.VolleyError;
import com.twitter.challenge.data.WeatherData;

public interface WeatherListener {
    public void updateCurrentWeather(WeatherData weatherData);
    public void errorCurrentWeather(VolleyError error);
    public void updateFutureWeather(WeatherData weatherData, int day);
    public void errorFutureWeather(VolleyError error, int day);
}
