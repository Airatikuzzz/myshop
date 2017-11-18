package com.example.maira.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.UUID;

/**
 * Created by maira on 15.11.2017.
 */

public class ProductFragment extends Fragment {

    private Product mProduct;

    private Button mButtonBuy;

    private TextView mTitle;
    private TextView mPrice;
    private TextView mDescription;

    private ImageView mImageView;


    private static final String ARG_PRODUCT_ID = "product_id";

    public static ProductFragment newInstance(UUID uuid) {
        Bundle bundle = new Bundle() ;
        bundle.putSerializable(ARG_PRODUCT_ID, uuid);

        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(bundle);
        Log.d("kek3", "in newInstance");
        return fragment;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_basket_button, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_basket);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            case R.id.menu_item_basket:{
                Intent intent = new Intent(getActivity(), BasketActivity.class);
                startActivity(intent);
                return true;
        }
        default:return super.onOptionsItemSelected(item);}
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductLab productLab = ProductLab.get(getActivity());
        List<Product> mProductList = productLab.getProducts();
        UUID uuid = (UUID) getArguments().getSerializable(ARG_PRODUCT_ID);
        //mProduct = productLab.getProduct();

        for (int i = 0; i < mProductList.size(); i++) {
            if (mProductList.get(i).getId().equals(uuid)) {
                mProduct = mProductList.get(i);
            }
        }
        Log.d("kek3", mProduct.getId().toString());

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.activity_product, container, false);
        mTitle = (TextView)v.findViewById(R.id.product_name);
        mPrice = (TextView)v.findViewById(R.id.product_price);
        mDescription = (TextView) v.findViewById(R.id.product_description) ;
        mTitle.setText(mProduct.getTitle());
        mPrice.setText(mProduct.getPrice().toString());
        Log.d("kek1", mProduct.getDescription().toString());
        mDescription.setText(mProduct.getDescription().toString());

        mImageView = (ImageView)v.findViewById(R.id.image_view);
        Picasso.with(getActivity())
                .load(mProduct.getUrlImage())
                .into(mImageView);

        mButtonBuy = (Button) v.findViewById(R.id.btn_buy);
        mButtonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasketLab basketLab = BasketLab.get(getActivity());
                basketLab.addProductToBasket(mProduct);
                Toast.makeText(getActivity(), "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasketLab basketLab = BasketLab.get(getActivity());
                basketLab.addProductToBasket(mProduct);
                Intent intent = new Intent(getActivity(), BasketActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
