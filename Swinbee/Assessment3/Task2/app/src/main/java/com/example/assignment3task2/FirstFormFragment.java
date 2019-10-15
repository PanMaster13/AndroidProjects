package com.example.assignment3task2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class FirstFormFragment extends Fragment {

    private static double FIBER_AMOUNT = 0.5;
    private double final_price = 0.0;
    private String first_order_details = "with nothing";
    private double protein_price = 0.0, fiber_price = 0.0;
    private boolean protein_is_selected = false;
    private int fiber_box_counter = 0;
    private boolean fiber_is_three = false;

    private HashMap<String, Double> ingredient_price = new HashMap<>();

    private OnFragmentInteractionListener mListener;
    private static String PARAM_KEY = "MSG1";

    private Button next_fragment_btn;
    private RadioGroup protein_group;
    private CheckBox lettuce_btn, tomato_btn, pickle_btn, onion_btn;

    public FirstFormFragment() {} // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            final_price = getArguments().getDouble(PARAM_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_first_form, container, false);
        // Protein Section
        protein_group = view.findViewById(R.id.protein_group);
        // Fiber Section
        lettuce_btn = view.findViewById(R.id.lettuce_btn);
        tomato_btn = view.findViewById(R.id.tomato_btn);
        pickle_btn = view.findViewById(R.id.pickle_btn);
        onion_btn = view.findViewById(R.id.onion_btn);
        // Button to next fragment
        next_fragment_btn = view.findViewById(R.id.first_form_btn);
        // Inserting values
        ingredient_price.put("beef", 4.50);
        ingredient_price.put("chicken", 3.00);
        ingredient_price.put("fish", 4.00);
        ingredient_price.put("egg", 2.00);
        ingredient_price.put("fiber", FIBER_AMOUNT);
        // Protein radio group onclick listener
        protein_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton btn = view.findViewById(i);
                protein_price = ingredient_price.get(btn.getText());
                first_order_details = removeWord(first_order_details, "beef ");
                first_order_details = removeWord(first_order_details, "chicken ");
                first_order_details = removeWord(first_order_details, "fish ");
                first_order_details = removeWord(first_order_details, "egg ");
                first_order_details = btn.getText() + " " + first_order_details;
                setPrice();
                protein_is_selected = true;
            }
        });
        // Fiber checkboxes button listeners
        createFiberButtonListener(lettuce_btn, ingredient_price.get("fiber"), " ,lettuce");
        createFiberButtonListener(tomato_btn, ingredient_price.get("fiber"), " ,tomato");
        createFiberButtonListener(pickle_btn, ingredient_price.get("fiber"), " ,pickle");
        createFiberButtonListener(onion_btn, ingredient_price.get("fiber"), " ,onion");
        // Button listener
        next_fragment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (protein_is_selected){
                    onButtonPressed(final_price, first_order_details);
                } else if (!protein_is_selected){
                    Toast.makeText(getContext(), "A selection from the protein section must be selected!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkFiberBoxes(); // Ensures that the appropriate checkboxes are enabled/disabled when user goes back to this page
    }

    public void onButtonPressed(double final_price, String first_order_details) {
        if (mListener != null) {
            mListener.onFragmentInteraction(final_price, first_order_details);
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

        void onFragmentInteraction(double final_price, String first_order_details);
    }

    // Sets fiber price based on discount and disables appropriate checkbox
    public void checkFiberLimit() {
        if (fiber_box_counter == 3) {
            fiber_price = fiber_price - 0.5;
            if (!lettuce_btn.isChecked()) {
                lettuce_btn.setEnabled(false);
            } else if(!tomato_btn.isChecked()) {
                tomato_btn.setEnabled(false);
            } else if(!pickle_btn.isChecked()) {
                pickle_btn.setEnabled(false);
            } else if(!onion_btn.isChecked()) {
                onion_btn.setEnabled(false);
            }
            fiber_is_three = true;
        } else if (fiber_is_three) {
            fiber_price = fiber_price + 0.5;
            lettuce_btn.setEnabled(true);
            tomato_btn.setEnabled(true);
            pickle_btn.setEnabled(true);
            onion_btn.setEnabled(true);
            fiber_is_three = false;
        }
    }

    // Ensures that the appropriate checkboxes are enabled/disabled when user goes back to this page
    public void checkFiberBoxes(){
        if (fiber_box_counter == 3) {
            if (!lettuce_btn.isChecked()) {
                lettuce_btn.setEnabled(false);
            } else if(!tomato_btn.isChecked()) {
                tomato_btn.setEnabled(false);
            } else if(!pickle_btn.isChecked()) {
                pickle_btn.setEnabled(false);
            } else if(!onion_btn.isChecked()) {
                onion_btn.setEnabled(false);
            }
            fiber_is_three = true;
        } else if (fiber_is_three) {
            lettuce_btn.setEnabled(true);
            tomato_btn.setEnabled(true);
            pickle_btn.setEnabled(true);
            onion_btn.setEnabled(true);
            fiber_is_three = false;
        }
    }

    // Changes price on screen
    public void setPrice() {
        final_price = protein_price + fiber_price;
    }

    // Function to create fiber checkbox button listener
    public void createFiberButtonListener(final CheckBox btn, final double value, final String detail){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn.isChecked()){
                    fiber_price = fiber_price + value;
                    first_order_details = removeWord(first_order_details, " nothing");
                    first_order_details = first_order_details + detail;
                    fiber_box_counter++;
                    checkFiberLimit();
                } else if (!btn.isChecked()){
                    fiber_price = fiber_price - value;
                    first_order_details = removeWord(first_order_details, detail);
                    fiber_box_counter--;
                    if (fiber_box_counter == 0){
                        first_order_details = first_order_details + " nothing";
                    }
                    checkFiberLimit();
                }
                setPrice();
            }
        });
    }

    // Deals with removal of text
    public String removeWord(String string, String text){
        if (string.contains(text)){
            string = string.replaceAll(text, "");
        }
        return string;
    }
}
