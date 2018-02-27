package com.example.maruthiraja.shopkeeperapp;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SelectedItem extends AppCompatActivity {
    Toolbar toolbar;
    String itemid;
    private DatabaseReference mdatabase;
    ImageView imageView;
    TextView des,name,title;
    TextView price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);
        itemid = getIntent().getStringExtra("position");
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        imageView = (ImageView) findViewById(R.id.imageView2);
        des = (TextView) findViewById(R.id.selectDescription);
        price = (TextView) findViewById(R.id.selectPrice);
        title = (TextView) findViewById(R.id.selectTitle);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("shop_details");
        mdatabase.child(itemid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String description = (String) dataSnapshot.child("description").getValue();
                String image = (String) dataSnapshot.child("image").getValue();
                String pristr = (String) dataSnapshot.child("price").getValue();
                String rating = (String) dataSnapshot.child("rating").getValue();
                String titlestr = (String) dataSnapshot.child("title").getValue();
                price.setText(pristr);
                title.setText(titlestr);
                des.setText(description);
                Picasso.with(getApplicationContext()).load(image).centerCrop().resize(imageView.getMeasuredWidth(),imageView.getMeasuredHeight()).error(R.drawable.ic_broken_image_black_24dp)
                        .placeholder(R.drawable.ic_image_black_24dp).into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setSupportActionBar(toolbar);
        Toast.makeText(this, itemid, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
