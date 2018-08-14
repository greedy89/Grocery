
package com.senos.seno.grocery.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.senos.seno.grocery.ApplicationGrocery;

@Table(database = ApplicationGrocery.class)
public class Grocery extends BaseModel implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;
    @SerializedName("codebarcode")
    @Expose
    @Column
    private String codebarcode;
    @SerializedName("namabarang")
    @Expose
    @Column
    private String namabarang;
    @SerializedName("hargajual")
    @Expose
    @Column
    private String hargajual;
    @SerializedName("modal")
    @Expose
    @Column
    private String modal;
    @SerializedName("tanggal")
    @Expose
    @Column
    private String tanggal;
    @SerializedName("status")
    @Expose
    @Column
    private String status;
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
    private final static long serialVersionUID = -2262074399793785271L;

    protected Grocery(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.codebarcode = ((String) in.readValue((String.class.getClassLoader())));
        this.namabarang = ((String) in.readValue((String.class.getClassLoader())));
        this.hargajual = ((String) in.readValue((String.class.getClassLoader())));
        this.modal = ((String) in.readValue((String.class.getClassLoader())));
        this.tanggal = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
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

    public String getHargajual() {
        return hargajual;
    }

    public void setHargajual(String hargajual) {
        this.hargajual = hargajual;
    }

    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(codebarcode);
        dest.writeValue(namabarang);
        dest.writeValue(hargajual);
        dest.writeValue(modal);
        dest.writeValue(tanggal);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
