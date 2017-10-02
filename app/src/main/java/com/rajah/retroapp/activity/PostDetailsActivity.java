package com.rajah.retroapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.rajah.retroapp.Constant;
import com.rajah.retroapp.R;
import com.rajah.retroapp.adapter.CommentListAdapter;
import com.rajah.retroapp.api.ApiClient;
import com.rajah.retroapp.interfaces.ApiInterface;
import com.rajah.retroapp.models.Comment;
import com.rajah.retroapp.models.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailsActivity extends AppCompatActivity {

    @BindView(R.id.show_topic)
    TextView post_title;
    @BindView(R.id.show_content)
    TextView post_content;

    @BindView(R.id.comment_recyclerView)
    RecyclerView comment_recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private CommentListAdapter commentListAdapter;
    private List<Comment> comments;
    private ApiInterface apiInterface;

    private Post posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(Constant.POSTBYTITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        layoutManager = new LinearLayoutManager(this);
        comment_recyclerView.setLayoutManager(layoutManager);
        comment_recyclerView.setHasFixedSize(true);

        receiveAndBindPostData();
    }

    private void receiveAndBindPostData() {

        Intent intentPost = this.getIntent();
        if (intentPost != null) {
//            posts = intentPost.getExtras().getParcelable(Constant.POST_BY_USER);

            String p_title = intentPost.getExtras().getString(Constant.TITLE_KEY);
            String p_content = intentPost.getExtras().getString(Constant.CONTENT_KEY);
            String post_id = intentPost.getExtras().getString(Constant.POST_ID_KEY);

            fetchComments(post_id);
//            Binding views
            post_title.setText(p_title);
            post_content.setText(p_content);
        }
    }

    private void fetchComments(String post_id) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Comment>> call = apiInterface.getCommentById(post_id);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                comments = response.body();

//                setting the Adapter
                commentListAdapter = new CommentListAdapter(getApplicationContext(), comments);
                comment_recyclerView.setAdapter(commentListAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
