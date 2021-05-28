package com.example.velaann;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    public static final String TAG = "TAG";
    Spinner spinner1;
    Spinner spinner2;
    ArrayAdapter adapter;
    EditText registernameinput,regcontactinput,regemailinput,regpassword,regconfirmpassword,regaddressinput;
    AwesomeValidation awesomeValidation;
    Button registerbutton;
    ProgressBar progressBarreg;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinner1 = findViewById(R.id.spinnercity);
        spinner2 = findViewById(R.id.spinnerstate);

        adapter = ArrayAdapter.createFromResource(this,R.array.city, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        adapter = ArrayAdapter.createFromResource(this,R.array.state, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        registernameinput=findViewById(R.id.registernameinput);
        regcontactinput= findViewById(R.id.regcontactinput);
        regemailinput=findViewById(R.id.regemailinput);
        registerbutton=findViewById(R.id.registerbutton);
        regpassword=findViewById(R.id.createpassword);
        regconfirmpassword=findViewById(R.id.confirmpassword);
        regaddressinput=findViewById(R.id.regaddressinput);
        progressBarreg=findViewById(R.id.progressBarreg);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
       /*if(fAuth.getCurrentUser()!=null)
        {
            Intent intent = new Intent(register.this,Home.class);
            startActivity(intent);
            finish();
        }*/


        awesomeValidation.addValidation(this,R.id.registernameinput, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.regcontactinput,"[5-9]{1}[0-9]{9}$",R.string.invalid_mobile);
        awesomeValidation.addValidation(this,R.id.regemailinput, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.createpassword,".{6,}",R.string.invalid_password);
        awesomeValidation.addValidation(this,R.id.confirmpassword,R.id.createpassword,R.string.invalid_confirm_password);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate())
                {
                    String email=regemailinput.getText().toString().trim();
                    String password=regpassword.getText().toString().trim();
                    String username=registernameinput.getText().toString().trim();
                    String contact=regcontactinput.getText().toString().trim();
                    String address = regaddressinput.getText().toString().trim();
                    String city=spinner1.getSelectedItem().toString().trim();
                    String state=spinner2.getSelectedItem().toString().trim();
                    progressBarreg.setVisibility(View.VISIBLE);
                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(),"Succesfully registered",Toast.LENGTH_SHORT).show();
                                    userId=fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference=fStore.collection("Users").document(userId);

                                    Map<String,Object> user=new HashMap<>();
                                    user.put("UserName",username);
                                    user.put("emailId",email);
                                    user.put("contactNo",contact);
                                    user.put("state",state);
                                    user.put("city",city);
                                    user.put("address",address);

                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    registerbutton.setTextColor(Color.BLACK);
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
                                    registerbutton.setTextColor(Color.BLACK);*/
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                }



                    }
                    });



                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter valid details",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}