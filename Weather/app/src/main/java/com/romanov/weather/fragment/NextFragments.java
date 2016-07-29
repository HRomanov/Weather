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

import com.romanov.weather.City;
import com.romanov.weather.R;
import com.romanov.weather.Weather;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class NextFragments extends Fragment {
    private static final int LAYOUT = R.layout.fragment_2;

    Typeface weatherFont;

    TextView cityField;
    TextView updatedField;
    TextView currentTemperatureField;
    TextView currentTemperatureField_2;
    TextView currentTemperatureField_3;
    TextView currentTemperatureField_4;
    TextView weatherIcon;
    TextView weatherIcon_2;
    TextView weatherIcon_3;
    TextView weatherIcon_4;
    TextView humidity;
    TextView humidity_2;
    TextView humidity_3;
    TextView humidity_4;
    TextView pressure;
    TextView pressure_2;
    TextView pressure_3;
    TextView pressure_4;
    TextView wind;
    TextView wind_2;
    TextView wind_3;
    TextView wind_4;

    Handler handler;

    public static int time_point;

    public NextFragments() {
        handler = new Handler();
    }


    public static NextFragments getInstance(int get_time_point) {
        Bundle args = new Bundle();
        NextFragments fragment = new NextFragments();
        fragment.setArguments(args);

        time_point = get_time_point;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(LAYOUT, container, false);

        cityField = (TextView) rootView.findViewById(R.id.city_field);
        updatedField = (TextView) rootView.findViewById(R.id.updated_field);

        currentTemperatureField = (TextView) rootView.findViewById(R.id.current_temperature_field);
        currentTemperatureField_2 = (TextView) rootView.findViewById(R.id.current_temperature_field_2);
        currentTemperatureField_3 = (TextView) rootView.findViewById(R.id.current_temperature_field_3);
        currentTemperatureField_4 = (TextView) rootView.findViewById(R.id.current_temperature_field_4);

        weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);
        weatherIcon_2 = (TextView) rootView.findViewById(R.id.weather_icon_2);
        weatherIcon_3 = (TextView) rootView.findViewById(R.id.weather_icon_3);
        weatherIcon_4 = (TextView) rootView.findViewById(R.id.weather_icon_4);

        humidity = (TextView) rootView.findViewById(R.id.humidity);
        humidity_2 = (TextView) rootView.findViewById(R.id.humidity_2);
        humidity_3 = (TextView) rootView.findViewById(R.id.humidity_3);
        humidity_4 = (TextView) rootView.findViewById(R.id.humidity_4);

        pressure = (TextView) rootView.findViewById(R.id.pressure);
        pressure_2 = (TextView) rootView.findViewById(R.id.pressure_2);
        pressure_3 = (TextView) rootView.findViewById(R.id.pressure_3);
        pressure_4 = (TextView) rootView.findViewById(R.id.pressure_4);

        wind = (TextView) rootView.findViewById(R.id.wind);
        wind_2 = (TextView) rootView.findViewById(R.id.wind_2);
        wind_3 = (TextView) rootView.findViewById(R.id.wind_3);
        wind_4 = (TextView) rootView.findViewById(R.id.wind_4);

        weatherIcon.setTypeface(weatherFont);
        weatherIcon_2.setTypeface(weatherFont);
        weatherIcon_3.setTypeface(weatherFont);
        weatherIcon_4.setTypeface(weatherFont);

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
    public void renderWeather(JSONObject json) {
        try {


            int mass_temp[] = new int[4];
            String mass_humidity[] = new String[4];
            String mass_pressure[] = new String[4];
            String mass_wind[] = new String[4];
            int mass_icon[] = new int[4];

            JSONObject city = json.getJSONObject("city");
            cityField.setText(city.getString("name").toUpperCase(Locale.US) + ", " + city.getString("country"));

            JSONObject list1 = json.getJSONArray("list").getJSONObject(time_point); // изменяеться
            int j = 0;

            for (int i = 0; i < 8; i++) {
                JSONObject list = json.getJSONArray("list").getJSONObject(time_point + i);  // изменяеться
                JSONObject details = list.getJSONArray("weather").getJSONObject(0);
                JSONObject main = list.getJSONObject("main");
                JSONObject wind1 = list.getJSONObject("wind");
                mass_temp[j] = main.getInt("temp");
                mass_humidity[j] = main.getString("humidity");
                mass_pressure[j] = main.getString("pressure");
                mass_wind[j] = wind1.getString("speed");
                mass_icon[j] = details.getInt("id");
                i++;
                j++;
            }
            setWeatherIcon(mass_icon[0], weatherIcon);//, json.getJSONObject("sys").getLong("sunrise") * 1000, json.getJSONObject("sys").getLong("sunset") * 1000);
            currentTemperatureField.setText(mass_temp[0] + "");
            humidity.setText(mass_humidity[0] + "");
            pressure.setText(mass_pressure[0] + "");
            wind.setText(mass_wind[0] + "");

            setWeatherIcon(mass_icon[1], weatherIcon_2);
            currentTemperatureField_2.setText(mass_temp[1] + "");
            humidity_2.setText(mass_humidity[1] + "");
            //    pressure_2.setText(mass_pressure[1]+"");
            wind_2.setText(mass_wind[1] + "");

            setWeatherIcon(mass_icon[2], weatherIcon_3);
            currentTemperatureField_3.setText(mass_temp[2] + "");
            humidity_3.setText(mass_humidity[2] + "");
            pressure_3.setText(mass_pressure[2] + "");
            wind_3.setText(mass_wind[2] + "");

            setWeatherIcon(mass_icon[3], weatherIcon_4);
            currentTemperatureField_4.setText(mass_temp[3] + "");
            humidity_4.setText(mass_humidity[3] + "");
            //        pressure_4.setText(mass_pressure[3]+"");
            wind_4.setText(mass_wind[3] + "");

            DateFormat df = DateFormat.getDateInstance();
            String updatedOn = df.format(new Date(list1.getLong("dt") * 1000 - 10800 * 1000));
            updatedField.setText("Date " + updatedOn);


        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId, TextView l) {
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
        l.setText(icon);

    }
}
