package com.example.theprincipleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theprincipleapp.db.Class;
import com.example.theprincipleapp.db.Course;
import com.example.theprincipleapp.db.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewAllClassesRecyclerAdapter extends RecyclerView.Adapter<ViewAllClassesRecyclerAdapter.ItemViewHolder>{
    final Context ctx;
    final List<Pair<Course, Integer>> cardData; // Course and class cid pair
    final String[] colours = {"#06aed5", "#086788", "#f0c808", "#ef6461", "#dd1c1a"}; // https://coolors.co/06aed5-086788-f0c808-5dfdcb-dd1c1a

    public ViewAllClassesRecyclerAdapter(Context ctx, List<Pair<Course, Integer>> cardData){
        this.ctx = ctx;
        this.cardData = cardData;
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
        holder.textViewCourseShortName.setText(cardData.get(position).first.short_name);
        holder.textViewCourseCode.setText(cardData.get(position).first.code);

//        holder.textViewCourseShortName.setText("course short name");
//        holder.textViewCourseCode.setText("course code");
    }

    @Override
    public int getItemCount() { return /*classes.size()*/16; }

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

                // When item is clicked open a new ViewClass intent, passing the class id as extra
                Intent intent = new Intent(ctx, ViewClass.class);
                intent.putExtra("cid", cardData.get(getLayoutPosition()).second);
                ctx.startActivity(intent);

//                Toast.makeText(ctx, "item clicked: " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
