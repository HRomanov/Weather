package com.romanov.weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.romanov.weather.fragment.ExempleFragment;
import com.romanov.weather.fragment.TodayFragment;

/**
 * Created by Геннадий on 19.04.2016.
 */
public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        tabs = new String[]{
                "Сегодня",
                "Потом",
                "Завтра"
        };
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return TodayFragment.getInstance();
            case 1:
                return ExempleFragment.getInstance();
            case 2:
                return ExempleFragment.getInstance();

        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
