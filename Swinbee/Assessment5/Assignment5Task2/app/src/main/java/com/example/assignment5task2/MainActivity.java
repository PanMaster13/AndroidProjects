package com.example.assignment5task2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class MainActivity extends AppCompatActivity {

    private Button start_btn, stop_btn;
    private EditText editText;
    private TextView test_textView;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_btn = findViewById(R.id.start_btn);
        stop_btn = findViewById(R.id.stop_btn);
        editText = findViewById(R.id.editText_minute);
        test_textView = findViewById(R.id.test_textView);
        if (!runtime_permissions()){
            enable_buttons();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    test_textView.append("\n" + intent.getExtras().get("coordinates"));
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    private void enable_buttons() {
        start_btn.setEnabled(true);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), Gps_Service.class);
                startService(startIntent);
                stop_btn.setEnabled(true);
                start_btn.setEnabled(false);
            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopIntent = new Intent(getApplicationContext(), Gps_Service.class);
                stopService(stopIntent);
                start_btn.setEnabled(true);
                stop_btn.setEnabled(false);
            }
        });
    }

    private boolean runtime_permissions() {
        if ((Build.VERSION.SDK_INT >= 23) && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100){
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                enable_buttons();
            } else {
                runtime_permissions();
            }
        }
    }

}
