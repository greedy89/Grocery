package com.senos.seno.grocery.alarmOld;

        import android.app.AlarmManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Build;
        import android.support.annotation.RequiresApi;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;

public class AlrmProperty {
    private static final int NOTIIFICATION_ID = 0;
    private static final String ACTION_NOTIFY = "com.senos.seno.grocery.ACTION_NOTIFY_MASUK";

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void startAlarm(Context ctx, String time){

        final AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        Intent notifyIntent = new Intent();
        notifyIntent.addCategory(Intent.CATEGORY_DEFAULT);
        notifyIntent.setAction("com.senos.seno.grocery.ACTION_NOTIFY_MASUK");

        SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
        int a = time.indexOf(":");
        int jamK = Integer.valueOf(time.substring(0,a));
        int menit = Integer.valueOf(time.substring(a+1,time.length()));

        Date jam = null;
        try {
            jam = sd.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,jamK);
        cal.set(Calendar.MINUTE,menit);
        final PendingIntent notifyPadingIntent = PendingIntent.getBroadcast(ctx.getApplicationContext(), NOTIIFICATION_ID, notifyIntent , PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),notifyPadingIntent);
    }
}
