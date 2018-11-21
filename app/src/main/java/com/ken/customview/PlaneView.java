package com.ken.customview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

public class PlaneView extends View{
    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作
    private Paint mDeafultPaint = new Paint();
    private PathMeasure measure;

    {
        mDeafultPaint.setColor(Color.BLUE);
        mDeafultPaint.setStrokeWidth(10);
        mDeafultPaint.setStyle(Paint.Style.STROKE);
    }

    public PlaneView(Context context) {
        this(context,null);
    }

    public PlaneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;       // 缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow, options);
        mMatrix = new Matrix();
        Path path = new Path();                                 // 创建 Path

        path.addCircle(0, 0, 200, Path.Direction.CW);           // 添加一个圆形

        // 创建 PathMeasure
        measure = new PathMeasure(path, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);      // 平移坐标系



        measure.getPosTan(measure.getLength() * currentValue, pos, tan);        // 获取当前位置的坐标以及趋势

        mMatrix.reset();                                                        // 重置Matrix
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

      //  canvas.drawPath(path, mDeafultPaint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, mDeafultPaint);                     // 绘制箭头

    }

    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
        postInvalidate();
    }

    public void startAnim(){

        ObjectAnimator anim = ObjectAnimator.ofFloat(this, "currentValue", 0, 1);
        anim.setDuration(6000);
        anim.setRepeatCount(Animation.INFINITE);
       // anim.setRepeatMode(ValueAnimator.RESTART);
        anim.start();

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("zz", "onAttachedToWindow: 挂在" );
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("zz", "onDetachedFromWindow: 消失" );
    }
}
