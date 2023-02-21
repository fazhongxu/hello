package com.xxl.hello.main.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xxl.core.aop.annotation.Safe;
import com.xxl.core.data.router.SystemRouterApi;
import com.xxl.core.media.audio.AudioCapture;
import com.xxl.core.media.audio.AudioCapture.OnAudioFrameCapturedListener;
import com.xxl.core.media.audio.AudioRecordFormat;
import com.xxl.core.ui.BaseEventBusWrapper;
import com.xxl.core.ui.fragment.BaseStateViewModelFragment;
import com.xxl.core.ui.state.EmptyState;
import com.xxl.core.utils.AppExpandUtils;
import com.xxl.core.utils.DecorationUtils;
import com.xxl.core.widget.recyclerview.OnRefreshDataListener;
import com.xxl.core.widget.text.LinkTouchMovementMethod;
import com.xxl.hello.common.config.CacheDirConfig;
import com.xxl.hello.main.BR;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainFragmentBinding;
import com.xxl.hello.main.ui.main.adapter.TestBindingAdapter;
import com.xxl.hello.main.ui.main.adapter.TestBindingRecycleItemListener;
import com.xxl.hello.main.ui.main.adapter.TestListEntity;
import com.xxl.hello.main.ui.main.window.PrivacyPolicyPopupWindow;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.widget.ui.view.record.OnRecordListener;
import com.xxl.hello.widget.ui.view.record.RecordButton;
import com.xxl.hello.widget.ui.web.base.BaseWebFragment;
import com.xxl.kit.AppRouterApi;
import com.xxl.kit.AppUtils;
import com.xxl.kit.FFmpegUtils;
import com.xxl.kit.ListUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.MediaUtils;
import com.xxl.kit.OnAppStatusChangedListener;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.ResourceUtils;
import com.xxl.kit.StringUtils;
import com.xxl.kit.TimeUtils;
import com.xxl.kit.ToastUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author xxl.
 * @date 2022/4/8.
 */
