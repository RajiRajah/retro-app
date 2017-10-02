package com.rajah.retroapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rajah.retroapp.Constant;
import com.rajah.retroapp.R;
import com.rajah.retroapp.adapter.AlbumListAdapter;
import com.rajah.retroapp.adapter.TodoListAdapter;
import com.rajah.retroapp.api.ApiClient;
import com.rajah.retroapp.interfaces.ApiInterface;
import com.rajah.retroapp.models.Album;
import com.rajah.retroapp.models.Todo;
import com.rajah.retroapp.models.User;
import com.rajah.retroapp.ui.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.album_recycler)
    RecyclerView album_recycler;
    @BindView(R.id.album_swipe_refresh_layout)
    SwipeRefreshLayout album_swipe_refresh_layout;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private User user;
    private String user_id;

    private List<Album> albums;
    private Context context;
    private AlbumListAdapter albumListAdapter;

    private ApiInterface apiInterface;

    public AlbumListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_album_list, container, false);
        ButterKnife.bind(this, view);

        context = getContext();

        layoutManager = new LinearLayoutManager(context);
        album_recycler.setLayoutManager(layoutManager);
        album_recycler.setHasFixedSize(true);
        album_recycler.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        album_swipe_refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                album_swipe_refresh_layout.setRefreshing(true);
                if (getArguments() != null) {
                    user = getArguments().getParcelable(Constant.USER);
                    user_id = user.getId().toString();
                    fetchAlbums(user_id);
                }
            }
        });

        return view;
    }


    @Override
    public void onRefresh() {
        fetchAlbums(user_id);
    }

    private void fetchAlbums(String user_id) {
        album_swipe_refresh_layout.setRefreshing(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Album>> callAlbum  =  apiInterface.getAlbumById(user_id);

        callAlbum.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                albums = response.body();
                album_swipe_refresh_layout.setRefreshing(false);

                albumListAdapter = new AlbumListAdapter(context, albums);
                album_recycler.setAdapter(albumListAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                album_swipe_refresh_layout.setRefreshing(false);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
