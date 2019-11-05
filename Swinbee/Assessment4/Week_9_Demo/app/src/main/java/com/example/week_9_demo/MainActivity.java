package com.example.week_9_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView textView;
    private ImageView imageView;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent1 = new Intent(getApplicationContext(), MyService.class);
                startService(intent1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiveFromService,
                new IntentFilter(MyService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiveFromService);
    }

    private BroadcastReceiver receiveFromService = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                String msg = bundle.getString(MyService.KEY);
                if (msg.equals("The monster says: you gae!")){
                    stopService(intent1);
                } else {
                    textView.setText(msg);
                    if (msg.contains("sleep")){
                        imageView.setImageResource(R.drawable.sleeping_monster);
                    } else {
                        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.fade);
                        imageView.startAnimation(animation1);
                        imageView.setImageResource(R.drawable.monster);
                    }
                }
            }
        }
    };
}
