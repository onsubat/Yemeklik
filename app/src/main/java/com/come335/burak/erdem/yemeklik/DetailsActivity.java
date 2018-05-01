package com.come335.burak.erdem.yemeklik;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Erdem on 21-Apr-18.
 */

public class DetailsActivity extends AppCompatActivity
{

    private ArrayList<String> restaurants = new ArrayList<>();

    int mealId;
    String mealName;
    String mealContent;
    String mealImage;
    float mealRating;
    float mealTotalPoints;
    int mealTimesRated;
    boolean boolSetDetailsOnce;
    String selectedRestaurant;
    String nullString;

    float historyCount;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    Button btnRate;
    Button btnMap;
    Button btnShare;
    RatingBar ratingBar;
    float ratingBarValue;
    RelativeLayout ratingLayout;
    Button btnSubmitRate;

    TextView nameT;
    TextView contentT;
    ImageView imageV;
    TextView ratingT;

    private static final String TAG = "DetailsActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_details);

        if(isServicesOK()){
            init();
        }


////////////////////////////////////////////////////////////////////////////////////////////////////
                                    /*Initializing Variables*/

        btnRate = findViewById(R.id.btnRate);
        btnMap = findViewById(R.id.btnMap);
        btnShare = findViewById(R.id.btnShare);
        ratingBar = findViewById(R.id.ratingBar);
        ratingLayout = findViewById(R.id.ratingLayout);
        ratingLayout.setVisibility(View.GONE);
        btnSubmitRate = findViewById(R.id.btnSubmit);

        ratingT = findViewById(R.id.tv_Rating);
        nameT = findViewById(R.id.tv_name);
        contentT = findViewById(R.id.tv_content);
        imageV = findViewById(R.id.iv_meal);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();//ONEMLI!!!DATABASE HİYERARŞİSİNDE İLK ADIMI BURADA ATIYORUZ

        boolSetDetailsOnce = true;
        selectedRestaurant = "null";
        nullString = "null";

////////////////////////////////////////////////////////////////////////////////////////////////////

        btnRate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!selectedRestaurant.equals("null"))
                {
                    ratingLayout.setVisibility(View.VISIBLE);
                    btnRate.setClickable(false);
                    btnShare.setClickable(false);
                    btnMap.setClickable(false);
                    toastMessage("Rating for: " + selectedRestaurant);
                }
                else
                    toastMessage("Restaurant isn't selected");
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
                btnMap.setClickable(true);

                myRef.child("meals").child(String.valueOf(mealId)).child("totalPoints").setValue(mealTotalPoints + ratingBarValue);
                myRef.child("meals").child(String.valueOf(mealId)).child("timesRated").setValue(mealTimesRated + 1);

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                myRef.child("users").child(user.getUid()).child("history").child(String.valueOf((int)historyCount)).child("date").setValue(String.valueOf(formattedDate));
                myRef.child("users").child(user.getUid()).child("history").child(String.valueOf((int)historyCount)).child("mealId").setValue(mealId);
                myRef.child("users").child(user.getUid()).child("history").child(String.valueOf((int)historyCount)).child("givenRate").setValue(ratingBarValue);
                myRef.child("users").child(user.getUid()).child("history").child(String.valueOf((int)historyCount)).child("restaurantName").setValue(selectedRestaurant);
                myRef.child("users").child(user.getUid()).child("selectedRestaurant").setValue("null");
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareSub = "Yemeklik Uygulamasi: Yemegi Paylasma";
                String shareBody = "Yemeklik Uygulamasi:" + "\n" + mealName + "\n" + mealContent;
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "SHARE"));
            }
        });

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                historyCount = dataSnapshot.child("users").child(user.getUid()).child("history").getChildrenCount();

                SingleMeal tempMeal = new SingleMeal();
                UserHistory tempUser = new UserHistory();

                if(boolSetDetailsOnce == true)
                {
                    tempMeal.setName(dataSnapshot.child("meals").child(String.valueOf(mealId)).getValue(SingleMeal.class).getName());
                    tempMeal.setContent(dataSnapshot.child("meals").child(String.valueOf(mealId)).getValue(SingleMeal.class).getContent());
                    tempMeal.setPhotoURL(dataSnapshot.child("meals").child(String.valueOf(mealId)).getValue(SingleMeal.class).getPhotoURL());
                    tempMeal.setRestaurants(dataSnapshot.child("meals").child(String.valueOf(mealId)).getValue(SingleMeal.class).getRestaurants());
                    mealName = tempMeal.getName();
                    mealContent = tempMeal.getContent();
                    mealImage = tempMeal.getPhotoURL();
                    restaurants = tempMeal.getRestaurants();
                }

                tempUser.setSelectedRestaurant(dataSnapshot.child("users").child(user.getUid()).getValue(UserHistory.class).getSelectedRestaurant());
                selectedRestaurant = tempUser.getSelectedRestaurant().toString();

                mealTimesRated = dataSnapshot.child("meals").child(String.valueOf(mealId)).getValue(SingleMeal.class).getTimesRated();
                mealTotalPoints = dataSnapshot.child("meals").child(String.valueOf(mealId)).getValue(SingleMeal.class).getTotalPoints();

                mealRating = tempMeal.calculateRating(mealTotalPoints, mealTimesRated);

                myRef.child("meals").child(String.valueOf(mealId)).child("rating").setValue(mealRating);
                ratingT.setText(String.format("%.1f", mealRating));

                setDetails();
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

    private void init()
    {
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DetailsActivity.this, MapActivity.class);
                intent.putStringArrayListExtra("restaurants", restaurants);
                startActivity(intent);
            }
        });
    }
//////////////////
    public boolean isServicesOK()
    {
        Log.d(TAG, "isServicesOK: Checking Google Services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(DetailsActivity.this);
        if (available== ConnectionResult.SUCCESS)
        {
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }

        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            Log.d(TAG, "isServicesOK: An error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(DetailsActivity.this , available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else
        {
            Toast.makeText(this,"We can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void setDetails()
    {
        if(boolSetDetailsOnce == true)
        {
            boolSetDetailsOnce = false;

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