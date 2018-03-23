package com.example.maruthiraja.shopkeeperapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class upload_items extends AppCompatActivity {

    private ImageView imageView ;
    private static final int GALLERY_REQUEST = 1;
    private EditText item_name;
    private EditText item_price;
    private EditText discription;
    private EditText item_quantity;
    private Uri imageUri = null;
    private StorageReference mstorage;
    private DatabaseReference mdatabase;
    private ProgressDialog mprograss;
    private Button upbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_upload_items);
        imageView = (ImageView) findViewById(R.id.imageView4_up);
        item_name = (EditText) findViewById(R.id.item_name_up);
        item_price = (EditText) findViewById(R.id.price_up);
        item_quantity = (EditText) findViewById(R.id.item_quantity);
        discription = (EditText) findViewById(R.id.discription);
        mstorage = FirebaseStorage.getInstance().getReference();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("shop_details");
        mprograss = new ProgressDialog(this);
        upbutton = (Button) findViewById(R.id.upload_button);
        upbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mprograss.setMessage("Uploading...!!!");
                mprograss.show();

                final String item = item_name.getText().toString().trim();
                final String price = item_price.getText().toString().trim();
                final String discrip = discription.getText().toString().trim();
                final String qnty = item_quantity.getText().toString().trim();

                if (!TextUtils.isEmpty(item) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(discrip) && imageUri != null && !TextUtils.isEmpty(qnty))
                {
                    StorageReference filepath = mstorage.child("images").child(imageUri.getLastPathSegment());

                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            DatabaseReference newpath = mdatabase.push();
                            newpath.child("title").setValue(item);
                            newpath.child("price").setValue(price);
                            newpath.child("description").setValue(discrip);
                            newpath.child("image").setValue(downloadUrl.toString());
                            newpath.child("review").setValue("none");
                            newpath.child("rating").setValue("-1");
                            newpath.child("quantity").setValue(qnty);
                            newpath.child("id").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                            newpath.child("id_title").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()+"_"+item);
                            mprograss.dismiss();
                            Toast.makeText(upload_items.this, "Upload successfull...!!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mprograss.dismiss();
                            Toast.makeText(upload_items.this, "Upload failed...!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    mprograss.dismiss();
                    Toast.makeText(upload_items.this, "fields are empty ...!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void opencamer(View view)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}
