package com.example.maruthiraja.shopkeeperapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class shopkeeperfirstpage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,HomeFragment.OnFragmentInteractionListener,CartFragment.OnFragmentInteractionListener,MyProfileFragment.OnFragmentInteractionListener
,MyOrderFragment.OnFragmentInteractionListener{

    Fragment fragment = null;
    Class fragmentClass = null;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private MaterialSearchView searchView;
    private RecyclerView itemlist;
    private DatabaseReference mdatabase;
    boolean doubleBackToExitPressedOnce = false;
    LinearLayoutManager horizontalLayoutManagaer;
    StaggeredGridLayoutManager gridLayoutManager;
    private List<String> listitems ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperfirstpage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Toast.makeText(this, "start callled", Toast.LENGTH_SHORT).show();
        fragmentClass = HomeFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("maru","hello");
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("shop_details");
        mAuth = FirebaseAuth.getInstance();
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        //horizontalLayoutManagaer = new LinearLayoutManager(shopkeeperfirstpage.this, LinearLayoutManager.HORIZONTAL, false);

        //itemlist = (RecyclerView) findViewById(R.id.item_list);
        //itemlist.setHasFixedSize(true);
        //itemlist.setLayoutManager(gridLayoutManager);
        listitems = new ArrayList<String>();


        mdatabase.keepSynced(true);


        mdatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String key = ds.getKey();
                    listitems.add(ds.child("title").getValue().toString());
                    setsuggestion(listitems);
                    //Toast.makeText(shopkeeperfirstpage.this, listitems.get(1), Toast.LENGTH_SHORT).show();
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
        });


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(shopkeeperfirstpage.this, "change", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(shopkeeperfirstpage.this,SearchItem.class);
                intent.putExtra("message",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                //Toast.makeText(shopkeeperfirstpage.this, "change", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                //Toast.makeText(shopkeeperfirstpage.this, "show", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
               // Toast.makeText(shopkeeperfirstpage.this, "close", Toast.LENGTH_SHORT).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void floatclick(View view){
        startActivity(new Intent(shopkeeperfirstpage.this,upload_items.class));
    }

    public void setsuggestion(List<String> listitems) {
        String listarr[] = listitems.toArray(new String[listitems.size()]);
       // Toast.makeText(this, listarr[0], Toast.LENGTH_SHORT).show();
        searchView.setSuggestions(listarr);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public static class ItemHolder extends RecyclerView.ViewHolder{

        View mview;

        public ItemHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setItemName(String name)
        {
            System.out.println("name"+name);
            TextView item_n = (TextView) mview.findViewById(R.id.item_name);
            item_n.setText(name);
        }

        public void setPrice(String price)
        {
            System.out.println("price"+price);
            TextView item_p = (TextView) mview.findViewById(R.id.item_price);
            item_p.setText(price);
        }

        public void setDescription(String desc)
        {
            System.out.println("desc"+desc);
            TextView item_d = (TextView) mview.findViewById(R.id.item_description);
            item_d.setText(desc);
        }

        public void setImage(String image)
        {
            System.out.println("image"+image);
            ImageView imageView = (ImageView) mview.findViewById(R.id.item_image);
            Picasso.with(mview.getContext()).load(image).into(imageView);
        }

        public void setRating(String rating)
        {
            System.out.println("Rating"+rating);
            RatingBar ratingBar = (RatingBar) mview.findViewById(R.id.ratingBar);
            Drawable progress = ratingBar.getProgressDrawable();
            DrawableCompat.setTint(progress, Color.WHITE);
            ratingBar.setNumStars(5);
            ratingBar.setRating(Float.parseFloat(rating));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (searchView.isSearchOpen()) {
                searchView.closeSearch();
            } else {
                //super.onBackPressed();
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    // Toast.makeText(this, "finish all activity", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);
            }

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shopkeeperfirstpage, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_logout)
        {
            mAuth.signOut();
            startActivity( new Intent(shopkeeperfirstpage.this,MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragmentClass = HomeFragment.class;
            // Handle the camera action
        } else if (id == R.id.nav_cart) {
            fragmentClass = CartFragment.class;

        } else if (id == R.id.nav_order) {
            fragmentClass = MyOrderFragment.class;

        } else if (id == R.id.nav_account) {
            fragmentClass = MyProfileFragment.class;

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
