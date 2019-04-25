package com.ken.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
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

import com.scwang.smartrefresh.layout.util.DensityUtil;

import static android.view.animation.Animation.INFINITE;

public class HeadestBatteryView2 extends View {
    private RectF oval;    // 圆弧的矩形框
    private Paint paint;
    private int step = (int) DensityUtil.dp2px(1.5f);
    private float strokeWidth;
    private Path path;

    private Paint mPaint, mPaintMore;//画笔
    private PointF drawPoint, drawPoint2;//绘制点
    private ValueAnimator animator;
    private float mWidth, mHeight, waveHeight;//控件宽，控件高，水位
    private float waveDeepmin = DensityUtil.dp2px(2);//最小的波峰与波谷
    private float waveDeepMax = DensityUtil.dp2px(3);//最大的波峰与波谷
    private float waveDeep = DensityUtil.dp2px(2);//波峰与波谷
    private static final double WAVE_SPEED = 48;   //波浪运动的速度
    private String MAINCOLOR_DEF = "#7e9bdb", NEXTCOLOR_DEF = "#7e9bdb";//默认颜色
    private int mainColor = Color.parseColor(MAINCOLOR_DEF), nextColor = Color.parseColor(NEXTCOLOR_DEF);//颜色
    private int count = 0;//绘制次数
    private RectF fillRectF;   // 在裁剪canvas时，填充path
    private int baseline;
    private final int maxProgress = 100;    //最大进度
    private int progress = 50;    //当前进度
    private float progressWidth;    //圆环的半径
    private int progressColor;
    private RectF bgRect;
    private RectF bottomBgRect;
    private Path path1;

    public HeadestBatteryView2(Context context) {
        this(context, null);
    }

    public HeadestBatteryView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadestBatteryView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadestBatteryView);
        try {
            progress = typedArray.getInteger(R.styleable.HeadestBatteryView_battery,0);
        } finally {
            if (typedArray != null) typedArray.recycle();
        }
        fillRectF = new RectF();
        bottomBgRect = new RectF();
        bgRect = new RectF();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#7e9bdb"));
        strokeWidth = DensityUtil.dp2px(2);
        paint.setStrokeWidth(0);
        path = new Path();

        mPaint = new Paint();
        mPaint.setColor(mainColor);//设置颜色
        mPaint.setAntiAlias(true);//抗锯齿(性能影响)
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setAlpha(21);
        mPaintMore = new Paint();
        mPaintMore.setAntiAlias(true);//抗锯齿
        mPaintMore.setStyle(Paint.Style.FILL);
        mPaintMore.setColor(nextColor);//设置颜色

        drawPoint = new PointF(0, 0);
        drawPoint2 = new PointF(0, 0);

        oval = new RectF();
        Rect rect = new Rect();
        paint.setTextSize(DensityUtil.dp2px(13));
        paint.getTextBounds(getProgressText(), 0, getProgressText().length(), rect);
        path1 = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;//获得控件宽度
        mHeight = h;//获得控件高度

        waveHeight = mHeight;//初始化开始水位
        fillRectF.set(getLeft(0), w / 2 + getLeft(0), w - getLeft(0), h - w / 2);
        bottomBgRect.set(strokeWidth,mHeight - mWidth,mWidth-strokeWidth,mHeight);
        bgRect.set(strokeWidth,mWidth/2,mWidth-strokeWidth,mHeight - mWidth / 2);

        progressWidth = w * 0.35f;
        oval.set(0, 0, mWidth, mWidth);  //用于定义的圆弧的形状和大小的界限

        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        //获得文字的基准线
        baseline = (int) (oval.centerY() - fontMetrics.top/2 - fontMetrics.bottom/2);

        super.onSizeChanged(w, h, oldw, oldh);
    }

    //设置主波、副波的颜色
    private void setMainWaveColor(int color) {
        mPaint.setColor(color);

        mPaintMore.setColor(color);
        mPaintMore.setAlpha(50);
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
        waveHeight = mHeight* (100 - progress) / 100;
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

    public int getProgress() {
        return progress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(progressColor);
        paint.setAlpha(76);

        // 绘制边框
        canvas.drawRect(bgRect,paint);
        canvas.drawArc(bottomBgRect,0,180,false,paint);


        // 保存画布当前状态
        path.reset();
        path1.reset();
        path.addRect(fillRectF, Path.Direction.CW);
        path1.addArc(bottomBgRect,0,180);
        path.op(path1, Path.Op.UNION);

        canvas.save();
        canvas.clipPath(path);

        drawPoint.x = strokeWidth;//重置为0,从原点开始绘制
        Double rightperiod = Math.PI / WAVE_SPEED * count;//每次平移Math.PI/8个周期  这里可以控制速度
        if (count == WAVE_SPEED * 2) {//每次平移Math.PI/8个周期,平移第16次,平移了一个完整的周期
            count = 0;//平移了一个完整周期归零重新开始计数
        } else {
            count++;
        }
        paint.setAlpha(255);
        //在宽度以内绘制一条条竖线
        while (drawPoint.x < mWidth - strokeWidth  && drawPoint.x >= strokeWidth) {
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
        canvas.restore();
        // 绘制进度条
        paint.setAlpha(255);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(mWidth / 2, mWidth / 2 , mWidth / 2 , paint); //画出圆
        paint.setColor(progressColor);

        canvas.drawCircle(mWidth / 2, mWidth / 2  , mWidth / 2 - DensityUtil.dp2px(1.5f), paint); //画出圆

        paint.setColor(Color.WHITE);
        //拿到字符串的宽度
        float stringWidth = paint.measureText(getProgressText());
        float x = (getMeasuredWidth() - stringWidth) / 2;
        canvas.drawText(getProgressText(), x, baseline, paint);
    }

    //中间的进度百分比
    private String getProgressText() {
        return progress + "%";
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

    public void destory(){
        if(animator!=null){
            animator.cancel();
            animator = null;
        }
    }

    private float getLeft(int i) {
        return strokeWidth / 2 + step * i;
    }
}
