package com.ken.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * 成功按钮自定义控件
 *
 * @author ken
 * @e-mail gr201655@163.com
 * @time 2018/10/11 10:09
 */
public class SuccessView extends View {
    /**
     * 按钮画笔
     */
    private Paint roundPaint = new Paint();

    /**
     * 按钮背景色
     */
    private int roundColor ;

    //按钮的宽高
    private int viewWidth = (int) Utils.dpToPixel(150);
    private int viewHeight = (int) Utils.dpToPixel(50);
    //按钮矩形
    private RectF roundRect;
    //按钮的圆角半径
    private int roundSize;
    private int initRoundSize = (int) Utils.dpToPixel(8);
    private int rectWidth = viewWidth;

    // 打勾的画笔
    private Paint tickPaint = new Paint();
    //“✔”的背景色
    private int tickColor = Color.WHITE;
    //“✔”的路径
    private Path tickPath = new Path();
    private Path copyTickPath = new Path();
    private float tickPercent = 0;

    private String content = "Success!";

    //文字的矩形控制
    private RectF textRectF = new RectF();

    private PathMeasure measure = new PathMeasure();

    //“✔”的长度
    private float tickLength;

    // 文字alpha动画
    private float percent = 1;

    //动画集合
    AnimatorSet set = new AnimatorSet();

    public SuccessView(Context context) {
        this(context,null);
    }

    public SuccessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(viewWidth,viewHeight);
    }

    private void initView(Context context) {
        roundColor = getContext().getResources().getColor(R.color.colorAccent);
        roundPaint.setColor(roundColor);
        roundPaint.setStyle(Paint.Style.FILL);
        roundPaint.setAntiAlias(true);
        roundPaint.setTextSize(Utils.dpToPixel(14));
        roundPaint.setTextAlign(Paint.Align.CENTER);
        tickPaint.setColor(tickColor);
        tickPaint.setStyle(Paint.Style.STROKE);
        tickPaint.setAntiAlias(true);
        tickPaint.setStrokeWidth(Utils.dpToPixel(6));
        tickPaint.setStrokeCap(Paint.Cap.ROUND);


        roundRect = new RectF(0,0, rectWidth,viewHeight);
        roundSize = initRoundSize;

        tickPath.moveTo((viewWidth - viewHeight) / 2 + Utils.dpToPixel(12),viewHeight/2 + Utils.dpToPixel(5));
        tickPath.lineTo(viewWidth/2 ,viewHeight/2 + Utils.dpToPixel(12));
        tickPath.lineTo((viewWidth + viewHeight) / 2 - Utils.dpToPixel(10),  Utils.dpToPixel(15));

        measure.setPath(tickPath,false);
        tickLength = measure.getLength();


        textRectF.left = 0;
        textRectF.top = 0;
        textRectF.right = viewWidth;
        textRectF.bottom = viewHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        roundPaint.setColor(roundColor);
        roundPaint.setAlpha(255);

        canvas.drawRoundRect(roundRect,roundSize,roundSize,roundPaint);

        measure.getSegment(0, tickLength * tickPercent,copyTickPath,true);

        canvas.drawPath(copyTickPath,tickPaint);

        //文字绘制
        roundPaint.setColor(Color.WHITE);
        roundPaint.setAlpha((int) (255*percent));
        Paint.FontMetricsInt fontMetrics = roundPaint.getFontMetricsInt();
        int baseline = (int) ((textRectF.bottom + textRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        //文字绘制到整个布局的中心位置
        canvas.drawText(content, textRectF.centerX(), baseline, roundPaint);
    }

    public int getRoundSize() {
        return roundSize;
    }

    public void setRoundSize(int roundSize) {
        this.roundSize = roundSize;
        invalidate();
    }

    public void setRectWidth(int rectWidth) {
        roundRect.right = viewWidth - rectWidth ;
        roundRect.left  = rectWidth ;
        this.rectWidth = rectWidth;

    }

    @Override
    public float getTranslationX() {
        return super.getTranslationX();
    }

    public float getTickPercent() {
        return tickPercent;
    }

    public void setTickPercent(float tickPercent) {
        this.tickPercent = tickPercent;
        invalidate();
    }

    /**
     * 取消动画
     */
    public void cancelAnim(){
        if(set != null){
            if(set.isRunning() || set.isStarted())set.cancel();
        }
    }

    /**
     * 重置动画
     */
    public void reset(){
        this.setTranslationY(0);
        roundSize = initRoundSize;
        rectWidth = viewWidth;
        roundRect.left = 0;
        roundRect.right = viewWidth;
        percent = 1;
        this.tickPercent = 0;
        copyTickPath.reset();
        cancelAnim();
        invalidate();
    }

    /**
     * 动画开始
     */
    public void startAnim(){
        //圆角矩形的圆角半径逐渐增大  使变成半圆的按钮
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "roundSize", initRoundSize, viewHeight / 2);
        ObjectAnimator viewWidthAnimator = ObjectAnimator.ofInt(this, "rectWidth", 0, (viewWidth - viewHeight)/2);
        viewWidthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 文字透明度变化
                percent =1 - animation.getAnimatedFraction();
            }
        });
        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", 0, -viewWidth);
        ObjectAnimator tickPath = ObjectAnimator.ofFloat(this, "tickPercent", 0, 1.0f);

        animator.setDuration(1000);
        viewWidthAnimator.setDuration(1000);
        translationY.setDuration(1000);
        tickPath.setDuration(1000);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                SuccessView.this.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                SuccessView.this.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                SuccessView.this.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.play(animator).with(viewWidthAnimator)
                .before(translationY)
                .before(tickPath);
        set.start();

    }
}
