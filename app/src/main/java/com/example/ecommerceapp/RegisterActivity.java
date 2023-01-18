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
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;

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

        // using underline texts
        SpannableString already = new SpannableString("Already have an account? Login!");
        already.setSpan(new UnderlineSpan(), 25, already.length(), 0);
        alreadyLogin.setText(already);

        // using validation
        AwesomeValidation mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        mAwesomeValidation.addValidation(this, R.id.reg_email, Patterns.EMAIL_ADDRESS, R.string.err_email);

        mAwesomeValidation.addValidation(this, R.id.reg_password,
                "(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}",
                R.string.err_password);
        mAwesomeValidation.addValidation(this, R.id.reg_mobile, "^03[0-9]{9}$", R.string.err_mobile);


        mAwesomeValidation.addValidation(this, R.id.reg_password, RegexTemplate.NOT_EMPTY, R.string.error);
        mAwesomeValidation.addValidation(this, R.id.reg_email, RegexTemplate.NOT_EMPTY, R.string.error);
        mAwesomeValidation.addValidation(this, R.id.reg_mobile, RegexTemplate.NOT_EMPTY, R.string.error);


        alreadyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_Activity = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(login_Activity);
            }
        });
        regSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAwesomeValidation.validate()) {

                    userRegister(regEmail.getText().toString(), regMobile.getText().toString(), regPassword.getText().toString());
                }
            }
        });
    }

    private void userRegister(String email, String mobile, String password) {
        String name = "Not Applicable";
        String address = "Not Applicable";

        Call<signup_response_model> call = Apicontroller.getInstance()
                .getapi()
<<<<<<< HEAD
                .getRegister(name,email, password,mobile,address);
       call.enqueue(new Callback<signup_response_model>() {
           @Override
           public void onResponse(@NonNull Call<signup_response_model> call, Response<signup_response_model> response) {
               signup_response_model obj =response.body();
               if (obj == null) throw new AssertionError();
               String result = obj.getResponse();
               if (result.equals("insert")) {
                   tv.setVisibility(View.VISIBLE);
                   Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                   regEmail.setText("");
                   regMobile.setText("");
                   regPassword.setText("");
               }
               else if (result.equals("exist")) {
                   tv.setVisibility(View.VISIBLE);
                   tv.setText("You are Already Registered");
                   Toast.makeText(RegisterActivity.this, "Already Registered", Toast.LENGTH_SHORT).show();

                   regEmail.setText("");
                   regMobile.setText("");
                   regPassword.setText("");
               }
           }
           @Override
           public void onFailure(Call<signup_response_model> call, Throwable t) {
               tv.setVisibility(View.VISIBLE);
               tv.setText("Something Went Wrong");
               Log.e("myerror", "onFailure: " + t.getMessage());
              Toast.makeText(RegisterActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
           }
       });
=======
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
                    regEmail.requestFocus();
                } else if (result.equals("exist")) {
                    tv.setVisibility(View.VISIBLE);
                    tv.setText(getResources().getString(R.string.Already_Registered));
                    tv.setTextColor(getResources().getColor(R.color.golden));

                    regEmail.setText("");
                    regMobile.setText("");
                    regPassword.setText("");
                    regEmail.requestFocus();
                } else if (result.equals("failed")) {
                    tv.setVisibility(View.VISIBLE);
                    tv.setText(getResources().getString(R.string.Entry_Failed));
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
                Toast.makeText(RegisterActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
>>>>>>> 030e5bdf3a051ebd3487ecf87489e9e5603ba693
    }
}