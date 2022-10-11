package com.xxl.hello.main.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xxl.core.aop.annotation.Safe;
import com.xxl.core.media.audio.AudioCapture;
import com.xxl.core.media.audio.AudioCapture.OnAudioFrameCapturedListener;
import com.xxl.core.media.audio.AudioRecordFormat;
import com.xxl.core.ui.BaseEventBusWrapper;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.core.utils.AppExpandUtils;
import com.xxl.core.utils.TestUtils;
import com.xxl.core.widget.recyclerview.OnRefreshDataListener;
import com.xxl.hello.common.config.CacheDirConfig;
import com.xxl.hello.main.BR;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainFragmentBinding;
import com.xxl.hello.main.ui.main.adapter.TestBindingAdapter;
import com.xxl.hello.main.ui.main.adapter.TestBindingRecycleItemListener;
import com.xxl.hello.main.ui.main.window.PrivacyPolicyPopupWindow;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.widget.ui.view.record.OnRecordListener;
import com.xxl.hello.widget.ui.view.record.RecordButton;
import com.xxl.kit.AppRouterApi;
import com.xxl.kit.AppUtils;
import com.xxl.kit.Bool;
import com.xxl.kit.FFmpegUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.MediaUtils;
import com.xxl.kit.OnAppStatusChangedListener;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.TimeUtils;
import com.xxl.kit.ToastUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author xxl.
 * @date 2022/4/8.
 */
public class MainFragment extends BaseViewModelFragment<MainViewModel, MainFragmentBinding>
        implements MainNavigator, OnAppStatusChangedListener, OnAudioFrameCapturedListener, TestBindingRecycleItemListener, OnRefreshDataListener {

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


    /**
     * 设置页面视图
     *
     * @param view
     */
    @Override
    protected void setupLayout(@NonNull final View view) {
        registerAppStatusChangedListener(this);
        mMainViewModel.setObservableUserId(String.valueOf(TestUtils.currentTimeMillis()));
        setupRecord();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mViewDataBinding.rvList.setAdapter(mTestBindingAdapter);
        mViewDataBinding.refreshLayout.setRefreshDataListener(this);
        mViewDataBinding.refreshLayout.bindRecyclerView(mViewDataBinding.rvList, mTestBindingAdapter);
        mTestBindingAdapter.setListener(this);
        List<String> list = Arrays.asList("1", "2", "3", "4", "hello");
        mViewDataBinding.refreshLayout.setPageSize(20);
        mTestBindingAdapter.setList(list);
    }

    @Override
    protected void requestData() {
        mMainViewModel.requestQueryUserInfo();
    }

    //endregion

    //region: MainNavigator

    @Safe
    @Override
    public void onTestClick() {
        AppRouterApi.Login.navigation(getActivity());
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
            mMainViewModel.setObservableUserId(response == null ? String.valueOf(TestUtils.currentTimeMillis()) : response.getUserId());
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
        final OnRequestCallBack<List<String>> callBack = list -> {
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
    public void onItemClick(@NonNull String value) {
        ToastUtils.success(value).show();
    }

    //endregion

    //endregion


}