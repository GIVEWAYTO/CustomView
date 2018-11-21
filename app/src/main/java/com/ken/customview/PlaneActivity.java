package com.ken.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlaneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane);

        PlaneView planeView = findViewById(R.id.plane);
        planeView.startAnim();
    }
}
