package com.example.theprincipleapp.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theprincipleapp.db.Task;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final Context context;
    public List<Task> tasks;

    public TaskAdapter(Context context) {
        this.context = context;
        tasks = new ArrayList<>();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(0/*R.layout.TODO*/, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // Bind newPos to holder
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        // Card views

        public TaskViewHolder( View itemView) {
            super(itemView);
            // Init views
        }
    }

}
