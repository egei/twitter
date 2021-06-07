package com.twitter.challenge.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twitter.challenge.data.WeatherData;

public class WeatherRequest {
    public static String Tag = WeatherRequest.class.getSimpleName();
    WeatherListener listener;

    RequestQueue queue = null;
    String baseUrl ="https://twitter-code-challenge.s3.amazonaws.com/";

    public void setQueue(Context context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
    }

    public void setListener(WeatherListener listener) {
        this.listener = listener;
    }

    public void getCurrentWeather() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl+"current.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(Tag, "Response:" + response);
                        Gson gson = new GsonBuilder().create();
                        WeatherData weatherData = gson.fromJson(response, WeatherData.class);
                        Log.d(Tag,weatherData.toString());
                        listener.updateCurrentWeather(weatherData);
//                        String selectedId = manifestResponse.getSelectedId();
//                        myModel.setManifestResponse(manifestResponse);
//                        getImage(selectedId);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MyImageRequest", "Error: " + error.getMessage());
                listener.errorCurrentWeather(error);
                //textView.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

    public void getFutureWeather(int day) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl+"future_" + day + ".json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(Tag, "Response:" + response);
                        Gson gson = new GsonBuilder().create();
                        WeatherData weatherData = gson.fromJson(response, WeatherData.class);
                        Log.d(Tag,weatherData.toString());
                        listener.updateFutureWeather(weatherData, day);
//                        String selectedId = manifestResponse.getSelectedId();
//                        myModel.setManifestResponse(manifestResponse);
//                        getImage(selectedId);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MyImageRequest", "Error: " + error.getMessage());
                listener.errorFutureWeather(error, day);
                //textView.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

}
