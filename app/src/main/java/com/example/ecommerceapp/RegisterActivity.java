package com.example.ecommerceapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Headers;

public class RegisterActivity extends AppCompatActivity {

    EditText regEmail, regMobile, regPassword;
    Button regSubmit;
    TextView tv, alreadyLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        tv = findViewById(R.id.message_inserted);
        alreadyLogin = findViewById(R.id.already_login);
        regEmail = findViewById(R.id.reg_email);
        regMobile = findViewById(R.id.reg_mobile);
        regPassword = findViewById(R.id.reg_password);
        regSubmit = findViewById(R.id.reg_submit);

        SpannableString already = new SpannableString("Already have an account? Login!");
        already.setSpan(new UnderlineSpan(), 25, already.length(), 0);
        alreadyLogin.setText(already);

        alreadyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_Activity = new Intent(RegisterActivity.this , MainActivity.class);
                startActivity(login_Activity);
            }
        });
        regSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister(regEmail.getText().toString(), regMobile.getText().toString(), regPassword.getText().toString());
            }
        });
    }

    private void userRegister(String email, String mobile, String password) {
        String name = "Not Applicable";
        String address = "Not Applicable";

        Call<signup_response_model> call = Apicontroller.getInstance()
                .getapi()
                .getRegister(name, email, password, mobile, address);
        call.enqueue(new Callback<signup_response_model>() {
            @Override
            public void onResponse(@NonNull Call<signup_response_model> call, Response<signup_response_model> response) {
                signup_response_model obj = response.body();
                if (obj == null) throw new AssertionError();
                String result = obj.getMessage().trim();
                if (result.equals("insert")) {
                    tv.setVisibility(View.VISIBLE);
                    tv.setText(getResources().getString(R.string.Registered));
                    tv.setTextColor(getResources().getColor(R.color.green));

                    regEmail.setText("");
                    regMobile.setText("");
                    regPassword.setText("");
                } else if (result.equals("exist")) {
                    tv.setVisibility(View.VISIBLE);
                    tv.setText(getResources().getString(R.string.Already_Registered));
                    tv.setTextColor(getResources().getColor(R.color.red));

                    regEmail.setText("");
                    regMobile.setText("");
                    regPassword.setText("");
                    regEmail.requestFocus();
                }
            }

            @Override
            public void onFailure(Call<signup_response_model> call, Throwable t) {
                tv.setVisibility(View.VISIBLE);
                tv.setText(getResources().getString(R.string.Went_Wrong));
                Log.e("myerror", "onFailure: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}