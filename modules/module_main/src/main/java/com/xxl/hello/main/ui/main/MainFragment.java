package com.xxl.hello.main.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xxl.core.aop.annotation.Async;
import com.xxl.core.aop.annotation.Safe;
import com.xxl.core.media.audio.AudioCapture;
import com.xxl.core.media.audio.AudioCapture.OnAudioFrameCapturedListener;
import com.xxl.core.media.audio.AudioRecordFormat;
import com.xxl.core.ui.BaseEventBusWrapper;
import com.xxl.core.ui.fragment.BaseStateViewModelFragment;
import com.xxl.core.ui.state.EmptyState;
import com.xxl.core.utils.AppExpandUtils;
import com.xxl.core.utils.CrashHandler;
import com.xxl.core.utils.DecorationUtils;
import com.xxl.core.widget.recyclerview.OnRefreshDataListener;
import com.xxl.core.widget.text.LinkTouchMovementMethod;
import com.xxl.hello.common.config.AppConfig;
import com.xxl.hello.common.config.CacheDirConfig;
import com.xxl.hello.main.BR;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainFragmentBinding;
import com.xxl.hello.main.ui.main.adapter.OnTestRecycleItemListener;
import com.xxl.hello.main.ui.main.adapter.TestBindingRecycleItemListener;
import com.xxl.hello.main.ui.main.adapter.TestListEntity;
import com.xxl.hello.main.ui.main.adapter.multi.TestMultiAdapter;
import com.xxl.hello.router.api.MainRouterApi;
import com.xxl.hello.router.api.UserRouterApi;
import com.xxl.hello.service.data.model.api.user.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.media.MediaPreviewItemEntity;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi;
import com.xxl.hello.service.handle.api.AppSchemeService;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.ui.view.record.OnRecordListener;
import com.xxl.hello.widget.ui.view.record.RecordButton;
import com.xxl.hello.widget.ui.window.CommonMessagePopupWindow;
import com.xxl.kit.AppUtils;
import com.xxl.kit.ClipboardUtils;
import com.xxl.kit.FFmpegUtils;
import com.xxl.kit.ImageUtils;
import com.xxl.kit.ListUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.MediaUtils;
import com.xxl.kit.OnAppStatusChangedListener;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.PathUtils;
import com.xxl.kit.ResourceUtils;
import com.xxl.kit.StringUtils;
import com.xxl.kit.ThreadUtils;
import com.xxl.kit.TimeUtils;
import com.xxl.kit.ToastUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        TestBindingRecycleItemListener, OnRefreshDataListener, OnTestRecycleItemListener {

    //region: 成员变量

    /**
     * 首页数据模型
     */
    private MainViewModel mMainViewModel;

    /**
     * 携带到首页跳转下个页面的路径
     */
    @Autowired(name = MainRouterApi.Main.PARAMS_KEY_NEXT_PATH)
    String mNextPath;

    /**
     * 携带到首页的数据
     */
    @Autowired(name = MainRouterApi.Main.PARAMS_KEY_EXTRA_DATA)
    String mExtraData;

    @Inject
    TestMultiAdapter mTestBindingAdapter;

    /**
     * 首页EventBus通知事件监听
     */
    @Inject
    MainEventBusWrapper mMainEventBusWrapper;

    /**
     * Scheme 处理
     */
    @Inject
    AppSchemeService mAppSchemeService;

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
    protected boolean enableRouterInject() {
        return true;
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
        setupExceptionMessage();
        handleNavigationPath();
        mViewDataBinding.tvTest.setMovementMethod(LinkTouchMovementMethod.getInstance());
        mMainViewModel.setObservableUserId(String.valueOf(TimeUtils.currentServiceTimeMillis()));
        setupRecord();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mViewDataBinding.rvList.addItemDecoration(DecorationUtils.createHorizontalDividerItemDecoration(ResourceUtils.getAttrColor(AppUtils.getTopActivity(), R.attr.h_common_divider_color), 10, 0));
        mViewDataBinding.refreshLayout.setRefreshDataListener(this);
        mViewDataBinding.refreshLayout.bindRecyclerView(mViewDataBinding.rvList, mTestBindingAdapter, new GridLayoutManager(getActivity(), 3));
        mViewDataBinding.refreshLayout.setPageSize(20);
        mTestBindingAdapter.setListener(this);
        mTestBindingAdapter.setDragItemEnable(true, R.id.tv_content, mViewDataBinding.rvList);
    }

    @Override
    protected void requestData() {
        showLoadingState();
        mMainViewModel.requestQueryUserInfo(getStateResponseListener());
    }

    //endregion

    //region: MainNavigator

    @Async
    @Override
    public void onTestClick() {
        //UserRouterApi.Login.newBuilder().navigation(getActivity());
//
        String videoPath = PathUtils.getAppIntCachePath() + File.separator + "11.mp4";
        String videoPath2 = PathUtils.getAppIntCachePath() + File.separator + "2.mp4";
        String videoPath7 = PathUtils.getAppIntCachePath() + File.separator + "7.mov";
        String gifPath = PathUtils.getAppIntCachePath() + File.separator + "output.gif";
        String palettegenPath = PathUtils.getAppIntCachePath() + File.separator + "palettegen.png";
        String maskPath = PathUtils.getAppIntCachePath() + File.separator + "mask.png";
        String frame_dir = PathUtils.getAppIntCachePath() + File.separator+"frame";

        int interval = 10; // 每秒提取10帧
        int scaleWidth = 320; // 缩放宽度
        int scaleHeight = 320; // 缩放高度

        float speed = 1.0f; // GIF播放速度

        boolean b = ResourceUtils.copyFileFromAssets("11.mp4", videoPath);

        if (b) {
//            FFmpegUtils.replaceVideoBackgroundColor(videoPath6,videoPath7,null,null);
//            MediaInformation mediaInformation = FFmpegUtils.getMediaInformation(videoPath);

//            Bitmap starBitmap = ImageUtils.createStarBitmap(720, 720);
            Bitmap starBitmap = ImageUtils.createStarBitmap(720, 720);
            boolean save = ImageUtils.save(starBitmap, maskPath, Bitmap.CompressFormat.PNG);

            // 先拆分再合并也可以
//            FFmpegUtils.videoFrameExtraction(videoPath,maskPath,frame_dir);
//            FFmpegUtils.frame2Gif(frame_dir,gifPath,10,320,null);
            // 一个命令拆分+合并也可以
            FFmpegUtils.frame2Gif2(videoPath,maskPath,gifPath);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(getActivity())
                            .asGif()
                            .load(gifPath)
                            .into(mViewDataBinding.ivImage);
                }
            });

           /* FFmpegUtils.executeConvertVideoPalettegen(videoPath2, palettegenPath, interval, scaleWidth, scaleHeight, new OnRequestCallBack<Boolean>() {
                @Override
                public void onSuccess(@Nullable Boolean aBoolean) {
                    FFmpegUtils.executeConvertVideoToGif(videoPath2,palettegenPath,gifPath,interval,scaleWidth,scaleHeight,speed,null);
                    Log.e("aaa", "onTestClick: "+gifPath + FileUtils.isFileExists(gifPath));

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getActivity())
                                    .asGif()
                                    .load(gifPath)
                                    .into(mViewDataBinding.ivImage);
                        }
                    });
                }
            });*/


        }
    }



    public static Bitmap createStarBitmap(int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);  // 设置五角星的颜色

        Path path = new Path();

        // 计算中心点
        float centerX = width / 2f;
        float centerY = height / 2f;

        // 计算大圆半径和小圆半径
        float radius = Math.min(width, height) / 2.5f;
        float innerRadius = radius / 2.5f;

        // 从顶部中间开始绘制
        path.moveTo(centerX, centerY - radius);
        double angle = Math.PI / 2d;

        for (int i = 1; i < 10; i++) {
            angle += Math.PI * 2 / 10;
            float r = (i % 2 == 0) ? radius : innerRadius;
            float x = centerX + (float) Math.cos(angle) * r;
            float y = centerY - (float) Math.sin(angle) * r;
            path.lineTo(x, y);
        }
        path.close();

        canvas.drawPath(path, paint);

        return output;
    }


    public static Bitmap createStarBitmap2(int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);  // 设置五角星的颜色

        Path path = new Path();

        // 计算中心点
        float centerX = width / 2f;
        float centerY = height / 2f;

        // 计算大圆半径和小圆半径
        float radius = Math.min(width, height) / 2f;  // 使用更大的半径
        float innerRadius = radius / 2.5f;  // 内圆半径仍然是外圆半径的一部分

        // 从顶部中间开始绘制
        path.moveTo(centerX, centerY - radius);
        double angle = Math.PI / 2d;

        for (int i = 1; i < 10; i++) {
            angle += Math.PI * 2 / 10;
            float r = (i % 2 == 0) ? radius : innerRadius;
            float x = centerX + (float) Math.cos(angle) * r;
            float y = centerY - (float) Math.sin(angle) * r;
            path.lineTo(x, y);
        }
        path.close();

        canvas.drawPath(path, paint);

        return output;
    }

    /**
     * 创建五角星图形
     */
    public static Bitmap createStarBitmap3(int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap output = null;
        try {

            output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            Rect rect = new Rect(0, 0, width, height);

            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);

            Path path = new Path();

            float halfWidth = width / 2;
            float halfHeight = width / 2;

            float mid = Math.min(halfWidth, halfHeight);
            float bigRadius = mid;
            float smallRadius = mid / 2.5f;

            path.moveTo(mid, 0);
            for (int i = 0; i < 5; i++) {
                float x = (float) (mid + bigRadius * Math.sin(i * 2 * Math.PI / 5));
                float y = (float) (mid - bigRadius * Math.cos(i * 2 * Math.PI / 5));
                path.lineTo(x, y);
                x = (float) (mid + smallRadius * Math.sin((i * 2 + 1) * Math.PI / 5));
                y = (float) (mid - smallRadius * Math.cos((i * 2 + 1) * Math.PI / 5));
                path.lineTo(x, y);
            }

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            canvas.clipPath(path);
            canvas.drawColor(Color.WHITE);
            paint.setXfermode(null);
            canvas.clipPath(path);

            canvas.drawBitmap(output, rect, rect, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public static Bitmap createStarBitmap4(int width, int height, float translateY) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);  // 设置五角星的颜色

        // 平移画布
        canvas.translate(0, translateY);  // 在Y轴方向上平移

        Path path = new Path();

        // 计算中心点
        float centerX = width / 2f;
        float centerY = height / 2f;

        // 计算大圆半径和小圆半径
        float radius = Math.min(width, height) / 2f;  // 使用更大的半径
        float innerRadius = radius / 2.5f;  // 内圆半径仍然是外圆半径的一部分

        // 从顶部中间开始绘制
        path.moveTo(centerX, centerY - radius);
        double angle = Math.PI / 2d;

        for (int i = 1; i < 10; i++) {
            angle += Math.PI * 2 / 10;
            float r = (i % 2 == 0) ? radius : innerRadius;
            float x = centerX + (float) Math.cos(angle) * r;
            float y = centerY - (float) Math.sin(angle) * r;
            path.lineTo(x, y);
        }
        path.close();

        canvas.drawPath(path, paint);

        return output;
    }

    public static Bitmap createStarBitmap8(int width, int height) {
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setColor(Color.WHITE);  // 设置五角星的颜色

        Path path = new Path();

        // 计算中心点
        float centerX = width / 2f;
        float centerY = height / 2f;

        // 计算大圆半径和小圆半径
        float radius = Math.min(width, height) / 2.5f;  // 使用更大的半径以充分利用空间
        float innerRadius = radius / 2.5f;

        // 从画布中心顶部开始绘制五角星
        double angle = -Math.PI / 2;
        for (int i = 0; i < 10; i++) {
            float r = (i % 2 == 0) ? radius : innerRadius;
            float x = centerX + (float) Math.cos(angle) * r;
            float y = centerY + (float) Math.sin(angle) * r;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            angle += Math.PI / 5;
        }
        path.close();

        // 使用 PorterDuff 模式清除路径外的区域
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.clipPath(path);
        canvas.drawColor(Color.WHITE);
        paint.setXfermode(null);
        canvas.clipPath(path, Region.Op.REPLACE);

        // 绘制路径
        canvas.drawPath(path, paint);

        return output;
    }


    public static Bitmap createStarBitmap7(int width, int height) {
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);  // 设置五角星的颜色

        Path path = new Path();

        // 计算中心点
        float centerX = width / 2f;
        float centerY = height / 2f;

        // 计算大圆半径和小圆半径
        float radius = Math.min(width, height) / 2.5f;
        float innerRadius = radius / 2.5f;

        // 使用原始的角度和半径计算方法
        float mid = Math.min(centerX, centerY);
        for (int i = 0; i < 5; i++) {
            float x = (float) (mid + radius * Math.sin(i * 2 * Math.PI / 5));
            float y = (float) (mid - radius * Math.cos(i * 2 * Math.PI / 5));
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            x = (float) (mid + innerRadius * Math.sin((i * 2 + 1) * Math.PI / 5));
            y = (float) (mid - innerRadius * Math.cos((i * 2 + 1) * Math.PI / 5));
            path.lineTo(x, y);
        }
        path.close();

        // 计算路径的边界并居中
        RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        float offsetX = centerX - (bounds.left + bounds.right) / 2;
        float offsetY = centerY - (bounds.top + bounds.bottom) / 2;
        path.offset(offsetX, offsetY);

        // 绘制路径
        canvas.drawPath(path, paint);

        return output;
    }
    public static Bitmap createStarBitmap6(int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);  // 设置五角星的颜色

        Path path = new Path();

        // 计算中心点
        float centerX = width / 2f;
        float centerY = height / 2f;

        // 计算大圆半径和小圆半径
        float radius = Math.min(width, height) / 2f;  // 使用更大的半径
        float innerRadius = radius / 2.5f;  // 内圆半径仍然是外圆半径的一部分

        // 从顶部中间开始绘制
        double angle = -Math.PI / 2;
        for (int i = 0; i < 10; i++) {
            float r = (i % 2 == 0) ? radius : innerRadius;
            float x = centerX + (float) Math.cos(angle) * r;
            float y = centerY + (float) Math.sin(angle) * r;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            angle += Math.PI / 5;
        }
        path.close();

        // 计算路径的边界并居中
        RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        float offsetX = centerX - (bounds.left + bounds.right) / 2;
        float offsetY = centerY - (bounds.top + bounds.bottom) / 2;
        path.offset(offsetX, offsetY);

        // 绘制路径
        canvas.drawPath(path, paint);
        return output;
    }


    public static Bitmap createStarBitmap5(int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);  // 设置五角星的颜色

        Path path = new Path();

        // 计算中心点
        float centerX = width / 2f;
        float centerY = height / 2f;

        // 计算大圆半径和小圆半径
        float radius = Math.min(width, height) / 2.5f;
        float innerRadius = radius / 2.5f;

        // 计算五角星的顶点，以确定最高点和最低点
        float minY = Float.MAX_VALUE;
        float maxY = Float.MIN_VALUE;
        double angle = -Math.PI / 2;
        for (int i = 0; i < 10; i++) {
            float r = (i % 2 == 0) ? radius : innerRadius;
            float y = centerY + (float) Math.sin(angle) * r;
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
            angle += Math.PI / 5;
        }

        // 计算五角星的垂直偏移，以使其居中
        float verticalOffset = (minY + maxY) / 2 - centerY;

        // 从顶部中间开始绘制
        path.moveTo(centerX, centerY - radius - verticalOffset);
        angle = -Math.PI / 2;
        for (int i = 0; i < 10; i++) {
            float r = (i % 2 == 0) ? radius : innerRadius;
            float x = centerX + (float) Math.cos(angle) * r;
            float y = centerY + (float) Math.sin(angle) * r - verticalOffset;
            path.lineTo(x, y);
            angle += Math.PI / 5;
        }
        path.close();

        canvas.drawPath(path, paint);

        return output;
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
        MediaUtils.MediaEntity mediaInfo = MediaUtils.getMediaEntity(audioFile.getAbsolutePath());
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
     * 处理导航跳转路径
     */
    private void handleNavigationPath() {
        LogUtils.d("main scheme path " + mNextPath);
        if (!TextUtils.isEmpty(mNextPath)) {
            if (StringUtils.containsIgnoreCase(mNextPath, AppConfig.APP_SCHEME_TAG)) {
                mAppSchemeService.navigation(getActivity(), mExtraData, true);
                mNextPath = null;
                mExtraData = null;
            }
        }
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

    /**
     * 设置异常信息展示
     */
    private void setupExceptionMessage() {
        if (TextUtils.isEmpty(CrashHandler.getInstance().getAppLastCrashMessage())) {
            return;
        }
        CommonMessagePopupWindow.from(getActivity())
                .setTitle(getString(R.string.core_exception_info))
                .setMessage(CrashHandler.getInstance().getAppLastCrashMessage())
                .setNegativeButtonFakeBoldText(true)
                .setPositiveButtonFakeBoldText(true)
                .setNegativeButton(getString(R.string.resources_cancel_text), v -> {
                    CrashHandler.getInstance().clearAppLastCrashMessage();
                })
                .setPositiveButton(getString(R.string.resources_copy_text), v -> {
                    ClipboardUtils.copyText(CrashHandler.getInstance().getAppLastCrashMessage());
                    ToastUtils.success(R.string.resources_text_copied).show();
                    CrashHandler.getInstance().clearAppLastCrashMessage();
                })
                .showPopupWindow();
    }

    private void setupRecord() {
        mViewDataBinding.tvDeleteLatest.setOnClickListener(v -> {
            AudioCapture.getInstance().deleteLastAudioFile();
            List<String> audioFiles = AudioCapture.getInstance().getAudioFiles();
            ToastUtils.success("删除最后一条录音成功 " + ListUtils.getSize(audioFiles)).show();
        });
        mViewDataBinding.tvComplete.setOnClickListener(v -> {
            List<String> audioFiles = AudioCapture.getInstance().getAudioFiles();

            AudioCapture.getInstance().mergeAudioFiles(new OnRequestCallBack<String>() {
                @Override
                public void onSuccess(@Nullable String path) {
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.success("完成录音 " + path + " " + ListUtils.getSize(audioFiles)).show();
                        }
                    });
                }
            });

        });
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
                        .setAudioRecordFormat(AudioRecordFormat.AAC)
                        .setOutFilePath(CacheDirConfig.SHARE_MUSIC_FILE_DIR)
                        .setMultiRecord(true)
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
        if (UserRouterApi.Login.isRequestCode(requestCode)) {
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
     * 媒体视图点击
     *
     * @param testListEntity
     * @param targetView
     */
    @Override
    public void onMediaItemClick(@NonNull TestListEntity testListEntity,
                                 @NonNull View targetView) {
        if (isActivityFinishing()) {
            return;
        }
        List<MediaPreviewItemEntity> mediaPreviewItemEntities = new ArrayList<>();
        List<TestListEntity> entities = mTestBindingAdapter.getData();
        if (!ListUtils.isEmpty(entities)) {
            for (TestListEntity entity : entities) {
                int position = mTestBindingAdapter.findItemPositon(entity);
                if (entity.getMediaType() == SystemEnumsApi.CircleMediaType.IMAGE) {
                    MediaPreviewItemEntity mediaPreviewItemEntity = MediaPreviewItemEntity.obtain()
                            .setMediaUrl(entity.getUrl());
                    mediaPreviewItemEntities.add(mediaPreviewItemEntity);
                    if (position >= 0) {
                        mediaPreviewItemEntity.setTargetViewAttributes(mTestBindingAdapter.getViewByPosition(position, R.id.iv_photo));
                    }
                }
            }
        }

        WidgetRouterApi.MediaPreview.newBuilder()
                .setMediaPreviewItemEntities(mediaPreviewItemEntities)
                .setCurrentPosition(mTestBindingAdapter.findItemPositon(testListEntity))
                .navigation();
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