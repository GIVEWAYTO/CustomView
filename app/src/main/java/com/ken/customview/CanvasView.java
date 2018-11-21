package com.ken.customview;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static com.ken.customview.Utils.dpToPixel;

public class CanvasView extends View {
    float progress = 50;
    private int mWidth;
    private int mHeight;
    final float radius = dpToPixel(80);

    RectF arcRectF = new RectF();
    Paint paint ;
    public CanvasView(Context context) {
        this(context,null);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heitht = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("zz", "onMeasure: w 1 == " + width );
        Log.e("zz", "onMeasure: h 1 == " + heitht );
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;

        Log.e("zz", "onSizeChanged: 2 == " + w );
        Log.e("zz", "onSizeChanged: 2 == " + h );
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        Log.e("zz", "initPaintcccccccccccccccsssssssssssssssssssss: ssssss" );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       // canvas.translate(mWidth / 2,mHeight / 2);

//        RectF rect = new RectF(-400,-400,400,400);   // 矩形区域
//
//        for (int i=0; i<=20; i++)
//        {
//            canvas.scale(0.9f,0.9f);
//            canvas.drawRect(rect,paint);
//        }

        //////////////////////////////////
//        Path path = new Path();                     // 创建Path
//
//        path.lineTo(200, 200);                      // lineTo
//
//        path.setLastPoint(200,100);                 // setLastPoint
//
//        path.lineTo(200,0);                         // lineTo
//
//        canvas.drawPath(path, paint);              // 绘制Path

        canvas.translate(mWidth / 5, mHeight / 5);  // 移动坐标系到屏幕中心
        canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴

        Path path = new Path();                     // path中添加一个圆形(圆心在坐标原点)
        path.addCircle(0,0,100, Path.Direction.CW);

        Path dst = new Path();                      // dst中添加一个矩形
        dst.addRect(-100,-100,100,100, Path.Direction.CW);

        path.offset(100,0,dst);                     // 平移

        canvas.drawPath(path,paint);               // 绘制path

        paint.setColor(Color.BLUE);                // 更改画笔颜色

        canvas.drawPath(dst,paint);                // 绘制dst
    }
}
