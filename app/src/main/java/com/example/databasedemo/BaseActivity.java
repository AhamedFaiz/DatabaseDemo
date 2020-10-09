package com.example.databasedemo;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class BaseActivity extends AppCompatActivity {

    public ProgressBar mProgressBar;


    @Override
    public void setContentView(int layoutResID) {

        //Associating frame layout with the base activity
        ConstraintLayout constraintLayout =(ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base,null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activityContent);
        mProgressBar = constraintLayout.findViewById(R.id.progressBar);
        getLayoutInflater().inflate(layoutResID,frameLayout,true);
        super.setContentView(constraintLayout);
    }

    public void showProgressBar(boolean visibility){
        mProgressBar.setVisibility(visibility? View.VISIBLE : View.GONE);
    }
}
