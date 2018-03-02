package com.example.maruthiraja.shopkeeperapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchItem extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView itemlist;
    private DatabaseReference mdatabase;
    StaggeredGridLayoutManager gridLayoutManager;
    private List<String> listitems ;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        setrecycler();
    }

    private void setrecycler() {
        mdatabase = FirebaseDatabase.getInstance().getReference().child("shop_details");
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mdatabase.keepSynced(true);

       /* mdatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String key = ds.getKey();
                    listitems.add(ds.child("title").getValue().toString());
                    // setsuggestion(listitems);
                    // Toast.makeText(shopkeeperfirstpage.this, listitems.get(1), Toast.LENGTH_SHORT).show();
                   // System.out.println("value");
                }

                //String value = dataSnapshot.getValue(String.class);
                //System.out.println(value);
                // Toast.makeText(viewItem.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Failed to read value.", error.toException());
                System.out.println(error.toException());
            }
        });*/



        //Toast.makeText(this, "called", Toast.LENGTH_SHORT).show();
        itemlist = (RecyclerView) findViewById(R.id.item_list);
        itemlist.setHasFixedSize(true);
        itemlist.setLayoutManager(gridLayoutManager);
        listitems = new ArrayList<String>();
        FirebaseRecyclerAdapter<ItemShow,HomeFragment.ItemHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ItemShow, HomeFragment.ItemHolder>(
                ItemShow.class,
                R.layout.item_list,
                HomeFragment.ItemHolder.class,
                mdatabase.orderByChild("id_title").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()+"_"+message)

        ) {

            @Override
            protected void populateViewHolder(HomeFragment.ItemHolder viewHolder, ItemShow model, int position) {
                viewHolder.setItemName(model.getTitle());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setImage(model.getImage());
                viewHolder.setRating(model.getRating());
            }
        };

        itemlist.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
