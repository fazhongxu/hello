package com.xxl.hello.widget.ui.web

import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.just.agentweb.WebViewClient
import com.xxl.core.data.router.SystemRouterApi
import com.xxl.core.widget.web.AgentScrollWebView
import com.xxl.core.widget.web.AgentScrollWebView.OnScrollChangeListener
import com.xxl.hello.widget.BR
import com.xxl.hello.widget.R
import com.xxl.hello.widget.databinding.WidgetFragmentCommonWebBinding
import com.xxl.hello.widget.ui.web.base.BaseWebFragment
import com.xxl.kit.ColorUtils

/**
 *
 * @author xxl.
 * @date 2025/7/7.
 */
class CommonWebFragment :
    BaseWebFragment<CommonWebViewModel, WidgetFragmentCommonWebBinding>(),
    CommonWebNavigator, OnRefreshListener, OnScrollChangeListener {

    //region: 成员变量

    private var mCommonWebBinding: WidgetFragmentCommonWebBinding? = null

    private var mCommonWebViewModel: CommonWebViewModel? = null

    private var webView: AgentScrollWebView? = null

    /**
     * url
     */
    @JvmField
    @Autowired(name = SystemRouterApi.WebView.PARAMS_KEY_URL)
    var mUrl: String? = null

    /**
     * 是否可以分享
     */
    @JvmField
    @Autowired(name = SystemRouterApi.WebView.PARAMS_KEY_SHARE_ENABLE)
    var mShareEnable: Boolean = true

    /**
     * 是否可以刷新
     */
    @JvmField
    @Autowired(name = SystemRouterApi.WebView.PARAMS_KEY_REFRESH_ENABLE)
    var mRefreshEnable: Boolean = true

    //endregion

    //region: 构造函数

    companion object {
        fun newInstance(args: Bundle): CommonWebFragment {
            val fragment = CommonWebFragment()
            fragment.arguments = args
            return fragment
        }
    }

    //endregion

    //region: 页面生命周期

    override fun getLayoutRes(): Int {
        return R.layout.widget_fragment_common_web
    }

    override fun createViewModel(): CommonWebViewModel {
        mCommonWebViewModel = createViewModel(CommonWebViewModel::class.java)
        mCommonWebViewModel!!.navigator = this
        return mCommonWebViewModel!!
    }

    override fun enableRouterInject(): Boolean {
        return true
    }

    override fun getViewModelVariable(): Int {
        return BR.viewModel
    }


    override fun getViewNavigatorVariable(): Int {
        return BR.navigator
    }

    override fun setupData() {
        mCommonWebBinding = viewDataBinding
    }

    override fun setupLayout(rootView: View) {
        super.setupLayout(rootView)
        setupLayout()
    }

    /**
     * 获取url
     *
     * @return
     */
    override fun getUrl(): String {
        return mUrl!!
    }

    override fun getAgentWebParent(): ViewGroup {
        return mCommonWebBinding!!.llContentContainer
    }

    override fun getCustomWebView(): WebView {
        webView = AgentScrollWebView(context)
        webView!!.setOnScrollChangeListener(this)
        return webView!!
    }

    override fun getWebViewClient(): WebViewClient {
        return mWebViewClient
    }

    private var mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            mCommonWebBinding!!.swipeRefreshLayout.isRefreshing = false
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            mCommonWebBinding!!.swipeRefreshLayout.isRefreshing= false
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            super.onReceivedSslError(view, handler, error)
            mCommonWebBinding!!.swipeRefreshLayout.isRefreshing= false
        }
    }

    //endregion

    //region: 页面视图渲染

    private fun setupLayout() {
        val swipeRefreshLayout = mCommonWebBinding!!.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeColors(ColorUtils.getColor(R.color.resources_primary_color))
        swipeRefreshLayout.isEnabled = mRefreshEnable
    }

    //endregion

    //region: CommonWebNavigator

    //endregion

    //region: OnRefreshListener

    override fun onRefresh() {
        webView?.reload()
    }

    //endregion

    //region: OnScrollChangeListener

    override fun onWebScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        if (mRefreshEnable && webView != null) {
            mCommonWebBinding!!.swipeRefreshLayout.isEnabled = webView!!.scrollY <= 1
        }
    }

    //endregion

    //region: Activity 操作

    fun onBackPressed(): Boolean {
        if (webView != null && webView!!.canGoBack()) {
            webView!!.goBack()
            return true
        }
        return false
    }

    //endregion


}