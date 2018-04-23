package com.come335.burak.erdem.yemeklik;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Erdem on 21-Apr-18.
 */

public class DetailsActivity extends AppCompatActivity {

    int mealId;
    String mealName;
    String mealContent;
    String mealImage;
    float ratingPointsToSend;
    float mealRating;
    float mealTotalPoints;
    int mealTimesRated;
    boolean boolSetDetails;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Button btnRate;
    Button btnShare;
    RatingBar ratingBar;
    float ratingBarValue;
    RelativeLayout ratingLayout;
    Button btnSubmitRate;

    TextView nameT;
    TextView contentT;
    ImageView imageV;
    TextView ratingT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_details);


////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /*Initializing Variables*/

        btnRate = findViewById(R.id.btnRate);
        btnShare = findViewById(R.id.btnShare);
        ratingBar = findViewById(R.id.ratingBar);
        ratingLayout = findViewById(R.id.ratingLayout);
        ratingLayout.setVisibility(View.GONE);
        btnSubmitRate = findViewById(R.id.btnSubmit);

        ratingT = findViewById(R.id.tv_Rating);
        nameT = findViewById(R.id.tv_name);
        contentT = findViewById(R.id.tv_content);
        imageV = findViewById(R.id.iv_meal);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        boolSetDetails = true;

////////////////////////////////////////////////////////////////////////////////////////////////////

        btnRate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ratingLayout.setVisibility(View.VISIBLE);
                btnRate.setClickable(false);
                btnShare.setClickable(false);
            }
        });

        btnSubmitRate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ratingLayout.setVisibility(View.GONE);
                btnRate.setClickable(true);
                btnShare.setClickable(true);
                myRef.child("meal").child(String.valueOf(mealId)).child("totalPoints").setValue(mealTotalPoints + ratingBarValue);
                myRef.child("meal").child(String.valueOf(mealId)).child("timesRated").setValue(mealTimesRated + 1);
            }
        });

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    SingleMeal tempMeal = new SingleMeal();
                    tempMeal.setName(ds.child(String.valueOf(mealId)).getValue(SingleMeal.class).getName());
                    tempMeal.setContent(ds.child(String.valueOf(mealId)).getValue(SingleMeal.class).getContent());
                    tempMeal.setPhotoURL(ds.child(String.valueOf(mealId)).getValue(SingleMeal.class).getPhotoURL());
                    tempMeal.setTimesRated(ds.child(String.valueOf(mealId)).getValue(SingleMeal.class).getTimesRated());
                    tempMeal.setTotalPoints(ds.child(String.valueOf(mealId)).getValue(SingleMeal.class).getTotalPoints());

                    mealName = tempMeal.getName();
                    mealContent = tempMeal.getContent();
                    mealImage = tempMeal.getPhotoURL();
                    mealTimesRated = tempMeal.getTimesRated();
                    mealTotalPoints = tempMeal.getTotalPoints();
                    mealRating = tempMeal.calculateRating(mealTotalPoints, mealTimesRated);

                    myRef.child("meal").child(String.valueOf(mealId)).child("rating").setValue(mealRating);
                    ratingT.setText(String.valueOf(mealRating));

                    setDetails(boolSetDetails);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        getIdWithIntent();
        listenerForRatingBar();


    }///////////////////////////////OnCreateEnds////////////////////////////////////////////////////

    private void getIdWithIntent()
    {
        mealId = getIntent().getIntExtra("id", 0);
    }

    private void setDetails(boolean _bool)
    {
        if(_bool == true)
        {
            boolSetDetails = false;

            nameT.setText(mealName);

            contentT.setText(mealContent);

            Glide.with(this).asBitmap().load(mealImage).into(imageV);
        }

    }

    public void listenerForRatingBar()
    {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
            {
                ratingBarValue = v;
            }
        });
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
