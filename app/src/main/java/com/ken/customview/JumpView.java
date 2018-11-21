package com.ken.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

public class JumpView extends android.support.v7.widget.AppCompatTextView {
    private final int VIEW_SIZE = 20;
    private int width;
    private final int radius;
    private Point startPoint;
    private Point endPoint;
    private Paint paint;

    public JumpView(Context context) {
        super(context);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        setTextColor(Color.parseColor("#ffffff"));
        setText("1");
        setGravity(Gravity.CENTER);

        width = (int) Utils.dpToPixel(VIEW_SIZE);
        radius = width / 2;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
        this.startPoint.y -= 50;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }


    public void startAnim(){
        if(startPoint == null || endPoint == null)return;
        //设置控制点
        Point controllPoint = new Point();
        controllPoint.x = (startPoint.x + startPoint.y)/2 + 10;
        controllPoint.y = (int) (startPoint.y - Utils.dpToPixel(60));

        BezierEvaluator evaluator = new BezierEvaluator(controllPoint);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(evaluator, startPoint, endPoint);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ViewGroup viewGroup = (ViewGroup) getParent();
                viewGroup.removeView(JumpView.this);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                setX(point.x);
                setY(point.y);
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width,width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(width/2,width/2,width/2,paint);
        super.onDraw(canvas);
    }


    public class BezierEvaluator implements TypeEvaluator<Point> {

        private Point controllPoint;

        public BezierEvaluator(Point controllPoint) {
            this.controllPoint = controllPoint;
        }

        @Override
        public Point evaluate(float t, Point startValue, Point endValue) {
            int x = (int) ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controllPoint.x + t * t * endValue.x);
            int y = (int) ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controllPoint.y + t * t * endValue.y);
            return new Point(x, y);
        }
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
