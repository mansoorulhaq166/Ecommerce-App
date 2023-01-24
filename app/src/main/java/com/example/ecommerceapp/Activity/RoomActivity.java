package com.example.ecommerceapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ecommerceapp.AppDataBase;
import com.example.ecommerceapp.Interface.ProductDao;
import com.example.ecommerceapp.Model.Product;
import com.example.ecommerceapp.R;

public class RoomActivity extends AppCompatActivity {

    EditText editTextId, editTextName, editTextPrice, editTextQuantity;
    Button save, check;
    TextView notifyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        editTextId = findViewById(R.id.id_text);
        editTextName = findViewById(R.id.name_text);
        editTextPrice = findViewById(R.id.price_text);
        editTextQuantity = findViewById(R.id.quantity_text);
        save = findViewById(R.id.save_product);
        check = findViewById(R.id.check_cart);
        notifyText = findViewById(R.id.lbl_text);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDataBase dataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class,
                        "cart_db").allowMainThreadQueries().build();
                ProductDao productDao = dataBase.productDao();

                Boolean check = productDao.is_Exist(Integer.parseInt(editTextId.getText().toString()));
                if (!check) {
                    int pid = Integer.parseInt(editTextId.getText().toString());
                    String name = editTextName.getText().toString();
                    int price = Integer.parseInt(editTextPrice.getText().toString());
                    int quantity = Integer.parseInt(editTextQuantity.getText().toString());
                    productDao.insertRecord(new Product(pid, name, price, quantity));

                    editTextId.setText("");
                    editTextName.setText("");
                    editTextPrice.setText("");
                    editTextQuantity.setText("");
                    notifyText.setTextColor(getResources().getColor(R.color.green));
                    notifyText.setText("Product Inserted Successfully");
                } else {
                    editTextId.setText("");
                    editTextName.setText("");
                    editTextPrice.setText("");
                    editTextQuantity.setText("");
                    notifyText.setTextColor(getResources().getColor(R.color.red));
                    notifyText.setText("Product Already in Cart");
                }
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoomActivity.this, CartActivity.class));
            }
        });
    }
}