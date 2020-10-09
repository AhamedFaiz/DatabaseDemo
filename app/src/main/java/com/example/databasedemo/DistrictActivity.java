package com.example.databasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.databasedemo.models.States;

import java.util.Objects;

public class DistrictActivity extends BaseActivity {
    private static final String TAG = "DistrictActivity";

    //vars
    Spinner spinner;
    String selectedDistrict;
    States state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        spinner = findViewById(R.id.districtSpinner);

        Intent intent = getIntent();
        state =  intent.getParcelableExtra("selected_state");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Log.d(TAG, "onCreate: " + Objects.requireNonNull(state).getDistricts());

        setupSpinner(state);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDistrict = state.getDistricts().get(i);
                Log.d(TAG, "onItemSelected: " + selectedDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findViewById(R.id.nextButtonDA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DistrictActivity.this,DataActivity.class);
                intent.putExtra("selectedState" , state.getState_name());
                intent.putExtra("selectedDistrict" ,selectedDistrict);
                startActivity(intent);
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

    private void setupSpinner(States state) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,state.getDistricts());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
}