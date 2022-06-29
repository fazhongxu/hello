package com.xxl.core.widget.web

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.animation.AccelerateDecelerateInterpolator
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.xxl.core.R
import com.xxl.kit.ColorUtils
import com.xxl.kit.LogUtils
import java.util.*

/**
 * 带刷新的WebView
 *
 * @author xxl.
 * @date 2022/6/26.
 */
open class RefreshWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr), SwipeRefreshLayout.OnRefreshListener, ScrollWebView.OnScrollChangeListener {

    //region: 成员变量

    companion object {

        /**
         * tag
         */
        private val TAG: String = javaClass.simpleName + " "
    }

    var progressBar: ProgressBar? = null

    /**
     * 刷新视图
     */
    var refreshLayout: SwipeRefreshLayout? = null

    var webView: ScrollWebView? = null

    var webChromeClient: WebChromeClient? = null

    var webViewClient: WebViewClient? = null

    /**
     * 监听回调
     */
    var onWebCallBack: OnWebCallBack? = null

    /**
     * 是否允许刷新
     */
    var refreshEnable = true

    /**
     * 刷新的颜色
     */
    private var refreshColor = 0

    /**
     * 是否显示进度条
     */
    var showProgress = true

    /**
     * 当前加载进度
     */
    private var currentProgress = 0

    /**
     * 进度动画是否开启
     */
    private var isAnimStart = false

    //endregion

    //region: 构造函数

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        View.inflate(context, R.layout.core_layout_refresh_web, this)
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.RefreshWebView)
        refreshEnable = typeArray.getBoolean(R.styleable.RefreshWebView_refresh_enable, true)
        refreshColor = typeArray.getColor(R.styleable.RefreshWebView_refresh_color, ColorUtils.getColor(R.color.colorPrimary))
        showProgress = typeArray.getBoolean(R.styleable.RefreshWebView_show_progress, true)
        progressBar = findViewById(R.id.progress_bar)
        refreshLayout = findViewById(R.id.swipe_refresh_layout)
        webView = findViewById(R.id.web_view)
        typeArray.recycle()
        initLayout()
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 初始化视图
     */
    private fun initLayout() {
        initWeb(webView)
        initRefreshLayout(refreshLayout)
        initProgress(progressBar)
    }

    /**
     * 初始化web
     */
    open fun initWeb(webView: ScrollWebView?) {
        initWebSetting(webView)
        webView?.isFocusableInTouchMode = true
        webView?.requestFocus()
        webView?.setOnKeyListener(OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack()
                    return@OnKeyListener true
                }
            }
            false
        })
        webView?.setOnScrollChangeListener(this)
    }

    /**
     * 初始化web设置
     * @param webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    open fun initWebSetting(webView: ScrollWebView?) {
        webView?.settings?.let {
            it.javaScriptEnabled = true
            it.domStorageEnabled = true
            it.displayZoomControls = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
        webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                LogUtils.d(TAG + "onReceivedTitle")
                onWebCallBack?.onReceivedTitle(view, title)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                currentProgress = progressBar!!.progress
                LogUtils.d(TAG + "onProgressChanged" + newProgress + "--" + currentProgress)
                if (newProgress == 100) {
                    if (isAnimStart) {
                        isAnimStart = false
                        progressBar?.setProgress(newProgress)
                        setProgressSmooth(progressBar!!.progress, object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                progressBar?.visibility = View.GONE
                                progressBar?.progress = 0
                            }
                        })
                    }
                } else {
                    if (showProgress) {
                        if (progressBar?.visibility != View.VISIBLE) {
                            progressBar?.visibility = View.VISIBLE
                        }
                        if (!isAnimStart) {
                            isAnimStart = true
                            setProgressSmooth(newProgress)
                        }
                    }
                }
                onWebCallBack?.onProgressChanged(view, newProgress)
            }
        }

        webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                LogUtils.d(TAG + "shouldOverrideUrlLoading")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (isHttp(request?.url.toString())) {
                        view?.loadUrl(request?.url.toString())
                    }
                } else if (isHttp(request.toString())) {
                    view?.loadUrl(request.toString())
                }
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                isAnimStart = false
                LogUtils.d(TAG + "onPageFinished")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                LogUtils.d(TAG + "onPageFinished")
                refreshLayout?.isRefreshing = false
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                LogUtils.e(TAG + "onReceivedError")
                refreshLayout?.isRefreshing = false
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                super.onReceivedSslError(view, handler, error)
                LogUtils.e(TAG + "onReceivedSslError")
                refreshLayout?.isRefreshing = false
                handler?.proceed()
            }
        }

        webView?.webChromeClient = webChromeClient
        webView?.webViewClient = webViewClient

    }

    /**
     * 初始化刷新视图
     * @param refreshLayout
     */
    open fun initRefreshLayout(refreshLayout: SwipeRefreshLayout?) {
        refreshLayout?.isEnabled = refreshEnable
        refreshLayout?.setColorSchemeColors(refreshColor)
        refreshLayout?.setOnRefreshListener(this)
    }

    /**
     * 初始化进度条
     * @param progressBar
     */
    open fun initProgress(progressBar: ProgressBar?) {
        progressBar?.visibility = if (showProgress) View.VISIBLE else View.GONE
    }

    /**
     * 设置进度条平滑移动
     */
    private fun setProgressSmooth(progress: Int, animatorListenerAdapter: AnimatorListenerAdapter? = null) {
        val animator = ObjectAnimator.ofInt(progressBar, "progress", currentProgress, progress)
        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener {
            onAnimationEnd()
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                animatorListenerAdapter?.onAnimationEnd(animation)
            }
        })
        animator.start()
    }

    //endregion

    //region: 提供方法

    /**
     * 设置是否展示进度条
     */
    fun setRefreshEnable(enable: Boolean): RefreshWebView {
        refreshEnable = enable
        return this
    }

    /**
     * 设置是否展示进度条
     */
    fun setShowProgress(show: Boolean): RefreshWebView {
        showProgress = show
        return this
    }

    /**
     * 设置监听
     */
    fun setWebCallBack(callBack: OnWebCallBack): RefreshWebView {
        onWebCallBack = callBack
        return this
    }

    /**
     * 加载链接
     * @param url
     */
    fun loadUrl(url: String) {
        webView?.loadUrl(url)
    }

    /**
     * 释放webview
     */
    fun release() {
        release(true)
    }

    /**
     * 释放webview
     * @param includeDiskFiles
     */
    fun release(includeDiskFiles: Boolean) {
        webView?.clearCache(includeDiskFiles)
        webView?.destroy()
    }

    //endregion

    //region: OnRefreshListener

    override fun onRefresh() {
        refreshLayout?.isRefreshing = true
        webView?.reload()
    }

    //endregion

    //region: OnScrollChangeListener

    override fun onWebScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        LogUtils.d("onWebScrollChanged" + t + ".." + webView?.scrollY)
        refreshLayout?.isEnabled = webView?.scrollY == 0
    }

    //endregion

    //region: Inner Class OnWebCallBack

    interface OnWebCallBack {

        /**
         * 接收标题
         *
         * @param webView
         * @param title
         */
        fun onReceivedTitle(webView: WebView?, title: String?)

        /**
         * 加载进度
         * @param view
         * @param newProgress
         */
        fun onProgressChanged(view: WebView?, newProgress: Int) {

        }
    }

    //endregion

    //region: 内部辅助方法

    /**
     * 判断是否是http
     */
    private fun isHttp(url: String?): Boolean {
        return url?.toLowerCase(Locale.getDefault())?.startsWith("https") ?: false
    }

    //endregion

}