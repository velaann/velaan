package com.example.velaann;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class language extends AppCompatActivity {
    Button english,tamil;
    private static final int TIME_INTERVAL=2000;
    private long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        english  = findViewById(R.id.buttonenglish);
        tamil = findViewById(R.id.buttontamil);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppLocale("en");
                Intent intent = new Intent(language.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
           tamil.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   setAppLocale("ta");
                   Intent intent = new Intent(language.this, MainActivity.class);
                   startActivity(intent);
                   finish();
               }
           });
       /* if(getIntent().getBooleanExtra("EXIT",false))
        {
            finish();
            System.exit(0);
        }*/

    }

    private void setAppLocale(String localeCode) {
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
    }
    public void onBackPressed() {
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

}