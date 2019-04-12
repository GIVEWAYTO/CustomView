package com.ken.customview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;

public class WeboActivity extends AppCompatActivity {
    private int totalDy = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_webo);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        final int height = (int) (outMetrics.heightPixels - Utils.dpToPixel(50)); // 全屏减去底部固定view的高度


        // 如果换成视频控件 记得要把 视频控件的上下滑动给禁掉  避免rv的滑动事件被抢掉
        final ImageView profile = findViewById(R.id.profile);
        ViewGroup.LayoutParams layoutParams = profile.getLayoutParams();
        layoutParams.height = height ;
        profile.setLayoutParams(layoutParams);

        // 视频的固定高度
        final float video_height = Utils.dpToPixel(200);

        final RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new WeboAdapter(height));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy -= dy;
                ViewGroup.LayoutParams layoutParams = profile.getLayoutParams();

                if(dy  > 0 && layoutParams.height > video_height){
                    layoutParams.height = layoutParams.height - dy;
                    if(layoutParams.height < video_height){
                        layoutParams.height = (int) video_height;
                    }
                    profile.setLayoutParams(layoutParams);
                }else if(dy < 0 && layoutParams.height < height ){
                    // 还未完全收缩
                    if(layoutParams.height >  video_height){

                        layoutParams.height = layoutParams.height - dy;
                        if(layoutParams.height >= height){
                            layoutParams.height = height;
                            rv.smoothScrollToPosition(0);
                        }

                        // 已经拖动到最原始状态  保证一定闭合
                        if(totalDy == 0){
                            layoutParams.height = height;
                        }
                        profile.setLayoutParams(layoutParams);


                        // 已经完全收缩  要先把rv的第1个布局露出(前面还有第0个不局)，才让video往下滑动
                    }else if(Math.abs(totalDy) - video_height <= video_height){
                        layoutParams.height = layoutParams.height - dy;
                        profile.setLayoutParams(layoutParams);
                    }

                }
            }
        });
    }

    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
}
