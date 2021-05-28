package com.example.velaann;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class splashscreen extends AppCompatActivity {

   TextView txtFood;
   RelativeLayout relativeLayout;
   Animation txtAnimation,layoutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        txtAnimation = AnimationUtils.loadAnimation(splashscreen.this,R.anim.fall_down);
        layoutAnimation = AnimationUtils.loadAnimation(splashscreen.this,R.anim.bottom_to_top);

        txtFood = findViewById(R.id.txtFood);
        relativeLayout = findViewById(R.id.relMain);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setAnimation(layoutAnimation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtFood.setVisibility(View.VISIBLE);

                        txtFood.setAnimation(txtAnimation);
                    }
                },500);
            }
        },1000);
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent = new Intent(splashscreen.this,language.class);
               startActivity(intent);
           }
       },6000) ;
       if(getIntent().getBooleanExtra("EXIT",false))
       {
           finish();
           System.exit(0);
       }
    }
}