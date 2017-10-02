package com.rajah.retroapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rajah.retroapp.Constant;
import com.rajah.retroapp.R;
import com.rajah.retroapp.fragments.AlbumListFragment;
import com.rajah.retroapp.fragments.PostFragment;
import com.rajah.retroapp.fragments.TodoListFragment;
import com.rajah.retroapp.fragments.UserInfoFragment;
import com.rajah.retroapp.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.rajah.retroapp.adapter.UserListAdapter.randImages;

public class UserProfile extends AppCompatActivity {

    @BindView(R.id.username_txt) TextView username;
    @BindView(R.id.userImg) ImageView userImg;


    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;


    private Context context;
    private Random randNum;
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        ButterKnife.bind(this);

//        toolbar comp.
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        receiveAndBindUserData();

//        random value generator
        randNum = new Random();
//        set Image using glide
        Glide.with(getApplicationContext())
                .load(randImages[randNum.nextInt(randImages.length)])
                .centerCrop()
                .placeholder(R.drawable.no_images_found)
                .fallback(R.drawable.no_images_found)
                .bitmapTransform(new CropCircleTransformation(context))
//                .transform(new CircleTransform(context))
                .into(userImg);


        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void receiveAndBindUserData() {
        Intent i = this.getIntent();
        User user = i.getExtras().getParcelable(Constant.USER_PRO);
        username.setText(user.getName());
//        Send data received to Fragments
       mUser = user;
    }


    private void setupViewPager(ViewPager viewPager) {
//      to Pass Data to Fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.USER, mUser);

//      set MyFragment Arguments
        UserInfoFragment userInfoFragment = new UserInfoFragment();
        PostFragment postfragment = new PostFragment();
        TodoListFragment todoListFragment = new TodoListFragment();
        AlbumListFragment albumListFragment = new AlbumListFragment();

        userInfoFragment.setArguments(bundle);
        postfragment.setArguments(bundle);
        todoListFragment.setArguments(bundle);
        albumListFragment.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(userInfoFragment, Constant.PROFILE);
        adapter.addFragment(postfragment, Constant.POST);
        adapter.addFragment(todoListFragment,Constant.TODO);
        adapter.addFragment(albumListFragment, Constant.ALBUM);
        viewPager.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title ){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
