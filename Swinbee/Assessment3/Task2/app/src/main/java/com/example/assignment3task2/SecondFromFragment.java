package com.example.assignment3task2;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

public class SecondFromFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private double final_price, true_final_price;
    private double fat_price = 0.0, size_multiplier = 0.0;
    private String first_order_details, second_order_details = " top up with", final_order_details;

    private CheckBox cheese_btn, mayo_btn, mustard_btn, bbq_btn;
    private Spinner size_spinner;
    private Button return_btn;
    private TextView price_display, order_details_display;

    private HashMap<String, Double> ingredient_price_2 = new HashMap<>();

    public SecondFromFragment() {
        // Required empty public constructor
    }

    public static SecondFromFragment newInstance(double final_price, String first_order_details) {
        SecondFromFragment fragment = new SecondFromFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_PARAM1, final_price);
        args.putString(ARG_PARAM2, first_order_details);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            final_price = getArguments().getDouble(ARG_PARAM1);
            first_order_details = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_from, container, false);

        // Fat Section
        ingredient_price_2.put("Mustard", 0.70);
        ingredient_price_2.put("Cheese", 1.00);
        ingredient_price_2.put("Mayo", 0.50);
        ingredient_price_2.put("BBQ", 1.00);
        // Size Section
        ingredient_price_2.put("Small", 1.0);
        ingredient_price_2.put("Regular", 1.2);
        ingredient_price_2.put("Large", 1.3);
        ingredient_price_2.put("Gigantic", 1.5);

        price_display = view.findViewById(R.id.order_price);
        order_details_display = view.findViewById(R.id.order_details);

        cheese_btn = view.findViewById(R.id.cheese_btn);
        mayo_btn = view.findViewById(R.id.mayo_btn);
        mustard_btn = view.findViewById(R.id.mustard_btn);
        bbq_btn = view.findViewById(R.id.bbq_btn);

        createFatButtonListener(cheese_btn, ingredient_price_2.get("Cheese") , " ,cheese");
        createFatButtonListener(mayo_btn, ingredient_price_2.get("Mayo"), " ,mayonaise");
        createFatButtonListener(mustard_btn, ingredient_price_2.get("Mustard"), " ,mustard");
        createFatButtonListener(bbq_btn, ingredient_price_2.get("BBQ"), " ,barbeque");

        size_spinner = view.findViewById(R.id.size_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.size_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        size_spinner.setAdapter(adapter);

        size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String sizeValue = (String)adapterView.getItemAtPosition(position);
                size_multiplier = ingredient_price_2.get(sizeValue);
                setPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                size_multiplier = 1.0;
                setPrice();
            }
        });

        return_btn = view.findViewById(R.id.return_btn);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    // Changes price on screen
    public void setPrice() {
        true_final_price = (final_price + fat_price) * size_multiplier;
        final_order_details = first_order_details + second_order_details;
        order_details_display.setText(final_order_details);
        price_display.setText(String.format(Locale.ENGLISH, "RM%.2f", true_final_price));
    }

    // Creates button listener for fat section
    public void createFatButtonListener(final CheckBox btn, final double value, final String detail)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn.isChecked()) {
                    fat_price = fat_price + value;
                    second_order_details = second_order_details + detail;
                } else if (!btn.isChecked()) {
                    fat_price = fat_price - value;
                    second_order_details = removeWord(second_order_details, detail);
                }
                setPrice();
            }
        });
    }

    // Deals with removal of text from end string
    public String removeWord(String string, String text){
        if (string.contains(text)){
            string = string.replaceAll(text, "");
        }
        return string;
    }
}
