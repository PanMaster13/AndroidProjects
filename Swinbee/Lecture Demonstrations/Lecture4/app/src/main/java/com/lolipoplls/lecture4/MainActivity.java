package com.lolipoplls.lecture4;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private TextView textView_order, textView_total;

    private static int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        textView_order = findViewById(R.id.textView_order);
        textView_total = findViewById(R.id.textView_total);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Main2Activity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE)
            if(resultCode == Activity.RESULT_OK)
                if(data!=null){
                    String food_ordered = "";
                    double total = 0.0;

                    ArrayList<Order> orderArrayList = data.getParcelableArrayListExtra(Main2Activity.ORDER_LIST_KEY);
                    for (Order order:orderArrayList){
                        food_ordered+=order.toString() + "\n";
                        total = total + (order.getQty()*order.getPrice());
                    }
                    textView_order.setText(food_ordered);
                    textView_total.setText("RM " + Double.toString(total));
                }

    }
}
