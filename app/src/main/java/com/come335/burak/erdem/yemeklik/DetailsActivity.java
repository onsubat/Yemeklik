package com.come335.burak.erdem.yemeklik;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Erdem on 21-Apr-18.
 */

public class DetailsActivity extends AppCompatActivity {

    String mealName;
    String mealContent;
    String mealImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_details);

        getIncomingIntent();
    }

    private void getIncomingIntent()
    {
        mealName = getIntent().getStringExtra("name");
        mealContent = getIntent().getStringExtra("content");
        mealImage = getIntent().getStringExtra("image");

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
    }
}
