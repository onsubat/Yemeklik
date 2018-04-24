package com.come335.burak.erdem.yemeklik;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
/**
 * Created by User on 1/1/2018.
 */

public class Recycler2 extends RecyclerView.Adapter<Recycler2.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mContent = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();
    private Context mContext;

    public Recycler2(Context context, ArrayList<String> names, ArrayList<String> content, ArrayList<String> images, ArrayList<String> dates)
    {
        mNames = names;
        mContent = content;
        mImages = images;
        mDates = dates;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_cardview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.tvMealName.setText(mNames.get(position));

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.ivImage);

        holder.tvdate.setText(mDates.get(position));
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvMealName;
        TextView tvContent;
        ImageView ivImage;
        TextView tvdate;
        RelativeLayout parentLayout_history;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.name_history);
            tvContent = itemView.findViewById(R.id.content_history);
            ivImage = itemView.findViewById(R.id.iview_history);
            tvdate = itemView.findViewById(R.id.date_history);
            parentLayout_history = itemView.findViewById(R.id.parent_layout_history);
        }
    }
}