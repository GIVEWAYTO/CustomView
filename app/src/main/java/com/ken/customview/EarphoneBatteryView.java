package com.ken.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class EarphoneBatteryView extends RelativeLayout {
    public EarphoneBatteryView(Context context) {
        this(context,null);
    }

    public EarphoneBatteryView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EarphoneBatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context,R.layout.left_ear_battery,this);

        HeadestBatteryView2 battery = findViewById(R.id.battery);
        battery.setProgress(60);
    }
}
