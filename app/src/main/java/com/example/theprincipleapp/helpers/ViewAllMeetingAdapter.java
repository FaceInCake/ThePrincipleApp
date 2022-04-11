package com.example.theprincipleapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theprincipleapp.R;
import com.example.theprincipleapp.db.Meeting;
import com.example.theprincipleapp.db.Weekday;


public class ViewAllMeetingAdapter extends RecyclerView.Adapter<ViewAllMeetingAdapter.MeetingViewHolder> {
    private final Context context;
    public List<Meeting> meetings = new ArrayList<>();
    final String[] colours = {"#ef6461", "#06aed5", "#086788", "#dd1c1a", "#f0c808"};
    final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    public ViewAllMeetingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_meetings_card, parent, false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeetingViewHolder holder, int position) {
        holder.viewColourBar.setBackgroundColor(Color.parseColor(colours[position % colours.length]));
        holder.textViewMeetingSection.setText(String.format(Locale.CANADA, "Section: %d", meetings.get(position).section));
        holder.textViewMeetingStartTime.setText(String.format(Locale.CANADA, "Begins at: %s", timeFormat.format(meetings.get(position).start)));
        holder.textViewMeetingType.setText(meetings.get(position).type.toString());
        holder.textViewMeetingDays.setText(meetings.get(position).weekdays.abbreviate());

        if (meetings.get(position).weekdays.contains(getToday())){
            holder.cardViewMeeting.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public class MeetingViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewMeeting;
        TextView textViewMeetingType, textViewMeetingDays, textViewMeetingSection, textViewMeetingStartTime;
        View viewColourBar;

        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewMeeting = itemView.findViewById(R.id.cardViewMeeting);
            textViewMeetingSection = itemView.findViewById(R.id.textViewMeetingSection);
            textViewMeetingStartTime = itemView.findViewById(R.id.textViewMeetingStartTime);
            textViewMeetingType = itemView.findViewById(R.id.textViewMeetingType);
            textViewMeetingDays = itemView.findViewById(R.id.textViewMeetingDays);
            viewColourBar = itemView.findViewById(R.id.viewColourBar);

            itemView.setOnClickListener(v -> {
                // When item is clicked open a new ViewClass intent, passing the class parcelable as an extra
                Intent intent = new Intent(context, Meeting.class);
                intent.putExtra("mid", meetings.get(getAdapterPosition()).mid);
                context.startActivity(intent);
            });
        }
    }

    private static Weekday getToday(){
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
            case Calendar.MONDAY:    return Weekday.MONDAY;
            case Calendar.TUESDAY:   return Weekday.TUESDAY;
            case Calendar.WEDNESDAY: return Weekday.WEDNESDAY;
            case Calendar.THURSDAY:  return Weekday.THURSDAY;
            case Calendar.FRIDAY:    return Weekday.FRIDAY;
            case Calendar.SATURDAY:  return Weekday.SATURDAY;
            default:                 return Weekday.SUNDAY;
        }
    }
}
