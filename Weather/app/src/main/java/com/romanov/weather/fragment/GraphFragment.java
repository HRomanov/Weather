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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.romanov.weather.City;
import com.romanov.weather.R;
import com.romanov.weather.Weather;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class GraphFragment extends Fragment {
    private static final int LAYOUT = R.layout.fragment_table;

    Typeface weatherFont;

    TextView cityField;
    TextView tempView_01;
    TextView tempView_02;
    TextView tempView_03;
    TextView tempView_04;
    TextView tempView_05;
    TextView tempView_06;
    TextView tempView_07;
    TextView tempView_08;
    TextView currentTemperatureField;
    TextView weatherIcon;
    TextView temp_max;
    TextView description;
    TextView humidity;
    TextView pressure;
    com.jjoe64.graphview.GraphView graphTable;
    Handler handler;

    public GraphFragment() {
        handler = new Handler();
    }


    public static GraphFragment getInstance() {
        Bundle args = new Bundle();
        GraphFragment fragment = new GraphFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(LAYOUT, container, false);

        cityField = (TextView) rootView.findViewById(R.id.city_field);
        tempView_01 = (TextView) rootView.findViewById(R.id.TextView02);
        tempView_02 = (TextView) rootView.findViewById(R.id.textView7);
        tempView_03 = (TextView) rootView.findViewById(R.id.textView10);
        tempView_04 = (TextView) rootView.findViewById(R.id.textView13);
        tempView_05 = (TextView) rootView.findViewById(R.id.textView16);
        tempView_06 = (TextView) rootView.findViewById(R.id.textView19);
        tempView_07 = (TextView) rootView.findViewById(R.id.textView22);
        tempView_08 = (TextView) rootView.findViewById(R.id.textView25);

        graphTable = (com.jjoe64.graphview.GraphView) rootView.findViewById(R.id.graph);


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

            double mass_temp[] = new double[8];



            JSONObject city = json.getJSONObject("city");
            cityField.setText(city.getString("name").toUpperCase(Locale.US) + ", " + city.getString("country"));
            for (int i = 0; i < 8; i++) {
                JSONObject list = json.getJSONArray("list").getJSONObject(i);
                JSONObject main = list.getJSONObject("main");
                mass_temp[i] = main.getDouble("temp");
            }
            GraphView graph = graphTable;

            DataPoint[] lol = {
                    new DataPoint(3, mass_temp[0]),
                    new DataPoint(6, mass_temp[1]),
                    new DataPoint(9, mass_temp[2]),
                    new DataPoint(12, mass_temp[3]),
                    new DataPoint(15, mass_temp[4]),
                    new DataPoint(18, mass_temp[5]),
                    new DataPoint(21, mass_temp[6]),
                    new DataPoint(24, mass_temp[7])

            };
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(lol);
            graph.addSeries(series);

            tempView_01.setText(mass_temp[0] + "°C");
            tempView_02.setText(mass_temp[1] + "°C");
            tempView_03.setText(mass_temp[2] + "°C");
            tempView_04.setText(mass_temp[3] + "°C");
            tempView_05.setText(mass_temp[4] + "°C");
            tempView_06.setText(mass_temp[5] + "°C");
            tempView_07.setText(mass_temp[6] + "°C");
            tempView_08.setText(mass_temp[7] + "°C");

//            description.setText(details.getString("description").toUpperCase(Locale.US));
//            humidity.setText("Humidity: " + main.getString("humidity") + "%");
//            pressure.setText("Pressure: " + main.getString("pressure") + " hPa");
//            temp_max.setText("Temp max: " + main.getDouble("temp_max"));
//
//            currentTemperatureField.setText(String.format("%.2f", main.getDouble("temp")) + " °C");
//
//            DateFormat df = DateFormat.getDateInstance();
//            String updatedOn = df.format(new Date(list.getLong("dt") *1000-10800*1000));
//            updatedField.setText("Date " + updatedOn);
//
//            setWeatherIcon(details.getInt("id"));//, json.getJSONObject("sys").getLong("sunrise") * 1000, json.getJSONObject("sys").getLong("sunset") * 1000);


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
