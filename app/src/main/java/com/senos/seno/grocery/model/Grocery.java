
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
    @SerializedName("codebarcode")
    @Expose
    private String codebarcode;
    @SerializedName("namabarang")
    @Expose
    private String namabarang;
    @SerializedName("hargabarang")
    @Expose
    private String hargabarang;
    @SerializedName("hargabeli")
    @Expose
    private String hargabeli;
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
    private final static long serialVersionUID = -882527037861972779L;

    protected Grocery(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.codebarcode = ((String) in.readValue((String.class.getClassLoader())));
        this.namabarang = ((String) in.readValue((String.class.getClassLoader())));
        this.hargabarang = ((String) in.readValue((String.class.getClassLoader())));
        this.hargabeli = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Grocery() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodebarcode() {
        return codebarcode;
    }

    public void setCodebarcode(String codebarcode) {
        this.codebarcode = codebarcode;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public String getHargabarang() {
        return hargabarang;
    }

    public void setHargabarang(String hargabarang) {
        this.hargabarang = hargabarang;
    }

    public String getHargabeli() {
        return hargabeli;
    }

    public void setHargabeli(String hargabeli) {
        this.hargabeli = hargabeli;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(codebarcode);
        dest.writeValue(namabarang);
        dest.writeValue(hargabarang);
        dest.writeValue(hargabeli);
    }

    public int describeContents() {
        return  0;
    }

}
