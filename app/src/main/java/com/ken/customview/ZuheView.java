package com.ken.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ZuheView extends LinearLayout {
    public ZuheView(Context context) {
        this(context,null);
    }

    public ZuheView(Context context, @Nullable AttributeSet attrs) {
        this(context, null,0);
    }

    public ZuheView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.zuhe,this);

    }
}
