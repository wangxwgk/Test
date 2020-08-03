package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import android.os.Bundle;



public class Main2Activity extends AppCompatActivity {
    private WebView mWebView;
    private FrameLayout mVideoContainer;
    private WebChromeClient.CustomViewCallback mCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 全透明实现
            //getWindow.setStatusBarColor(Color.TRANSPARENT)
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |                 View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main2);

        Intent intent =getIntent();
        Bundle bundle =intent.getExtras();
        String str2 = bundle.getString("name");
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setKeepScreenOn(true);
        mWebView.clearCache(true);
        mVideoContainer = (FrameLayout) findViewById(R.id.videoContainer);

        initWebView();

        mWebView.loadUrl(str2);
        mWebView.addJavascriptInterface(new JsObject(), "onClick");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            /**
             * 你可以在出话之前加载一些资源
             */
            @Override
            public void onLoadResource(WebView view, String url) {

            }
        });

    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new CustomWebViewChromeClient());
        mWebView.setWebViewClient(new CustomWebClient());

        mWebView.addJavascriptInterface(new JsObject(), "onClick");
    }

    private class JsObject {

        @JavascriptInterface
        public void fullscreen() {
            //监听到用户点击全屏按钮
            fullScreen();
        }
    }

    private class CustomWebViewChromeClient extends WebChromeClient {

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            fullScreen();
            mWebView.setVisibility(View.GONE);
            mVideoContainer.setVisibility(View.VISIBLE);
            mVideoContainer.addView(view);
            mCallBack = callback;
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            fullScreen();
            if (mCallBack != null) {
                mCallBack.onCustomViewHidden();
            }
            mWebView.setVisibility(View.VISIBLE);
            mVideoContainer.removeAllViews();
            mVideoContainer.setVisibility(View.GONE);
            super.onHideCustomView();
        }
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private class CustomWebClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String js = TagUtils.getJs(url);
            view.loadUrl(js);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}