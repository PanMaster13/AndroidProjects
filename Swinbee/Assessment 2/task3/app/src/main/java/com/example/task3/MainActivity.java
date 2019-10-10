package com.example.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Declaring radio group, radio button, checkboxes and buttons
    RadioGroup proteinGroup;
    CheckBox lettuce_btn, tomato_btn, pickle_btn, onion_btn, cheese_btn, mayo_btn, mustard_btn;
    Button order_btn, reset_btn;

    // All fiber ingredients have the same amount
    final double FIBER_AMOUNT = 0.5;
    double proteinPrice = 0.0;
    double fiberPrice = 0.0;
    double fatPrice = 0.0;
    double sizeMultiplier = 0.0;
    double result = 0.0;

    // Applicable for Fiber Section only
    int checkBoxCounter = 0;
    boolean fiberBonusState = false;

    // For Order Checking
    boolean proteinSelectedStatus = false;

    // Contains the price for each ingredient (e.g. beef, lettuce)
    HashMap<String, Double> ingredientPrice = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigns variables to their views
        // Input data into the hash map
        // Set listeners to radio group and checkboxes
        initialisation();

        // Generate Dropdown Selection List
        final Spinner spinner = findViewById(R.id.sizeSelection);
        // Create ArrayAdapter using string array and default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.size_array, android.R.layout.simple_spinner_item);
        // Decides how the array should be displayed (Dropdown for this case)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applies adapter to spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String sizeValue = (String)adapterView.getItemAtPosition(position);
                // String values are compared with .equals built-in function
                if (sizeValue.equals("Small")) {
                    sizeMultiplier = ingredientPrice.get("Small");
                    setPrice();
                } else if(sizeValue.equals("Regular")) {
                    sizeMultiplier = ingredientPrice.get("Regular");
                    setPrice();
                } else if(sizeValue.equals("Large")) {
                    sizeMultiplier = ingredientPrice.get("Large");
                    setPrice();
                } else if(sizeValue.equals("Gigantic")) {
                    sizeMultiplier = ingredientPrice.get("Gigantic");
                    setPrice();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sizeMultiplier = 1.0;
                setPrice();
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clears all button selections and set spinner to default value
                proteinGroup.clearCheck(); // This will trigger the special condition in the radio group listener
                lettuce_btn.setChecked(false);
                tomato_btn.setChecked(false);
                pickle_btn.setChecked(false);
                onion_btn.setChecked(false);
                cheese_btn.setChecked(false);
                mayo_btn.setChecked(false);
                mustard_btn.setChecked(false);
                spinner.setSelection(0);
                // Set fiber checkbox to default
                fiberBonusState = true;
                checkBoxCounter = 0;
                checkFiberLimit();
                fiberBonusState = false;
                // Set values to their defaults
                fiberPrice = 0.0;
                fatPrice = 0.0;
                // Recalculate default
                setPrice();
            }
        });

        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                CharSequence text;

                if (!proteinSelectedStatus) {
                    text = "You must select a main filling from the Protein Section.";
                } else {
                    text = String.format(Locale.ENGLISH, "Amount to be paid: RM%.2f", result);
                }
                // Toast is a simple UI feedback that works similarly to Alert Boxes in JavaScript
                Toast msg = Toast.makeText(context, text, duration);
                msg.show();
            }
        });
    }

    // Initialises the views variables to their views
    // Input data into the hash map
    // Create button listeners for radio and checkboxes
    public void initialisation()
    {
        // Declaring View variables
        // Radio group for protein section
        proteinGroup = findViewById(R.id.radioGroup);
        // Fiber checkboxes
        lettuce_btn = findViewById(R.id.checkBox1);
        tomato_btn = findViewById(R.id.checkBox2);
        pickle_btn = findViewById(R.id.checkBox3);
        onion_btn = findViewById(R.id.checkBox4);
        // Fat checkboxes
        cheese_btn = findViewById(R.id.checkBox5);
        mayo_btn = findViewById(R.id.checkBox6);
        mustard_btn = findViewById(R.id.checkBox7);
        // Order and reset button
        order_btn = findViewById(R.id.order);
        reset_btn = findViewById(R.id.reset);
        // Setting ingredients and their respective prices
        // Protein Section
        ingredientPrice.put("Beef", 4.50);
        ingredientPrice.put("Chicken", 3.00);
        ingredientPrice.put("Fish", 4.00);
        ingredientPrice.put("Egg", 2.00);
        // Fiber Section
        ingredientPrice.put("Lettuce", FIBER_AMOUNT);
        ingredientPrice.put("Tomato", FIBER_AMOUNT);
        ingredientPrice.put("Pickle", FIBER_AMOUNT);
        ingredientPrice.put("Onion", FIBER_AMOUNT);
        // Fat Section
        ingredientPrice.put("Mustard", 0.70);
        ingredientPrice.put("Cheese", 1.00);
        ingredientPrice.put("Mayo", 0.50);
        // Size Section
        ingredientPrice.put("Small", 1.0);
        ingredientPrice.put("Regular", 1.2);
        ingredientPrice.put("Large", 1.3);
        ingredientPrice.put("Gigantic", 1.5);
        // Creating on click listeners
        // ----------------------Protein Section-------------------------------
        proteinGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // This will occur when reset button is pressed
                if (i == -1) {
                    proteinPrice = 0.0;
                    proteinSelectedStatus = false;
                } else{ // This will occur on normal circumstances
                    RadioButton btn = findViewById(i);
                    proteinPrice = ingredientPrice.get(btn.getText());
                    setPrice();
                    proteinSelectedStatus = true;
                }
            }
        });
        // ---------------------Fiber Section--------------------------------
        createFiberButtonListener(lettuce_btn, ingredientPrice.get("Lettuce"));
        createFiberButtonListener(tomato_btn, ingredientPrice.get("Tomato"));
        createFiberButtonListener(pickle_btn, ingredientPrice.get("Pickle"));
        createFiberButtonListener(onion_btn, ingredientPrice.get("Onion"));
        // ---------------------------Fat Section-------------------------------
        createFatButtonListener(mustard_btn, ingredientPrice.get("Mustard"));
        createFatButtonListener(cheese_btn, ingredientPrice.get("Cheese"));
        createFatButtonListener(mayo_btn, ingredientPrice.get("Mayo"));
    }

    // Sets fiber price based on discount and disables appropriate checkbox
    public void checkFiberLimit() {
        if (checkBoxCounter == 3) {
            fiberPrice = fiberPrice - 0.5;
            if (!lettuce_btn.isChecked()) {
                lettuce_btn.setEnabled(false);
            } else if(!tomato_btn.isChecked()) {
                tomato_btn.setEnabled(false);
            } else if(!pickle_btn.isChecked()) {
                pickle_btn.setEnabled(false);
            } else if(!onion_btn.isChecked()) {
                onion_btn.setEnabled(false);
            }
            fiberBonusState = true;
        } else if (fiberBonusState) {
            fiberPrice = fiberPrice + 0.5;
            lettuce_btn.setEnabled(true);
            tomato_btn.setEnabled(true);
            pickle_btn.setEnabled(true);
            onion_btn.setEnabled(true);
            fiberBonusState = false;
        }
    }

    // Changes price on screen
    public void setPrice() {
        TextView priceText = findViewById(R.id.price);

        result = (proteinPrice + fiberPrice + fatPrice) * sizeMultiplier;
        priceText.setText(String.format(Locale.ENGLISH, "RM%.2f", result));
    }

    // Creates button listener for fiber section
    public void createFiberButtonListener(final CheckBox btn, final double value) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn.isChecked()) {
                    fiberPrice = fiberPrice + value;
                    checkBoxCounter++;
                    checkFiberLimit();
                } else if(!btn.isChecked()) {
                    fiberPrice = fiberPrice - value;
                    checkBoxCounter--;
                    checkFiberLimit();
                }
                setPrice();
            }
        });
    }

    // Creates button listener for fat section
    public void createFatButtonListener(final CheckBox btn, final double value)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn.isChecked()) {
                    fatPrice = fatPrice + value;
                } else if (!btn.isChecked()) {
                    fatPrice = fatPrice - value;
                }
                setPrice();
            }
        });
    }
}
