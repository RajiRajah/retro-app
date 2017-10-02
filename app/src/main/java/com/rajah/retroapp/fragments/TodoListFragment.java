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
import com.rajah.retroapp.adapter.TodoListAdapter;
import com.rajah.retroapp.api.ApiClient;
import com.rajah.retroapp.interfaces.ApiInterface;
import com.rajah.retroapp.models.Todo;
import com.rajah.retroapp.models.User;
import com.rajah.retroapp.ui.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TodoListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.todo_recyclerView)
    RecyclerView todo_recyclerView;
    @BindView(R.id.todo_swipe_refresh_layout)
    SwipeRefreshLayout todo_swipe_refresh_layout;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private User user;
    private String user_id;

    private List<Todo> todos;
    private Context context;
    private TodoListAdapter todoListAdapter;

    private ApiInterface apiInterface;

    public TodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);
        ButterKnife.bind(this, view);

        context = getContext();

        layoutManager = new LinearLayoutManager(context);
        todo_recyclerView.setLayoutManager(layoutManager);
        todo_recyclerView.setHasFixedSize(true);
        todo_recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        todo_swipe_refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                todo_swipe_refresh_layout.setRefreshing(true);
                if (getArguments() != null) {
                    user = getArguments().getParcelable(Constant.USER);
                    user_id = user.getId().toString();
                    fetchTodos(user_id);
                }
            }
        });

        return view;
    }

    @Override
    public void onRefresh() {
        fetchTodos(user_id);
    }

    private void fetchTodos(String user_id) {
        todo_swipe_refresh_layout.setRefreshing(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Todo>> callTodo = apiInterface.getTodoById(user_id);

        callTodo.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                todos = response.body();
                todo_swipe_refresh_layout.setRefreshing(false);

//                setting the adapter
                todoListAdapter = new TodoListAdapter(context, todos);
                todo_recyclerView.setAdapter(todoListAdapter);

            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
