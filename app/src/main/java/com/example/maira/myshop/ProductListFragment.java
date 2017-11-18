package com.example.maira.myshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maira on 12.11.2017.
 */

public class ProductListFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    List<Product> mProducts;
    RecyclerView mRecyclerView;

    Handler mHandler;
    DrawerLayout drawer;

    ProductAdapter mProductAdapter;
    public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_basket_button, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_basket);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getActivity(), BasketActivity.class);
        startActivity(intent);
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);

        Log.d("kek", "done1");

        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_recycler);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(manager);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);

        drawer = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) v.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ProductLab productLab = ProductLab.get(getActivity(), this);
        mProducts = productLab.getProducts();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle;
        private TextView mPrice;
        private ImageView mImage;
        private Product mProduct;

        public ProductHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            mImage = (ImageView) itemView.findViewById(R.id.imageViewSmall);
            itemView.setOnClickListener(this);
        }

        public void bindHolder(Product product) {
            mProduct = product;
            mTitle.setText(mProduct.getTitle());
            mPrice.setText(mProduct.getPrice().toString());
            Picasso.with(getActivity())
                    .load(mProduct.getUrlImage().toString())
                    .into(mImage);
        }

        @Override
        public void onClick(View v) {
            Intent intent = ProductViewPagerActivity.newIntent(getActivity(), mProduct.getId());
            startActivity(intent);
        }
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            getActivity().onBackPressed();
        }
    }


    public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

        private List<Product> mProductList;

        public ProductAdapter(List<Product> products) {
            mProductList = products;
        }

        public List<Product> getProducts() {
            return mProductList;
        }


        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.product_list_item, parent, false);
            return new ProductHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {
            Product product = mProductList.get(position);
            Log.d("kek3", product.toString());
            holder.bindHolder(product);
        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }
    }


    public void updateUIRes(){
        updateUI();
    }
    private void updateUI() {
        //addSneakersToDB();
        mProductAdapter = new ProductAdapter(mProducts);
        Log.d("kek2", "зашел в updateUI");
        mRecyclerView.setAdapter(mProductAdapter);
        Log.d("kek2", "зашел в updateUI");
        mProductAdapter.notifyDataSetChanged();
        Log.d("kek2", "зашел в updateUI");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.delivery_activity:
                Intent i = new Intent(getActivity(), DeliveryActivity.class);
                startActivity(i);
                break;
            case R.id.nac_basket:
                Intent intent = new Intent(getActivity(), BasketActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

