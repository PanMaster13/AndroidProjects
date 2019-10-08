package com.example.week6demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements FragmentOne.OnFragmentInteractionListener {

    private LinearLayout linearLayout;
    private Button button;
    private String msg = "HELLO FRAGMENT 1 FROM MAIN ACTIVITY";
    private static String KEY = "MSG1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linear_layout);
        button = findViewById(R.id.button_switch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.removeAllViews();
                Bundle bundle = new Bundle();
                bundle.putString(KEY, msg);
                FragmentOne fragmentOne = new FragmentOne();
                fragmentOne.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.linear_layout, fragmentOne)
                        .commit();
            }
        });
    }

    @Override
    public void onFragmentInteraction(String msg) {
        FragmentTwo fragmentTwo = FragmentTwo.newInstance(msg);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.linear_layout, fragmentTwo)
                .addToBackStack(null)
                .commit();
    }
}
