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

        // Declaring View variables
        // Radio group for protein section
        final RadioGroup group = findViewById(R.id.radioGroup);

        // Protein radio buttons
        final RadioButton beef = findViewById(R.id.radioButton1);
        final RadioButton chicken = findViewById(R.id.radioButton2);
        final RadioButton fish = findViewById(R.id.radioButton3);
        final RadioButton egg = findViewById(R.id.radioButton4);

        // Fiber checkboxes
        final CheckBox lettuce = findViewById(R.id.checkBox1);
        final CheckBox tomato = findViewById(R.id.checkBox2);
        final CheckBox pickle = findViewById(R.id.checkBox3);
        final CheckBox onion = findViewById(R.id.checkBox4);

        // Fat checkboxes
        final CheckBox cheese = findViewById(R.id.checkBox5);
        final CheckBox mayo = findViewById(R.id.checkBox6);
        final CheckBox mustard = findViewById(R.id.checkBox7);

        // Order and reset button
        final Button order = findViewById(R.id.order);
        final Button reset = findViewById(R.id.reset);

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
        createProteinButtonListener(beef, ingredientPrice.get("Beef"));
        createProteinButtonListener(chicken, ingredientPrice.get("Chicken"));
        createProteinButtonListener(fish, ingredientPrice.get("Fish"));
        createProteinButtonListener(egg, ingredientPrice.get("Egg"));

        // ---------------------Fiber Section--------------------------------
        createFiberButtonListener(lettuce, ingredientPrice.get("Lettuce"));
        createFiberButtonListener(tomato, ingredientPrice.get("Tomato"));
        createFiberButtonListener(pickle, ingredientPrice.get("Pickle"));
        createFiberButtonListener(onion, ingredientPrice.get("Onion"));

        // ---------------------------Fat Section-------------------------------
        createFatButtonListener(mustard, ingredientPrice.get("Mustard"));
        createFatButtonListener(cheese, ingredientPrice.get("Cheese"));
        createFatButtonListener(mayo, ingredientPrice.get("Mayo"));

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
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                String sizeValue = (String)adapterView.getItemAtPosition(position);
                if (sizeValue.equals("Small")) // String values are compared with .equals built-in function
                {
                    sizeMultiplier = ingredientPrice.get("Small");
                    setPrice();
                }
                else if(sizeValue.equals("Regular"))
                {
                    sizeMultiplier = ingredientPrice.get("Regular");
                    setPrice();
                }
                else if(sizeValue.equals("Large"))
                {
                    sizeMultiplier = ingredientPrice.get("Large");
                    setPrice();
                }
                else if(sizeValue.equals("Gigantic"))
                {
                    sizeMultiplier = ingredientPrice.get("Gigantic");
                    setPrice();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                sizeMultiplier = 1.0;
                setPrice();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group.clearCheck();

                lettuce.setChecked(false);
                tomato.setChecked(false);
                pickle.setChecked(false);
                onion.setChecked(false);

                cheese.setChecked(false);
                mayo.setChecked(false);
                mustard.setChecked(false);

                spinner.setSelection(0);

                // Set fiber checkbox to default
                fiberBonusState = true;
                checkBoxCounter = 0;
                checkFiberLimit();
                fiberBonusState = false;

                // Set values to their defaults
                proteinPrice = 0.0;
                fiberPrice = 0.0;
                fatPrice = 0.0;
                proteinSelectedStatus = false;

                // Recalculate default
                setPrice();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                CharSequence text;

                if (!proteinSelectedStatus)
                {
                    text = "You must select a main filling from the Protein Section.";
                }
                else
                {
                    text = String.format(Locale.ENGLISH, "Amount to be paid: RM%.2f", result);
                }

                // Toast is a simple UI feedback that works similarly to Alert Boxes in JavaScript
                Toast msg = Toast.makeText(context, text, duration);
                msg.show();
            }
        });
    }

    // Sets fiber price based on discount and disables appropriate checkbox
    public void checkFiberLimit()
    {
        CheckBox lettuce = findViewById(R.id.checkBox1);
        CheckBox tomato = findViewById(R.id.checkBox2);
        CheckBox pickle = findViewById(R.id.checkBox3);
        CheckBox onion = findViewById(R.id.checkBox4);

        if (checkBoxCounter == 3)
        {
            fiberPrice = fiberPrice - 0.5;
            if (!lettuce.isChecked())
            {
                lettuce.setEnabled(false);
            }
            else if(!tomato.isChecked())
            {
                tomato.setEnabled(false);
            }
            else if(!pickle.isChecked())
            {
                pickle.setEnabled(false);
            }
            else if(!onion.isChecked())
            {
                onion.setEnabled(false);
            }
            fiberBonusState = true;
        }
        else if (fiberBonusState)
        {
            fiberPrice = fiberPrice + 0.5;
            lettuce.setEnabled(true);
            tomato.setEnabled(true);
            pickle.setEnabled(true);
            onion.setEnabled(true);
            fiberBonusState = false;
        }
    }

    // Changes price on screen
    public void setPrice()
    {
        TextView priceText = findViewById(R.id.price);

        result = (proteinPrice + fiberPrice + fatPrice) * sizeMultiplier;
        priceText.setText(String.format(Locale.ENGLISH, "RM%.2f", result));
    }

    // Creates button listener for protein section
    public void createProteinButtonListener(RadioButton btn, final double value)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proteinPrice = value;
                setPrice();
                proteinSelectedStatus = true;
            }
        });
    }

    // Creates button listener for fiber section
    public void createFiberButtonListener(final CheckBox btn, final double value)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn.isChecked())
                {
                    fiberPrice = fiberPrice + value;
                    checkBoxCounter++;
                    checkFiberLimit();
                }
                else if(!btn.isChecked())
                {
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
                if (btn.isChecked())
                {
                    fatPrice = fatPrice + value;
                }
                else if (!btn.isChecked())
                {
                    fatPrice = fatPrice - value;
                }
                setPrice();
            }
        });
    }
}
