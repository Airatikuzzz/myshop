package com.example.maira.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by maira on 16.11.2017.
 */

public class BasketFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TextView tvCountProductsBasket;
    private TextView tvAllSum;
    private Button mButton;
    private List<Product> mProducts;
    private BasketAdapter mBasketAdapter;

    public static Fragment newInstance() {
        return new BasketFragment();
    }

    private void uppdateUI(){
        BasketLab basketLab = BasketLab.get(getActivity());
        Double sum = 0.0;
        if(basketLab.isEmptyBasket()){
            Log.d("kek", "зашел");
            mProducts = basketLab.getProductsFromBasket();
            mBasketAdapter = new BasketAdapter(mProducts);
            mRecyclerView.setAdapter(mBasketAdapter);
            mBasketAdapter.notifyDataSetChanged();

            for(Product p : mProducts){
                String s = p.getPrice();
                s = s.replace("$", "");
                Log.d("kek3", s);
                sum+=Double.parseDouble(s);
            }
        }
        Integer a = mProducts.size();
        Log.d("kek", a.toString());
        tvAllSum.setText(sum.toString());
        tvCountProductsBasket.setText(a.toString());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_basket, container, false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recycler_basket);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvAllSum = (TextView)v.findViewById(R.id.tvAllPrice);
        tvCountProductsBasket = (TextView) v.findViewById(R.id.tvCountOfProducts);
        mButton = (Button) v.findViewById(R.id.btnEnd);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CheckOutActivity.class );
                startActivity(i);
            }
        });
        uppdateUI();
        return v;
    }

    public class BasketHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mPrice;
        private ImageView mImage;
        private Product mProduct;

        public BasketHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tvTitleinBasket);
            mPrice = (TextView) itemView.findViewById(R.id.tvPriceInBasket);
            mImage = (ImageView) itemView.findViewById(R.id.imgViewInBasket);
        }

        public void bindHolder(Product product) {
            mProduct = product;
            mTitle.setText(mProduct.getTitle());
            mPrice.setText(mProduct.getPrice().toString());
            Picasso.with(getActivity()) //передаем контекст приложения
                    .load(mProduct.getUrlImage().toString()) //адрес изображения
                    .into(mImage);
        }
    }

    public class BasketAdapter extends RecyclerView.Adapter<BasketHolder> {

        private List<Product> mProductList;

        public BasketAdapter(List<Product> products) {
            mProductList = products;
        }

        public List<Product> getProducts() {
            return mProductList;
        }


        @Override
        public BasketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_in_basket, parent, false);
            return new BasketHolder(view);
        }

        @Override
        public void onBindViewHolder(BasketHolder holder, int position) {
            Product product = mProductList.get(position);
            holder.bindHolder(product);
        }

        @Override
        public int getItemCount() {
            return mProductList.size();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_basket, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            case R.id.clear:{
                BasketLab basketLab = BasketLab.get(getActivity());
                basketLab.clearBasket();
                mBasketAdapter.notifyDataSetChanged();
                return true;
            }
            default:return super.onOptionsItemSelected(item);}
    }

}
