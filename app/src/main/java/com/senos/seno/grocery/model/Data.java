
package com.senos.seno.grocery.model;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("grocery")
    @Expose
    private List<Grocery> grocery = null;
    public final static Parcelable.Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -8597494446708650559L;

    protected Data(Parcel in) {
        in.readList(this.grocery, (com.senos.seno.grocery.model.Grocery.class.getClassLoader()));
    }

    public Data() {
    }

    public List<Grocery> getGrocery() {
        return grocery;
    }

    public void setGrocery(List<Grocery> grocery) {
        this.grocery = grocery;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(grocery);
    }

    public int describeContents() {
        return  0;
    }

}
