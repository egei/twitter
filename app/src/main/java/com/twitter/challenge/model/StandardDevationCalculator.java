package com.twitter.challenge.model;

import com.twitter.challenge.data.WeatherData;
import com.twitter.challenge.network.WeatherListener;

import java.util.HashMap;

public class StandardDevationCalculator {
    public static float calculateStandDev(HashMap<Integer,WeatherData> weatherInfo) {
        int numberOfValues = 0;
        double average = 0.0;
        double stdDev = 0.0;
        for (WeatherData weatherData:weatherInfo.values()) {
            average+= weatherData.getWeather().getTemp();
            numberOfValues++;
        }
        average /= numberOfValues;
        for (WeatherData weatherData:weatherInfo.values()) {
            stdDev += (weatherData.getWeather().getTemp() - average)*
                    (weatherData.getWeather().getTemp() - average);
        }
        if (numberOfValues > 1) {
            stdDev /= numberOfValues - 1;
        } else {
            stdDev = 0;
        }
        return (float)stdDev;
    }
}
