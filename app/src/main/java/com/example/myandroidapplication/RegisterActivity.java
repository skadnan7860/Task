package com.example.myandroidapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilUserName, tilEmail, tilPassword, tilConfirmPassword;
    private TextInputEditText tietUserName, tietEmail, tietPassword, tietConfirmPassword;
    private TextView btnSignUp, txtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        spannableText();
    }

    private void initView() {
        tilUserName = findViewById(R.id.tilUserName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);

        tietUserName = findViewById(R.id.tietUserName);
        tietEmail = findViewById(R.id.tietEmail);
        tietPassword = findViewById(R.id.tietPassword);
        tietConfirmPassword = findViewById(R.id.tietConfirmPassword);

        btnSignUp = findViewById(R.id.btnSignUp);
        txtSignUp = findViewById(R.id.txtSignUp);

        InitializeDataBase();
    }

    private void spannableText() {
        SpannableString spn = new SpannableString("Already Have An Account? Log In");
        spn.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 25, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 24, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spn.setSpan(new ForegroundColorSpan(Color.BLACK), 25, 31, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        txtSignUp.setMovementMethod(LinkMovementMethod.getInstance());
        txtSignUp.setClickable(true);
        txtSignUp.setText(spn);
    }

    private void InitializeDataBase() {
        final MySqliteDataBase db = new MySqliteDataBase(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = tietUserName.getText().toString();
                String email = tietEmail.getText().toString();
                String password = tietPassword.getText().toString();
                String confirmPassword = tietConfirmPassword.getText().toString();

                // Validate user input
                if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    boolean result = db.addUser(userName, password, email);
                    if (result) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
