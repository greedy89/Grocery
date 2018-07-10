
package com.senos.seno.grocery.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Grocery implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("namaBarang")
    @Expose
    private String namaBarang;
    @SerializedName("hargaBeli")
    @Expose
    private String hargaBeli;
    @SerializedName("hargaJual")
    @Expose
    private String hargaJual;
    public final static Parcelable.Creator<Grocery> CREATOR = new Creator<Grocery>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Grocery createFromParcel(Parcel in) {
            return new Grocery(in);
        }

        public Grocery[] newArray(int size) {
            return (new Grocery[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8428681302422100779L;

    protected Grocery(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.barcode = ((String) in.readValue((String.class.getClassLoader())));
        this.namaBarang = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaBeli = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaJual = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Grocery() {
    }

    /**
     * 
     * @param id
     * @param barcode
     * @param hargaJual
     * @param namaBarang
     * @param hargaBeli
     */
    public Grocery(String id, String barcode, String namaBarang, String hargaBeli, String hargaJual) {
        super();
        this.id = id;
        this.barcode = barcode;
        this.namaBarang = namaBarang;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Grocery withId(String id) {
        this.id = id;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Grocery withBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public Grocery withNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
        return this;
    }

    public String getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(String hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public Grocery withHargaBeli(String hargaBeli) {
        this.hargaBeli = hargaBeli;
        return this;
    }

    public String getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(String hargaJual) {
        this.hargaJual = hargaJual;
    }

    public Grocery withHargaJual(String hargaJual) {
        this.hargaJual = hargaJual;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(barcode);
        dest.writeValue(namaBarang);
        dest.writeValue(hargaBeli);
        dest.writeValue(hargaJual);
    }

    public int describeContents() {
        return  0;
    }

}
