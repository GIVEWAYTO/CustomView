package com.ken.customview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RippleView extends View {
    Paint paint = new Paint();
    private int size;
    private Random random;

    /**
     * 水波纹的数据
     */
    private List<RippleBean> beanList;

    /**
     * 每次水波纹增加的半径
     */
    private float RIPPLE_INTEVAL = Utils.dpToPixel(50);

    /**
     * 水波纹扩散速度
     */
    int speed = 2;
    private ValueAnimator valueAnimator;
    private ValueAnimator clickAnimator;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(Utils.dpToPixel(15));
        paint.setStyle(Paint.Style.FILL);
        beanList = new ArrayList<>();
        random = new Random();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //取出宽度的确切数值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //取出高度的确切数值
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int size = width > height ? height : width;

        setMeasuredDimension(size, size);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(255);
        paint.setColor(Color.parseColor("#99333333"));
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);
//        paint.setStyle(Paint.Style.STROKE);
        for (RippleBean bean : beanList) {
            if (bean.radius > size / 2 - Utils.dpToPixel(10)) continue;
            drwaRipple(bean, canvas);
        }
    }

    /**
     * 绘制单个水波纹
     *
     * @param bean
     * @param canvas
     */
    private void drwaRipple(RippleBean bean, Canvas canvas) {
        paint.setColor(bean.colorValue);
        paint.setAlpha((int) bean.alpha);
        canvas.drawCircle(size / 2, size / 2, bean.radius, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > size) {
            size = w;
        }
    }


    public void startAnim2() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        beanList.add(new RippleBean(Utils.dpToPixel(30), Color.rgb(r, g, b), 255));
        if ((clickAnimator != null && (clickAnimator.isStarted() || clickAnimator.isRunning()))) {
            return;
        }

        cancelAnim(valueAnimator);
        if (restartAnim(clickAnimator)) return;
        clickAnimator = ValueAnimator.ofFloat(0, 1);
        clickAnimator.setRepeatCount(ValueAnimator.INFINITE);
        clickAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (beanList.size() > 0 && beanList.get(0).radius >= size / 2) {
                    beanList.remove(0);
                }
                for (RippleBean bean : beanList) {
                    if (bean.radius < size / 2)
                        bean.radius += speed;
                    bean.alpha = 255.0f * (size / 2 - bean.radius - 15) / (size / 2);
                }

                invalidate();
            }
        });
        clickAnimator.start();
    }

    private boolean restartAnim(Animator animator) {
        if (animator != null && !(animator.isStarted() || animator.isRunning())) {
            animator.start();
            return true;
        }
        return false;
    }

    public void startAnim() {
        if (beanList.size() == 0) {
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            beanList.add(new RippleBean(0, Color.rgb(r, g, b), 255));
        }
        cancelAnim(clickAnimator);
        if ((valueAnimator != null && (valueAnimator.isStarted() || valueAnimator.isRunning())))
            return;
        if (restartAnim(valueAnimator)) return;

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if (beanList.size() > 0 && beanList.get(beanList.size() - 1).radius > RIPPLE_INTEVAL) {
                    int r = random.nextInt(256);
                    int g = random.nextInt(256);
                    int b = random.nextInt(256);
                    beanList.add(new RippleBean(0, Color.rgb(r, g, b), 255));
                    if (beanList.get(0).radius >= size / 2) {
                        beanList.remove(0);
                    }
                }

                for (RippleBean bean : beanList) {
                    if (bean.radius < size / 2)

                        bean.radius += speed;
                    bean.alpha = 255.0f * (size / 2 - bean.radius - 15) / (size / 2);
                }

                invalidate();
            }
        });

        valueAnimator.start();
    }

    private void cancelAnim(Animator animator) {
        if (animator != null && (animator.isStarted() || animator.isRunning())) {
            animator.cancel();
        }
    }

    class RippleBean {
        /**
         * 水波纹 初始的半径
         */
        float radius;
        /**
         * 水波纹颜色
         */
        int colorValue;
        /**
         * 水波纹透明度
         */
        float alpha;

        private RippleBean(float radius, int colorValue, float alpha) {
            this.radius = radius;
            this.alpha = alpha;
            this.colorValue = colorValue;
        }
    }
}
