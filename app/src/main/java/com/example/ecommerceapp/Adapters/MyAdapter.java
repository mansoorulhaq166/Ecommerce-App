package com.example.ecommerceapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.ecommerceapp.AppDataBase;
import com.example.ecommerceapp.Interface.ProductDao;
import com.example.ecommerceapp.Model.Product;
import com.example.ecommerceapp.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Product> products;
    TextView rateView;

    public MyAdapter(List<Product> products, TextView rateView) {
        this.products = products;
        this.rateView = rateView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        holder.recId.setText(String.valueOf(products.get(adapterPosition).getPid()));
        holder.recName.setText(products.get(adapterPosition).getPname());
        holder.recPrice.setText(String.valueOf(products.get(adapterPosition).getPrice()));
        holder.recQuantity.setText(String.valueOf(products.get(adapterPosition).getQnt()));

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDataBase dataBase = Room.databaseBuilder(holder.recId.getContext(), AppDataBase.class,
                        "cart_db").allowMainThreadQueries().build();
                ProductDao productDao = dataBase.productDao();
                productDao.deleteById(products.get(adapterPosition).getPid());
                products.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                updatePrice();
            }
        });
    }

    private void updatePrice() {
        int sum = 0, i;
        for (i=0; i<products.size();i++) {
            sum = sum+(products.get(i).getPrice()*products.get(i).getQnt());

            rateView.setText("Total Amount: PKR " + sum);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView recId, recName, recPrice, recQuantity;
        ImageButton delBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recId = itemView.findViewById(R.id.recid);
            recName = itemView.findViewById(R.id.recpname);
            recPrice = itemView.findViewById(R.id.recpprice);
            recQuantity = itemView.findViewById(R.id.recqnt);
            delBtn = itemView.findViewById(R.id.delbtn);

        }
    }
}
