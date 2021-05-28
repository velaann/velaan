package com.example.velaann;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.velaann.Model.Category;
import com.example.velaann.Model.CategoryTwo;

import com.example.velaann.Model.CategoryTwo;
import com.example.velaann.ViewHolder.CategoryTwoViewHolder;
import com.example.velaann.ViewHolder.CategoryViewHolder;

import com.squareup.picasso.Picasso;

public class simple_display extends AppCompatActivity {

    TextView textView_name;
    ImageView imageView;
    Button sellbutton,buybutton;
    String tempUserName;
    String image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_display);

        Intent intent = getIntent();
        tempUserName = intent.getStringExtra("userName");
        image = intent.getStringExtra("image");
        sellbutton=(Button)findViewById(R.id.buttonsell);
        buybutton=(Button)findViewById(R.id.buttonbuy);

        //view init
        textView_name = findViewById(R.id.main_text_name);
        imageView = findViewById(R.id.main_img);
        if (!image.isEmpty() && !tempUserName.isEmpty()) {
            textView_name.setText(tempUserName);
            Picasso.get().load(image).into(imageView);
        }

        sellbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proname=textView_name.getText().toString();
                Intent intent = new Intent(simple_display.this,selling.class);
                intent.putExtra("productName",proname);
                startActivity(intent);
                sellbutton.setTextColor(Color.WHITE);
            }
        });

        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productname=textView_name.getText().toString();
                Intent intent1=new Intent(simple_display.this,buying.class);
                intent1.putExtra("buyproname",productname);
                startActivity(intent1);
                buybutton.setTextColor(Color.WHITE);
            }
        });


    }
}