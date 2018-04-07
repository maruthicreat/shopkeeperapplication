package com.example.maruthiraja.shopkeeperapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectedItem extends AppCompatActivity {
    Toolbar toolbar;
    String itemid;
    private DatabaseReference mdatabase;
    private DatabaseReference myRef;
    Button del,mod;
    ImageView imageView;
    ListView listView;
    TextView des,name,title,noofitem;
    TextView price;
    RatingBar rb;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_selected_item);
        itemid = getIntent().getStringExtra("position");
        del = (Button) findViewById(R.id.deletebtn);
        mod = (Button) findViewById(R.id.modifybtn);
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        imageView = (ImageView) findViewById(R.id.imageView2);
        listView = (ListView) findViewById(R.id.reviewlist);
        des = (TextView) findViewById(R.id.selectDescription);
        adapter = new ArrayAdapter<String>(this,
                R.layout.itemreview, listItems);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        price = (TextView) findViewById(R.id.selectPrice);
        noofitem = (TextView) findViewById(R.id.noofitemtext);
        title = (TextView) findViewById(R.id.selectTitle);
        rb = (RatingBar) findViewById(R.id.ratingBar2);
        myRef = FirebaseDatabase.getInstance().getReference().child("product_reviews");
        mdatabase = FirebaseDatabase.getInstance().getReference().child("shop_details");
        mdatabase.child(itemid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String description = (String) dataSnapshot.child("description").getValue();
                    String image = (String) dataSnapshot.child("image").getValue();
                    String pristr = (String) dataSnapshot.child("price").getValue();
                    String rating = (String) dataSnapshot.child("rating").getValue();
                    String titlestr = (String) dataSnapshot.child("title").getValue();
                    String itemno = (String) dataSnapshot.child("quantity").getValue();
                    price.setText(pristr);
                    noofitem.setText(itemno);
                    title.setText(titlestr);
                    des.setText(description);
                    rb.setRating(Float.parseFloat(rating));
                    Picasso.with(getApplicationContext()).load(image).centerCrop().resize(imageView.getMeasuredWidth(),imageView.getMeasuredHeight()).error(R.drawable.ic_broken_image_black_24dp)
                            .placeholder(R.drawable.ic_image_black_24dp).into(imageView);
                }catch (Exception e)
                {
                    finish();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdatabase.child(itemid).removeValue();
                finish();
                Toast.makeText(SelectedItem.this, "Item deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selint = new Intent(getApplicationContext(),ModifyActivity.class);
                Intent intent = selint.putExtra("itemid", itemid);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        insertreview();
        listView.setFocusable(false);
       // Toast.makeText(this, itemid, Toast.LENGTH_SHORT).show();
    }


    private void insertreview() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot da : dataSnapshot.getChildren()) {
                    for (DataSnapshot dd : da.getChildren()) {
                        String check = dd.getKey();
                        System.out.println("check :"+check);
                        System.out.println("item id : "+itemid);
                        if (check.equals(itemid))
                        {
                            String rev = dd.child("review").getValue().toString();
                            System.out.println(rev);
                            listItems.add(rev);
                            listView.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
