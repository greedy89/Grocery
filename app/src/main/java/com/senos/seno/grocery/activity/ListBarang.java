package com.senos.seno.grocery.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.senos.seno.grocery.ApplicationGrocery;
import com.senos.seno.grocery.R;
import com.senos.seno.grocery.adapter.AdapterListBarang;
import com.senos.seno.grocery.model.Data;
import com.senos.seno.grocery.model.Grocery;
import com.senos.seno.grocery.model.Grocery_Table;
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

                        setList(barang.getData().getGrocery());

                        saveDB(barang.getData().getGrocery());
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
                setList(loadDataOffline());
//                call.cancel();

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

    private void saveDB(List<Grocery> items){
        FlowManager.getDatabase(ApplicationGrocery.class).
                beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Grocery>() {
                            @Override
                            public void processModel(Grocery grocery, DatabaseWrapper wrapper) {
                                grocery.save();
                            }
                        }
                ).addAll(items).build()).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG);
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                Toast.makeText(getApplicationContext(), "Data berhasil di simpan di sqlLite", Toast.LENGTH_LONG);
            }
        }).build().execute();
    }

    private List<Grocery> loadDataOffline(){
        List<Grocery> result= SQLite.select().from(Grocery.class).queryList();
        return result ;

    }
    private List<Grocery> findDataOffline(String a){
        List<Grocery> result = SQLite.select().from(Grocery.class).
                where(Grocery_Table.codebarcode.like("%"+a+"%")).
                or(Grocery_Table.namabarang.like("%"+a+"%")).
                queryList();
//        if(result.size()==0){
//            result = SQLite.select().from(Grocery.class).
//                    where(Grocery_Table.namabarang.like("%"+a+"%")).
//                    queryList();
//        }
        return result;
    }
    @Override
    protected void onResume() {
        super.onResume();
        getGrocery();
    }

    public void caribarang(View view) {
        EditText valueCari = (EditText) findViewById(R.id.searchValue);
        String value = valueCari.getText().toString();
        List<Grocery> data = findDataOffline(value);
        setList(data);
    }
}
