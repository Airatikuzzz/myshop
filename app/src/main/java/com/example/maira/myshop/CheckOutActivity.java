package com.example.maira.myshop;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Button btnCheckOut = (Button) findViewById(R.id.button_check_out);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutActivity.this);
                builder.setTitle("Информация")
                        .setMessage("Ваша заказ принят!")
                        .setIcon(R.drawable.ic_delivery)
                        .setCancelable(true);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
