package com.example.maira.myshop;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by maira on 12.11.2017.
 */

public class Product {

    public Product(UUID id, String title, String price, String urlImage, String description) {
        mId = id;
        mTitle = title;
        mPrice = price;
        mUrlImage = urlImage;
        mDescription = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mPrice='" + mPrice + '\'' +
                ", mUrlImage='" + mUrlImage + '\'' +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }

    @SerializedName("mId")
    private UUID mId;
    @SerializedName("mTitle")
    private String mTitle;
    @SerializedName("mPrice")
    private String mPrice;
    @SerializedName("mUrlImage")
    private String mUrlImage;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    private String mDescription;


    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getUrlImage() {
        return mUrlImage;
    }

    public void setUrlImage(String urlImage) {
        mUrlImage = urlImage;
    }

    public Product(UUID uuid) {
        mId = uuid;
    }

    public Product(){
        this(UUID.randomUUID());
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
