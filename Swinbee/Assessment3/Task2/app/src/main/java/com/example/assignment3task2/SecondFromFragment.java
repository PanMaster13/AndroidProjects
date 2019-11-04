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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class SecondFromFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static double FIBER_AMOUNT = 0.5;

    private double final_price, init_price = 0.0, fat_price = 0.0, size_multiplier = 0.0;
    private String first_order_details, second_order_details = " top up with nothing", final_order_details;
    private boolean fiber_is_three, first_word_is_there = false;
    private int fat_box_counter = 0;

    private CheckBox cheese_btn, mayo_btn, mustard_btn, bbq_btn;
    private Spinner size_spinner;
    private Button return_btn;
    private TextView price_display, order_details_display;

    private ArrayList<String> selected_items;
    private HashMap<String, Double> ingredient_price = new HashMap<>();

    public SecondFromFragment() { } // Required empty public constructor

    public static SecondFromFragment newInstance(ArrayList<String> selected_items, String first_order_details, boolean fiber_is_three) {
        SecondFromFragment fragment = new SecondFromFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, selected_items);
        args.putString(ARG_PARAM2, first_order_details);
        args.putBoolean(ARG_PARAM3, fiber_is_three);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selected_items = getArguments().getStringArrayList(ARG_PARAM1);
            first_order_details = getArguments().getString(ARG_PARAM2);
            fiber_is_three = getArguments().getBoolean(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_from, container, false);
        // Protein Section
        ingredient_price.put("beef", 4.50);
        ingredient_price.put("chicken", 3.00);
        ingredient_price.put("fish", 4.00);
        ingredient_price.put("egg", 2.00);
        // Fiber Section
        ingredient_price.put("lettuce", FIBER_AMOUNT);
        ingredient_price.put("tomato", FIBER_AMOUNT);
        ingredient_price.put("pickle", FIBER_AMOUNT);
        ingredient_price.put("onion", FIBER_AMOUNT);
        // Fat Section
        ingredient_price.put("Mustard", 0.70);
        ingredient_price.put("Cheese", 1.00);
        ingredient_price.put("Mayo", 0.50);
        ingredient_price.put("BBQ", 1.00);
        // Size Section
        ingredient_price.put("Small", 1.0);
        ingredient_price.put("Regular", 1.2);
        ingredient_price.put("Large", 1.3);
        ingredient_price.put("Gigantic", 1.5);
        // TextView to display results of selection
        price_display = view.findViewById(R.id.order_price);
        order_details_display = view.findViewById(R.id.order_details);
        // Fat checkboxes
        cheese_btn = view.findViewById(R.id.cheese_btn);
        mayo_btn = view.findViewById(R.id.mayo_btn);
        mustard_btn = view.findViewById(R.id.mustard_btn);
        bbq_btn = view.findViewById(R.id.bbq_btn);
        // Calculates price from previous fragment
        calculateInitPrice();
        // Fat checkbox onclick listeners
        createFatButtonListener(cheese_btn, ingredient_price.get("Cheese") , "cheese");
        createFatButtonListener(mayo_btn, ingredient_price.get("Mayo"), "mayonaise");
        createFatButtonListener(mustard_btn, ingredient_price.get("Mustard"), "mustard");
        createFatButtonListener(bbq_btn, ingredient_price.get("BBQ"), "barbeque");
        // Size selection spinner
        size_spinner = view.findViewById(R.id.size_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.size_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        size_spinner.setAdapter(adapter);
        size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String sizeValue = (String)adapterView.getItemAtPosition(position);
                size_multiplier = ingredient_price.get(sizeValue);
                setPrice();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                size_multiplier = 1.0;
                setPrice();
            }
        });
        // Button to go to previous fragment
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
        final_price = (init_price + fat_price) * size_multiplier;
        final_order_details = first_order_details + second_order_details;
        order_details_display.setText(final_order_details);
        price_display.setText(String.format(Locale.ENGLISH, "RM%.2f", final_price));
    }

    public void calculateInitPrice(){
        for (int i = 0; i < selected_items.size(); i++){
            init_price = init_price + ingredient_price.get(selected_items.get(i));
        }
        if (fiber_is_three) {
            init_price = init_price - FIBER_AMOUNT;
        }
    }

    // Creates button listener for fat section
    public void createFatButtonListener(final CheckBox btn, final double value, final String detail)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn.isChecked()) {
                    second_order_details = removeWord(second_order_details, " nothing");
                    fat_price = fat_price + value;
                    if (!first_word_is_there){
                        second_order_details = second_order_details + " " + detail;
                        first_word_is_there = true;
                    } else {
                        second_order_details = second_order_details + ", " + detail;
                    }
                    fat_box_counter++;
                } else if (!btn.isChecked()) {
                    fat_price = fat_price - value;
                    if (fat_box_counter == 1){
                        second_order_details = removeWord(second_order_details, " " + detail);
                        first_word_is_there = false;
                    } else {
                        second_order_details = removeWord(second_order_details, ", " + detail);
                    }
                    fat_box_counter--;
                    if (fat_box_counter == 0){
                        second_order_details = second_order_details + " nothing";
                    }
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
