package com.twitter.challenge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.twitter.challenge.data.WeatherData;
import com.twitter.challenge.model.StandardDevationCalculator;
import com.twitter.challenge.network.WeatherListener;
import com.twitter.challenge.network.WeatherRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements WeatherListener {
    WeatherRequest weatherRequest;
    WeatherData currentWeather;
    HashMap<Integer,WeatherData> futureWeather = new HashMap();
    int numberOfFutureDays = 0;
    int errorCount = 0;
    private TextView tempature;
    private TextView windSpeed;
    Button stdDevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numberOfFutureDays = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherRequest = new WeatherRequest();
        weatherRequest.setQueue(this);
        weatherRequest.getCurrentWeather();
        weatherRequest.setListener(this);

        for (int i = 1; i <= 5; i++) {
            weatherRequest.getFutureWeather(i);
        }

        stdDevButton = (Button)findViewById(R.id.stddevButton) ;

        stdDevButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (numberOfFutureDays > 3) {
                    StandardDevationCalculator.calculateStandDev(futureWeather);
                }
            }
        });

        tempature = (TextView) findViewById(R.id.temperature);
        tempature.setText(getString(R.string.temperature, 34f, TemperatureConverter.celsiusToFahrenheit(34)));
        windSpeed = (TextView) findViewById(R.id.wind);
    }

    @Override
    public void updateCurrentWeather(WeatherData weatherData) {
        currentWeather = weatherData;
        float temp = (float) weatherData.getWeather().getTemp();
        tempature.setText(getString(R.string.temperature, temp,
                TemperatureConverter.celsiusToFahrenheit(temp)));
        windSpeed.setText(getString(R.string.wind, (float) currentWeather.getWind().speed));
    }

    @Override
    public void errorCurrentWeather(VolleyError error) {
        errorCount++;
        Toast.makeText(this, R.string.errorCurrent, Toast.LENGTH_LONG).show();
        if (errorCount < 5) {
            weatherRequest.getCurrentWeather();
        }

    }

    @Override
    public void updateFutureWeather(WeatherData weatherData, int day) {
        numberOfFutureDays++;
        futureWeather.put(day, weatherData);
    }

    @Override
    public void errorFutureWeather(VolleyError error, int day) {
        errorCount++;
        Toast.makeText(this, R.string.errorCurrent, Toast.LENGTH_LONG).show();
        if (errorCount < 5) {
            weatherRequest.getFutureWeather(day);
        }

    }
}
