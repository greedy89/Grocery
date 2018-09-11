package com.senos.seno.grocery.activity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.senos.seno.grocery.R;
import com.senos.seno.grocery.model.Grocery;
import com.senos.seno.grocery.model.Grocery_Table;
import com.senos.seno.grocery.alarmOld.AlrmProperty;
import com.senos.seno.grocery.schedulerOld.ExampleJobService;


import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NumberFormat numberFormat;
    private Locale localeID = new Locale("in", "ID");
    private TextView txtime ;
    private String strTime;
    private BroadcastReceiver receiver;
    //    TextView a;
//    Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String a = getIntent().getStringExtra("barcode");
        IntentFilter intentFilter = new IntentFilter("com.senos.seno.grocery.ACTION_NOTIFY_MASUK");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver,intentFilter);
        TextView txCodebarcode = (TextView) findViewById(R.id.txtHasilScan);
        TextView txNamabarang = (TextView) findViewById(R.id.txNamabarang);
        TextView txhargaBarang = (TextView) findViewById(R.id.txHarga);
        txtime = (TextView)findViewById(R.id.txtime);
        if (a != null) {
            Grocery result = SQLite.select().from(Grocery.class).where(Grocery_Table.codebarcode.like(a)).querySingle();
            if(result!=null){
                txCodebarcode.setText(result.getCodebarcode());
                txNamabarang.setText(result.getNamabarang());
                numberFormat = NumberFormat.getCurrencyInstance(localeID);
                try {
                    txhargaBarang.setText(numberFormat.format(Integer.valueOf(result.getHargajual())));
                } catch (Exception e) {

                }
            }else{
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Information");
                alertDialogBuilder.setMessage("Data produk ini belum terdaftar");
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialogBuilder.setCancelable(false);
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();

            }
        }
//        a  = (TextView) findViewById(R.id.textView8);
//        cal = Calendar.getInstance();
//        a.setText(cal.getTime().toString());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent inten = new Intent(HomeActivity.this, ScanForFindBarang.class);
            startActivity(inten);
        } else if (id == R.id.nav_gallery) {
            Intent inten = new Intent(HomeActivity.this, ListBarang.class);
            startActivity(inten);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            Intent inten = new Intent(HomeActivity.this, TambahBarang.class);
            startActivity(inten);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent inten = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(inten);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void scanBarcode(View view) {
        Intent n = new Intent(HomeActivity.this, ScanForFindBarang.class);
        n.putExtra("flag", "cariBarang");
        startActivity(n);
    }

    public void runAlarm(View view) {
        Calendar cal = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                strTime = String.valueOf(hourOfDay)+":"+String.valueOf(minute);
                txtime.setText(strTime);
            }
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startalarm(View view) {
//        AlarmNotification.deliverNotification(HomeActivity.this,"seno","pulang");
//        IntentFilter intentFilter = new IntentFilter("com.senos.seno.grocery.ACTION_NOTIFY_MASUK");
//        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
//         receiver = new MyReciver();
//        registerReceiver(receiver,intentFilter);
        AlrmProperty.startAlarm(HomeActivity.this,strTime);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            JobInfo info = new JobInfo.Builder(123,componentName)
//                    .setPersisted(true)
//                    .setPeriodic(15*60*1000)
//                    .build();
//            JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
//            int resultJob = scheduler.schedule(info);
//            if(resultJob == JobScheduler.RESULT_SUCCESS){
//                Log.d("GroceryService", "job scheduled");
//                Toast.makeText(this,"job scheduled",Toast.LENGTH_LONG).show();
//            }else{
//                Log.d("GroceryService","job scheduling failed");
//                Toast.makeText(this,"job scheduling failed",Toast.LENGTH_LONG).show();
//            }
//
//        }

//        IntentFilter intentFilter = new IntentFilter("com.senos.seno.grocery.ACTION_NOTIFY_MASUK");
//        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
//        registerReceiver(receiver,intentFilter);
    }

    public void stopSchedule(View view) {
        JobScheduler scheduler = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.cancel(123);

            Toast.makeText(this,"Job cancelled",Toast.LENGTH_LONG).show();
            Log.d("GroceryService","job cancelled");
        }


    }

    public void startSchedule(View view) {
        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
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


    //    public void tblDateFormat(View view) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        a.setText(sdf.format(cal.getTime()));
//    }

}
