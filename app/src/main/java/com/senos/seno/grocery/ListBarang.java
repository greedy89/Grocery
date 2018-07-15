package com.senos.seno.grocery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.senos.seno.grocery.adapter.AdapterListBarang;
import com.senos.seno.grocery.model.Barang;
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
        Call<Barang> grocery = interfaces.getAll();
        grocery.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang> response) {
                Barang barang = response.body();
                if (response.body() != null) {
                    if(barang.getData().getGrocery().size()>0){
                        setList(barang.getData().getGrocery());
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
            public void onFailure(Call<Barang> call, Throwable t) {
                Toast.makeText(ListBarang.this, "is something wrong", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void setList(List<Grocery> items){
        mAdapter = new AdapterListBarang(ListBarang.this,items);
        mAdapter.setOnItemClickListener(new AdapterListBarang.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Grocery grocery, int position) {
                Toast.makeText(ListBarang.this,grocery.getNamabarang()+"\n"+
                                                            grocery.getHargabarang(),Toast.LENGTH_LONG);
            }
        });
        rv.setAdapter(mAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getGrocery();
    }
}
