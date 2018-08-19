package com.senos.seno.grocery.schedulerOld;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.senos.seno.grocery.alarmOld.AlarmNotification;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ExampleJobService extends JobService {
    private static final String TAG = "GroceryService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG,"job service started");
        AlarmNotification.deliverNotification(getApplicationContext(),"seno","sunawar");
        jobFinished(params,true);
        return true;
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "job cancelled before completion");
        return true;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
//        Intent restartService = new Intent(getApplicationContext(), this.getClass());
//        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartService, PendingIntent.FLAG_ONE_SHOT);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME, 5000, pendingIntent);

        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            JobScheduler scheduler1 = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler1.cancel(123);

            Toast.makeText(this,"Job cancelled",Toast.LENGTH_LONG).show();
            Log.d("GroceryService","job cancelled");


            JobInfo info = new JobInfo.Builder(123,componentName)
                    .setPersisted(true)
//                    .setOverrideDeadline(15*60*1000)
                    .setPeriodic(15*60*1000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();
            JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
            int resultJob = scheduler.schedule(info);
            if(resultJob == JobScheduler.RESULT_SUCCESS){
                Log.d("GroceryService", "job scheduled");
                Toast.makeText(this,"job scheduled",Toast.LENGTH_LONG).show();
            }else{
                Log.d("GroceryService","job scheduling failed");
                Toast.makeText(this,"job scheduling failed",Toast.LENGTH_LONG).show();
            }

        }
    }
}
