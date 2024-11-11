package com.example.myandroidapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText tietUserName,tietPassword;
    private TextView btnLogin,txtSignUp;
    private MySqliteDataBase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        tietUserName = findViewById(R.id.tietUserName);
        tietPassword = findViewById(R.id.tietPassword);
        txtSignUp = findViewById(R.id.txtSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        spannableText();
        loginWithDataBase();
    }

    private void spannableText() {
        SpannableString spn = new SpannableString("Don't Have An Account? Sing Up");
        spn.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        }, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 22, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spn.setSpan(new ForegroundColorSpan(Color.BLACK), 23, 30, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        txtSignUp.setMovementMethod(LinkMovementMethod.getInstance());
        txtSignUp.setClickable(true);
        txtSignUp.setText(spn);
    }

    private void loginWithDataBase() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new MySqliteDataBase(MainActivity.this);
                String username = tietUserName.getText().toString().trim();
                String password = tietPassword.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both username and password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (db.checkUser(username, password)) {
                    // If credentials are correct, navigate to the next activity
                    Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, FeedbackDashboardActivity.class);  // Change to your desired activity
                    startActivity(intent);
                    finish();  // Close the login activity
                } else {
                    // If credentials are incorrect, show an error
                    Toast.makeText(MainActivity.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}