package com.example.maruthiraja.shopkeeperapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

public class SearchItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        Intent intent = getIntent();

        // 2. get message value from intent
        String message = intent.getStringExtra("message");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


        Button btn = (Button) findViewById(R.id.backbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SearchItem.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
