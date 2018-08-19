package com.senos.seno.grocery.alarmOld;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


import com.senos.seno.grocery.R;
import com.senos.seno.grocery.activity.HomeActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmNotification {
    static NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static String CHANELL_ID = "com.senos.seno.grocery.1001";

    public static void deliverNotification(Context context, String title, String message) {
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent contentIntent = new Intent(context, HomeActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentPendingIntent)
                .setAutoCancel(false)
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channelManager = new NotificationChannel(CHANELL_ID,"SENO",NotificationManager.IMPORTANCE_HIGH);
            channelManager.enableVibration(true);
            channelManager.setVibrationPattern(new long[1000]);
            builder.setChannelId(CHANELL_ID);
            mNotificationManager.createNotificationChannel(channelManager);
        }
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
