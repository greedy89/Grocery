package com.senos.seno.grocery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.senos.seno.grocery.R;
import com.senos.seno.grocery.model.Grocery;
import com.senos.seno.grocery.model.Grocery_Table;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NumberFormat numberFormat;
    private Locale localeID = new Locale("in", "ID");

    //    TextView a;
//    Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String a = getIntent().getStringExtra("barcode");
        TextView txCodebarcode = (TextView) findViewById(R.id.txtHasilScan);
        TextView txNamabarang = (TextView) findViewById(R.id.txNamabarang);
        TextView txHargebarang = (TextView) findViewById(R.id.txHarga);
        if (a != null) {
            Grocery result = SQLite.select().from(Grocery.class).where(Grocery_Table.codebarcode.like(a)).querySingle();
            txCodebarcode.setText(result.getCodebarcode());
            txNamabarang.setText(result.getNamabarang());
            numberFormat = NumberFormat.getCurrencyInstance(localeID);
            try {
                txHargebarang.setText(numberFormat.format(Integer.valueOf(result.getHargajual())));
            } catch (Exception e) {

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
        Intent n = new Intent (HomeActivity.this,ScanForFindBarang.class);
        n.putExtra("flag","cariBarang");
        startActivity(n);
    }

//    public void tblDateFormat(View view) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        a.setText(sdf.format(cal.getTime()));
//    }
}
