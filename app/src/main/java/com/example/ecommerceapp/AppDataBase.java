package com.example.ecommerceapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ecommerceapp.Interface.ProductDao;
import com.example.ecommerceapp.Model.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract ProductDao productDao();
}
