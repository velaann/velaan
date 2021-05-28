package com.example.velaann;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;




import java.util.HashMap;
import java.util.Map;

public class selling extends AppCompatActivity {
    public static final String TAG = "TAG";
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    Boolean isAvaialable = false;

    TextView productname,validity;
     EditText quantity,price,contact,name,location,email;
     Button sellbutton;
     String data;
     String pname,pname_eng;
    AwesomeValidation awesomeValidation;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
   // int iend=pname.indexOf("/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling);
        Intent intent = getIntent();
        pname=intent.getStringExtra("productName");
        int iend=pname.indexOf("/");
        pname_eng=pname.substring(0 , iend);
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        productname=(TextView)findViewById(R.id.sell);
        productname.setText(pname);
        quantity=(EditText)findViewById(R.id.sellquantity);
        validity=(TextView) findViewById(R.id.sellvalidity);
        validity.setText(date_n);
        price=(EditText)findViewById(R.id.sellprice);
        contact=(EditText)findViewById(R.id.sellphone);
        name=(EditText)findViewById(R.id.sellname);
        location=(EditText)findViewById(R.id.selllocation);
        email=(EditText)findViewById(R.id.sellemail);
        sellbutton=(Button)findViewById(R.id.sellbutton);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        // text watcher
        price.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if(!price.getText().toString().contains("₹") && !isAvaialable) {
                    price.setText(price.getText() + "₹");
                    isAvaialable = true;
                }
                else if(price.getText().length() == 0 && isAvaialable && !price.getText().toString().contains("₹")){
                    price.setText(price.getText() + "₹");
                    isAvaialable = false;
                }
            }
        });
        quantity.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if(!quantity.getText().toString().contains("KG") && !isAvaialable) {
                    quantity.setText(quantity.getText() + "KG");
                    isAvaialable = true;
                }
                else if(quantity.getText().length() == 0 && isAvaialable && !quantity.getText().toString().contains("KG")){
                    quantity.setText(quantity.getText() + "KG");
                    isAvaialable = false;
                }
            }
        });

        awesomeValidation.addValidation(this,R.id.sellquantity, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.sellprice, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.sellvalidity, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        userId=fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                //proname.setText(documentSnapshot.getString("UserName"));
                //procontact.setText(documentSnapshot.getString("contactNo"));
                email.setText(documentSnapshot.getString("emailId"));
                name.setText(documentSnapshot.getString("UserName"));
                location.setText(documentSnapshot.getString("address"));
                contact.setText(documentSnapshot.getString("contactNo"));
                //procity.setText(documentSnapshot.getString("city"));
                //prostate.setText(documentSnapshot.getString("state"));
            }
        });

        sellbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate())
                {
                    String productQuantity=quantity.getText().toString().trim();
                    String productValidity=validity.getText().toString().trim();
                    String productPrice=price.getText().toString().trim();
                    String sellerContact=contact.getText().toString().trim();
                    String sellerName=name.getText().toString().trim();
                    String sellerLoaction=location.getText().toString().trim();
                    String sellerEmail=email.getText().toString().trim();
                    data=dateButton.getText().toString().trim();
                    DocumentReference documentReference=fStore.collection(""+pname_eng).document(userId);

                    Map<String,Object> user=new HashMap<>();
                    user.put("Product_Quantity",productQuantity);
                    user.put("Product_Price",productPrice);
                    user.put("Seller_contactNo",sellerContact);
                    user.put("Seller_Name",sellerName);
                    user.put("Product_Valididty",productValidity);
                    user.put("Seller_Location",sellerLoaction);
                    user.put("valid_till",data);
                    //user.put("address",address);
                    userId=fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference1=fStore.collection("user"+userId).document(pname_eng+"["+productValidity+"]");

                    Map<String,Object> seller=new HashMap<>();
                    seller.put("Product_Quantityy",productQuantity);
                    seller.put("Product_Pricee",productPrice);
                    seller.put("Product_Namee",pname);
                    //seller.put("Seller_Name",sellerName);
                    seller.put("Product_Valididtyy",productValidity);
                    //seller.put("Seller_Location",sellerLoaction);
                    seller.put("valid_tilll",data);

                    documentReference1.set(seller).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Product Succesfully saved in your sellings",Toast.LENGTH_LONG).show();
                            Log.d(TAG,"onsuccess: user profile is created for "+ userId);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"onfailure: "+e.toString());
                        }
                    });

                    Intent intent = new Intent(selling.this,your_sellings.class);
                    startActivity(intent);
                    sellbutton.setTextColor(Color.BLACK);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Product Succesfully registered",Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"onsuccess: user profile is created for "+ userId);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"onfailure: "+e.toString());
                        }
                    });

                   /* fAuth.createUserWithEmailAndPassword(sellerName,sellerLoaction).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Product Succesfully registered",Toast.LENGTH_SHORT).show();
                                userId=fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=fStore.collection(""+pname).document(userId);

                                Map<String,Object> user=new HashMap<>();
                                user.put("Product_Quantity",productQuantity);
                                user.put("Product_Price",productPrice);
                                user.put("Seller_contactNo",sellerContact);
                                user.put("Seller_Name",sellerName);
                                user.put("Product_Valididty",productValidity);
                                //user.put("address",address);

                                Intent intent = new Intent(selling.this,Home.class);
                                startActivity(intent);
                                sellbutton.setTextColor(Color.BLACK);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"onsuccess: user profile is created for "+ userId);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"onfailure: "+e.toString());
                                    }
                                });
                                   /* documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG,"onsuccess: user profile is created for "+ userId);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG,"onfailure: "+e.toString());
                                        }
                                    });*/
                                   /*Intent intent1 = new Intent(register.this,MainActivity.class);
                                    startActivity(intent1);
                                    registerbutton.setTextColor(Color.BLACK);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }



                        }
                    });*/

                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter valid details",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}