package com.example.maira.myshop;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BasketActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return BasketFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
