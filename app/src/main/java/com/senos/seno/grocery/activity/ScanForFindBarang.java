package com.senos.seno.grocery.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.senos.seno.grocery.R;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanForFindBarang extends AppCompatActivity implements ZBarScannerView.ResultHandler{
    private ZBarScannerView mScannerView;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private String wkwk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        checkPermission();
        mScannerView = new ZBarScannerView(this);
        ViewGroup contentpanel = (ViewGroup)findViewById(R.id.contentPanel);
        contentpanel.addView(mScannerView);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {


        if (requestCode == MY_CAMERA_REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                //dispatchTakePictureIntent();

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }


    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(ScanForFindBarang.this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
            Intent inten ;
            inten = new Intent(this,HomeActivity.class);
            inten.putExtra("barcode",result.getContents());
            startActivity(inten);
            finish();
    }
}
