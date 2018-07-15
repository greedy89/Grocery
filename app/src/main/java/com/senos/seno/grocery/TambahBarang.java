package com.senos.seno.grocery;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TambahBarang extends AppCompatActivity {
    EditText hrgBeli,hrgBarang,nmBarang,codeBarcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Tambah Barang");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);
        inisiasicomponent();
    }

    private void inisiasicomponent(){
        codeBarcode = (EditText)findViewById(R.id.etCodeBarcode);
        nmBarang = (EditText)findViewById(R.id.etNamaBarang);
        hrgBarang =(EditText)findViewById(R.id.etHargaBarang);
        hrgBeli = (EditText)findViewById(R.id.etHargaBeli);
        
    }

    public void scanBarcode(View view) {
        Intent intent = new Intent(TambahBarang.this,Main2Activity.class);
        intent.putExtra("tmbhBarang","TambahBarang");
        startActivity(intent);
    }

    public void addBarang(View view) {
        String namabarang = nmBarang.getText().toString();
        String codBarcode = codeBarcode.getText().toString();
        String hrgbarang = hrgBarang.getText().toString();
        String hrgbeli = hrgBeli.getText().toString();
        myAlert("",codBarcode+"\n"+
                namabarang+"\n"+
                hrgbarang+"\n"+
                hrgbeli);
    }

    private void myAlert(String title, String pesan){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(pesan);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }
}
