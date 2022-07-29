package com.xxl.hello.main.ui.main;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.hello.service.ui.SingleFragmentBarActivity;
import com.xxl.kit.AppRouterApi;

/**
 * @author xxl
 * @date 2021/07/16.
 */
@Route(path = AppRouterApi.MAIN_PATH)
public class MainActivity extends SingleFragmentBarActivity<MainFragment> {

    /**
     * 创建Fragment
     *
     * @return
     */
    @Override
    public MainFragment createFragment() {
        return MainFragment.newInstance();
    }

//    //region: 成员变量
//
//    /**
//     * 首页数据模型
//     */
//    private MainViewModel mMainViewModel;
//
//    /**
//     * 首页EventBus事件监听
//     */
//    @Inject
//    MainEventBusWrapper mMainEventBusWrapper;
//
//    //endregion
//
//    //region: 页面视图渲染
//
//    /**
//     * 设置标题栏布局
//     */
//    private void setupToolbarLayout() {
//        StatusBarUtil.setDarkMode(this);
//        final int statusBarHeight = StatusBarUtil.getStatusBarHeight();
//        mViewDataBinding.appBar.setPadding(DisplayUtils.dp2px(this, 10), statusBarHeight, 0, 0);
//    }
//
//    //endregion
//
//    //region: 页面生命周期
//
//    /**
//     * 获取视图资源ID
//     */
//    @Override
//    protected int getLayoutRes() {
//        return R.layout.activity_main;
//    }
//
//    /**
//     * 创建ViewModel数据模型
//     *
//     * @return
//     */
//    @Override
//    protected MainViewModel createViewModel() {
//        mMainViewModel = new ViewModelProvider(this, mViewModelProviderFactory).get(MainViewModel.class);
//        mMainViewModel.setNavigator(this);
//        return mMainViewModel;
//    }
//
//    /**
//     * 获取EventBus事件监听类
//     *
//     * @return
//     */
//    @Override
//    protected BaseEventBusWrapper getEventBusWrapper() {
//        return mMainEventBusWrapper;
//    }
//
//    /**
//     * 获取data binding 内的 ViewModel
//     *
//     * @return
//     */
//    @Override
//    public int getViewModelVariable() {
//        return BR.viewModel;
//    }
//
//    /**
//     * 获取data binding 内的 Navigator
//     *
//     * @return
//     */
//    @Override
//    public int getViewNavigatorVariable() {
//        return BR.navigator;
//    }
//
//    @Override
//    protected void beforeSetContentView() {
//        super.beforeSetContentView();
////        DisplayUtils.setFullScreen(this);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode,
//                                    int resultCode,
//                                    @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        if (AppRouterApi.Login.isRequestCode(requestCode)) {
//            ToastUtils.success(R.string.resources_login_success).show();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterAppStatusChangedListener(this);
//        AudioCapture.getInstance().release();
//    }
//
//    /**
//     * 设置数据
//     */
//    @Override
//    protected void setupData() {
//        LogUtils.d("当前登录用户ID..." + AppExpandUtils.getCurrentUserId());
//        if (!AppExpandUtils.isAgreePrivacyPolicy()) {
//            showPrivacyPolicyPopupWindow();
//        }
//    }
//
//    /**
//     * 设置页面视图
//     */
//    @Override
//    protected void setupLayout() {
//        setupToolbarLayout();
//        registerAppStatusChangedListener(this);
//        mMainViewModel.setObservableUserId(String.valueOf(TestUtils.currentTimeMillis()));
//        setupRecord();
//    }
//
//    @Override
//    protected void requestData() {
//        if (!AppExpandUtils.isAgreePrivacyPolicy()) {
//            return;
//        }
//        mMainViewModel.requestQueryUserInfo();
//    }
//
//    //endregion
//
//    //region: OnAppStatusChangedListener
//
//    @Override
//    public void onForeground(Activity activity) {
//        ToastUtils.success(R.string.resources_app_is_foreground_tips).show();
//    }
//
//    @Override
//    public void onBackground(Activity activity) {
//        ToastUtils.success(R.string.resources_app_is_background_tips).show();
//    }
//
//    //endregion
//
//    //region: MainActivityNavigator
//
//    @Safe
//    @CheckLogin
//    @Override
//    public void onTestClick() {
//        AppRouterApi.Login.navigation(this);
//    }
//
//    /**
//     * 请求查询用户信息完成
//     *
//     * @param response
//     */
//    @Override
//    public void onRequestQueryUserInfoComplete(@NonNull final QueryUserInfoResponse response) {
//        if (response != null) {
//            mMainViewModel.setObservableUserInfo(response.getUserId().concat("\n").concat(response.getAvatarUrl()));
//        }
//
//        final LoginUserEntity loginUserEntity = mMainViewModel.requestGetCurrentLoginUserEntity();
//        if (loginUserEntity != null) {
//            mMainViewModel.setObservableUserId(loginUserEntity.getUserId());
//        } else {
//            mMainViewModel.setObservableUserId(String.valueOf(TestUtils.currentTimeMillis()));
//        }
//    }
//
//    //endregion
//
//    //region: OnAudioFrameCapturedListener
//
//    /**
//     * 开始录音
//     */
//    @Override
//    public void onStartRecord() {
//        mViewDataBinding.tvTest.setText(getString(R.string.core_recording_text));
//    }
//
//    /**
//     * 停止录音
//     *
//     * @param audioFile 音频文件
//     */
//    @Override
//    public void onCompleteRecord(@NonNull final File audioFile) {
//        MediaUtils.MediaEntity mediaInfo = MediaUtils.getMediaInfo(audioFile.getAbsolutePath());
//        LogUtils.d("音频文件-->" + audioFile.getAbsolutePath() + "--" + mediaInfo.getDuration());
//        mViewDataBinding.tvTest.setText(getString(R.string.core_start_record_audio_text));
//
//        String audio = CacheDirConfig.SHARE_FILE_DIR + File.separator + "1.mp3";
//        String audio1 = CacheDirConfig.SHARE_FILE_DIR + File.separator + TimeUtils.currentTimeMillis() + "--" + ".mp3";
//        String audio2 = CacheDirConfig.SHARE_FILE_DIR + File.separator + TimeUtils.currentTimeMillis() + "录音背景音乐" + ".mp3";
//
//        new Thread() {
//            @Override
//            public void run() {
//                FFmpegUtils.adjustVolumeSub5db(audio, audio1);
//                FFmpegUtils.addBackgroundMusic(audioFile.getAbsolutePath(), audio1, audio2);
//            }
//        }.start();
//    }
//
//    /**
//     * 音频采集监听
//     *
//     * @param audioData
//     */
//    @Override
//    public void onAudioFrameCaptured(final byte[] audioData) {
//        LogUtils.d("音频数据" + "onAudioFrameCaptured: " + audioData);
//    }
//
//    //endregion
//
//    //region: Fragment 操作
//
//    /**
//     * 弹出隐私政策弹窗
//     */
//    private void showPrivacyPolicyPopupWindow() {
//        PrivacyPolicyPopupWindow.from(this)
//                .setOnDisagreeClickListener(v -> {
//                    AppUtils.exitApp();
//                })
//                .setOnAgreeClickListener(v -> {
//                    mMainViewModel.setAgreePrivacyPolicyStatus(true);
//                    AppExpandUtils.initPluginsAfterAgreePrivacyPolicy();
//                    requestData();
//                })
//                .showPopupWindow();
//    }
//
//    /**
//     * 测试按钮点击
//     */
//    @Safe(callBack = "onErrorCallback")
//    private void testCrash() {
//        int a = 1 / 0;
//        Log.e("aaa", "错了错了");
//    }
//
//    @Keep
//    private void onErrorCallback(@Nullable final Throwable throwable) {
//        ToastUtils.success("我是错误回调" + throwable.getMessage()).show();
//        Log.e("aaav", "错了错了v");
//    }
//
//    private void setupRecord() {
//        RecordButton recordButton = mViewDataBinding.recordBtn;
//        mViewDataBinding.recordBtn.setOnClickListener(v -> {
//            final RxPermissions rxPermissions = new RxPermissions(this);
//            Disposable disposable = rxPermissions.request(Manifest.permission.RECORD_AUDIO,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    .subscribe(isSuccess -> {
//                        if (isSuccess) {
//                            if (recordButton.isRunning()) {
//                                recordButton.stop();
//                            } else {
//                                recordButton.start();
//                            }
//                        } else {
//                            ToastUtils.warning(R.string.core_permission_record_audio_failure_tips).show();
//                        }
//                    }, throwable -> {
//                        ToastUtils.warning(R.string.core_permission_record_audio_failure_tips).show();
//                    });
//        });
//        mViewDataBinding.recordBtn.setRecordListener(new OnRecordListener() {
//
//            final SimpleDateFormat safeDateFormat = TimeUtils.getSafeDateFormat("mm:ss");
//
//            @Override
//            public void onButtonRecordStart() {
//                AudioCapture.getInstance()
//                        .setAudioRecordFormat(AudioRecordFormat.MP3)
//                        .setOutFilePath(CacheDirConfig.SHARE_FILE_DIR)
//                        .setOnAudioFrameCapturedListener(MainActivity.this)
//                        .startCapture();
//            }
//
//            @Override
//            public void onButtonRecording(final long currentTimeMills,
//                                          final long totalTimeMills) {
//                long timeSpan = TimeUtils.getTimeSpan(totalTimeMills, currentTimeMills, TimeUtils.TimeConstants.MSEC);
//                Log.e("aa", "onRecord: " + TimeUtils.millis2String(timeSpan, safeDateFormat));
//                mViewDataBinding.tvDuration.setText(TimeUtils.millis2String(timeSpan, safeDateFormat));
//            }
//
//            @Override
//            public void onButtonRecordStop(boolean isCanceled) {
//                mViewDataBinding.tvDuration.setText(TimeUtils.millis2String(mViewDataBinding.recordBtn.getMaxMilliSecond(), safeDateFormat));
//                AudioCapture.getInstance().stopCapture();
//            }
//
//            @Override
//            public void onButtonRecordFinish() {
//                mViewDataBinding.tvDuration.setText(TimeUtils.millis2String(mViewDataBinding.recordBtn.getMaxMilliSecond(), safeDateFormat));
//                AudioCapture.getInstance().stopCapture();
//            }
//        });
//    }
//
//    //endregion
//
//    //region: EventBus 操作
//
//    /**
//     * 刷新用户信息
//     *
//     * @param targetUserEntity
//     */
//    public void refreshUserInfo(@Nullable final LoginUserEntity targetUserEntity) {
//        if (targetUserEntity == null) {
//            return;
//        }
//        mMainViewModel.setObservableUserId(targetUserEntity.getUserId());
//    }
//
//    //endregion

}


