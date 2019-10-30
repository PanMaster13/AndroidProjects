package com.example.task1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class cyber_fragment extends Fragment {
    TextView textView;

    public cyber_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabview_fragment, container, false);
        textView = view.findViewById(R.id.text1);

        textView.setText("This is Cyber fragment");
        return view;
    }

}
