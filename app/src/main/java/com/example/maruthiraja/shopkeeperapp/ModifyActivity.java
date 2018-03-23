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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ModifyActivity extends AppCompatActivity {

    private ImageView imageView ;
    private static final int GALLERY_REQUEST = 1;
    private EditText item_name;
    private EditText item_price;
    private EditText discription;
    private String itemid;
    private EditText item_quantity;
    private Uri imageUri = null;
    private StorageReference mstorage;
    private DatabaseReference mdatabase;
    private ProgressDialog mprograss;
    private Button upbutton;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_modify);
        itemid = getIntent().getStringExtra("itemid");
        imageView = (ImageView) findViewById(R.id.imageView4_up);
        item_name = (EditText) findViewById(R.id.item_name_up);
        item_price = (EditText) findViewById(R.id.price_up);
        item_quantity = (EditText) findViewById(R.id.item_quantity_up);
        discription = (EditText) findViewById(R.id.discription_up);
        mstorage = FirebaseStorage.getInstance().getReference();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("shop_details");
        mprograss = new ProgressDialog(this);
        upbutton = (Button) findViewById(R.id.upload_button);


        mdatabase.child(itemid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String description = (String) dataSnapshot.child("description").getValue();
                    image = (String) dataSnapshot.child("image").getValue();
                    String pristr = (String) dataSnapshot.child("price").getValue();
                    String rating = (String) dataSnapshot.child("rating").getValue();
                    String titlestr = (String) dataSnapshot.child("title").getValue();
                    String itemno = (String) dataSnapshot.child("quantity").getValue();
                    item_price.setText(pristr);
                    item_quantity.setText(itemno);
                    item_name.setText(titlestr);
                    discription.setText(description);
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

        upbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mprograss.setMessage("Updating...!!!");
                mprograss.show();

                final String item = item_name.getText().toString().trim();
                final String price = item_price.getText().toString().trim();
                final String discrip = discription.getText().toString().trim();
                final String qnty = item_quantity.getText().toString().trim();
                if (imageUri == null)
                {
                    DatabaseReference newpath = mdatabase.child(itemid);
                    newpath.child("title").setValue(item);
                    newpath.child("price").setValue(price);
                    newpath.child("description").setValue(discrip);
                    newpath.child("image").setValue(image);
                    newpath.child("review").setValue("none");
                    newpath.child("rating").setValue("-1");
                    newpath.child("quantity").setValue(qnty);
                    newpath.child("id").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    newpath.child("id_title").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()+"_"+item);
                    mprograss.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated successfull...!!!", Toast.LENGTH_SHORT).show();
                }

                if (!TextUtils.isEmpty(item) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(discrip) && imageUri != null && !TextUtils.isEmpty(qnty))
                {
                    StorageReference filepath = mstorage.child("images").child(imageUri.getLastPathSegment());

                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            DatabaseReference newpath = mdatabase.child(itemid);
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
                            Toast.makeText(getApplicationContext(), "Updated successfull...!!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mprograss.dismiss();
                            Toast.makeText(getApplicationContext(), "Update failed...!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    mprograss.dismiss();
                    Toast.makeText(getApplicationContext(), "fields are empty ...!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

  }
    public void opencamermod(View view)
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
