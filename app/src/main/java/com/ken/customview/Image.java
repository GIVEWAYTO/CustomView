package com.ken.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class Image extends AppCompatImageView {
    public Image(Context context) {
        super(context);
    }

    public Image(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Image(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("zz", "onDraw: zzz == dfsf" );
    }
}
