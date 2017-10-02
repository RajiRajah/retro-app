package com.rajah.retroapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rajah.retroapp.R;
import com.rajah.retroapp.models.Todo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rajah on 9/6/2017.
 */

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.MyViewHolder> {

    private Context context;
    private List<Todo> todos;

    public TodoListAdapter(Context context, List<Todo> todos) {
        this.context = context;
        this.todos = todos;
    }

    @Override
    public TodoListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_item_list, parent, false);
        ButterKnife.bind(this, view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoListAdapter.MyViewHolder holder, int position) {
        holder.todo.setText(todos.get(position).getTitle());

        final Boolean status = todos.get(position).getCompleted();

        if(status.equals(true)){
            holder.complete_status.setChecked(true);
        }else {
            holder.complete_status.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.todo)
        TextView todo;
        @BindView(R.id.complete_status)
        CheckBox complete_status;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
