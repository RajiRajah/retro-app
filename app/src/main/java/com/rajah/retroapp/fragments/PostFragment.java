package com.rajah.retroapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rajah.retroapp.Constant;
import com.rajah.retroapp.R;
import com.rajah.retroapp.adapter.UserPostListAdapter;
import com.rajah.retroapp.api.ApiClient;
import com.rajah.retroapp.interfaces.ApiInterface;
import com.rajah.retroapp.models.Post;
import com.rajah.retroapp.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {

    @BindView(R.id.post_recyclerView)
    RecyclerView recyclerView;

    private User user;
    private String user_id;

    private ApiInterface apiInterface;
    private UserPostListAdapter userPostListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Post> posts;
    private Context context;



    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        ButterKnife.bind(this, view);

        context = getContext();

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (getArguments() != null) {
            user = getArguments().getParcelable(Constant.USER);
            user_id = user.getId().toString();
            getPost(user_id);
        }
        return view;
    }

    private void getPost(String post_id) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Post>> call = apiInterface.getPostById(post_id);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                posts = response.body();
//                Setting the Adapter
                userPostListAdapter = new UserPostListAdapter(posts, context);
                recyclerView.setAdapter(userPostListAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
