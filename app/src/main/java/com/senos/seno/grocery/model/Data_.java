
package com.senos.seno.grocery.model;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ implements Serializable, Parcelable
{

    @SerializedName("grocery")
    @Expose
    private List<Grocery> grocery = null;
    public final static Parcelable.Creator<Data_> CREATOR = new Creator<Data_>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data_ createFromParcel(Parcel in) {
            return new Data_(in);
        }

        public Data_[] newArray(int size) {
            return (new Data_[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4791934157435993947L;

    protected Data_(Parcel in) {
        in.readList(this.grocery, (com.senos.seno.grocery.model.Grocery.class.getClassLoader()));
    }

    public Data_() {
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
