package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;


public class MainActivity extends AppCompatActivity{

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.imageGridView);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this);
        gridView.setAdapter(gridViewAdapter);
    }

}
