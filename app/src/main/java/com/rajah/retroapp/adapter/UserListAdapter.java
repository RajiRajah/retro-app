package com.rajah.retroapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rajah.retroapp.Constant;
import com.rajah.retroapp.R;
import com.rajah.retroapp.activity.UserProfile;
import com.rajah.retroapp.interfaces.ItemClickListener;
import com.rajah.retroapp.models.User;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Rajah on 8/25/2017.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private List<User> users;
    private Random randNum;
    private Context context;

    //    array of url images
    public static String[] randImages = {
            "http://www.venmond.com/demo/vendroid/img/avatar/big.jpg",
            "http://www.huntleytutors.co.uk/wp-content/uploads/2015/07/Phoebe-Verbeeten2.jpg",
            "http://www.xideasoft.com/xidea/assets/img/people/img3-small.jpg",
            "http://profileplugin.com/wp-content/uploads/upme/cropped-1384318720_Eliza-Dushku-5-800x960.jpg",
            "https://a0.muscache.com/im/pictures/e5c1138e-518a-4d08-8549-0b20a2371ca0.jpg?aki_policy=profile_x_medium",
            "https://a0.muscache.com/im/pictures/49eb1933-f2b4-4ffe-b4d1-a745cd1cf38e.jpg?aki_policy=profile_x_medium",
            "https://a0.muscache.com/im/pictures/57f2d63a-2572-4fd9-91e4-a09b654ada0b.jpg?aki_policy=profile_x_medium",
            "https://a0.muscache.com/im/pictures/20695504-6434-47bb-a088-26fba1581b06.jpg?aki_policy=profile_x_medium",
            "https://a0.muscache.com/im/pictures/379d82ab-f0fb-4166-9be8-0558c8a99b25.jpg?aki_policy=profile_x_medium",
            "https://a0.muscache.com/im/pictures/d4caadb7-07b7-400c-a18b-83b633a93f94.jpg?aki_policy=profile_x_medium",
            "https://a0.muscache.com/im/pictures/964fdd6d-f50d-4927-a5e9-8f1f15c622f6.jpg?aki_policy=profile_x_medium",
            "https://a0.muscache.com/im/pictures/1a4ac832-9644-4ce6-9eec-7b1ba5d3c0cb.jpg?aki_policy=profile_x_medium",
            "https://i1.social.s-msft.com/profile/u/avatar.jpg?displayname=Vesa+Juvonen&size=extralarge&version=7f0318a7-1fa1-4bc3-bad3-8e87311d99c6",
            "http://keenthemes.com/preview/metronic/theme/assets/pages/media/profile/people19.png",
    };

    public UserListAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserListAdapter.MyViewHolder holder, final int position) {

        holder.userName.setText(users.get(position).getName());

//        rand number generator
        randNum = new Random();

//        Glide url image loader
        Glide.with(context)
                .load(randImages[randNum.nextInt(randImages.length)])
                .centerCrop()
                .placeholder(R.drawable.no_images_found)
                .fallback(R.drawable.no_images_found)
                .bitmapTransform(new CropCircleTransformation(context))
//                .transform(new CircleTransform(context))
                .into(holder.userImg);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                showUserProfile(users.get(position));
            }
        });
    }
//    User details
    private void showUserProfile(User user) {
        Intent intent = new Intent(context, UserProfile.class);
        intent.putExtra(Constant.USER_PRO, user);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.username_txt)
        TextView userName;
        @BindView(R.id.userImg)
        ImageView userImg;

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
