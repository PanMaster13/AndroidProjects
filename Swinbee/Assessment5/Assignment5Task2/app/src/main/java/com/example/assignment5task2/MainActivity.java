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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button start_btn, stop_btn;
    private EditText editText;
    private BroadcastReceiver broadcastReceiver;
    private String url = "http://172.17.13.143/OTHERS/task2Files/script.php";
    public static final String NOTIFICATION="NOTIFICATION FROM SERVICE";
    public static final String COORDINATES = "MSG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_btn = findViewById(R.id.start_btn);
        stop_btn = findViewById(R.id.stop_btn);
        editText = findViewById(R.id.editText_minute);
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
                    String coordinates = String.valueOf(intent.getExtras().get(COORDINATES));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
                    String currentDate = dateFormat.format(new Date());
                    String currentTime = timeFormat.format(new Date());
                    final String text = currentDate + " " + currentTime + " " + coordinates + "\n";
                    try{
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("Response", response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Error.Response", "Failed to request to server!");
                                    }
                                }
                        ){
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("request", "YES");
                                params.put("text", text);

                                return params;
                            }
                        };
                        queue.add(postRequest);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter(NOTIFICATION));
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
                if (!editText.getText().toString().isEmpty()){
                    int millisecond = Integer.parseInt(editText.getText().toString()) * 60000;
                    stop_btn.setEnabled(true);
                    start_btn.setEnabled(false);
                    Intent startIntent = new Intent(getApplicationContext(), Gps_Service.class);
                    startIntent.putExtra("Minutes", millisecond);
                    startService(startIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a value for minute!", Toast.LENGTH_SHORT).show();
                }

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
