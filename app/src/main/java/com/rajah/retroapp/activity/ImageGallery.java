package com.rajah.retroapp.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rajah.retroapp.R;
import com.rajah.retroapp.adapter.GalleryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import static com.rajah.retroapp.adapter.UserListAdapter.randImages;

public class ImageGallery extends AppCompatActivity {

    @BindView(R.id.img_recycler_view)
    RecyclerView img_recycler_view;

    private GalleryAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mLayoutManager = new GridLayoutManager(this,2);
        img_recycler_view.setLayoutManager(mLayoutManager);
        img_recycler_view.setItemAnimator(new DefaultItemAnimator());
//        setting adapter
        mAdapter = new GalleryAdapter(this, randImages);
        img_recycler_view.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
