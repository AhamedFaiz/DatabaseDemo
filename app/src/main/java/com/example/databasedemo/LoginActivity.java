package com.example.databasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.databasedemo.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends BaseActivity {

    TextInputLayout emailET;
    TextInputLayout passwordET;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this,StateActivity.class);
            startActivity(intent);
            finish();
        }

        emailET = findViewById(R.id.emailText);
        passwordET = findViewById(R.id.passwordText);


        findViewById(R.id.registerTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBar(true);
                getDataFromEditText();
            }
        });
    }

    private void getDataFromEditText() {

        String email = emailET.getEditText().getText().toString();
        String password = passwordET.getEditText().getText().toString();


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailET.setErrorEnabled(true);
                emailET.setError("Invalid Email Address");
        }else {
            passwordET.setErrorEnabled(false);
            emailET.setErrorEnabled(false);
            logInUser(email,password);
        }
    }

    private void logInUser(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        showProgressBar(false);
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"LogIn Successful",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this,StateActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}