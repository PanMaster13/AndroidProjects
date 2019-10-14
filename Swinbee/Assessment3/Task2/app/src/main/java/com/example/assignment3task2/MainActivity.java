package com.example.assignment3task2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements FirstFormFragment.OnFragmentInteractionListener {

    private ConstraintLayout main_layout;
    private Button main_btn;
    private String msg = "Pass the message: you gae";
    private static String KEY = "MSG1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_layout = findViewById(R.id.main_layout);
        main_btn = findViewById(R.id.main_btn);
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_layout.removeAllViews();
                Bundle bundle = new Bundle();
                bundle.putString(KEY, msg);
                FirstFormFragment formFragment1 = new FirstFormFragment();
                formFragment1.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.main_layout, formFragment1)
                        .commit();
            }
        });
    }

    @Override
    public void onFragmentInteraction(String msg) {
        SecondFromFragment secondFromFragment = SecondFromFragment.newInstance(msg);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout, secondFromFragment)
                .addToBackStack(null)
                .commit();
    }
}
