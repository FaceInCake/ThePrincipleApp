package com.example.theprincipleapp.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theprincipleapp.db.Meeting;


public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder> {
    private final Context context;
    public List<Meeting> meetings;

    public MeetingAdapter(Context context) {
        this.context = context;
        meetings = new ArrayList<>();
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(0/*R.layout.TODO*/, parent, false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeetingViewHolder holder, int position) {
        // Bind newPos to holder
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public static class MeetingViewHolder extends RecyclerView.ViewHolder {
        // Card views

        public MeetingViewHolder(View itemView) {
            super(itemView);
            // Init views
        }
    }

}
