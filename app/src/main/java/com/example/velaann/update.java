package com.example.velaann;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class update extends AppCompatActivity {
    private static final String TAG ="TAG" ;
    String name,quantity,price,date1,date2,username,location,contact;
    TextView t1,t2;
    EditText e1,e2,e3;
    Button button;
    int iend;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userId;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        t1 = findViewById(R.id.si1);
        e1 = findViewById(R.id.si2);
        e2 = findViewById(R.id.si3);
        t2 = findViewById(R.id.si4);
        e3 = findViewById(R.id.si5);
        button = findViewById(R.id.updatebu);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        quantity = intent.getStringExtra("quantity");
        price = intent.getStringExtra("price");
        date1 = intent.getStringExtra("date1");
        date2 = intent.getStringExtra("date2");
        t1.setText(name);
        e1.setText(quantity);
        e2.setText(price);
        t2.setText(date1);
        e3.setText(date2);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                //proname.setText(documentSnapshot.getString("UserName"));
                //procontact.setText(documentSnapshot.getString("contactNo"));
                //email.setText(documentSnapshot.getString("emailId"));
                username = (documentSnapshot.getString("UserName"));
                location = (documentSnapshot.getString("address"));
                contact = (documentSnapshot.getString("contactNo"));
                //procity.setText(documentSnapshot.getString("city"));
                //prostate.setText(documentSnapshot.getString("state"));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 firebaseFirestore = FirebaseFirestore.getInstance();
                 firebaseAuth = FirebaseAuth.getInstance();
                 userId = firebaseAuth.getCurrentUser().getUid();
                 iend = name.indexOf("/");
                 title = name.substring(0,iend);
                DocumentReference documentReference1=firebaseFirestore.collection(""+title).document(userId);
                String s1 = t1.getText().toString();
                String s2 = e1.getText().toString();
                String s3 = e2.getText().toString();
                String s4 = t2.getText().toString();
                String s5 = e3.getText().toString();
                Map<String,Object> user=new HashMap<>();
                user.put("Product_Quantity",s2);
                user.put("Product_Price",s3);
                user.put("Seller_contactNo",contact);
                user.put("Seller_Name",username);
                user.put("Product_Valididty",s4);
                user.put("Seller_Location",location);
                user.put("valid_till",s5);
                documentReference1.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Product Succesfully updated",Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"onsuccess: user profile is created for "+ userId);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"onfailure: "+e.toString());
                    }
                });

                DocumentReference documentReference2=firebaseFirestore.collection("user"+userId).document(title+"["+date1+"]");
                 s1 = t1.getText().toString();
                 s2 = e1.getText().toString();
                 s3 = e2.getText().toString();
                 s4 = t2.getText().toString();
                 s5 = e3.getText().toString();

                Map<String,Object> seller=new HashMap<>();
                seller.put("Product_Quantityy",s2);
                seller.put("Product_Pricee",s3);
                seller.put("Product_Namee",s1);
                //seller.put("Seller_Name",sellerName);
                seller.put("Product_Valididtyy",s4);
                //seller.put("Seller_Location",sellerLoaction);
                seller.put("valid_tilll",s5);
                documentReference2.set(seller).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Updated Product Succesfully saved in your sellings",Toast.LENGTH_LONG).show();
                        Log.d(TAG,"onsuccess: user profile is created for "+ userId);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"onfailure: "+e.toString());


                    }
                });

            }
        });
    }
}