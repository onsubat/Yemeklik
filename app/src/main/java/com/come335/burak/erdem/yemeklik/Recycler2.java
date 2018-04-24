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

    private  ArrayList<Integer> mIds = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mContent = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();
    private Context mContext;

    public Recycler2(Context context , ArrayList<Integer> ids, ArrayList<String> names, ArrayList<String> content, ArrayList<String> images, ArrayList<String> dates)
    {
        mIds = ids;
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

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.txtMealName.setText(mNames.get(position));
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMealName;
        ImageView image;
        TextView date;
        RelativeLayout parentLayout2;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iview2);
            txtMealName = itemView.findViewById(R.id.name2);
            parentLayout2 = itemView.findViewById(R.id.parent_layout2);
        }
    }
}