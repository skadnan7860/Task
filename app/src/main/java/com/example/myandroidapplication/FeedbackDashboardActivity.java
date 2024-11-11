package com.example.myandroidapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;

public class FeedbackDashboardActivity extends AppCompatActivity {

    private FloatingActionButton flbAdd;
    private RecyclerView rvFeedbackList;
    private ArrayList<String> _id, feedback, date;
    private FeedbackAdapter feedbackAdapter;
    private FeedbackDataBase feedbackDataBase;
    private TextView txtNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_dashbord);
        initView();
    }

    private void initView() {
        rvFeedbackList = findViewById(R.id.rvFeedbackList);
        flbAdd = findViewById(R.id.flbAdd);
        txtNoData = findViewById(R.id.txtNoData);

        flbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackDashboardActivity.this, AddFeedBackActivity.class);
                startActivity(intent);
                finish();
            }
        });

        feedbackDataBase = new FeedbackDataBase(FeedbackDashboardActivity.this);
        _id = new ArrayList<>();
        feedback = new ArrayList<>();
        date = new ArrayList<>();

        storeDataInArray();
    }

    private void setAdapterInRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FeedbackDashboardActivity.this);
        feedbackAdapter = new FeedbackAdapter(this, _id, feedback, date);
        rvFeedbackList.setAdapter(feedbackAdapter);
        feedbackAdapter.notifyDataSetChanged();
        rvFeedbackList.setLayoutManager(linearLayoutManager);
    }

    private void storeDataInArray() {
        Cursor cursor = feedbackDataBase.readAllData();
        if (cursor.getCount() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
            rvFeedbackList.setVisibility(View.GONE);
        } else {
            _id.clear();  // Clear the lists before adding fresh data
            feedback.clear();
            date.clear();

            if (cursor.moveToFirst()) {
                do {
                    _id.add(cursor.getString(0));
                    feedback.add(cursor.getString(1));
                    date.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }

            txtNoData.setVisibility(View.GONE);
            rvFeedbackList.setVisibility(View.VISIBLE);
        }

        setAdapterInRecyclerView();
    }

    // You can call storeDataInArray() whenever data changes (add, delete, update).


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
