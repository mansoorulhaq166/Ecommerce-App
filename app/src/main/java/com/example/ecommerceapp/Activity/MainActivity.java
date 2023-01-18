package com.example.ecommerceapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ecommerceapp.Apicontroller;
import com.example.ecommerceapp.Model.login_response_model;
import com.example.ecommerceapp.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView forgotPassword;
    TextView signUp, sign_report;
    EditText email, password;
    Button login;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        signUp = (TextView) findViewById(R.id.sign_up);
        sign_report = (TextView) findViewById(R.id.signin_report);
        email = (EditText) findViewById(R.id.enter_email);
        login = findViewById(R.id.button_login);
        password = (EditText) findViewById(R.id.enter_password);
        Drawable drawable = password.getCompoundDrawables()[2];

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(password.getTransformationMethod() instanceof PasswordTransformationMethod){
                            password.setTransformationMethod(null);
                            drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                            drawable.setAlpha(255);
                        }else{
                            password.setTransformationMethod(new PasswordTransformationMethod());
                            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                            drawable.setAlpha(255);
                        }
                       // password.performClick();
                        return true;
                    }
                }
                return false;
            }
        });
        verifyuserexistence();

        SpannableString forget = new SpannableString("Forgot Password ?");
        forget.setSpan(new UnderlineSpan(), 0, forget.length(), 0);
        forgotPassword.setText(forget);

        SpannableString sign_up = new SpannableString("Don't have an account, Sign Up!");
        sign_up.setSpan(new UnderlineSpan(), 23, sign_up.length(), 0);
        signUp.setText(sign_up);

        signUp.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processlogin(email.getText().toString(), password.getText().toString());
            }
        });
    }

    private void verifyuserexistence() {
        SharedPreferences sharedPreferences = getSharedPreferences("credentials",MODE_PRIVATE);
        if (sharedPreferences.contains("username")) {
            startActivity(new Intent(MainActivity.this,Dashboard.class));
        }
    }

    private void processlogin(String email, String password) {
        Call<login_response_model> call = Apicontroller.getInstance()
                .getapi().getLogin(email, password);

        call.enqueue(new Callback<login_response_model>() {
            @Override
            public void onResponse(Call<login_response_model> call, Response<login_response_model> response) {
                login_response_model obj = response.body();
                assert obj != null;
                String result = obj.getMessage();
                if (result.equals("exist")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("credentials",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username",email);
                    editor.putString("password",password);
                  //  editor.commit();
                    editor.apply();

                    Intent logged = new Intent(MainActivity.this, Dashboard.class);
                    startActivity(logged);
                    finish();
                } else if (result.equals("Doesnot_exist")) {
                    sign_report.setText("User Does not Exist!");
                    sign_report.setTextColor(getResources().getColor(R.color.red));
                }
            }

            @Override
            public void onFailure(Call<login_response_model> call, Throwable t) {
                sign_report.setText(getResources().getString(R.string.Went_Wrong));
                sign_report.setTextColor(getResources().getColor(R.color.red));
                Log.e("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}