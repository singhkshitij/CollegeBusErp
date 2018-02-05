package com.edu.aimt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by PRAV on 05-04-2016.
 */
public class SplashActivity extends AppCompatActivity {
    TextInputLayout emailLayout,passwordLayout;
    EditText email,password;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        emailLayout = (TextInputLayout) findViewById(R.id.input_layout_email);
        passwordLayout = (TextInputLayout) findViewById(R.id.input_layout_password);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        Button login = (Button)findViewById(R.id.splash_login_button);
        Button studentLogin = (Button) findViewById(R.id.student_login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStudentLogin();
            }
        });
        if(Build.VERSION.SDK_INT>=21)
            getWindow().setStatusBarColor(Color.RED);
    }
    private void checkStudentLogin(){
        String emailTxt = email.getText().toString();
        String pass = password.getText().toString();
        emailLayout.setError(null);
        passwordLayout.setError(null);
        if(!"1336310021".equals(emailTxt)){
            emailLayout.setError("Invalid Roll no.");
            requestFocus(email);
            return;
        }
        if(!"qwerty".equals(pass)){
            passwordLayout.setError(getString(R.string.password_error));
            requestFocus(password);
            return;
        }
        Intent i =new Intent(this,StudentActivity.class);
        startActivity(i);
        finish();
    }
    private void checkLogin(){
        String emailTxt = email.getText().toString();
        String pass = password.getText().toString();
        emailLayout.setError(null);
        passwordLayout.setError(null);
        if(!"arjit@aimt.com".equals(emailTxt)){
            emailLayout.setError(getString(R.string.email_error));
            requestFocus(email);
            return;
        }
        if(!"qwerty".equals(pass)){
                passwordLayout.setError(getString(R.string.password_error));
                requestFocus(password);
                return;
        }
        Intent i =new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
