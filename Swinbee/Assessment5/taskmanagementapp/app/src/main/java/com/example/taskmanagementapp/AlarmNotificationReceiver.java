package com.example.taskmanagementapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmNotificationReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String value = intent.getStringExtra("Value");
        String notification_msg = "You have " + value + " task(s) due in less than a day";
        intent = new Intent(context, MainActivity.class);
        context.startService(intent);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_01")
                .setSmallIcon(R.drawable.blue_calendar_icon).setWhen(System.currentTimeMillis())
                .setContentTitle("Notice from Task Management App!").setContentText(notification_msg);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("Msg", "Message");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
