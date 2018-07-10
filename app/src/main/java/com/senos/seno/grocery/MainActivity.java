package com.senos.seno.grocery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String a = getIntent().getStringExtra("barcode");
        TextView txt =(TextView)findViewById(R.id.txtHasilScan);

        if(a !=null){
            txt.setText(a);
        }

    }


    public void scanBarcode(View view) {
        Intent n = new Intent (MainActivity.this,Main2Activity.class);
        startActivity(n);
    }

}
