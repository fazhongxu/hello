package com.xxl.hello.widget.ui.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;

import com.king.mlkit.vision.camera.AnalyzeResult;
import com.king.mlkit.vision.camera.BaseCameraScan;
import com.king.mlkit.vision.camera.CameraScan;
import com.king.mlkit.vision.camera.analyze.Analyzer;
import com.king.wechat.qrcode.scanning.analyze.WeChatScanningAnalyzer;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xxl.core.image.selector.MediaSelector;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetFragmentQrcodeBinding;
import com.xxl.kit.ListUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.PathUtils;
import com.xxl.kit.ToastUtils;

import java.util.List;

/**
 * 二维码页面
 *
 * @author xxl.
 * @date 2023/08/02.
 */
public class QRCodeFragment extends BaseViewModelFragment<QRCodeViewModel, WidgetFragmentQrcodeBinding>
        implements QRCodeNavigator, CameraScan.OnScanResultCallback<List<String>> {

    //region: 成员变量

    /**
     * tag
     */
    private static final String TAG = QRCodeFragment.class.getSimpleName() + " ";

    /**
     * 二维码页面视图
     */
    private WidgetFragmentQrcodeBinding mQRCodeViewBinding;

    /**
     * 二维码页面视图模型
     */
    private QRCodeViewModel mQRCodeViewModel;

    /**
     * CameraScan
     */
    private CameraScan mCameraScan;

    /**
     * 开始时间
     */
    private long mBeginTimeMillis;

    //endregion

    //region: 构造函数

    public static QRCodeFragment newInstance(@NonNull final Bundle args) {
        final QRCodeFragment fragment = new QRCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.widget_fragment_qrcode;
    }

    @Override
    protected QRCodeViewModel createViewModel() {
        mQRCodeViewModel = createViewModel(QRCodeViewModel.class);
        mQRCodeViewModel.setNavigator(this);
        return mQRCodeViewModel;
    }

    @Override
    public void onResume() {
        super.onResume();
        startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (MediaSelector.isMediaRequestCode(requestCode)) {
            final LocalMedia targetMedia = ListUtils.getFirst(MediaSelector.obtainMultipleResult(data));
            if (targetMedia != null) {
                mBeginTimeMillis = System.currentTimeMillis();
                mQRCodeViewModel.requestDecodeQRCode(PathUtils.getFilePathByUri(Uri.parse(targetMedia.getPath())), result -> {
                    if (isActivityFinishing()) {
                        return;
                    }
                    onRequestDecodeQRCodeComplete(result);
                });
            }
        }
    }

    @Override
    protected boolean enableRouterInject() {
        return true;
    }

    @Override
    public int getViewModelVariable() {
        return BR.viewModel;
    }

    @Override
    public int getViewNavigatorVariable() {
        return BR.navigator;
    }

    @Override
    protected void setupData() {
        mQRCodeViewBinding = getViewDataBinding();
    }

    @Override
    public void setupLayout(@NonNull View view) {
        setupLayout();
    }

    //endregion

    //region: 页面视图渲染

    private void setupLayout() {
        initCameraScan();
    }

    /**
     * 初始化CameraScan
     */
    public void initCameraScan() {
        mCameraScan = createCameraScan(mQRCodeViewBinding.previewView)
                .setVibrate(true)
                .setAnalyzer(createAnalyzer())
                .setOnScanResultCallback(this);
    }

    /**
     * 创建微信二维码分析器
     *
     * @return
     */
    private Analyzer<List<String>> createAnalyzer() {
        return new WeChatScanningAnalyzer();
    }

    /**
     * 创建{@link CameraScan}
     *
     * @param previewView
     * @return
     */
    private CameraScan<List<String>> createCameraScan(PreviewView previewView) {
        return new BaseCameraScan<>(this, previewView);
    }

    /**
     * 启动相机预览
     */
    public void startCamera() {
        if (mCameraScan != null) {
            mCameraScan.startCamera();
            mBeginTimeMillis = System.currentTimeMillis();
        }
    }

    /**
     * 停止相机预览
     */
    public void stopCamera() {
        if (mCameraScan != null) {
            mCameraScan.stopCamera();
        }
    }

    /**
     * 释放相机
     */
    private void releaseCamera() {
        if (mCameraScan != null) {
            mCameraScan.release();
        }
    }


    //endregion

    //region: QRCodeNavigator

    //endregion

    //region: OnScanResultCallback

    /**
     * 扫码结果回调
     *
     * @param result
     */
    @Override
    public void onScanResultCallback(@NonNull AnalyzeResult<List<String>> result) {
        if (isActivityFinishing()) {
            return;
        }
        LogUtils.d(TAG + "onScanResultCallback " + result);
        if (result != null && !ListUtils.isEmpty(result.getResult())) {
            onRequestDecodeQRCodeComplete(ListUtils.getFirst(result.getResult()));
        }
    }

    //endregion

    //region: Activity 操作

    /**
     * 导航栏右边按钮点击
     *
     * @param view
     */
    public void onToolbarRightClick(View view) {
        if (isActivityFinishing()) {
            return;
        }
        MediaSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .forResult();
    }

    //endregion

    //region: Fragment 操作

    /**
     * 请求解析二维码完成
     *
     * @param result
     */
    private void onRequestDecodeQRCodeComplete(@NonNull String result) {
        if (isActivityFinishing()) {
            return;
        }
        final long time = System.currentTimeMillis() - mBeginTimeMillis;
        mCameraScan.setAnalyzeImage(false);
        LogUtils.d(TAG + "onRequestDecodeQRCodeComplete " + result + " time =" + time);
        ToastUtils.success(result + " 耗时 = " + time).show();
    }

    //endregion


}