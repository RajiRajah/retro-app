package com.rajah.retroapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rajah.retroapp.R;
import com.rajah.retroapp.models.Comment;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.rajah.retroapp.adapter.UserListAdapter.randImages;

/**
 * Created by Rajah on 9/4/2017.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.MyViewHolder> {

    private Context context;
    private List<Comment> comments;
    private Random randNum;

    public CommentListAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public CommentListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentListAdapter.MyViewHolder holder, int position) {
        holder.comment_name.setText(comments.get(position).getName());
        holder.comment_content.setText(comments.get(position).getBody());

        //        rand number generator
        randNum = new Random();

//        Glide url image loader
        Glide.with(context)
                .load(randImages[randNum.nextInt(randImages.length)])
                .centerCrop()
                .placeholder(R.drawable.no_images_found)
                .fallback(R.drawable.no_images_found)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(holder.comment_img);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.commentUsername)
        TextView comment_name;
        @BindView(R.id.comment_content)
        TextView comment_content;
        @BindView(R.id.comment_img)
        ImageView comment_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
