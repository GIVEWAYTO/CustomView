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
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class WeboActivity extends AppCompatActivity {
    private int totalDy = 0;
    private float bottomHeight;  // 固定的底部 高度
    private float stickyHeight = Utils.dpToPixel(80);  // 固定的底部 高度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_webo);

        // 获取视频最大长度 = 屏幕长度 - 底部固定view的宽度
        bottomHeight = Utils.dpToPixel(50);
        final int height = getVideoMaxHeight(bottomHeight);
        // 初始设置video的高度为最大长度
        final ImageView profile = findViewById(R.id.profile);
        final ImageView message = findViewById(R.id.message);
        ViewGroup.LayoutParams layoutParams = profile.getLayoutParams();
        ViewGroup.LayoutParams messagelayoutParams = message.getLayoutParams();
        layoutParams.height = height;
        messagelayoutParams.height = (int) stickyHeight;
        profile.setLayoutParams(layoutParams);
        message.setLayoutParams(messagelayoutParams);

        // 视频的固定高度
        final float video_height = Utils.dpToPixel(200);

        final RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new WeboAdapter(height,stickyHeight));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy -= dy;
                ViewGroup.LayoutParams layoutParams = profile.getLayoutParams();

                if (dy > 0 && layoutParams.height > video_height) {
                    layoutParams.height = layoutParams.height - dy;
                    if (layoutParams.height < video_height) {
                        layoutParams.height = (int) video_height;
                    }

                    // 如果刚好 现在video的高度是固定高度  则把后面跟随固定view展示出来  做成吸顶效果
                    if (layoutParams.height == video_height) {
                        if (message.getVisibility() == View.GONE) {
                            message.setVisibility(View.VISIBLE);
                        }
                    }
                    profile.setLayoutParams(layoutParams);
                } else if (dy < 0 && layoutParams.height < height) {
                    // 还未完全收缩
                    if (layoutParams.height > video_height) {

                        layoutParams.height = layoutParams.height - dy;
                        if (layoutParams.height >= height) {
                            layoutParams.height = height;
                            rv.smoothScrollToPosition(0);
                        }

                        profile.setLayoutParams(layoutParams);


                        // 已经完全收缩  要先把rv的第1个布局露出(前面还有第0个不局)，才让video往下滑动
                    } else if (Math.abs(totalDy) - video_height <= video_height) {

                        // 第一个露出来了  要把吸顶的布局隐藏
                        if (message.getVisibility() == View.VISIBLE) {
                            message.setVisibility(View.GONE);
                        }

                        layoutParams.height = layoutParams.height - dy;

                        profile.setLayoutParams(layoutParams);
                    }

                }
            }
        });
    }

    /**
     * @return 获取视频最大长度 = 屏幕长度 - 底部固定view的宽度
     * @param bottomHeight
     */
    private int getVideoMaxHeight(float bottomHeight) {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);

        return (int) (outMetrics.heightPixels - bottomHeight);
    }
}
