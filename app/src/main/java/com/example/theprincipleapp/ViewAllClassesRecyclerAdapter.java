package com.example.theprincipleapp;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theprincipleapp.db.UserClass;


public class ViewAllClassesRecyclerAdapter extends RecyclerView.Adapter<ViewAllClassesRecyclerAdapter.ItemViewHolder>{
    final Context ctx;
    public List<UserClass> classes;
    final String[] colours = {"#06aed5", "#086788", "#f0c808", "#ef6461", "#dd1c1a"}; // https://coolors.co/06aed5-086788-f0c808-5dfdcb-dd1c1a

    public ViewAllClassesRecyclerAdapter(Context ctx){
        this.ctx = ctx;
        classes = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_classes_class_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.viewColourBar.setBackgroundColor(Color.parseColor(colours[position % colours.length]));
        holder.textViewCourseShortName.setText(classes.get(position).course.code);
        holder.textViewCourseCode.setText(classes.get(position).course.full_name);
    }

    @Override
    public int getItemCount() { return classes.size(); }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewCourse;
        TextView textViewCourseShortName, textViewCourseCode;
        View viewColourBar;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewCourse = itemView.findViewById(R.id.cardViewCourse);
            textViewCourseShortName = itemView.findViewById(R.id.textViewCourseShortName);
            textViewCourseCode = itemView.findViewById(R.id.textViewCourseCode);
            viewColourBar = itemView.findViewById(R.id.viewColourBar);

            itemView.setOnClickListener(v -> {
                // When item is clicked open a new ViewClass intent, passing the class parcelable as an extra
                Intent intent = new Intent(ctx, ViewClass.class);
                intent.putExtra("cid", classes.get(getAdapterPosition()).cls.cid);
                ctx.startActivity(intent);
            });
        }
    }
}
