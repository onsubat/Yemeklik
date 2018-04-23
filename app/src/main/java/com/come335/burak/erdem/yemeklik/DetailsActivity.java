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

/**
 * Created by Erdem on 21-Apr-18.
 */

public class DetailsActivity extends AppCompatActivity {

    String mealName;
    String mealContent;
    String mealImage;
    float ratingPointsToSend;
    float mealRating;

    Button btnRate;
    Button btnShare;
    RatingBar ratingBar;
    RelativeLayout ratingLayout;
    Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_details);

        btnRate = findViewById(R.id.btnRate);
        btnShare = findViewById(R.id.btnShare);
        ratingBar = findViewById(R.id.ratingBar);
        ratingLayout = findViewById(R.id.ratingLayout);
        ratingLayout.setVisibility(View.GONE);
        btnSubmit = findViewById(R.id.btnSubmit);

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

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ratingLayout.setVisibility(View.GONE);
                btnRate.setClickable(true);
                btnShare.setClickable(true);
            }
        });

        getIncomingIntent();
        listenerForRatingBar();
    }

    private void getIncomingIntent()
    {
        mealName = getIntent().getStringExtra("name");
        mealContent = getIntent().getStringExtra("content");
        mealImage = getIntent().getStringExtra("image");
        mealRating = getIntent().getIntExtra("rating",0);

        setDetails();

    }

    private void setDetails()
    {
        TextView nameT = findViewById(R.id.tv_name);
        nameT.setText(mealName);

        TextView contentT = findViewById(R.id.tv_content);
        contentT.setText(mealContent);

        ImageView imageV = findViewById(R.id.iv_meal);
        Glide.with(this).asBitmap().load(mealImage).into(imageV);

        TextView ratingT = findViewById(R.id.tv_Rating);
        ratingT.setText("Rating: " + String.valueOf(mealRating));
    }

    public void listenerForRatingBar()
    {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
            {
                ratingPointsToSend = v;
            }
        });
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