public class MainFragment extends BaseStateViewModelFragment<MainViewModel, MainFragmentBinding>
        implements MainNavigator, OnAppStatusChangedListener, OnAudioFrameCapturedListener,
        TestBindingRecycleItemListener, OnRefreshDataListener {

    //region: 成员变量

    /**
     * 首页数据模型
     */
    private MainViewModel mMainViewModel;

    @Inject
    TestBindingAdapter mTestBindingAdapter;

    /**
     * 首页EventBus通知事件监听
     */
    @Inject
    MainEventBusWrapper mMainEventBusWrapper;
    private WebView webView;

    //endregion

    //region: 构造函数

    public final static MainFragment newInstance(@NonNull final Bundle bundle) {
        final MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    //endregion

    //region: 页面生命周期

    /**
     * 获取视图资源ID
     *
     * @return
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.main_fragment;
    }

    /**
     * 创建ViewModel数据模型
     *
     * @return
     */
    @Override
    protected MainViewModel createViewModel() {
        mMainViewModel = createViewModel(MainViewModel.class);
        mMainViewModel.setNavigator(this);
        return mMainViewModel;
    }

    @Override
    protected BaseEventBusWrapper getEventBusWrapper() {
        return mMainEventBusWrapper;
    }

    /**
     * 获取data binding 内的 ViewModel
     *
     * @return
     */
    @Override
    public int getViewModelVariable() {
        return BR.viewModel;
    }

    /**
     * 获取data binding 内的 Navigator
     *
     * @return
     */
    @Override
    public int getViewNavigatorVariable() {
        return BR.navigator;
    }

    /**
     * 设置数据
     */
    @Override
    protected void setupData() {
        LogUtils.d("当前登录用户ID..." + AppExpandUtils.getCurrentUserId());
        if (!AppExpandUtils.isAgreePrivacyPolicy()) {
            showPrivacyPolicyPopupWindow();
        }
    }

    @Override
    public EmptyState.EmptyStateProperty getCustomEmptyStateProperty() {
        return EmptyState.obtain("There is no data", R.drawable.resources_ic_no_data);
    }

    @Override
    public void onEmptyViewClick() {
        showCoreState();
    }

    /**
     * 设置页面视图
     *
     * @param view
     */
    @Override
    protected void setupLayout(@NonNull final View view) {
        registerAppStatusChangedListener(this);
        mViewDataBinding.tvTest.setMovementMethod(LinkTouchMovementMethod.getInstance());
        mMainViewModel.setObservableUserId(String.valueOf(TimeUtils.currentServiceTimeMillis()));
        setupRecord();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mViewDataBinding.rvList.addItemDecoration(DecorationUtils.createHorizontalDividerItemDecoration(ResourceUtils.getAttrColor(AppUtils.getTopActivity(), R.attr.h_common_divider_color), 10, 0));
        mViewDataBinding.refreshLayout.setRefreshDataListener(this);
        mViewDataBinding.refreshLayout.bindRecyclerView(mViewDataBinding.rvList, mTestBindingAdapter);
        mViewDataBinding.refreshLayout.setPageSize(20);
        mTestBindingAdapter.setListener(this);
        mTestBindingAdapter.setDragItemEnable(true, R.id.tv_content, mViewDataBinding.rvList);
    }

    @Override
    protected void requestData() {
        showLoadingState();
        mMainViewModel.requestQueryUserInfo(getStateResponseListener());
        onTestClick();
    }

    //endregion

    //region: MainNavigator

    @Safe
    @Override
    public void onTestClick() {
//        SystemRouterApi.WebView.newBuilder("https://v.douyin.com/B5JbFqd/", false)
//                .navigation(getActivity());
//
//        SystemRouterApi.WebView.newBuilder("https://www.douyin.com/video/7198027871871438114", false)
//                .navigation(getActivity());

        String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36";


        webView = new WebView(getActivity());
        WebSettings settings = webView.getSettings();
        settings.setUserAgentString(UA);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new InJavaScriptLocalObj(),"local_obj");

        /* 设置缓存模式,我这里使用的默认,不做多讲解 */
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        /* 设置默认文字格式UTF8 */
        settings.setDefaultTextEncodingName("UTF-8");
        /* 设置支持Js,必须设置的,不然网页基本上不能看 */
        settings.setJavaScriptEnabled(true);
        /* 设置交互Js接口 */

        settings.setSupportMultipleWindows(true);
        /* 设置为使用webview推荐的窗口 */
        settings.setUseWideViewPort(true);
        /* 设置网页自适应屏幕大小 ---这个属性应该是跟上面一个属性一起用 */
        settings.setLoadWithOverviewMode(true);
        /* 大部分网页需要自己保存一些数据,这个时候就的设置下面这个属性 */

        //最重要的一个属性
        settings.setDomStorageEnabled(true);
        /* HTML5的地理位置服务,设置为true,启用地理定位 */
        settings.setGeolocationEnabled(true);
        /* 设置是否允许webview使用缩放的功能,我这里设为false,不允许 */
        settings.setBuiltInZoomControls(false);
        /* 设置允许https + http混用 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /* 设置显示水平滚动条,就是网页右边的滚动条.我这里设置的不显示 */
        webView.setHorizontalScrollBarEnabled(false);
        /* 设置滚动条的样式 */
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                showSource();
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl("https://www.douyin.com/video/7198027871871438114");
    }

    /**
     * 获取html网页源码
     */
    private void showSource() {
        webView.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
    }


    final class InJavaScriptLocalObj {

        @JavascriptInterface
        public void showSource(String html) {

            String s = decode(html);
            Document document = Jsoup.parse(s);
            Elements videoElements = document.select("video");

            String videoUrl = "https:";
            for (Element element : videoElements) {
                final List<Node> nodes = element.childNodes();
                if (nodes != null && nodes.size() > 0) {
                    videoUrl = videoUrl + nodes.get(0).attr("src");
                    break;
                }
            }
            Log.e("aaa", "showSource: " + videoUrl);
        }

    }

    @Override
    public void onPause() {
        webView.onPause();
        super.onPause();
    }

    public static String decode(String data) {
        try {
            data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            data = data.replaceAll("\\+", "%2B");
            data = URLDecoder.decode(data, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * 测试按钮长按点击
     */
    @Override
    public boolean onTestLongClick() {
        mViewDataBinding.refreshLayout.showLoadingState();
        mViewDataBinding.refreshLayout.setVisibility(View.VISIBLE);
        mViewDataBinding.ctlContentContainer.setVisibility(View.GONE);
        mViewDataBinding.refreshLayout.requestData();
        return true;
    }

    /**
     * 请求查询用户信息完成
     *
     * @param response
     */
    @Override
    public void onRequestQueryUserInfoComplete(@NonNull final QueryUserInfoResponse response) {
        showCoreState();
        if (response != null && !TextUtils.isEmpty(response.getUserId())) {
            mMainViewModel.setObservableUserInfo(response.getUserId().concat("\n").concat(response.getAvatarUrl()));
        }

        final LoginUserEntity loginUserEntity = mMainViewModel.requestGetCurrentLoginUserEntity();
        if (loginUserEntity != null) {
            mMainViewModel.setObservableUserId(loginUserEntity.getUserId());
        } else {
            mMainViewModel.setObservableUserId((response == null || TextUtils.isEmpty(response.getUserId())) ? String.valueOf(TimeUtils.currentServiceTimeMillis()) : response.getUserId());
        }
    }

    //endregion

    //region: OnRefreshDataListener

    /**
     * 请求数据
     *
     * @param page     页码
     * @param pageSize 每页记录条数
     */
    @Override
    public void onRequestData(int page,
                              int pageSize) {
        if (isActivityFinishing()) {
            return;
        }
        final OnRequestCallBack<List<TestListEntity>> callBack = list -> {
            mViewDataBinding.refreshLayout.dismissState();
            mViewDataBinding.refreshLayout.setLoadData(list);
        };
        getViewModel().requestListData(page, pageSize, callBack);
    }

    //endregion

    //region: OnAppStatusChangedListener

    @Override
    public void onForeground(Activity activity) {
        ToastUtils.success(R.string.resources_app_is_foreground_tips).show();
    }

    @Override
    public void onBackground(Activity activity) {
        ToastUtils.success(R.string.resources_app_is_background_tips).show();
    }

    //endregion

    //region: OnAudioFrameCapturedListener

    /**
     * 开始录音
     */
    @Override
    public void onStartRecord() {
        mViewDataBinding.tvTest.setText(getString(R.string.core_recording_text));
    }

    /**
     * 停止录音
     *
     * @param audioFile 音频文件
     */
    @Override
    public void onCompleteRecord(@NonNull final File audioFile) {
        MediaUtils.MediaEntity mediaInfo = MediaUtils.getMediaInfo(audioFile.getAbsolutePath());
        LogUtils.d("音频文件-->" + audioFile.getAbsolutePath() + "--" + mediaInfo.getDuration());
        mViewDataBinding.tvTest.setText(getString(R.string.core_start_record_audio_text));

        String audio = CacheDirConfig.SHARE_FILE_DIR + File.separator + "1.mp3";
        String audio1 = CacheDirConfig.SHARE_FILE_DIR + File.separator + TimeUtils.currentTimeMillis() + "--" + ".mp3";
        String audio2 = CacheDirConfig.SHARE_FILE_DIR + File.separator + TimeUtils.currentTimeMillis() + "录音背景音乐" + ".mp3";
        ToastUtils.success(getString(R.string.core_record_audio_finish_text) + audioFile.getAbsolutePath()).show();
        new Thread() {
            @Override
            public void run() {
                FFmpegUtils.adjustVolumeSub5db(audio, audio1);
                FFmpegUtils.addBackgroundMusic(audioFile.getAbsolutePath(), audio1, audio2);
            }
        }.start();
    }

    /**
     * 音频采集监听
     *
     * @param audioData
     */
    @Override
    public void onAudioFrameCaptured(final byte[] audioData) {
        LogUtils.d("音频数据" + "onAudioFrameCaptured: " + audioData);
    }

    //endregion

    //region: Fragment 操作

    /**
     * 弹出隐私政策弹窗
     */
    private void showPrivacyPolicyPopupWindow() {
        PrivacyPolicyPopupWindow.from(getActivity())
                .setOnDisagreeClickListener(v -> {
                    AppUtils.exitApp();
                })
                .setOnAgreeClickListener(v -> {
                    mMainViewModel.setAgreePrivacyPolicyStatus(true);
                    AppExpandUtils.initPluginsAfterAgreePrivacyPolicy();
                    requestData();
                })
                .showPopupWindow();
    }

    /**
     * 测试按钮点击
     */
    @Safe(callBack = "onErrorCallback")
    private void testCrash() {
        int a = 1 / 0;
        Log.e("aaa", "错了错了");
    }

    @Keep
    private void onErrorCallback(@Nullable final Throwable throwable) {
        ToastUtils.success("我是错误回调" + throwable.getMessage()).show();
        Log.e("aaav", "错了错了v");
    }

    private void setupRecord() {
        RecordButton recordButton = mViewDataBinding.recordBtn;
        mViewDataBinding.recordBtn.setOnClickListener(v -> {
            final RxPermissions rxPermissions = new RxPermissions(this);
            Disposable disposable = rxPermissions.request(Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(isSuccess -> {
                        if (isSuccess) {
                            if (recordButton.isRunning()) {
                                recordButton.stop();
                            } else {
                                recordButton.start();
                            }
                        } else {
                            ToastUtils.warning(R.string.core_permission_record_audio_failure_tips).show();
                        }
                    }, throwable -> {
                        ToastUtils.warning(R.string.core_permission_record_audio_failure_tips).show();
                    });
        });
        mViewDataBinding.recordBtn.setRecordListener(new OnRecordListener() {

            final SimpleDateFormat safeDateFormat = TimeUtils.getSafeDateFormat("mm:ss");

            @Override
            public void onButtonRecordStart() {
                AudioCapture.getInstance()
                        .setAudioRecordFormat(AudioRecordFormat.MP3)
                        .setOutFilePath(CacheDirConfig.SHARE_FILE_DIR)
                        .setOnAudioFrameCapturedListener(MainFragment.this)
                        .startCapture();
            }

            @Override
            public void onButtonRecording(final long currentTimeMills,
                                          final long totalTimeMills) {
                long timeSpan = TimeUtils.getTimeSpan(totalTimeMills, currentTimeMills, TimeUtils.TimeConstants.MSEC);
                Log.e("aa", "onRecord: " + TimeUtils.millis2String(timeSpan, safeDateFormat));
                mViewDataBinding.tvDuration.setText(TimeUtils.millis2String(timeSpan, safeDateFormat));
            }

            @Override
            public void onButtonRecordStop(boolean isCanceled) {
                mViewDataBinding.tvDuration.setText(TimeUtils.millis2String(mViewDataBinding.recordBtn.getMaxMilliSecond(), safeDateFormat));
                AudioCapture.getInstance().stopCapture();
            }

            @Override
            public void onButtonRecordFinish() {
                mViewDataBinding.tvDuration.setText(TimeUtils.millis2String(mViewDataBinding.recordBtn.getMaxMilliSecond(), safeDateFormat));
                AudioCapture.getInstance().stopCapture();
            }
        });
    }

    //endregion

    //region: EventBus 操作

    /**
     * 刷新用户信息
     *
     * @param targetUserEntity
     */
    public void refreshUserInfo(@Nullable final LoginUserEntity targetUserEntity) {
        if (targetUserEntity == null) {
            return;
        }
        mMainViewModel.setObservableUserId(targetUserEntity.getUserId());
    }

    /**
     * 处理页面结果
     *
     * @param requestCode
     * @param data
     */
    public void handleActivityResult(final int requestCode, @Nullable Intent data) {
        if (isActivityFinishing()) {
            return;
        }
        if (AppRouterApi.Login.isRequestCode(requestCode)) {
            ToastUtils.success(R.string.resources_login_success).show();
        }
    }

    //region: TestBindingRecycleItemListener

    /**
     * 条目点击
     *
     * @param value
     */
    @Override
    public void onItemClick(@NonNull TestListEntity value) {
        ToastUtils.success(value.getContent()).show();
    }

    /**
     * 移除条目
     *
     * @param value
     */
    @Override
    public void onRemoveItemClick(@NonNull TestListEntity value) {
        mTestBindingAdapter.removeItem(value);
    }

    /**
     * 置顶
     *
     * @param entity
     */
    @Override
    public void onTopItemClick(@NonNull TestListEntity entity) {
        ToastUtils.success(entity + " " + StringUtils.getString(R.string.resources_set_top_text)).show();
        mTestBindingAdapter.removeItem(entity);
        entity.setTop(true);
        mTestBindingAdapter.addData(0, entity);
        mViewDataBinding.rvList.scrollToPosition(0);
    }

    /**
     * 刷新到顶部
     *
     * @param targetEntity
     */
    @Override
    public void onRefreshTopItemClick(@NonNull TestListEntity targetEntity) {
        targetEntity.setSortTime(TimeUtils.currentServiceTimeMillis());
        final List<TestListEntity> entities = mTestBindingAdapter.getData();
        if (!ListUtils.isEmpty(entities)) {
            Collections.sort(entities, (o1, o2) -> {
                if (Boolean.compare(o2.isTop(), o1.isTop()) == 0) {
                    return (int) (o2.getSortTime() - o1.getSortTime());
                }
                return Boolean.compare(o2.isTop(), o1.isTop());
            });
        }
        mTestBindingAdapter.notifyDataSetChanged();
    }

    //endregion

    //endregion


}