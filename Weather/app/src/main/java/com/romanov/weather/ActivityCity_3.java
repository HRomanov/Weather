package com.romanov.weather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.romanov.weather.adapter.TabsPagerFragmentAdapter;
import com.romanov.weather.adapter.TabsPagerFragmentAdapter_2;

public class ActivityCity_3 extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_city;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault); // используем свой стиль по умолчанию
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        initNavigationView();
        initTabs();

    }
    public void onMyButtonClick(View view)
    {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsPagerFragmentAdapter_2 adapter = new TabsPagerFragmentAdapter_2(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onMyButtonClick_back(View view)
    {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // обработка нажатия иконки поиска
                if (menuItem.getItemId() == R.id.search) {
                    showInputDialog();
                }
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu);
    }


    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Searh city");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Find", new DialogInterface.OnClickListener() {
            // посылаем введенный город в changeCity
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter city", Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    changeCity(input.getText().toString());

                }


            }


        });
        builder.show();
    }

    public void changeCity(String city) {

            finish();
            overridePendingTransition(0, 0);
            Intent intent = new Intent(this, ActivityCity_3.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);

        new City(this).setCity(city);
    }


    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigatoin_open, R.string.view_navigatoin_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                City city = null;
                String city1 = null;
                if (city != null) {
                    city1 = city.getCity();
                } else city1 = "lol";

                switch (menuItem.getItemId()) {
                    case R.id.city_name_1:
                        finish();
                        overridePendingTransition(0, 0);
                        Intent intent = new Intent(ActivityCity_3.this, ActivityCity_1.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;
                    case R.id.city_name_2:
                        finish();
                        overridePendingTransition(0, 0);
                        Intent intent1 = new Intent(ActivityCity_3.this, ActivityCity_2.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        break;
                    case R.id.city_name_3:
                        break;
                    case R.id.city_name_4:
                        finish();
                        overridePendingTransition(0, 0);
                        Intent intent2 = new Intent(ActivityCity_3.this, ActivityCity_4.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);
                        break;
                    case R.id.city_name_5:
                        finish();
                        overridePendingTransition(0, 0);
                        Intent intent3 = new Intent(ActivityCity_3.this, ActivityCity_5.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent3);
                        break;

                }
                return true;
            }
        });
    }

}




