package com.romanov.weather.fragment;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.romanov.weather.ActivityTable;
import com.romanov.weather.City;
import com.romanov.weather.R;
import com.romanov.weather.Weather;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TodayFragment extends Fragment {
    private static final int LAYOUT = R.layout.fragment_1;

        Typeface weatherFont;
        TextView cityField;
        TextView updatedField;
        TextView currentTemperatureField;
        TextView weatherIcon;
        TextView description;
        TextView humidity;
        TextView pressure;
        Handler handler;

    private double temp;

    public TodayFragment() {
        handler = new Handler();
    }


    public static TodayFragment getInstance() {
        Bundle args = new Bundle();
        TodayFragment fragment = new TodayFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(LAYOUT, container, false);

        cityField = (TextView) rootView.findViewById(R.id.city_field);
        updatedField = (TextView) rootView.findViewById(R.id.updated_field);
        currentTemperatureField = (TextView) rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);

        description = (TextView) rootView.findViewById(R.id.description);
        humidity = (TextView) rootView.findViewById(R.id.humidity);
        pressure = (TextView) rootView.findViewById(R.id.pressure);

        weatherIcon.setTypeface(weatherFont);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "weather.ttf");
        updateWeatherData(new City(getActivity()).getCity());

    }

    public void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = Weather.getJSON(getActivity(), city);
                if (json == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), getActivity().getString(R.string.place_not_found), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    @SuppressLint("SetTextI18n")
    private void renderWeather(JSONObject json) {
        try {
            JSONObject city = json.getJSONObject("city");
            cityField.setText(city.getString("name").toUpperCase(Locale.US) + ", " + city.getString("country"));

            JSONObject list = json.getJSONArray("list").getJSONObject(0);
            JSONObject details = list.getJSONArray("weather").getJSONObject(0);
            JSONObject main = list.getJSONObject("main");


            description.setText(details.getString("description").toUpperCase(Locale.US));
            humidity.setText("Humidity: " + main.getString("humidity") + "%");
            pressure.setText("Pressure: " + main.getString("pressure") + " hPa");


            currentTemperatureField.setText(String.format("%.2f", main.getDouble("temp")) + " °C");
            temp = main.getDouble("temp");


            DateFormat df = DateFormat.getDateInstance();
            String updatedOn = df.format(new Date(list.getLong("dt") *1000-10800*1000));
            updatedField.setText("Date " + updatedOn);

            setWeatherIcon(details.getInt("id"));//, json.getJSONObject("sys").getLong("sunrise") * 1000, json.getJSONObject("sys").getLong("sunset") * 1000);





        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
//            long currentTime = new Date().getTime();
//            if (currentTime >= sunrise && currentTime < sunset) {
            icon = getActivity().getString(R.string.weather_sunny);
//            } else {
//                icon = getActivity().getString(R.string.weather_clear_night);
//            }
        } else {
            switch (id) {
                case 2:
                    icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }


//
//    public void changeCity(String city){
//        updateWeatherData(city);
//    }


}
