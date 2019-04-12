package com.ken.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ProgressActivity extends AppCompatActivity {

    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        final HeadestBatteryView headestBatteryView = (HeadestBatteryView) findViewById(R.id.view1);
        findViewById(R.id.round).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((RoundRectWaveView) findViewById(R.id.round)).initWaveAnim();
                ((RoundRectWaveView) findViewById(R.id.round)).setProgress(62);
                headestBatteryView.initWaveAnim();
                headestBatteryView.setProgress(82);

            }
        }, 50);
        progress = 0;
        final RoundRectWaveView roundRectView = findViewById(R.id.round);
        findViewById(R.id.round).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress +=5;
                if(progress>100)progress = 0;
                roundRectView.setProgress(progress);
                headestBatteryView.setProgress(progress);
            }
        });

//        ((HeadestBatteryView) findViewById(R.id.view1)).setMaskFilter(BlurMaskFilter.Blur.INNER);
//        ((HeadestBatteryView) findViewById(R.id.view2)).setMaskFilter(BlurMaskFilter.Blur.OUTER);
//        ((HeadestBatteryView) findViewById(R.id.view3)).setMaskFilter(BlurMaskFilter.Blur.NORMAL);
//        ((HeadestBatteryView) findViewById(R.id.view4)).setMaskFilter(BlurMaskFilter.Blur.SOLID);
    }
}
