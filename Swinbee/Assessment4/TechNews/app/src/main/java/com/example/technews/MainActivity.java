package com.example.technews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements cyber_fragment.CyberFragmentInteractionListener, ai_fragment.AIFragmentInteractionListener{

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void CyberFragmentInteraction(String url) {
        web_view_fragment webViewFragment = web_view_fragment.newInstance(url);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainLayout, webViewFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void AIFragmentInteraction(String url) {
        web_view_fragment webViewFragment = web_view_fragment.newInstance(url);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainLayout, webViewFragment)
                .addToBackStack(null)
                .commit();
    }
}
