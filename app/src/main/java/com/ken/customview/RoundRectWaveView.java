package com.ken.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static android.view.animation.Animation.INFINITE;

public class RoundRectWaveView extends View {
    private RectF oval;    // 圆弧的矩形框
    private Paint paint;
    private float strokeWidthTwo;  // 最大一层边框
    private float strokeWidthOne;   // 最小一层边框
    private float strokeWidthThree; // 中间一层边框

    private Path mPath;//路径
    private Paint mPaint, mPaintMore;//画笔
    private PointF drawPoint, drawPoint2;//绘制点
    private ValueAnimator animator;
    private float mWidth, mHeight, waveHeight;//控件宽，控件高，水位
    private float waveDeepmin = Utils.dpToPixel(2);//最小的波峰与波谷
    private float waveDeepMax = Utils.dpToPixel(3);//最大的波峰与波谷
    private float waveDeep = Utils.dpToPixel(2);//波峰与波谷
    private static final double WAVE_SPEED = 48;   //波浪运动的速度
    private String MAINCOLOR_DEF = "#7e9bdb", NEXTCOLOR_DEF = "#7e9bdb";//默认颜色
    private int mainColor = Color.parseColor(MAINCOLOR_DEF), nextColor = Color.parseColor(NEXTCOLOR_DEF);//颜色
    private int count = 0;//绘制次数
    private RectF strokeRectThree;
    private RectF strokeRectTwo;
    private RectF strokeRectOne;
    private final float roundRectRadiusThree = Utils.dpToPixel(37);
    private final float roundRectRadiusSecond = Utils.dpToPixel(40);
    private int baseline;
    private final int maxProgress = 100;    //最大进度
    private float progress = 50;    //当前进度
    private float progressWidth;    //圆环的半径
    private int progressColor;
    private Rect rect;

    public RoundRectWaveView(Context context) {
        this(context, null);
    }

