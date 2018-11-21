package com.ken.customview;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FoodActivity extends AppCompatActivity {

    private ImageView delete;
    private RelativeLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        delete = findViewById(R.id.delete);

        content = findViewById(R.id.content);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("zz", "onTouchEvent: zzzz" );



            Log.e("zz", "MotionEvent.ACTION_UP  == + " +event.getX() + "   " + event.getY() );
            JumpView jumpView = new JumpView(this);
            jumpView.setStartPoint(new Point((int) event.getX(), (int) event.getY()));
            int[] location = new int[2];
            delete.getLocationOnScreen(location);

//            jumpView.setEndPoint(new Point(((int) delete.getX()), ((int) delete.getY())));
            jumpView.setEndPoint(new Point(((int) location[0]) + ((int) Utils.dpToPixel(10)), ((int) location[1])));
            int[] newPosistion = new int[2];
            delete.getLocationInWindow(newPosistion);

            Log.e("zz", "getLocationInWindow: x == " + location[0]  + "   y == " + location[1] + "     "  + delete.getX() + "   " + delete.getY()) ;
            Log.e("zz", "getLocationOnScreen: x == " + newPosistion[0] + "   y == " + newPosistion[1]);
           ((ViewGroup) this.getWindow().getDecorView()).addView(jumpView);
          //  content.addView(jumpView);
            jumpView.startAnim();
            return true;

    }
}
