package com.ken.customview;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LottieActivity extends AppCompatActivity {

    private AudioManager mAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        AppCompatImageView imageView = new AppCompatImageView(this);






        LottieAnimationView lottie = findViewById(R.id.lottie);
        lottie.setScale(0.5f);
        lottie.setImageAssetsFolder("ccs/");
        lottie.setAnimation("b.json");
        lottie.loop(true);
        lottie.playAnimation();





//        TextView textView = new TextView(this);
//        final LottieAnimationView voice_anim = findViewById(R.id.voice_anim);
//
//        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        if (mAudioManager != null) {
//            int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//            Log.e("zz", "onCreate:音量  == "   + streamVolume + "   " + mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) );
//        }
//        KeyPath keyPath = new KeyPath("**");
//        SimpleColorFilter filter = new SimpleColorFilter(Color.BLACK);
//        LottieValueCallback<ColorFilter> callback = new LottieValueCallback<ColorFilter>(filter);
//        voice_anim.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback);
//
//        voice_anim.setAnimation("tt.json");
//
//        voice_anim.playAnimation();
//        voice_anim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                voice_anim.playAnimation();
//            }
//        });
////        String jsonString = getJson("ccc.json", this);
////        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN);
////        voice_anim.addColorFilter(colorFilter);
//
//        registerVolumeReceiver();
//        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        float den = dm.density;
//        Log.e("zz", "onCreate: ss == " +den );

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

    public  String getJson(String fileName, Context context){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line=bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
    /**
     * 注册广播监听音量变化广播
     */
    private void registerVolumeReceiver() {
        VolumeReceiver receiver = new VolumeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(receiver, filter);
    }
    private class VolumeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction())) {
                int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                Log.e("zz", "onCreate:音量  == "   + currentVolume + "   " + mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) );
            }
        }

    }

}
