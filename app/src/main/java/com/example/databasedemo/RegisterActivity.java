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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends BaseActivity {

    //ui
    TextInputLayout nameET;
    TextInputLayout mobileET;
    TextInputLayout emailET;
    TextInputLayout passwordET;

    //vars
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBar(true);
                getDataFromEditText();
            }
        });

        findViewById(R.id.backToLoginTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });





    }

    private void getDataFromEditText() {
        String name = nameET.getEditText().getText().toString();
        String mobileNumber = mobileET.getEditText().getText().toString();
        String email = emailET.getEditText().getText().toString();
        String password = passwordET.getEditText().getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailET.setErrorEnabled(true);
            emailET.setError("Invalid Email Address");
        }else if(name.isEmpty()){
        nameET.setErrorEnabled(true);
        nameET.setError("Name Cannot be Empty");

        }else if(mobileNumber.length() != 10){
            mobileET.setErrorEnabled(true);
            mobileET.setError("Mobile Number needs to be 10 digit");
        }else if (password.length() < 5){
            passwordET.setErrorEnabled(true);
            passwordET.setError("Password should contain minimum of 5 characters");
        }else {
            mobileET.setErrorEnabled(false);
            passwordET.setErrorEnabled(false);
            emailET.setErrorEnabled(false);
            nameET.setErrorEnabled(false);

            User user = new User(name,email,password,mobileNumber);
           registerUser(user);
        }

    }

    private void registerUser(final User user) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            logInUser(user);
                        }else{
                            Toast.makeText(RegisterActivity.this,"Registration Failed",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    private void logInUser(User user) {
        user.setUserID(mAuth.getCurrentUser().getUid());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        showProgressBar(false);

                        Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this,StateActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showProgressBar(false);
                Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_LONG).show();
                mAuth.getCurrentUser().delete();
            }
        });
    }

    private void initViews() {
        nameET = findViewById(R.id.nameTextRA);
        mobileET = findViewById(R.id.mobileTextRA);
        emailET = findViewById(R.id.emailTextRA);
        passwordET = findViewById(R.id.passwordTextRA);
    }
}