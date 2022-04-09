package com.example.theprincipleapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theprincipleapp.R;
import com.example.theprincipleapp.ViewClass;
import com.example.theprincipleapp.db.UserClass;

public class HomeRecAdapter extends RecyclerView.Adapter<HomeRecAdapter.ItemViewHolder> {
    private final Context context;
    public List<UserClass> classes = new ArrayList<UserClass>();

    public HomeRecAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.name.setText(classes.get(position).course.short_name);
        holder.code.setText(classes.get(position).course.code);
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name, code;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewCourseShortName);
            code = itemView.findViewById(R.id.textViewCourseCode);
            itemView.setOnClickListener(view -> {
                Intent i = new Intent(context, ViewClass.class);
                i.putExtra("cid", classes.get(getAdapterPosition()).cls.cid);
                context.startActivity(i);
            });
        }
    }

}
