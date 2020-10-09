package com.example.databasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.databasedemo.models.Post;
import com.example.databasedemo.models.States;
import com.example.databasedemo.models.SubmissionForm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataActivity extends BaseActivity {
    private static final String TAG = "DataActivity";

    //ui
    Spinner spinner;
    TextInputLayout nameET;
    TextInputLayout mobileET;
    TextInputLayout whatsAppET;
    TextInputLayout addressET;

    //vars
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference stateRef = db.collection("Posts");
    List<Post> postList = new ArrayList<>();
    String selectedPost;
    String selectedState;
    String selectedDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        spinner = findViewById(R.id.postSpinner);
        nameET = findViewById(R.id.nameText);
        mobileET = findViewById(R.id.mobileText);
        whatsAppET = findViewById(R.id.whatsAppText);
        addressET = findViewById(R.id.addressText);

        showProgressBar(true);


        Intent intent = getIntent();
        selectedState = intent.getStringExtra("selectedState");
        selectedDistrict = intent.getStringExtra("selectedDistrict");
        loadData();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPost = postList.get(0).getPosts().get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromEditText();
            }
        });


    }

    private void getDataFromEditText() {
        String name = nameET.getEditText().getText().toString();
        String whatsAppNumber = whatsAppET.getEditText().getText().toString();
        String mobileNumber = mobileET.getEditText().getText().toString();
        String address = addressET.getEditText().getText().toString();
        if(name.isEmpty()){
            nameET.setErrorEnabled(true);
            nameET.setError("Name Cannot be Empty");
        }else if (!(mobileNumber.length() == 10)){
            mobileET.setErrorEnabled(true);
            mobileET.setError("Enter 10 digit Mobile Number");
        }else if (!whatsAppNumber.isEmpty() && !(whatsAppNumber.length() == 10)){
            whatsAppET.setErrorEnabled(true);
            whatsAppET.setError("Enter 10 digit Mobile Number");
        }else{
            whatsAppET.setErrorEnabled(false);
            mobileET.setErrorEnabled(false);
            nameET.setErrorEnabled(false);

            SubmissionForm submissionForm = new SubmissionForm(selectedState,selectedDistrict,name,
                    selectedPost,mobileNumber,whatsAppNumber,address);

            showProgressBar(true);

            submitForm(submissionForm);
        }


    }

    private void submitForm(SubmissionForm submissionForm) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Submitted Forms")
                .add(submissionForm)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        showProgressBar(false);
                        Toast.makeText(getApplicationContext(),"Form Submitted SuccessFully",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DataActivity.this,SuccessActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showProgressBar(false);
                Toast.makeText(DataActivity.this,"Form Submission Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadData() {
        stateRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                postList.add(document.toObject(Post.class));
                            }
                            setupSpinner();
                        } else {
                            showProgressBar(false);
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private  void setupSpinner(){

        showProgressBar(false);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,postList.get(0).getPosts());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
}