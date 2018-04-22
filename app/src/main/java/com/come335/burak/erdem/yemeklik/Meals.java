package com.come335.burak.erdem.yemeklik;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Erdem on 21-Apr-18.
 */

public class Meals extends AppCompatActivity{

    private ArrayList<Integer> images = new ArrayList<Integer>();
    private ArrayList<String>  names = new ArrayList<String>();
    private StorageReference mStorageRef;
    private StorageReference riversRef;
    private ImageView tempImage;


    RecyclerView rView;
    RecyclerView.Adapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals_recyclerview);


////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /*Initializing Variables*/

        images.add(R.drawable.iskender);
        images.add(R.drawable.manti);
        images.add(R.drawable.iskender);
        images.add(R.drawable.manti);


//        String url = "https://firebasestorage.googleapis.com/v0/b/yemeklik-e3353.appspot.com/o/karniyarik.jpg?alt=media&token=9b1e8e0d-148a-410f-a638-2db334e63338";
//        tempImage = findViewById(R.id.tempImage);
//        Glide.with(getApplicationContext()).load(url).into(tempImage);
//        int drawableId = Integer.parseInt(tempImage.getTag().toString());
//        images.add(drawableId);


        names.add("iskender");
        names.add("manti");
        names.add("iskender");
        names.add("manti");

        rView = findViewById(R.id.rview);
        rAdapter = new RecyclerViewAdapter(this, images, names);
        rView.setAdapter(rAdapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        riversRef = mStorageRef.child("images/rivers.jpg");


////////////////////////////////////////////////////////////////////////////////////////////////////

    }

//    private void LoadContent()
//    {
//        File localFile = File.createTempFile("images", "jpg");
//        riversRef.getFile(localFile)
//                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        // Successfully downloaded data to local file
//                        // ...
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle failed download
//                // ...
//            }
//        });
//    }

}
