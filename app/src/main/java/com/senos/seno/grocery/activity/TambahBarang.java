package com.senos.seno.grocery.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.senos.seno.grocery.R;
import com.senos.seno.grocery.model.Grocery;
import com.senos.seno.grocery.model.ReturnValue;
import com.senos.seno.grocery.service.ApiClient;
import com.senos.seno.grocery.service.ApiInterfaces;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBarang extends AppCompatActivity {
    EditText modal, hrgJual, nmBarang, codeBarcode, tanggal;
    String id, tanggalData, statusData;
    Button tbladdBarcode, tblhapus;
    Calendar cal;
    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Tambah Barang");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);
        inisiasicomponent();
        loadData();
    }

    private void inisiasicomponent() {
        codeBarcode = (EditText) findViewById(R.id.etCodeBarcode);
        nmBarang = (EditText) findViewById(R.id.etNamaBarang);
        hrgJual = (EditText) findViewById(R.id.etHargaJual);
        modal = (EditText) findViewById(R.id.etModal);
        tanggal = (EditText) findViewById(R.id.etTanggal);
        cal = Calendar.getInstance();
        tanggal.setText(sdf.format(cal.getTime()));
        tbladdBarcode = (Button) findViewById(R.id.tblAddBarcode);
        tbladdBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(TambahBarang.this, ScanForAddBarang.class);
                startActivity(inten);
            }
        });
        tblhapus = (Button) findViewById(R.id.tblHapus);
        tblhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertHapusBarang("Hapus", "Data yang sudah di hapus tidak dapan di kempablikan. \nHapus Barang ?");

            }
        });
        tblhapus.setVisibility(View.GONE);
        hrgJual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hrgJual.setText("");
            }
        });
        modal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.setText("");
            }
        });
    }

    public void scanBarcode(View view) {
//        Intent intent = new Intent(TambahBarang.this,ScanForFindBarang.class);
//        intent.putExtra("tmbhBarang","TambahBarang");
//        startActivity(intent);
    }

    public void addBarang(View view) {
        alertTambahBarang("Add Comfirmation", codeBarcode.getText().toString() + "\n" +
                "Nama barang : " + nmBarang.getText().toString() + "\n" +
                "Harga Jual : " + hrgJual.getText().toString() + "\n" +
                "Modal : " + modal.getText().toString());

    }

    private void alertTambahBarang(String title, String pesan) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(pesan);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cal = Calendar.getInstance();
                tanggalData = sdf.format(cal.getTime());
                if (id != null) {
                    updateBarang();
                } else {
                    tambahbarang();
                }

            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void alertHapusBarang(String title, String pesan) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(pesan);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hapusBarang();
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void tambahbarang() {
        ApiInterfaces interfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<ReturnValue> barang = interfaces.tambahBarang(
                toRequestBody(codeBarcode.getText().toString()),
                toRequestBody(nmBarang.getText().toString()),
                toRequestBody(hrgJual.getText().toString()),
                toRequestBody(modal.getText().toString()),
                toRequestBody(tanggalData),
                toRequestBody("")
        );
        barang.enqueue(new Callback<ReturnValue>() {
            @Override
            public void onResponse(Call<ReturnValue> call, Response<ReturnValue> response) {
                ReturnValue r = response.body();
                if (r.getStatus() == true) {
                    Toast.makeText(TambahBarang.this, r.getMessage().toString(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(TambahBarang.this, r.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnValue> call, Throwable t) {
                Toast.makeText(TambahBarang.this, "gagal mengirim please check your connection/n" + t.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void updateBarang() {
        ApiInterfaces interfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<ReturnValue> barang2 = interfaces.updateBarang(
                toRequestBody(id),
                toRequestBody(codeBarcode.getText().toString()),
                toRequestBody(nmBarang.getText().toString()),
                toRequestBody(hrgJual.getText().toString()),
                toRequestBody(modal.getText().toString()),
                toRequestBody(tanggalData),
                toRequestBody("")
        );
        barang2.enqueue(new Callback<ReturnValue>() {
            @Override
            public void onResponse(Call<ReturnValue> call, Response<ReturnValue> response) {
                ReturnValue data = response.body();
                if (data.getStatus() == true) {
                    Toast.makeText(TambahBarang.this, data.getMessage().toString(), Toast.LENGTH_LONG);
                    finish();
                } else {
                    Toast.makeText(TambahBarang.this, data.getMessage().toString(), Toast.LENGTH_LONG);
                    finish();
                }


            }

            @Override
            public void onFailure(Call<ReturnValue> call, Throwable t) {
                Toast.makeText(TambahBarang.this, "please make sure you connected with internet" + t.getMessage(), Toast.LENGTH_LONG);
                finish();
            }
        });
    }

    private void hapusBarang() {
        ApiInterfaces interfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<ReturnValue> barang2 = interfaces.deleteBarang(toRequestBody(id));
        barang2.enqueue(new Callback<ReturnValue>() {
            @Override
            public void onResponse(Call<ReturnValue> call, Response<ReturnValue> response) {
                ReturnValue value = response.body();
                if (value != null) {
                    if (value.getStatus() != false) {
                        Toast.makeText(TambahBarang.this, "Data berhasil di hapus", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(TambahBarang.this, value.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReturnValue> call, Throwable t) {
                Toast.makeText(TambahBarang.this, "you not connect to the internet", Toast.LENGTH_SHORT).show();
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

    private void loadData() {
        Intent inten = getIntent();

//        Grocery data = (Grocery) inten.getSerializableExtra("editbarang");
        if (inten.getStringExtra("barcode") != null) {
            codeBarcode.setText(inten.getStringExtra("barcode"));
        } else if (inten.getSerializableExtra("editbarang") != null) {
            Grocery data = (Grocery) inten.getSerializableExtra("editbarang");
            nmBarang.setText(data.getNamabarang());
            hrgJual.setText(data.getHargajual());
            modal.setText(data.getModal());
            codeBarcode.setText(data.getCodebarcode());
            id = data.getId();
            tanggal.setText(data.getTanggal());
            tanggalData = data.getTanggal();
            statusData = data.getStatus();
            tblhapus.setVisibility(View.VISIBLE);
        } else {

        }
    }

}
