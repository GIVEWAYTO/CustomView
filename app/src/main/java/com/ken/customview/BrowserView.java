package com.ken.customview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * Created by zddz on 2018/12/14.
 */

public class BrowserView extends RelativeLayout {

    private View mRootView;
    private TextView tv_back;
    private TextView tv_refresh;
    private WebView wb_main;
    private boolean mNeedClearHistory = false;


    private View mCustomView = null;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;

    //
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_back:
                    if (mCustomView != null) {
                        hideCustomView();
                    }
                    if (wb_main != null && wb_main.canGoBack()) {
                        wb_main.goBack();
                    } else if (mListener != null) {

                        mListener.onClose();
                    }
                    break;
                case R.id.tv_refresh:
                    if (wb_main != null) {
                        wb_main.reload();
                    }
                    break;
            }
        }
    };

    public BrowserView(Context context) {
        this(context, null);
    }

    public BrowserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //View.inflate(getContext(), R.layout.view_browser, this);
        mRootView = LayoutInflater.from(context).inflate(R.layout.view_browser, this, true);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(mOnClickListener);
        tv_refresh = (TextView) findViewById(R.id.tv_refresh);
        tv_refresh.setOnClickListener(mOnClickListener);
        wb_main = (WebView) findViewById(R.id.wb_main);
        intiWebView();
        wb_main.loadUrl("https://www.baidu.com/");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void intiWebView() {
        WebSettings mWebSettings = wb_main.getSettings();
        //
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setSupportZoom(true);
        //
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setJavaScriptEnabled(true);
        //
        mWebSettings.setSavePassword(true);
        mWebSettings.setSaveFormData(true);
        mWebSettings.setDomStorageEnabled(true);//播放视频必需
        mWebSettings.setSupportMultipleWindows(true);
        //
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//播放视频必需
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        wb_main.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("intent") || url.startsWith("youku")) {
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }

            @Override
            public void doUpdateVisitedHistory(WebView webView, String s, boolean b) {
                if (mNeedClearHistory) {
                    webView.clearHistory();
                    mNeedClearHistory = false;
                }
            }
        });

        wb_main.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                if (view == null) {
                    return;
                }
                // if a view already exists then immediately terminate the new one
                if (mCustomView != null && callback != null) {
                    callback.onCustomViewHidden();
                    return;
                }
                mCustomView = view;
                wb_main.setVisibility(View.GONE);
                mCustomViewCallback = callback;
                if (mListener != null) {
                    mListener.onShowCustomView();
                }
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
                hideCustomView();
            }
        });


    }


    private void hideCustomView() {
        if (mCustomView == null || mCustomViewCallback == null) {
            return;
        }
        wb_main.setVisibility(View.VISIBLE);

        mCustomView.setVisibility(View.GONE);
        // Remove the custom view from its container.
        mCustomViewCallback.onCustomViewHidden();
        mCustomView = null;
        if (mListener != null) {
            mListener.onHideCustomView();
        }
    }

    public void destroyWebView() {
        ViewParent parent = getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(this);
        }
        wb_main.stopLoading();
        // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
        wb_main.getSettings().setJavaScriptEnabled(false);
        wb_main.clearHistory();
        wb_main.clearView();
        removeAllViews();
        try {
            wb_main.destroy();
            wb_main = null;
        } catch (Throwable ex) {
        }
    }

    public void toBlank() {
        wb_main.loadUrl("about:blank");
        mNeedClearHistory = true;
        wb_main.stopLoading();
        wb_main.clearHistory();
        removeAllViews();
    }

    public void loadUrl(String url) {
        toBlank();
        wb_main.loadUrl(url);
    }

    private OnBrowserViewListener mListener;

    public void setOnBrowserViewListener(OnBrowserViewListener listener) {
        mListener = listener;
    }

    public interface OnBrowserViewListener {
        void onClose();

        void onShowCustomView();

        void onHideCustomView();

    }


}
