package com.example.week_7_demo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentTwo extends Fragment {

    private ImageView imageView;
    private String link = "https://i.kym-cdn.com/entries/icons/original/000/031/443/It's_Always_Sunny_in_Philadelphia_-_Frank_Reynolds_on_the_gun_controversy_-_FULL_SCENE_0-43_screenshot.png";

    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_two, container, false);
        imageView = view.findViewById(R.id.imageView2);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        GetImage getImage = new GetImage(imageView);
        getImage.execute(link);
    }
}
