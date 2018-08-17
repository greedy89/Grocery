package com.senos.seno.grocery.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.senos.seno.grocery.R;
import com.senos.seno.grocery.model.Grocery;
import com.senos.seno.grocery.model.Grocery_Table;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private NumberFormat numberFormat;
    private Locale localeID =new Locale("in","ID");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String a = getIntent().getStringExtra("barcode");
        TextView txCodebarcode =(TextView)findViewById(R.id.txtHasilScan);
        TextView txNamabarang=(TextView)findViewById(R.id.txNamabarang);
        TextView txHargebarang =(TextView)findViewById(R.id.txHarga);

        if(a !=null){
            Grocery result = SQLite.select().from(Grocery.class).where(Grocery_Table.codebarcode.like(a)).querySingle();
            txCodebarcode.setText(result.getCodebarcode());
            txNamabarang.setText(result.getNamabarang());
            numberFormat = NumberFormat.getCurrencyInstance(localeID);
            try{
                txHargebarang.setText(numberFormat.format(Integer.valueOf(result.getHargajual())));
            }catch (Exception e){

            }

        }

    }


    public void scanBarcode(View view) {
        Intent n = new Intent (MainActivity.this,ScanForFindBarang.class);
        n.putExtra("flag","cariBarang");
        startActivity(n);
    }

}
