package com.example.myandroidapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> _id, feedback, date;
    private FeedbackDataBase feedbackDataBase;

    public FeedbackAdapter(Context context, ArrayList<String> _id, ArrayList<String> feedback, ArrayList<String> date) {
        this.context = context;
        this._id = _id;
        this.feedback = feedback;
        this.date = date;
    }

    @NonNull
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_feedback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder holder, int position) {

        feedbackDataBase = new FeedbackDataBase(context);
        holder.txtID.setText("ID : " + _id.get(position));
        holder.txtFeedback.setText("Feedback : " + feedback.get(position));
        holder.txtDate.setText("Date : " + date.get(position));

        // Edit feedback functionality
        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditFeedbackActivity.class);
                intent.putExtra("feedback", feedback.get(position));
                intent.putExtra("id", _id.get(position));
                context.startActivity(intent);
            }
        });

        // Delete feedback functionality
        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the feedback item from the database
                String id = _id.get(position);
                feedbackDataBase.deleteData(id);

                // Remove the item from the ArrayLists
                _id.remove(position);
                feedback.remove(position);
                date.remove(position);

                // Notify the adapter that the item has been removed
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());

                // After deletion, refresh the data
                storeDataInArray();
            }
        });
    }

    @Override
    public int getItemCount() {
        return _id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtID, txtFeedback, txtDate, txtEdit, txtDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtFeedback = itemView.findViewById(R.id.txtFeedback);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDelete = itemView.findViewById(R.id.txtDelete);
            txtEdit = itemView.findViewById(R.id.txtEdit);
        }
    }

    // Helper method to refresh the data when something changes (called from the activity)
    private void storeDataInArray() {
        Cursor cursor = feedbackDataBase.readAllData();
        if (cursor.getCount() == 0) {
            // Handle no data
        } else {
            _id.clear();
            feedback.clear();
            date.clear();
            if (cursor.moveToFirst()) {
                do {
                    _id.add(cursor.getString(0));
                    feedback.add(cursor.getString(1));
                    date.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
        }
        notifyDataSetChanged();
    }
}

