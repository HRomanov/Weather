package com.romanov.weather;

import android.app.Activity;
import android.content.SharedPreferences;


public class City {

    SharedPreferences prefs;

    public City(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }



    // Если пользователь еще не выбрал город
    // Киев будет стоять по умолчанию
    public String getCity() {
        return prefs.getString("city", "Kiev, UA");
    }

    void setCity(String city) {
        prefs.edit().putString("city", city).commit();
    }

}