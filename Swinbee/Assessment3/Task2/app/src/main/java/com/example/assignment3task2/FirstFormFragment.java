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

import java.util.ArrayList;
import java.util.HashMap;

public class FirstFormFragment extends Fragment {

    private static double FIBER_AMOUNT = 0.5;
    private String first_order_details = "with no fiber";
    private ArrayList<String> fiber_items = new ArrayList<>();
    private String protein_item;
    private boolean protein_is_selected = false;
    private int fiber_box_counter = 0;
    private boolean fiber_is_three = false;
    private boolean first_word_is_there = false;

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
                protein_item = String.valueOf(btn.getText());
                first_order_details = removeWord(first_order_details, "beef ");
                first_order_details = removeWord(first_order_details, "chicken ");
                first_order_details = removeWord(first_order_details, "fish ");
                first_order_details = removeWord(first_order_details, "egg ");
                first_order_details = btn.getText() + " " + first_order_details;
                protein_is_selected = true;
            }
        });
        // Fiber checkboxes button listeners
        createFiberButtonListener(lettuce_btn, "lettuce");
        createFiberButtonListener(tomato_btn, "tomato");
        createFiberButtonListener(pickle_btn, "pickle");
        createFiberButtonListener(onion_btn, "onion");
        // Button listener
        next_fragment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (protein_is_selected){
                    ArrayList<String> selected_items = new ArrayList<>();
                    selected_items.add(protein_item);
                    selected_items.addAll(fiber_items);
                    onButtonPressed(selected_items, first_order_details, fiber_is_three);
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

    public void onButtonPressed(ArrayList<String> arrayList, String first_order_details, boolean fiber_is_three) {
        if (mListener != null) {
            mListener.onFragmentInteraction(arrayList, first_order_details, fiber_is_three);
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
        void onFragmentInteraction(ArrayList<String> arrayList, String first_order_details, boolean fiber_is_three);
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

    // Function to create fiber checkbox button listener
    public void createFiberButtonListener(final CheckBox btn, final String detail){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn.isChecked()){
                    fiber_items.add(String.valueOf(btn.getText()));
                    first_order_details = removeWord(first_order_details, " no fiber");
                    if (!first_word_is_there){
                        first_order_details = first_order_details + " " + detail;
                        first_word_is_there = true;
                    } else {
                        first_order_details = first_order_details + ", " + detail;
                    }
                    fiber_box_counter++;
                   checkFiberBoxes();
                } else if (!btn.isChecked()){
                    fiber_items.remove(String.valueOf(btn.getText()));
                    if (fiber_box_counter == 1){
                        first_order_details = removeWord(first_order_details, " " + detail);
                        first_word_is_there = false;
                    } else {
                        first_order_details = removeWord(first_order_details, ", " + detail);
                    }
                    fiber_box_counter--;
                    if (fiber_box_counter == 0){
                        first_order_details = first_order_details + " no fiber";
                    }
                    checkFiberBoxes();
                }
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
