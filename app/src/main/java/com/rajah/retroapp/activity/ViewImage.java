package com.rajah.retroapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rajah.retroapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rajah.retroapp.adapter.UserListAdapter.randImages;

public class ViewImage extends AppCompatActivity {

    @BindView(R.id.full_image)
    ImageView full_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if(intent != null) {
            int imagePos = intent.getExtras().getInt("imgPos");

            Glide.with(getApplicationContext()).load(randImages[imagePos])
                    .thumbnail(0.5f)
                    .crossFade()
                    .placeholder(R.drawable.no_image)
                    .fallback(R.drawable.no_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(full_image);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
