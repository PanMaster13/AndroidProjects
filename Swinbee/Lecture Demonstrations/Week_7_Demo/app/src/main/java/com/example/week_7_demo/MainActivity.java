package com.example.week_7_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter
                (getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
    }
}
