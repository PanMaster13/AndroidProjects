package com.example.taskmanagementapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent = new Intent(context, MainActivity.class);
        context.startService(intent);
        createNotification(context, String.valueOf(System.currentTimeMillis()));
    }

    private void createNotification(Context context, String notificationMsg){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_01")
                .setSmallIcon(R.drawable.blue_calendar_icon).setWhen(System.currentTimeMillis())
                .setContentTitle("Alarm Notification").setVibrate(new long[] {1000})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText("Task due soon!").setOnlyAlertOnce(true);

        Intent notification_intent = new Intent(context, MainActivity.class);
        notification_intent.putExtra("Msg", notificationMsg);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notification_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
