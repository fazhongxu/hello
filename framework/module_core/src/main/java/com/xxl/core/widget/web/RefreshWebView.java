package com.xxl.core.widget.web;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xxl.core.R;
import com.xxl.kit.ColorUtils;
import com.xxl.kit.LogUtils;

/**
 * 自定义带刷新的WebView
 *
 * @author xxl.
 * @date 2022/6/25.
 */
public class RefreshWebView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener,
        ScrollWebView.OnScrollChangeListener {

    //region: 成员变量

    /**
     * TAG
     */
    private static final String TAG = RefreshWebView.class.getSimpleName() + " ";

    protected SwipeRefreshLayout mRefreshLayout;

    protected ScrollWebView mWebView;

    protected ProgressBar mProgressBar;

    protected WebChromeClient mWebChromeClient;

    protected WebViewClient mWebViewClient;

    private OnWebCallBack mOnWebCallBack;

    /**
     * 是否可以刷新
     */
    protected boolean mRefreshEnable;

    /**
     * 是否展示进度条
     */
    protected boolean mShowProgress;

    /**
     * 刷新颜色值
     */
    private int mRefreshColorRes;

    /**
     * 当前加载进度
     */
    private int mCurrentProgress = 0;

    /**
     * 进度动画是否开启
     */
    private boolean mIsAnimStart = false;

    //endregion

    //region: 构造函数

    public RefreshWebView(Context context) {
        this(context, null);
    }

    public RefreshWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.core_layout_refresh_web, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RefreshWebView);
        mRefreshColorRes = typedArray.getColor(R.styleable.RefreshWebView_refresh_color, ColorUtils.getColor(R.color.framework_core_primary_color));
        mRefreshEnable = typedArray.getBoolean(R.styleable.RefreshWebView_refresh_enable, true);
        mShowProgress = typedArray.getBoolean(R.styleable.RefreshWebView_show_progress, true);
        typedArray.recycle();
        mRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mProgressBar = findViewById(R.id.progress_bar);
        mWebView = findViewById(R.id.web_view);
        initWeb(mWebView);
        initRefreshLayout(mRefreshLayout);
    }

    /**
     * 初始化Web
     *
     * @param webView
     */
    protected void initWeb(ScrollWebView webView) {
        initWebSetting(webView);
        webView.setFocusableInTouchMode(true);
        webView.requestFocus();
        webView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        webView.setOnScrollChangeListener(this);
    }

    /**
     * 初始化web设置
     *
     * @param webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    protected void initWebSetting(ScrollWebView webView) {
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView web, String title) {
                super.onReceivedTitle(web, title);
                if (mOnWebCallBack != null) {
                    mOnWebCallBack.onReceivedTitle(web, title);
                }
                LogUtils.d(TAG + "onReceivedTitle");
            }

            @Override
            public void onProgressChanged(WebView web, int newProgress) {
                super.onProgressChanged(web, newProgress);
                mCurrentProgress = mProgressBar.getProgress();
                LogUtils.d(TAG + "onProgressChanged" + newProgress + "--" + mCurrentProgress);
                if (newProgress == 100) {
                    if (mIsAnimStart) {
                        mIsAnimStart = false;
                        mProgressBar.setProgress(newProgress);
                        setProgressSmooth(mProgressBar.getProgress(), new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                mProgressBar.setVisibility(View.GONE);
                                mProgressBar.setProgress(0);
                            }
                        });
                    }
                }else {
                    if (mShowProgress) {
                        if (mProgressBar.getVisibility() != View.VISIBLE) {
                            mProgressBar.setVisibility(View.VISIBLE);
                        }
                        if (!mIsAnimStart) {
                            mIsAnimStart = true;
                            setProgressSmooth(newProgress,null);
                        }
                    }
                }
                if (mOnWebCallBack != null) {
                    mOnWebCallBack.onProgressChanged(web, newProgress);
                }
            }
        };
        webView.setWebChromeClient(mWebChromeClient);
        mWebViewClient = new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView web, WebResourceRequest request) {
                LogUtils.d(TAG + "shouldOverrideUrlLoading");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request.getUrl() != null) {
                        if (isHttp(request.getUrl().toString())) {
                            web.loadUrl(request.getUrl().toString());
                        }
                    }
                } else {
                    if (isHttp(request.toString())) {
                        web.loadUrl(request.toString());
                    }
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtils.d(TAG + "onPageStarted");
                mIsAnimStart = false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mRefreshLayout.setRefreshing(false);
                LogUtils.d(TAG + "onPageFinished");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mRefreshLayout.setRefreshing(false);
                LogUtils.e(TAG + "onReceivedError");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                mRefreshLayout.setRefreshing(false);
                LogUtils.e(TAG + "onReceivedSslError");
                handler.proceed();
            }
        };
        webView.setWebViewClient(mWebViewClient);
    }

    /**
     * 初始化刷新视图
     *
     * @param refreshLayout
     */
    protected void initRefreshLayout(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(mRefreshColorRes);
    }

    //endregion

    //region: SwipeRefreshLayout.OnRefreshDataListener

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        mWebView.reload();
    }

    //endregion

    //region: OnScrollChangeListener

    @Override
    public void onWebScrollChanged(int l, int t, int oldl, int oldt) {
        Log.e(TAG, "onWebScrollChanged: " + t + "--" + mWebView.getScrollY());
        if (mWebView.getScrollY() == 0) {
            mRefreshLayout.setEnabled(true);
        } else {
            mRefreshLayout.setEnabled(false);
        }
    }

    //endregion

    //region: 提供方法

    /**
     * 设置进度条平滑移动
     */
    private void setProgressSmooth(int progress, AnimatorListenerAdapter animatorListenerAdapter) {
        ObjectAnimator animator = ObjectAnimator.ofInt(mProgressBar, "progress", mCurrentProgress, progress);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (animatorListenerAdapter != null) {
                    animatorListenerAdapter.onAnimationEnd(animation);
                }
            }
        });
        animator.start();
    }

    public ScrollWebView getWebView() {
        return mWebView;
    }

    public WebChromeClient getWebChromeClient() {
        return mWebChromeClient;
    }

    public WebViewClient getWebViewClient() {
        return mWebViewClient;
    }

    /**
     * 判断是否可以刷新
     *
     * @return
     */
    public boolean isRefreshEnable() {
        return mRefreshEnable;
    }

    /**
     * 设置是否可以刷新
     *
     * @param isRefreshEnable
     * @return
     */
    public RefreshWebView setRefreshEnable(boolean isRefreshEnable) {
        this.mRefreshEnable = isRefreshEnable;
        return this;
    }

    /**
     * 设置是否显示进度条
     *
     * @param isShowProgress
     * @return
     */
    public RefreshWebView setShowProgress(boolean isShowProgress) {
        this.mShowProgress = isShowProgress;
        return this;
    }

    /**
     * 设置web回调
     *
     * @param callBack
     * @return
     */
    public RefreshWebView setOnWebCallBack(OnWebCallBack callBack) {
        this.mOnWebCallBack = callBack;
        return this;
    }

    /**
     * 设置是否在刷新中
     *
     * @param refreshing
     */
    public void setRefresh(final boolean refreshing) {
        mRefreshLayout.setRefreshing(refreshing);
    }

    /**
     * 加载链接
     *
     * @param url
     */
    public void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        mWebView.loadUrl(url);
    }

    /**
     * 释放WebView
     */
    public void release() {
        release(true);
    }

    /**
     * 释放WebView
     *
     * @param includeDiskFiles
     */
    public void release(boolean includeDiskFiles) {
        mWebView.clearCache(includeDiskFiles);
        mWebView.clearHistory();
    }

    //endregion

    //region: 内部辅助方法

    /**
     * 判断字符串是否是网络地址
     *
     * @param s
     * @return
     */
    private boolean isHttp(@Nullable final String s) {
        if (s == null || s.length() <= 0) {
            return false;
        }
        return s.toLowerCase().startsWith("http");
    }

    //endregion

    //region: Inner Class OnWebCallBack

    public interface OnWebCallBack {

        /**
         * 标题回调
         *
         * @param view
         * @param title
         */
        default void onReceivedTitle(WebView view,
                                     String title) {

        }

        /**
         * 加载进度回调
         *
         * @param view
         * @param newProgress
         */
        default void onProgressChanged(WebView view,
                                       int newProgress) {

        }

    }

    //endregion
}