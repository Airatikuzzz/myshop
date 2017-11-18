package com.example.maira.myshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;
import java.util.UUID;

/**
 * Created by maira on 15.11.2017.
 */

public class ProductViewPagerActivity extends AppCompatActivity{
    public static final String EXTRA_PRODUCT_ID = "com.exapmle.maira.myshop.product_id";
    private ViewPager mViewPager;
    private List<Product> mProductList;
    private static UUID uuidReserve;

    public static Intent newIntent(Context context, UUID uuid){
        Intent intent = new Intent(context, ProductViewPagerActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, uuid);
        uuidReserve = uuid;
        return intent;
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(EXTRA_PRODUCT_ID, uuidReserve);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        UUID uuid;
        if(savedInstanceState!=null){
            uuid = (UUID)savedInstanceState.getSerializable(EXTRA_PRODUCT_ID);
        }
        else {
            uuid = (UUID) getIntent().getSerializableExtra(EXTRA_PRODUCT_ID);
        }
        //Log.d("kek3", uuid.toString());

        mViewPager = (ViewPager) findViewById(R.id.product_activity_view_pager);
        mProductList = ProductLab.get(this).getProducts();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Product product = mProductList.get(position);
                return ProductFragment.newInstance(product.getId());
            }

            @Override
            public int getCount() {
                return mProductList.size();
            }
        });
        for (int i = 0; i < mProductList.size(); i++) {
            if (mProductList.get(i).getId().equals(uuid)) {
                mViewPager.setCurrentItem(i);
                break;
            }

        }
    }

}
