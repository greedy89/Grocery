package com.senos.seno.grocery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.os.RecoverySystem;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.senos.seno.grocery.adapter.AdapterListBarang;
import com.senos.seno.grocery.model.Data;
import com.senos.seno.grocery.model.Grocery;
import com.senos.seno.grocery.service.ApiClient;
import com.senos.seno.grocery.service.ApiInterfaces;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBarang extends AppCompatActivity {
    private RecyclerView rv;
    private AdapterListBarang mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang);
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        getGrocery();
    }

    private void getGrocery() {
        ApiInterfaces interfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Data> grocery = interfaces.getAll();
        grocery.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Data barang = response.body();
                if (response.body() != null) {
                    if(barang.getData().getGrocery().size()>0){
//                        ProgressDialog pd = new ProgressDialog(ListBarang.this);
//                        pd.setMessage("Please wait preparing Data");
//                        pd.setCancelable(false);
//                        pd.show();
                        setList(barang.getData().getGrocery());
//                        pd.dismiss();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ListBarang.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ListBarang.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(ListBarang.this, "is something wrong", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void setList(List<Grocery> items){
        mAdapter = new AdapterListBarang(ListBarang.this,items);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdapterListBarang.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Grocery grocery, int position) {
                Intent intent = new Intent(ListBarang.this,TambahBarang.class);
                intent.putExtra("editbarang", (Parcelable) grocery);
                startActivity(intent);

            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        getGrocery();
    }
}
