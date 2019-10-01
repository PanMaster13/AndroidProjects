package com.example.task3;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RadioGroup group = findViewById(R.id.radioGroup);

        final RadioButton beef = findViewById(R.id.radioButton1);
        final RadioButton chicken = findViewById(R.id.radioButton2);
        final RadioButton fish = findViewById(R.id.radioButton3);
        final RadioButton egg = findViewById(R.id.radioButton4);

        final CheckBox lettuce = findViewById(R.id.checkBox1);
        final CheckBox tomato = findViewById(R.id.checkBox2);
        final CheckBox pickle = findViewById(R.id.checkBox3);
        final CheckBox onion = findViewById(R.id.checkBox4);

        final CheckBox cheese = findViewById(R.id.checkBox5);
        final CheckBox mayo = findViewById(R.id.checkBox6);
        final CheckBox mustard = findViewById(R.id.checkBox7);

        final Button order = findViewById(R.id.order);
        final Button reset = findViewById(R.id.reset);

        // ----------------------Protein Section-------------------------------
        beef.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                proteinPrice = 4.5;
                setPrice();
                proteinSelectedStatus = true;
            }
        });

        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proteinPrice = 3.0;
                setPrice();
                proteinSelectedStatus = true;
            }
        });

        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proteinPrice = 4.0;
                setPrice();
                proteinSelectedStatus = true;
            }
        });

        egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proteinPrice = 2.0;
                setPrice();
                proteinSelectedStatus = true;
            }
        });

        // ---------------------Fiber Section--------------------------------
        lettuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lettuce.isChecked())
                {
                    fiberPrice = fiberPrice + 0.5;
                    checkBoxCounter++;
                    checkFiberLimit();
                }
                else if(!lettuce.isChecked())
                {
                    fiberPrice = fiberPrice - 0.5;
                    checkBoxCounter--;
                    checkFiberLimit();
                }
                setPrice();
            }
        });

        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tomato.isChecked())
                {
                    fiberPrice = fiberPrice + 0.5;
                    checkBoxCounter++;
                    checkFiberLimit();
                }
                else if(!tomato.isChecked())
                {
                    fiberPrice = fiberPrice - 0.5;
                    checkBoxCounter--;
                    checkFiberLimit();
                }
                setPrice();
            }
        });

        pickle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pickle.isChecked())
                {
                    fiberPrice = fiberPrice + 0.5;
                    checkBoxCounter++;
                    checkFiberLimit();
                }
                else if (!pickle.isChecked())
                {
                    fiberPrice = fiberPrice - 0.5;
                    checkBoxCounter--;
                    checkFiberLimit();
                }
                setPrice();
            }
        });

        onion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onion.isChecked())
                {
                    fiberPrice = fiberPrice + 0.5;
                    checkBoxCounter++;
                    checkFiberLimit();
                }
                else if (!onion.isChecked())
                {
                    fiberPrice = fiberPrice - 0.5;
                    checkBoxCounter--;
                    checkFiberLimit();
                }
                setPrice();
            }
        });

        // ---------------------------Fat Section-------------------------------
        cheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cheese.isChecked())
                {
                    fatPrice = fatPrice + 1.0;
                }
                else if (!cheese.isChecked())
                {
                    fatPrice = fatPrice - 1.0;
                }
                setPrice();
            }
        });

        mayo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mayo.isChecked())
                {
                    fatPrice = fatPrice + 0.5;
                }
                else if(!mayo.isChecked())
                {
                    fatPrice = fatPrice - 0.5;
                }
                setPrice();
            }
        });

        mustard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mustard.isChecked())
                {
                    fatPrice = fatPrice + 0.7;
                }
                else if(!mustard.isChecked())
                {
                    fatPrice = fatPrice - 0.7;
                }
                setPrice();
            }
        });



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
                    sizeMultiplier = 1.0;
                    setPrice();
                }
                else if(sizeValue.equals("Regular"))
                {
                    sizeMultiplier = 1.2;
                    setPrice();
                }
                else if(sizeValue.equals("Large"))
                {
                    sizeMultiplier = 1.3;
                    setPrice();
                }
                else if(sizeValue.equals("Gigantic"))
                {
                    sizeMultiplier = 1.5;
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
        priceText.setText(String.format("RM%.2f", result));
    }
}
