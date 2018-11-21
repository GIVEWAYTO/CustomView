package com.ken.customview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean isOpen = false;
    int progress = 0;
    boolean right = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PlaneView planeView = findViewById(R.id.plane);
        planeView.startAnim();
        final TextView textView = new TextView(this);
        final TextView text = findViewById(R.id.text);
        String text1 = "用这个构造函数是创建一个 PathMeasure 并关联一个 Path， 其实和创建一个空的 PathMeasure 后调用 setPath 进行关联效果是一样的，同样，被关联的 Path 也必须是已经创建好的，如果关联之后 Path 内容进行了更改，则需要使用 setPath 方法重新关联。\n" +
                "该方法有两个参数，第一个参数自然就是被关联的 Path 了，第二个参数是用来确保 Path 闭合，如果设置为 true， 则不论之前Path是否闭合，都会自动闭合该 Path(如果Path可以闭合的话)。";
        textView.setText(text1);
        text.setText(text1);
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("zz", "run: line == " + textView.getLineCount() );
                Log.e("zz", "run: cccc == " + text.getLineCount() );

            }
        },50);
        Log.e("zz", "onCreate: " );
//       final CheckView checkView =   findViewById(R.id.check);
//
     //   final LoadingView loadingView = findViewById(R.id.loading);
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(progress == 100){
//                    right = false;
//                }else if(progress == 0){
//                    right = true;
//                }
//                if(right){
//                    progress += 20;
//                }else{
//                    progress -= 20;
//                }
//                loadingView.setProgressAnim(progress);
//                isOpen = !isOpen;
//            }
//        });
    }

    public void jump2Food(View c){
        Intent intent = new Intent(this,FoodActivity.class);
        startActivity(intent);
    }
    public void jump2Path(View c){
        Intent intent = new Intent(this,PathActivity.class);
        startActivity(intent);
    }
    public void airbnb(View c){
        Intent intent = new Intent(this,LottieActivity.class);
        startActivity(intent);
    }
    public void success(View v){
         start(SuccessActivity.class);
    }

    public  void start(Class clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("zz", "onStart: " );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zz", "onResume: " );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("zz", "onRestart: " );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("zz", "onPause: " );
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("zz", "onAttachedToWindow: Main" );
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("zz", "onDetachedFromWindow: Main" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("zz", "onDestroy: " );
    }
}
