package com.ken.customview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LoadingView extends View {
    private Paint paint = new Paint();
    private RectF arcRect;

    private float progress = 0; //进度
    private int totalWidth;
    private int totalHeight;
    private Rect bgRect;
    private int bgColor;
    private int progressColor;

    private float currentLength = 0;
    private final int TOTAL_PROGRESS = 100;
    private int arcRadius;
    private int oldProgress = 0;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        bgColor = getContext().getResources().getColor(R.color.colorAccent);
        progressColor = getContext().getResources().getColor(R.color.colorPrimary);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        totalWidth = w;
        totalHeight = h;   //半圆的半径 = 高度 / 2
        arcRadius = h /2;
        arcRect = new RectF(0, 0, totalHeight, totalHeight);
        bgRect = new Rect(totalHeight / 2, 0, totalWidth, totalHeight);
        currentLength = totalWidth *  progress / TOTAL_PROGRESS ;
    }

    public void setProgress(float progress) {
        if (progress < 0) progress = 0;
        if (progress > 100) progress = 100;
        this.progress = progress;
        currentLength = totalWidth *  progress / TOTAL_PROGRESS ;
        invalidate();

        Log.e("zz", "setProgress: totalWidth == " + totalWidth );
    }

    public void setProgressAnim(final int progress){

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", oldProgress, progress);
        animator.setDuration(1000);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                oldProgress = progress;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentLength < (totalHeight / 2)) { //现在的进度还处于半圆当中
            //画背景
            paint.setColor(bgColor);
            canvas.drawArc(arcRect, 90, 180, false, paint);
            canvas.drawRect(bgRect, paint);

            //画进度

            //圆弧的角度
            int arcAngle = (int) Math.toDegrees(Math.acos((arcRadius - currentLength) /(float) arcRadius));
            //起始角度
            float starAngle = 180 - arcAngle;
            //滑过的角度
            float sweepAngle = 2 * arcAngle;
            paint.setColor(progressColor);
            canvas.drawArc(arcRect, starAngle, sweepAngle,false,paint);

        } else {

            //画背景
            paint.setColor(bgColor);
            canvas.drawRect(bgRect, paint);
            //画进度
            paint.setColor(progressColor);
            canvas.drawArc(arcRect, 90, 180, false, paint);
            canvas.drawRect(new RectF(arcRadius,0,currentLength,totalHeight),paint);
        }

    }
}
