package com.example.assignment3task2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FirstFormFragment extends Fragment {
    private String param;
    private OnFragmentInteractionListener mListener;
    private static String PARAM_KEY = "MSG1";
    private TextView textView;
    private Button btn;

    public FirstFormFragment() {
        // Required empty public constructor
    }

    public static FirstFormFragment newInstance(String param1) {
        FirstFormFragment fragment = new FirstFormFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_KEY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param = getArguments().getString(PARAM_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_form, container, false);
        textView = view.findViewById(R.id.text1);
        textView.setText(param);
        btn = view.findViewById(R.id.first_form_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed(param);
            }
        });

        return view;
    }

    public void onButtonPressed(String msg) {
        if (mListener != null) {
            mListener.onFragmentInteraction(msg);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(String msg);
    }
}
