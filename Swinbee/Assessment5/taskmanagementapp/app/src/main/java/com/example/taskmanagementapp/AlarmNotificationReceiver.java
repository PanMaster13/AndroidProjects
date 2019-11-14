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
        String task_name = intent.getStringExtra("title");
        intent = new Intent(context, MainActivity.class);
        String message = "The task '" + task_name + "' is due within less than a day!";
        context.startService(intent);
        sendNotification(context, String.valueOf(System.currentTimeMillis()), message);
    }

    private void sendNotification(Context context, String notificationMsg, String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_01")
                .setSmallIcon(R.drawable.blue_calendar_icon).setWhen(System.currentTimeMillis())
                .setContentTitle("Notice from Task Management App!").setContentText(message)
                .setOnlyAlertOnce(true);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("Msg", notificationMsg);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }
}
