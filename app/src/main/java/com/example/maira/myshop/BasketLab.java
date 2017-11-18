package com.example.maira.myshop;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maira on 18.11.2017.
 */

public class BasketLab {
    private List<Product> mBasket = new ArrayList<>();

    private static BasketLab sBasketLab;

    private Context mContext;

    public static BasketLab get(Context context){
        if(sBasketLab == null){
            sBasketLab = new BasketLab(context);
        }
        return sBasketLab;
    }

    private BasketLab(Context context){
        //clearBasket();
        //mAppContext = appContext;
        mContext = context.getApplicationContext();

    }

    public void addProductToBasket(Product product){
        mBasket.add(product);
        String json = new Gson().toJson(mBasket);
        BasketPreferences.setStoredQuery(mContext, json);
    }

    public List<Product> getProductsFromBasket(){
        Type type = new TypeToken<List<Product>>(){}.getType();
        List<Product> basket= new Gson().fromJson(BasketPreferences.getStoredQuery(mContext),type);
        mBasket = basket;
        return basket;
    }

    public boolean isEmptyBasket(){
        if(BasketPreferences.getStoredQuery(mContext).contains("")){
            return true;
        }
        return false;
    }

    public void clearBasket(){
        mBasket.clear();
        String json = new Gson().toJson(mBasket);
        BasketPreferences.setStoredQuery(mContext, json);
    }
}
