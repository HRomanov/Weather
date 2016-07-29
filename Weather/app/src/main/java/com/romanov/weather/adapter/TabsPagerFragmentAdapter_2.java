package com.romanov.weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.romanov.weather.fragment.GraphFragment;
import com.romanov.weather.fragment.NextFragments;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Геннадий on 19.04.2016.
 */
public class TabsPagerFragmentAdapter_2 extends FragmentStatePagerAdapter {

    private String[] tabs;


    public TabsPagerFragmentAdapter_2(FragmentManager fm) {
        super(fm);

        GregorianCalendar newCal = new GregorianCalendar();
        int day = newCal.get(Calendar.DAY_OF_WEEK);
        String dae_s = Integer.toString(day);
        int real_day = day - 1;
        String tmp;
        String[] days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        for (int i = 0; i < (real_day - 1); ++i) {          //Move the specified number of elements
            tmp = days[0];                                  //Save first element
            days[0] = days[days.length - 1];                //First is last
            for (int j = 0; j < days.length - 1; ++j) {
                String _val = days[j + 1];                  //Save second
                days[j + 1] = tmp;                          //Second is the first
                tmp = _val;                                 // Save the value of the second
            }
        }

        tabs = new String[]{"Today", days[1], days[2], days[3], days[4]};


//
//        if(real_day == 1) {
//            String[] days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
//            tabs = new String[]{"Today",days[1],days[2],days[3],days[4]};
//        }
//        if(real_day == 2) {
//            String[] days = new String[]{"Tue", "Wed", "Thu", "Fri", "Sat", "Sun","Mon"};
//            tabs = new String[]{"Today",days[1],days[2],days[3],days[4]};
//        }
//        if(real_day == 3) {
//            String[] days = new String[]{"Wed", "Thu", "Fri", "Sat", "Sun","Mon","Tue"};
//            tabs = new String[]{"Today",days[1],days[2],days[3],days[4]};
//        }
//        if(real_day == 4) {
//            String[] days = new String[]{"Thu", "Fri", "Sat", "Sun","Mon","Tue", "Wed"};
//            tabs = new String[]{"Today",days[1],days[2],days[3],days[4]};
//        }
//        if(real_day == 5) {
//            String[] days = new String[]{"Fri", "Sat", "Sun","Mon","Tue", "Wed", "Thu",};
//            tabs = new String[]{"Today",days[1],days[2],days[3],days[4]};
//        }
//        if(real_day == 6) {
//            String[] days = new String[]{"Sat", "Sun","Mon","Tue", "Wed", "Thu", "Fri"};
//            tabs = new String[]{"Today",days[1],days[2],days[3],days[4]};
//        }
//        if(real_day == 7) {
//            String[] days = new String[]{"Sun","Mon","Tue", "Wed", "Thu", "Fri", "Sat"};
//            tabs = new String[]{"Today",days[1],days[2],days[3],days[4]};
//        }

    }




    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return GraphFragment.getInstance();
            case 1:
                return NextFragments.getInstance(8);
            case 2:
                return NextFragments.getInstance(16);
            case 3:
                return NextFragments.getInstance(24);
            case 4:
                return NextFragments.getInstance(32);


        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
