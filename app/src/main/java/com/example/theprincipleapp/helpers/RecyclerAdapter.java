package com.example.theprincipleapp.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theprincipleapp.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    ArrayList<String> al_imageNames;
    ArrayList<Integer> al_images;
    Context context;
  //  CardView cardView;

    public RecyclerAdapter(Context context, ArrayList<String> al_imageNames, ArrayList<Integer> al_images) {
        this.context = context;
        this.al_imageNames = al_imageNames;
        this.al_images = al_images;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.textView.setText(al_imageNames.get(position));
        holder.imageView.setImageResource(al_images.get(position));

    }

    @Override
    public int getItemCount() {

        return al_imageNames.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {


            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        //    itemView.setOnClickListener(V->{});

        }
    }

}
