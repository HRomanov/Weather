package com.romanov.weather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romanov.weather.R;

/**
 * Created by Геннадий on 19.04.2016.
 */
public class ExempleFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_exemple;

    private View view;

    public static ExempleFragment getInstance() {
        Bundle args = new Bundle();
        ExempleFragment fragment = new ExempleFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }
}
