package com.senos.seno.grocery.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senos.seno.grocery.R;
import com.senos.seno.grocery.model.Grocery;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterListBarang extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Grocery> items = new ArrayList<>();
    private Context context;
    private NumberFormat numberFormat;
    private Locale localeID =new Locale("in","ID");

    AdapterListBarang.OnItemClickListener mOnItemClickListener;

    public AdapterListBarang(Context context , List<Grocery> items){
        this.context = context;
        this.items = items;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Grocery grocery, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mOnItemClickListener = mItemClickListener;
    }

    public class originalViewHolder extends RecyclerView.ViewHolder{
        public TextView codeBarcode, namaBarang, hargaJual;
        public View layoutparent;
        public originalViewHolder(View itemView) {
            super(itemView);
            codeBarcode = (TextView)itemView.findViewById(R.id.adpTxtcodebarcode);
            namaBarang = (TextView)itemView.findViewById(R.id.adpTxtnamaBarang);
            hargaJual = (TextView) itemView.findViewById(R.id.adpTxtharga);
            layoutparent = itemView.findViewById(R.id.layoutParent);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlstbarang,parent,false);
        vh = new originalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            if(holder instanceof originalViewHolder){
                originalViewHolder view = (originalViewHolder) holder;
                Grocery grocery = items.get(position);
                view.codeBarcode.setText( grocery.getCodebarcode());
                view.namaBarang.setText(grocery.getNamabarang());
                numberFormat = NumberFormat.getCurrencyInstance(localeID);
                try{
                    if(grocery.getModal()!=null){
                        view.hargaJual.setText(numberFormat.format(Integer.valueOf(grocery.getHargajual())));
                    }else{
                        view.hargaJual.setText("Rp0");
                    }

                }catch (Exception e){

                }
                view.layoutparent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(view, items.get(position), position);
                        }
                    }
                });

            }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
