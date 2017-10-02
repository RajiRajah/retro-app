package com.rajah.retroapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajah.retroapp.Constant;
import com.rajah.retroapp.R;
import com.rajah.retroapp.activity.PostDetailsActivity;
import com.rajah.retroapp.interfaces.ItemClickListener;
import com.rajah.retroapp.models.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rajah on 8/27/2017.
 */

public class UserPostListAdapter extends RecyclerView.Adapter<UserPostListAdapter.MyViewHolder> {

    private Context context;
    private List<Post> posts;

    public UserPostListAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public UserPostListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserPostListAdapter.MyViewHolder holder, int position) {
        holder.post_title.setText(posts.get(position).getTitle());
        holder.post_content.setText(posts.get(position).getBody());

//            getting posts
        final String post_id = String.valueOf(posts.get(position).getId());
        final String title = posts.get(position).getTitle();
        final String content = posts.get(position).getBody();

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                sendUserPost(title, content, post_id);
            }
        });
    }

    private void sendUserPost(String... postDetails) {
        Intent intent = new Intent(context, PostDetailsActivity.class);
        intent.putExtra(Constant.TITLE_KEY, postDetails[0]);
        intent.putExtra(Constant.CONTENT_KEY, postDetails[1]);
        intent.putExtra(Constant.POST_ID_KEY, postDetails[2]);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.post_title)
        TextView post_title;
        @BindView(R.id.post_content)
        TextView post_content;

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
