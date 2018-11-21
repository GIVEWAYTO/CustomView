package com.ken.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PathTestView extends View {
    private Paint paint = new Paint();

    public PathTestView(Context context) {
        this(context, null);
    }

    public PathTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getMeasuredWidth() / 2 ,getMeasuredHeight() / 2);
        Path path = new Path();
        paint.setColor(Color.BLACK);
        path.addRect(-200,-200,200,200,Path.Direction.CW);
        path.addRoundRect(-100,-100,100,100,15,15, Path.Direction.CW);
        Path circle = new Path();
        circle.addCircle(0,0,50, Path.Direction.CW);
        path.addPath(circle,100,50);
        canvas.drawPath(path,paint);
        paint.setColor(Color.BLUE);
        path.addCircle(0,0,50, Path.Direction.CW);
        path.close();
        canvas.drawPath(path,paint);
    }
}
