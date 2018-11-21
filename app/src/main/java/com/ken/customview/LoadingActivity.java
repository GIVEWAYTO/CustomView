package com.ken.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoadingActivity extends AppCompatActivity {
    int progress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final LoadingView loadingView = findViewById(R.id.load);
        loadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress += 10;
                if(progress > 100)progress = 0;
                loadingView.setProgressAnim(progress);
            }
        });
    }
}
