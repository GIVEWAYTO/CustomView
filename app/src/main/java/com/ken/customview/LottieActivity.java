package com.ken.customview;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class LottieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        TextView textView = new TextView(this);

//        final LottieAnimationView load = findViewById(R.id.load);
//        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f)
//                .setDuration(6500);
//        load.loop(true);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                load.setProgress((Float) animation.getAnimatedValue());
//            }
//        });
//        animator.start();

//        decoration

//        ImageView image = findViewById(R.id.image);
//        RotateAnimation rotate  = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        LinearInterpolator lin = new LinearInterpolator();
//        rotate.setDuration(800);//设置动画持续周期
//        LinearInterpolator lir = new LinearInterpolator();
//
//        rotate.setInterpolator(lir);
//        rotate.setRepeatCount(-1);//设置重复次数
//        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        image.setAnimation(rotate);

    }
}
