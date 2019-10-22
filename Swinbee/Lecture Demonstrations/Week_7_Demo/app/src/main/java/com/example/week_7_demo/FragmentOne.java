package com.example.week_7_demo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {
    private ImageView imageView;
    private String link="https://i.kym-cdn.com/photos/images/original/001/474/240/656.jpg";

    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        imageView = view.findViewById(R.id.imageView1);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        GetImage getImage = new GetImage(imageView);
        getImage.execute(link);
    }
}
