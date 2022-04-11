package com.example.theprincipleapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import com.example.theprincipleapp.R;
import com.example.theprincipleapp.ViewTask;
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
        View view = LayoutInflater.from(context).inflate(R.layout.card_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.name.setText(tasks.get(position).name);
        holder.code.setText(tasks.get(position).type.toString());
        holder.due.setText(
                new SimpleDateFormat( // Jul 03, 8:30 PM
                        "MMM dd, hh:mm a", Locale.getDefault()
                ).format(tasks.get(position).due)
        );
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView name, code, due;

        public TaskViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.taskTitle);
            code = itemView.findViewById(R.id.taskClassCode);
            due = itemView.findViewById(R.id.taskDueDate);
            itemView.setOnClickListener(view -> {
                Intent i = new Intent(context, ViewTask.class);
                i.putExtra("tid", tasks.get(getAdapterPosition()).tid);
                context.startActivity(i);
            });
        }
    }

}
