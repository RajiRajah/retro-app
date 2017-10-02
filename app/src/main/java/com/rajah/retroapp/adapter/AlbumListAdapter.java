package com.rajah.retroapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajah.retroapp.R;
import com.rajah.retroapp.activity.ImageGallery;
import com.rajah.retroapp.interfaces.ItemClickListener;
import com.rajah.retroapp.models.Album;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rajah on 9/6/2017.
 */

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.MyViewHolder>{

    private Context context;
    private List<Album> albums;

    public AlbumListAdapter(Context context, List<Album> albums) {
        this.context = context;
        this.albums = albums;
    }

    @Override
    public AlbumListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item_list, parent,false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumListAdapter.MyViewHolder holder, int position) {
        holder.album_txt.setText(albums.get(position).getTitle());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
               nextActivity();
            }
        });
    }

    private void nextActivity() {
        Intent i = new Intent(context, ImageGallery.class);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.album_txt)
        TextView album_txt;

        ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
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
