package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements cyber_fragment.CyberFragmentInteractionListener, ai_fragment.AIFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter
                (getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);
    }

    @Override
    public void CyberFragmentInteraction(String url) {
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void AIFragmentInteraction(String url) {
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }
}
