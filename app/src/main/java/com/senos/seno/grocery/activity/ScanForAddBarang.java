package com.senos.seno.grocery.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanForAddBarang extends AppCompatActivity implements ZBarScannerView.ResultHandler{
    private ZBarScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.scan_for_add_barang);
        mScannerView = new ZBarScannerView(this);
        setContentView(mScannerView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(ScanForAddBarang.this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent(ScanForAddBarang.this,TambahBarang.class);
        intent.putExtra("barcode",result.getContents());
        startActivity(intent);
        finish();
    }
}
