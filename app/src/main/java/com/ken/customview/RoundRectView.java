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
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.airbnb.lottie.parser.ColorParser;

import static android.view.animation.Animation.INFINITE;

public class RoundRectView extends View {


    private int width;
    private int height;
    private Paint paint;
    RectF square = new RectF();
    RectF progressSquare = new RectF();
    Path path = new Path();
    private int progress = 30;
    private final int PROGRESS_MAX = 100;
    private int radius = (int) Utils.dpToPixel(15);
    float[] radii = {0, 0, 0, 0, radius, radius, radius, radius};

    public RoundRectView(Context context) {
        this(context, null);
    }

    public RoundRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        square.set(0, 0, width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(square, radius, radius, paint);
        paint.setColor(Color.parseColor("#7E9BDB"));
        float top = (float) (square.bottom * (PROGRESS_MAX - progress)) / PROGRESS_MAX;
        progressSquare.set(square.left, top, square.right, square.bottom);

        path.addRoundRect(progressSquare, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }
}