    public RoundRectWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.color1));
        paint.setAntiAlias(true);

        strokeWidthThree = Utils.dpToPixel(4);
        strokeWidthTwo = Utils.dpToPixel(2);
        strokeWidthOne = Utils.dpToPixel(1);

        oval = new RectF();
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(mainColor);//设置颜色
        mPaint.setAntiAlias(true);//抗锯齿(性能影响)
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha(21);
        mPaintMore = new Paint();
        mPaintMore.setAntiAlias(true);//抗锯齿
        mPaintMore.setStyle(Paint.Style.FILL);
        mPaintMore.setColor(nextColor);//设置颜色
        mPaintMore.setAlpha(16);
        drawPoint = new PointF(0, 0);
        drawPoint2 = new PointF(0, 0);



        rect = new Rect();
        paint.setTextSize(Utils.dpToPixel(12));

        paint.getTextBounds(getProgressText(), 0, getProgressText().length(), rect);



        // 边框大小
        strokeRectThree = new RectF();
        strokeRectTwo = new RectF();
        strokeRectOne = new RectF();

    }

    //设置主波、副波的颜色
    private void setMainWaveColor(int color) {
        mPaint.setColor(color);
        mPaint.setAlpha(41);
        mPaintMore.setColor(color);
        mPaintMore.setAlpha(26);
    }

    //中间的进度百分比
    private String getProgressText() {
        return ((int) progress) + "%";
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;//获得控件宽度
        mHeight = h;//获得控件高度

        waveHeight = mHeight;//初始化开始水位


        // 计算 圆角边框的大小
        strokeRectThree.set(strokeWidthThree, strokeWidthThree, w - strokeWidthThree, h - strokeWidthThree);
        strokeRectTwo.set(strokeWidthTwo, strokeWidthTwo, w - strokeWidthTwo, h - strokeWidthTwo);
        strokeRectOne.set(strokeWidthOne, strokeWidthOne, w - strokeWidthOne, h - strokeWidthOne);

        progressWidth = h * 0.25f;
        oval.set(w / 2 - progressWidth, h * 0.16f, w / 2 + progressWidth, h * 0.16f + progressWidth * 2);  //用于定义的圆弧的形状和大小的界限

        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        //获得文字的基准线
        baseline = (int) (oval.centerY() - fontMetrics.top/2 - fontMetrics.bottom/2);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 修改波浪的百分比高度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (progress > 100) progress = 100;
        if (progress < 0) progress = 0;
        this.progress = progress;
        waveHeight = mHeight * (100 - progress) / 100;
        if (progress < 20) {
            progressColor = getResources().getColor(R.color.progress_color_red);
            setMainWaveColor(progressColor);
            paint.setColor(progressColor);
        } else if (progress < 90) {
            progressColor = getResources().getColor(R.color.progress_color_blue);
            setMainWaveColor(progressColor);
            paint.setColor(progressColor);
        } else {
            progressColor = getResources().getColor(R.color.progress_color_green);
            setMainWaveColor(progressColor);
            paint.setColor(progressColor);
        }
    }

    /**
     * 开始波浪动画
     */
    public void initWaveAnim() {
        animator = ValueAnimator.ofFloat(waveDeepmin, waveDeepMax);//设置属性值变化范围，最大波峰波谷与最小
        animator.setDuration(3000);//设置动画时间
        animator.setInterpolator(new LinearInterpolator());//控制动画的变化速率
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                waveDeep = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setRepeatMode(ValueAnimator.REVERSE);//往返模式
        animator.setRepeatCount(INFINITE);
        animator.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(progressColor);

        // 绘制外边框
        // 最里层  也就是第三层
        paint.setAlpha(26);
        paint.setStrokeWidth(strokeWidthThree);

        canvas.drawRoundRect(strokeRectThree, roundRectRadiusThree, roundRectRadiusThree, paint);

        // 中间层  也就是第二层
        paint.setAlpha(76);
        paint.setStrokeWidth(strokeWidthTwo);

        canvas.drawRoundRect(strokeRectTwo, roundRectRadiusSecond, roundRectRadiusSecond, paint);

        // 最外层  也就是第一层
        paint.setAlpha(128);
        paint.setStrokeWidth(strokeWidthOne);
        canvas.drawRoundRect(strokeRectOne, roundRectRadiusSecond, roundRectRadiusSecond, paint);

        // 绘制波浪
        mPath.reset();//重置路径
        mPath.addRoundRect(strokeRectOne, roundRectRadiusSecond, roundRectRadiusSecond, Path.Direction.CW);//画以(arcRa,arcRa),半径为arcRa的顺时针的圆
        canvas.clipPath(mPath);//裁剪

        drawPoint.x = 0;//重置为0,从原点开始绘制
        Double rightperiod = Math.PI / WAVE_SPEED * count;//每次平移Math.PI/8个周期  这里可以控制速度
        if (count == WAVE_SPEED * 2) {//每次平移Math.PI/8个周期,平移第16次,平移了一个完整的周期
            count = 0;//平移了一个完整周期归零重新开始计数
        } else {
            count++;
        }

        //在宽度以内绘制一条条竖线
        while (drawPoint.x < mWidth) {
            //第一条波的y坐标
            Double anglenum = Math.PI / 180;
            drawPoint.y = (float) (waveHeight - waveDeep * Math.sin(drawPoint.x * anglenum - rightperiod));
            //第二条波的y坐标，比第一条向右移动了Math.PI/2个周期
            drawPoint2.y = (float) (waveHeight - waveDeep * Math.sin(drawPoint.x * anglenum - rightperiod - Math.PI / 2));
            //先绘制被遮挡的副波的竖线
            canvas.drawLine(drawPoint.x, drawPoint2.y, drawPoint.x, mHeight, mPaintMore);
            //绘制最上面显示主波的竖线
            canvas.drawLine(drawPoint.x, drawPoint.y, drawPoint.x, mHeight, mPaint);
            //跳到下一个点继续
            drawPoint.x++;
        }


        // 绘制进度条
        paint.setAlpha(255);
        paint.setStrokeWidth(Utils.dpToPixel(5));
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() * 0.16f + progressWidth, progressWidth +Utils.dpToPixel(1f), paint); //画出圆


        paint.setColor(Color.WHITE);
        canvas.drawArc(oval, 270, 360 * ((100 - progress) / maxProgress), false, paint);  //根据进度画圆弧



        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() * 0.16f + progressWidth, progressWidth +Utils.dpToPixel(1f), paint); //画出圆
        paint.setStrokeWidth(0);
        paint.setColor(Color.parseColor("#333333"));
        //拿到字符串的宽度
        float stringWidth = paint.measureText(getProgressText());
        float x =(getMeasuredWidth()-stringWidth)/2;
        canvas.drawText(getProgressText(), x, baseline, paint);
    }
}
