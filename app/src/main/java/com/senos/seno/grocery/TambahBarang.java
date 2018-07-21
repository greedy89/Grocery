package com.senos.seno.grocery;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.senos.seno.grocery.model.Barang;
import com.senos.seno.grocery.model.ReturnValue;
import com.senos.seno.grocery.service.ApiClient;
import com.senos.seno.grocery.service.ApiInterfaces;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBarang extends AppCompatActivity {
    EditText hrgBeli,hrgBarang,nmBarang,codeBarcode;
    String codeBarang,namaBarang,hargaBarang,hargaBeli;
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
//        Intent intent = new Intent(TambahBarang.this,Main2Activity.class);
//        intent.putExtra("tmbhBarang","TambahBarang");
//        startActivity(intent);
    }

    public void addBarang(View view) {
        String namabarang = nmBarang.getText().toString();
        String codBarcode = codeBarcode.getText().toString();
        String hrgbarang = hrgBarang.getText().toString();
        String hrgbeli = hrgBeli.getText().toString();
        alertTambahBarang("Add Comfirmation",codBarcode+"\n"+
                namabarang+"\n"+
                hrgbarang+"\n"+
                hrgbeli);

    }

    private void alertTambahBarang(String title, String pesan){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(pesan);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tambahbarang();
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }
    private void tambahbarang(){
        codeBarang = codeBarcode.getText().toString();
        namaBarang = nmBarang.getText().toString();
        hargaBarang = hrgBarang.getText().toString();
        hargaBeli = hrgBeli.getText().toString();

        ApiInterfaces interfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<ReturnValue> barang2 = interfaces.tambahBarang(toRequestBody(codeBarang),toRequestBody(namaBarang),toRequestBody(hargaBarang),toRequestBody(hargaBeli));

        barang2.enqueue(new Callback<ReturnValue>() {
            @Override
            public void onResponse(Call<ReturnValue> call, Response<ReturnValue> response) {
                ReturnValue r = response.body();
                if(r.getStatus()==true){
                    Toast.makeText(TambahBarang.this,r.getMessage().toString(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(TambahBarang.this,r.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnValue> call, Throwable t) {
                Toast.makeText(TambahBarang.this,"gagal mengirim please check your connection",Toast.LENGTH_LONG).show();
            }
        });
    }
    public RequestBody toRequestBody(String value) {
        if (value == null) {
            value = "";
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }
}
