package com.rajah.retroapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rajah.retroapp.R;
import com.rajah.retroapp.activity.ViewImage;
import com.rajah.retroapp.interfaces.ItemClickListener;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.rajah.retroapp.adapter.UserListAdapter.randImages;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private Context mContext;
    private String[] arrayOfImage;

    public GalleryAdapter(Context context, String[] arrayOfImage) {
        mContext = context;
        this.arrayOfImage = arrayOfImage;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);
        ButterKnife.bind(this, itemView);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //        rand int number generator
        Random randNum = new Random();

        Glide.with(mContext).load(randImages[randNum.nextInt(randImages.length)])
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(R.drawable.no_image)
                .fallback(R.drawable.no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                viewImage(pos);
            }
        });

    }

    private void viewImage(int pos) {
        Intent i = new Intent(mContext, ViewImage.class);
        i.putExtra("imgPos",pos);
        mContext.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return arrayOfImage.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        ItemClickListener itemClickListener;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }

}