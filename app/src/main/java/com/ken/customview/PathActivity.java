package com.ken.customview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class PathActivity extends AppCompatActivity {

    private RadarView mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        mChart = findViewById(R.id.radar);
    }

    private void initRadar() {
        String[] mActivities = new String[]{"商业模式", "核心壁垒", "团队结构", "运营管理", "盈利能力", "成长能力"};
        mChart.setTitles(mActivities);
        ArrayList<Float> data = new ArrayList<>();
        data.add(1.5f);
        data.add(4f);
        data.add(3.8f);
        data.add(2.2f);
        data.add(3.0f);
        data.add(4.5f);
        mChart.setData(data);
        mChart.setMaxValue(5);
        mChart.setValuePaintColor(Color.rgb(239, 83, 80));
        mChart.setStrokeWidth(3f);
        mChart.setMainPaintColor(Color.GRAY);
        mChart.setCircleRadius(1f);
        mChart.setTextPaintTextSize(28);
        mChart.setInnerAlpha(166);
        mChart.setLableCount(6);
        mChart.setDrawLabels(false);
        mChart.setShowValueText(true);
        mChart.invalidate();
    }
}
