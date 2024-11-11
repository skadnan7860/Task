package com.example.myandroidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Calendar;

public class AddFeedBackActivity extends AppCompatActivity {
    private EditText edtEnterFeedback;
    private TextView txtAddFeedback,txtEditFeedback,txtSubmit;
    private ConstraintLayout clAddFeedback;
    private String strId,strFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed_back);

        initView();
        getCurrentDate();

    }

    private void initView() {

        edtEnterFeedback = findViewById(R.id.edtEnterFeedback);
        txtAddFeedback = findViewById(R.id.txtAddFeedback);
        txtEditFeedback = findViewById(R.id.txtEditFeedback);
        txtSubmit = findViewById(R.id.txtSubmit);
        clAddFeedback = findViewById(R.id.clAddFeedback);

        txtAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clAddFeedback.setVisibility(View.VISIBLE);
            }
        });
        getIntentData();
        addDataFromDataBase();
    }

    public void getIntentData(){

        if (getIntent().hasExtra("id") && getIntent().hasExtra("feedback")){

            strId =getIntent().getStringExtra("id");
            strFeedback = getIntent().getStringExtra("feedback");

            edtEnterFeedback.setText(strFeedback);
            if (strFeedback.isEmpty()){
                clAddFeedback.setVisibility(View.GONE);
            }else {
                clAddFeedback.setVisibility(View.VISIBLE);
            }

        }

    }


    public String getCurrentDate() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;  // Months are 0-based in Calendar
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Format the date as a String (e.g., "2024-11-10")
        return year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
    }


    private void addDataFromDataBase() {

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strFeedback = edtEnterFeedback.getText().toString();
                if (strFeedback.isEmpty()){
                    Toast.makeText(AddFeedBackActivity.this, "Please Enter Feedback Form", Toast.LENGTH_SHORT).show();

                }else {
                    FeedbackDataBase db = new FeedbackDataBase(AddFeedBackActivity.this);
                    db.insertFeedbackData(edtEnterFeedback.getText().toString(),getCurrentDate());
                    Intent intent = new Intent(AddFeedBackActivity.this,FeedbackDashboardActivity.class);
                    startActivity(intent);
                    finish();
                }




            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}