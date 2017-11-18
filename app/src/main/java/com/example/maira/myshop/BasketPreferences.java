package com.example.maira.myshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by maira on 16.11.2017.
 */

public class BasketPreferences {
    private static final String PREF_BASKET_JSON= "basketdatajson";

    public static String getStoredQuery(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_BASKET_JSON, null);
    }

    public static void setStoredQuery(Context context, String data){
       // Log.d("kek2", data);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_BASKET_JSON, data)
                .apply();
    }
}
