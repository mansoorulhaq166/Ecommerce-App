package com.example.ecommerceapp.Interface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ecommerceapp.Model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertRecord(Product product);

    @Query("Select Exists(Select * from Product where pid = :productId)")
    Boolean is_Exist(int productId);

    @Query("Select * from Product")
    List<Product> getAllProduct();

    @Query("Delete from Product where pid = :id")
    void deleteById(int id);
}
