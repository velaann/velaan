package com.example.velaann;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.velaann.ui.loadingdialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;



import static android.graphics.Color.green;

public class MainActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL=2000;
    private long backPressed;


     TextView signup,forgotpasssword;
    EditText emaillogin,passwordlogin;
    FirebaseAuth fauthlogin;
    Spinner spinner3;
    ArrayAdapter adapter3;
    Button login,mBtn,tamilbutton;
    /*ProgressBar progressBarlogin;*/
    AwesomeValidation awesomeValidation;

    //@SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setAppLocale("ta");
        setContentView(R.layout.activity_main);
        /*spinner3=findViewById(R.id.spinnerlang);
        adapter3 = ArrayAdapter.createFromResource(this,R.array.language, android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        String sele_lang=spinner3.getSelectedItem().toString().trim();
        if(sele_lang=="தமிழ்") {
            setAppLocale("ta");

        }
        else
        {
            setAppLocale("en");
        }*/




        loadingdialog loadingDialog = new loadingdialog(MainActivity.this);
      //  tamilbutton=findViewById(R.id.tamilbutton);
        signup = (TextView) findViewById(R.id.signup);
        forgotpasssword=findViewById(R.id.forrgotpasswordlogin);
        emaillogin = (EditText) findViewById(R.id.email);
        passwordlogin = (EditText) findViewById(R.id.password);
        /*progressBarlogin = (ProgressBar)findViewById(R.id.progressBarlogin);*/
        login = (Button) findViewById(R.id.loginbutton);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        fauthlogin = FirebaseAuth.getInstance();
        awesomeValidation.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.password,".{6,}",R.string.invalid_password);
        if(fauthlogin.getCurrentUser()!=null)
        {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
        }


     /*   tamilbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppLocale("ta");
            }


        });*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){
                    String email = emaillogin.getText().toString().trim();
                    String password = passwordlogin.getText().toString().trim();
                   loadingDialog.startLoadingDialog();
                    fauthlogin.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {

                                Intent intent = new Intent(MainActivity.this,Home.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),"Succesfully logged in",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                /*progressBarlogin.setVisibility(View.GONE);*/
                            }
                        }
                    });

                }



            }
        });
        forgotpasssword.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetmail=new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter registered mail to recieve password reset link!");
                passwordResetDialog.setView(resetmail);

                passwordResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail=resetmail.getText().toString();
                        fauthlogin.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Reset link has been sent to your registered mail!",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"Error in resetting password!"+ e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
                        //startActivity(intent1);

                    }
                });
                passwordResetDialog.create().show();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Authentication.class);
                startActivity(intent);
                signup.setTextColor(Color.GREEN);
            }
        });


    }
    public void onBackPressed(){
        /*Intent intentback = new Intent(getApplicationContext(),splashscreen.class);
        intentback.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentback.putExtra("EXIT",true);
        startActivity(intentback);
        finish();
        System.exit(0);*/

        if (backPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else
        {
            Toast.makeText(getBaseContext(),"press back again to exit app",Toast.LENGTH_SHORT).show();
            Intent intentback = new Intent(getApplicationContext(),splashscreen.class);
            intentback.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentback.putExtra("EXIT",true);
            startActivity(intentback);
            finish();
            System.exit(0);

        }
        backPressed=System.currentTimeMillis();
    }

































































 /*   private void setAppLocale(String localeCode) {
        Resources res= getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            conf.setLocale(new Locale(localeCode.toLowerCase()));
        }
        else{
            conf.locale = new Locale(localeCode.toLowerCase());
        }
        res.updateConfiguration(conf,dm);
    }*/


}