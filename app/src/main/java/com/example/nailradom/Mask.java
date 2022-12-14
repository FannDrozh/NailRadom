package com.example.nailradom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Mask implements Parcelable {

    private int ID;
    private String Name;
    private String Price;
    private String Image;

    protected Mask(Parcel in){
        ID = in.readInt();
        Name = in.readString();
        Price = in.readString();
    }

    public static final Parcelable.Creator<Mask> CREATOR = new Parcelable.Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Name);
        dest.writeString(Price);
        dest.writeString(Image);
    }

    public Mask(int ID_Nail, String Name, String Price, String Image)
    {
        this.ID = ID_Nail;
        this.Name = Name;
        this.Price = Price;
        this.Image = Image;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}