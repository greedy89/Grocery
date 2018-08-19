package com.senos.seno.grocery.alarmOld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"seno sunawar",Toast.LENGTH_LONG);
        AlarmNotification.deliverNotification(context,"seno","pulang");
    }
}
