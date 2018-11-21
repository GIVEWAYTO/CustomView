package com.ken.customview;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class RippleActivity extends AppCompatActivity {

    private RippleView rippleView;
    private View icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);

        rippleView = findViewById(R.id.ripple);

        icon = findViewById(R.id.icon);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rippleView.startAnim();

        ScaleAnimation animation = new ScaleAnimation(1,1.5f,1,1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setRepeatMode(ValueAnimator.REVERSE);
        animation.setRepeatCount(ValueAnimator.INFINITE);
        icon.setAnimation(animation);
        animation.start();
    }

    public void click(View v){
        switch (v.getId()) {
            case R.id.btnA:
                rippleView.startAnim();
                break;
            case R.id.btnB:
                rippleView.startAnim2();
                break;
        }
    }
}
