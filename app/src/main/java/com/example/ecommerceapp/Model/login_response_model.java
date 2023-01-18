package com.example.ecommerceapp.Model;

public class login_response_model {
    String message;

    public login_response_model() {
    }

    public login_response_model(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
