package com.lolipoplls.lecture4;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private CheckBox checkBox_pizza, checkBox_burger;
    private EditText editText_pizza, editText_burger;
    private Button btn_submit;
    private ArrayList<Order> orderList;
    public static String ORDER_LIST_KEY="ORDER_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initUI();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrder();
            }
        });
    }

    private void initUI(){
        checkBox_pizza = findViewById(R.id.checkBox_pizza);
        checkBox_burger = findViewById(R.id.checkBox_burger);
        editText_pizza = findViewById(R.id.editText_pizza_qty);
        editText_burger = findViewById(R.id.editText_burger_qty);
        btn_submit = findViewById(R.id.button2);
    }

    private void getOrder(){
        orderList = new ArrayList<Order>();

        if(checkBox_pizza.isChecked()){
            Order order = new Order(checkBox_pizza.getText().toString(), Integer.parseInt(editText_pizza.getText().toString()), 18.00);
            orderList.add(order);
        }

        if(checkBox_burger.isChecked()){
            Order order = new Order(checkBox_burger.getText().toString(), Integer.parseInt(editText_burger.getText().toString()), 7.00);
            orderList.add(order);
        }

        Intent return_intent = new Intent();
        return_intent.putParcelableArrayListExtra(ORDER_LIST_KEY, orderList);
        setResult(Activity.RESULT_OK, return_intent);
        finish();
    }
}
