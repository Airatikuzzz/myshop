package com.example.maira.myshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by maira on 12.11.2017.
 */

public class ProductLab {
    private static ProductLab sProductLab;
    private Context mContext;
     private ProductListFragment mFragment;

    final List<Product> products = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference myRef;

    public static ProductLab get(Context context){
        if(sProductLab == null){
            sProductLab = new ProductLab(context);
        }
        return sProductLab;
    }

    public static ProductLab get(Context context, ProductListFragment fragment){
        if(sProductLab == null){
            sProductLab = new ProductLab(context, fragment);
        }
        return sProductLab;
    }


    private ProductLab(Context context){
        //clearBasket();
        //mAppContext = appContext;
        mContext = context.getApplicationContext();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d :  dataSnapshot.getChildren()){
                    Product p = d.getValue(Product.class);
                    Log.d("kek2", "loaded : "+p.toString());
                    products.add(p);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private ProductLab(Context context, final ProductListFragment fragment){
        //clearBasket();
        //mAppContext = appContext;
        mContext = context.getApplicationContext();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d :  dataSnapshot.getChildren()){
                    Product p = d.getValue(Product.class);
                    Log.d("kek2", "loaded : "+p.toString());
                    products.add(p);
                }
                fragment.updateUIRes();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List<Product> getProducts(){
        return products;
    }


}
