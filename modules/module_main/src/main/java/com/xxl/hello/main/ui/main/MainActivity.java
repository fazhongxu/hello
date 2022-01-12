package com.xxl.hello.main.ui.main;

import android.Manifest;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xxl.core.aop.annotation.CheckNetwork;
import com.xxl.core.aop.annotation.Delay;
import com.xxl.core.aop.annotation.Safe;
import com.xxl.core.aop.annotation.SingleClick;
import com.xxl.core.data.router.AppRouterApi;
import com.xxl.core.listener.OnAppStatusChangedListener;
import com.xxl.core.media.audio.AudioCapture;
import com.xxl.core.utils.AppExpandUtils;
import com.xxl.core.utils.AppUtils;
import com.xxl.core.utils.DisplayUtils;
import com.xxl.core.utils.LogUtils;
import com.xxl.core.utils.StatusBarUtil;
import com.xxl.core.utils.TestUtils;
import com.xxl.core.utils.ToastUtils;
import com.xxl.hello.common.CacheDirConfig;
import com.xxl.hello.main.BR;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.ActivityMainBinding;
import com.xxl.hello.main.ui.main.window.PrivacyPolicyPopupWindow;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.ui.BaseEventBusWrapper;
import com.xxl.hello.service.ui.DataBindingActivity;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author xxl
 * @date 2021/07/16.
 */
@Route(path = AppRouterApi.MAIN_PATH)
public class MainActivity extends DataBindingActivity<MainViewModel, ActivityMainBinding> implements MainActivityNavigator, OnAppStatusChangedListener,
        AudioCapture.OnAudioFrameCapturedListener {

    //region: 成员变量

    /**
     * 首页数据模型
     */
    private MainViewModel mMainViewModel;

    /**
     * 首页EventBus事件监听
     */
    @Inject
    MainEventBusWrapper mMainEventBusWrapper;

    //endregion

    //region: 页面视图渲染

    /**
     * 设置标题栏布局
     */
    private void setupToolbarLayout() {
        StatusBarUtil.setDarkMode(this);
        final int statusBarHeight = StatusBarUtil.getStatusBarHeight();
        mViewDataBinding.appBar.setPadding(DisplayUtils.dp2px(this, 10), statusBarHeight, 0, 0);
    }

    //endregion

    //region: 页面生命周期

    /**
     * 获取视图资源ID
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    /**
     * 创建ViewModel数据模型
     *
     * @return
     */
    @Override
    protected MainViewModel createViewModel() {
        mMainViewModel = new ViewModelProvider(this, mViewModelProviderFactory).get(MainViewModel.class);
        mMainViewModel.setNavigator(this);
        return mMainViewModel;
    }

    /**
     * 获取EventBus事件监听类
     *
     * @return
     */
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

    @Override
    protected void beforeSetContentView() {
        super.beforeSetContentView();
//        DisplayUtils.setFullScreen(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterAppStatusChangedListener(this);
        AudioCapture.getInstance().release();
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

    /**
     * 设置页面视图
     */
    @Override
    protected void setupLayout() {
        setupToolbarLayout();
        registerAppStatusChangedListener(this);
        mMainViewModel.setObservableUserId(String.valueOf(TestUtils.currentTimeMillis()));
    }

    @Override
    protected void requestData() {
        if (!AppExpandUtils.isAgreePrivacyPolicy()) {
            return;
        }
        mMainViewModel.requestQueryUserInfo();
    }

    //endregion

    //region: OnAppStatusChangedListener

    @Override
    public void onForeground(Activity activity) {
        ToastUtils.show(getString(R.string.resources_app_is_foreground_tips));
    }

    @Override
    public void onBackground(Activity activity) {
        ToastUtils.show(getString(R.string.resources_app_is_background_tips));
    }

    //endregion

    //region: MainActivityNavigator

    @SingleClick
//    @CheckLogin
    @CheckNetwork
    @Delay(delay = 200)
    @Override
    public void onTestClick() {
//        UserRouterApi.Login.navigation();
        audioCapture();
        // TODO: 2022/1/10 音频存储 ，转码
    }

    /**
     * 请求查询用户信息完成
     *
     * @param response
     */
    @Override
    public void onRequestQueryUserInfoComplete(@NonNull final QueryUserInfoResponse response) {
        if (response != null) {
            mMainViewModel.setObservableUserInfo(response.getUserId().concat("\n").concat(response.getAvatarUrl()));
        }

        final LoginUserEntity loginUserEntity = mMainViewModel.requestGetCurrentLoginUserEntity();
        if (loginUserEntity != null) {
            mMainViewModel.setObservableUserId(loginUserEntity.getUserId());
        } else {
            mMainViewModel.setObservableUserId(String.valueOf(TestUtils.currentTimeMillis()));
        }
    }

    //endregion

    //region: OnAudioFrameCapturedListener

    /**
     * 开始录音
     */
    @Override
    public void onStartRecord() {
        mViewDataBinding.tvTest.setText(getString(R.string.core_stop_record_audio_text));
    }

    /**
     * 停止录音
     *
     * @param audioFile 音频文件
     */
    @Override
    public void onStopRecord(@NonNull final File audioFile){
        mViewDataBinding.tvTest.setText(getString(R.string.core_start_record_audio_text));
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
        PrivacyPolicyPopupWindow.from(this)
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

    private void onErrorCallback(@Nullable final Throwable throwable) {
        ToastUtils.show("我是错误回调" + throwable.getMessage());
        Log.e("aaav", "错了错了v");
    }

    /**
     * 音频采集
     */
    private void audioCapture() {
        final RxPermissions rxPermissions = new RxPermissions(this);
        Disposable disposable = rxPermissions.request(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(isSuccess -> {
                    if (isSuccess) {
                        if (AudioCapture.getInstance().isCaptureStarted()) {
                            AudioCapture.getInstance().stopCapture();
                            return;
                        }
                        AudioCapture.getInstance()
                                .setOnAudioFrameCapturedListener(this)
                                .setOutFilePath(CacheDirConfig.SHARE_FILE_DIR)
                                .startCapture();
                    } else {
                        ToastUtils.show(getString(R.string.core_permission_record_audio_failure_tips));
                    }
                }, throwable -> {
                    ToastUtils.show(getString(R.string.core_permission_record_audio_failure_tips));
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

    //endregion

}


