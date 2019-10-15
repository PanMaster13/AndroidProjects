package com.example.assignment3task2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FirstFormFragment.OnFragmentInteractionListener {

    private ConstraintLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_layout = findViewById(R.id.main_layout);
        main_layout.removeAllViews();
        FirstFormFragment formFragment1 = new FirstFormFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.main_layout, formFragment1)
                .commit();
    }

    @Override
    public void onFragmentInteraction(double final_price, String first_order_details) {
        SecondFromFragment secondFromFragment = SecondFromFragment.newInstance(final_price, first_order_details);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout, secondFromFragment)
                .addToBackStack(null)
                .commit();
    }
}
