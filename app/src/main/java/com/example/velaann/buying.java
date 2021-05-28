package com.example.velaann;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class buying extends AppCompatActivity {

    RecyclerView productslist;
    FirebaseFirestore firebaseFirestore;
    String pname,pname_eng;
    FirestoreRecyclerAdapter adapter;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying);

        Intent intent=getIntent();
        pname=intent.getStringExtra("buyproname");
        int iend=pname.indexOf("/");
        pname_eng=pname.substring(0 , iend);
        productslist=(RecyclerView)findViewById(R.id.productslist);
        firebaseFirestore=FirebaseFirestore.getInstance();

        Query query=firebaseFirestore.collection(""+pname_eng);

        FirestoreRecyclerOptions<ProductsModel> options =new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query,ProductsModel.class)
                .build();
         adapter= new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {
                holder.textView1.setText(model.getSeller_Name());
                holder.textView2.setText(model.getProduct_Quantity());
                holder.textView3.setText(model.getProduct_Price());
                holder.textView4.setText(model.getProduct_Valididty());
                holder.textView5.setText(model.getSeller_Location());
                holder.textView6.setText(model.getSeller_contactNo());
                holder.textView7.setText(model.getvalid_till());
            }
        };
         productslist.setHasFixedSize(true);
         productslist.setLayoutManager(new LinearLayoutManager(this));
         productslist.setAdapter(adapter);
    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7;
        Button call,fav;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.textView1);
            textView2=itemView.findViewById(R.id.textView22);
            textView3=itemView.findViewById(R.id.textView24);
            textView4=itemView.findViewById(R.id.productvalidfrom);
            textView5= itemView.findViewById(R.id.textView26);
            textView6=itemView.findViewById(R.id.textView27);
            textView7=itemView.findViewById(R.id.textView28);
            call=itemView.findViewById(R.id.callbutton);


            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     number=textView6.getText().toString();
                     phoneCall(number);
                }
            });

        }
    }
    public void phoneCall(String number)
    {
        if(ContextCompat.checkSelfPermission(buying.this, Manifest.permission.CALL_PHONE)
           != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(buying.this,new String[]{Manifest.permission.CALL_PHONE},100);
        }
        else {
            Intent intentcall = new Intent(Intent.ACTION_CALL);
            intentcall.setData(Uri.parse("tel:" + number));
            startActivity(intentcall);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            Intent intentcall = new Intent(Intent.ACTION_CALL);
            intentcall.setData(Uri.parse("tel:" + number));
            startActivity(intentcall);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}