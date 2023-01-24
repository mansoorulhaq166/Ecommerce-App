package com.example.ecommerceapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ecommerceapp.Adapters.MyAdapter;
import com.example.ecommerceapp.AppDataBase;
import com.example.ecommerceapp.Interface.ProductDao;
import com.example.ecommerceapp.Model.Product;
import com.example.ecommerceapp.R;

import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView rateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Objects.requireNonNull(getSupportActionBar()).hide();
        rateView = findViewById(R.id.rateview);
        getroomdata();
    }

    private void getroomdata() {
        AppDataBase appDataBase = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,
                "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = appDataBase.productDao();

        recyclerView = findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Product> products = productDao.getAllProduct();

        MyAdapter myAdapter = new MyAdapter(products, rateView);
        recyclerView.setAdapter(myAdapter);

        int sum = 0, i;
        for (i=0; i<products.size();i++) {
            sum = sum+(products.get(i).getPrice()*products.get(i).getQnt());

            rateView.setText("Total Amount: PKR " + sum);
        }
    }
}