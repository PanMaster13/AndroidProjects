package com.example.week_9_demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyService extends Service {

    public static final String NOTIFICATION="NOTIFICATION FROM SERVICE";
    public static final String KEY="MSG";

    private Thread backgroundThread;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        backgroundThread = new Thread(myThread);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Starting service");
        backgroundThread.start();
        sendingBroadcast("Monster is going to sleep");
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service is destroyed");

    }

    private Runnable myThread = new Runnable() {
        @Override
        public void run() {
            try{
                System.out.println("The monster is going to sleep");
                // 3 seconds
                Thread.sleep(3000);

            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
            System.out.println("waking up");
            sendingBroadcast("The monster has awaken!!!");
            sendingBroadcast("The monster says: you gae!");
        }
    };

    private void sendingBroadcast(String msg){
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(KEY, msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
