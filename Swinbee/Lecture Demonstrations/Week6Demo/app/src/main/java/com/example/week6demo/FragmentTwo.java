package com.example.week6demo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentTwo extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private TextView textView2;

    public FragmentTwo() {
        // Required empty public constructor
    }

    public static FragmentTwo newInstance(String param1) {
        FragmentTwo fragment = new FragmentTwo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two, container, false);
        textView2 = view.findViewById(R.id.textView2);
        textView2.setText(mParam1);
        return view;
    }

}
