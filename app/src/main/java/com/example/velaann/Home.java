package com.example.velaann;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.velaann.Interface.SubCategoryOnClickInterface;
import com.example.velaann.Model.Category;
import com.example.velaann.Model.CategoryTwo;
import com.example.velaann.Model.UploadItem;
import com.example.velaann.ViewHolder.CategoryTwoViewHolder;
import com.example.velaann.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FirebaseAuth fAuth;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    FirebaseRecyclerAdapter<CategoryTwo, CategoryTwoViewHolder> adapter2;
    RecyclerView.LayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        fAuth=FirebaseAuth.getInstance();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.Home:

                        Intent intent = new Intent(Home.this, Home.class);
                        startActivity(intent);
                        break;

                    case R.id.MyProfile:
                           Intent intent1 = new Intent(Home.this,MyProfile.class);
                           startActivity(intent1);
                           break;

                    case R.id.Contactus:
                        Intent intent3 = new Intent(Home.this,Contactus.class);
                        startActivity(intent3);
                             break;
                    case R.id.Logout:
                                 fAuth.signOut();
                        Intent intent4= new Intent(Home.this,MainActivity.class);
                        startActivity(intent4);
                                 break;
                    case R.id.your_order:
                        Intent intent5= new Intent(Home.this,your_sellings.class);
                        startActivity(intent5);
                        break;



//Paste your privacy policy link

//                    case  R.id.nav_Policy:{
//
//                        Intent browserIntent  = new Intent(Intent.ACTION_VIEW , Uri.parse(""));
//                        startActivity(browserIntent);
//
//                    }
                    //       break;
                    case  R.id.Share:{

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));

                    }
                    break;
                }
                return false;
            }
        });
       /* btn_add_item = findViewById(R.id.btn_add_item);
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });*/

        //firebase init
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Category");

        manager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(manager);

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(reference,Category.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i, @NonNull final Category category) {

                categoryViewHolder.categoryName.setText(category.getCategoryName());
                FirebaseRecyclerOptions<CategoryTwo> options2 = new FirebaseRecyclerOptions.Builder<CategoryTwo>()
                        .setQuery(reference.child(category.getCategoryId()).child("data"),CategoryTwo.class)
                        .build();

                adapter2 = new FirebaseRecyclerAdapter<CategoryTwo, CategoryTwoViewHolder>(options2) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryTwoViewHolder categoryTwoViewHolder, int i, @NonNull final CategoryTwo categoryTwo) {
                       // categoryTwoViewHolder.dataId.setText(categoryTwo.getDataId());
                        categoryTwoViewHolder.dataName.setText(categoryTwo.getDataName());
                        Picasso.get().load(categoryTwo.getImage()).into(categoryTwoViewHolder.image);
                       // categoryTwoViewHolder.dataAge.setText(categoryTwo.getDataAge());
                       categoryTwoViewHolder.SubCategoryInterfaceClick(new SubCategoryOnClickInterface() {
                            @Override
                            public void onClick(View view, boolean isLongPressed) {
                                Intent intent = new Intent(Home.this,simple_display.class);
                              //  intent.putExtra("userId",categoryTwo.getDataId());
                                 intent.putExtra("userName",categoryTwo.getDataName());
                                 intent.putExtra("image",categoryTwo.getImage());
                                 //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                             //   intent.putExtra("userAge",categoryTwo.getDataAge());
                                  startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CategoryTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v2 = LayoutInflater.from(getBaseContext())
                                .inflate(R.layout.item_view_two,parent,false);
                        return new CategoryTwoViewHolder(v2);
                    }
                };
                adapter2.startListening();
                adapter2.notifyDataSetChanged();
                categoryViewHolder.category_recyclerView.setAdapter(adapter2);
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v1 = LayoutInflater.from(getBaseContext())
                        .inflate(R.layout.item_view_one,parent,false);
                return new CategoryViewHolder(v1);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
}
