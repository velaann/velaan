package com.example.velaann;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProductViewholder> {
   /* public RecyclerAdapter(String sellername, String sellerlocation, String sellercontact,
                           String productprice, String productquantity, String productvalidity, Context context) {
        this.sellername = sellername;
        this.sellerlocation = sellerlocation;
        this.sellercontact = sellercontact;
        this.productprice = productprice;
        this.productquantity = productquantity;
        this.productvalidity = productvalidity;
        this.context = context;
    }*/

     ArrayList<String> sellername;
        ArrayList<String>productprice;
        ArrayList<String>productquantity;
        ArrayList<String>productvalidity;

    ArrayList<String>sellerlocation;
    ArrayList<String>sellercontact;
    private Context context;

    public RecyclerAdapter(ArrayList<String> sellername, ArrayList<String> productprice, ArrayList<String> productquantity, ArrayList<String> productvalidity,
                           ArrayList<String> sellerlocation, ArrayList<String> sellercontact, Context context) {
        this.sellername = sellername;
        this.productprice = productprice;
        this.productquantity = productquantity;
        this.productvalidity = productvalidity;
        this.sellerlocation = sellerlocation;
        this.sellercontact = sellercontact;
        this.context = context;
    }

   //String sellername,sellerlocation,sellercontact,productprice,productquantity,productvalidity;


    /*public RecyclerAdapter(ArrayList<String> sellername, ArrayList<String> productprice, ArrayList<String> productquantity,
                           ArrayList<String> productvalidity, ArrayList<String> sellerlocation, ArrayList<String> sellercontact, Context context) {
        this.sellername = sellername;
        this.productprice = productprice;
        this.productquantity = productquantity;
        this.productvalidity = productvalidity;
        this.sellerlocation = sellerlocation;
        this.sellercontact = sellercontact;
        this.context = context;
    }*/

    @NonNull
    @Override
    public ProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_list_item,parent,false);
        return new ProductViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewholder holder, int position) {
        holder.name.setText(sellername.get(position));
        holder.quantity.setText(productquantity.get(position));
        holder.price.setText(productprice.get(position));
        holder.validity.setText(productvalidity.get(position));
        holder.location.setText(sellerlocation.get(position));
        holder.contact.setText(sellercontact.get(position));

    }

    @Override
    public int getItemCount() {
        return sellername.size();
    }

   /* @Override
    public int getItemCount() {
        return sellername.size();
    }*/

    public class ProductViewholder extends RecyclerView.ViewHolder {
        TextView name,location,contact,price,quantity,validity;
        public ProductViewholder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.textView31);
            quantity=itemView.findViewById(R.id.textView32);
            price=itemView.findViewById(R.id.textView33);
            validity=itemView.findViewById(R.id.textView34);
            location=itemView.findViewById(R.id.textView35);
            contact=itemView.findViewById(R.id.textView36);

        }
    }
}
