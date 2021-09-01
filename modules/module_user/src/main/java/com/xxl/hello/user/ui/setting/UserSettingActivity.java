package com.xxl.hello.user.ui.setting;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xxl.hello.core.config.NetworkConfig;
import com.xxl.hello.core.image.loader.ImageLoader;
import com.xxl.hello.core.image.selector.MediaSelector;
import com.xxl.hello.core.utils.PathUtils;
import com.xxl.hello.core.utils.ToastUtils;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.qunlifier.ForUserBaseUrl;
import com.xxl.hello.service.ui.DataBindingActivity;
import com.xxl.hello.user.BR;
import com.xxl.hello.user.R;
import com.xxl.hello.user.databinding.UserActivitySettingBinding;
import com.xxl.hello.widget.paths.UserRouterApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 用户设置页面
 *
 * @author xxl.
 * @date 2021/07/26.
 */
@Route(path = UserRouterApi.UserSetting.PATH)
public class UserSettingActivity extends DataBindingActivity<UserSettingViewModel, UserActivitySettingBinding> implements UserSettingNavigator {

    //region: 成员变量

    /**
     * 用户设置数据模型
     */
    private UserSettingViewModel mUserSettingViewModel;

    /**
     * 用户模块主机地址
     */
    @ForUserBaseUrl
    @Inject
    String mBaseUrl;

    //endregion

    //region: 页面生命周期

    /**
     * 获取视图资源ID
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.user_activity_setting;
    }

    /**
     * 创建ViewModel
     *
     * @return
     */
    @Override
    protected UserSettingViewModel createViewModel() {
        mUserSettingViewModel = new ViewModelProvider(this, mViewModelProviderFactory).get(UserSettingViewModel.class);
        mUserSettingViewModel.setNavigator(this);
        return mUserSettingViewModel;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (MediaSelector.isPictureRequestCode(requestCode)) {
                final List<LocalMedia> mediaList = MediaSelector.obtainMultipleResult(data);
                final LocalMedia media = mediaList.get(0);
                final Uri uri = Uri.parse(media.getPath());
                final Uri targetUri = PathUtils.getUriByFilePath(PathUtils.getFilePathByUri(uri));
                mUserSettingViewModel.requestUpdateUserInfo(PathUtils.getFilePathByUri(uri));
            }
        }
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
        mViewDataBinding = getViewDataBinding();
        setNetworkConfig();
    }

    /**
     * 设置页面视图
     */
    @Override
    protected void setupLayout() {
        ImageLoader.with(this)
                .load("https://avatars.githubusercontent.com/u/24353536?s=400&u=43f37f2e73f15a1dfad58f0d63c35418715a5621&v=4")
                .into(mViewDataBinding.ivUserAvatar);
    }

    @Override
    protected void requestData() {
        super.requestData();
        final LoginUserEntity loginUserEntity = mUserSettingViewModel.requestGetCurrentLoginUserEntity();
        mUserSettingViewModel.setTargetUserInfo(loginUserEntity);
    }

    //endregion

    //region: 页面试图渲染

    /**
     * 设置用户头像
     *
     * @param imageUrl
     */
    private void setupUserAvatar(@NonNull final String imageUrl) {
        ImageLoader.with(this)
                .load(imageUrl)
                .error(R.drawable.user_ic_data_error)
                .into(mViewDataBinding.ivUserAvatar);
    }

    //endregion

    //region: UserSettingNavigator

    /**
     * 更新用户信息完成
     *
     * @param targetUserEntity
     */
    @Override
    public void onUpdateUserInfoComplete(@NonNull final LoginUserEntity targetUserEntity) {
        setupUserAvatar(targetUserEntity.getAvatar());
    }

    /**
     * 用户头像点击
     */
    @Override
    public void onUserAvatarClick() {
        final RxPermissions rxPermissions = new RxPermissions(this);
        final Disposable disposable = rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(isSuccess -> {
                    if (isSuccess) {
                        MediaSelector.create(this)
                                .openGallery(PictureMimeType.ofImage())
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                    } else {
                        ToastUtils.show(getString(R.string.resources_permission_read_of_white_external_storage_failure_tips));
                    }
                }, throwable -> {
                    ToastUtils.show(throwable.getMessage());
                });
        mUserSettingViewModel.addCompositeDisposable(disposable);
    }

    /**
     * 切换网络环境点击
     */
    @Override
    public void onSwitchEnvironmentClick() {
        NetworkConfig.switchEnvironment();
    }

    //endregion

    //region: Fragment 操作

    /**
     * 设置网络配置环境信息
     */
    private void setNetworkConfig() {
        final String networkConfigInfo = getString(R.string.resources_is_develop_environment_format, String.valueOf(NetworkConfig.isNetworkDebug()))
                .concat("\n")
                .concat(getString(R.string.resources_host_format, mBaseUrl));
        mUserSettingViewModel.setNetworkConfig(networkConfigInfo);
    }

    //endregion

    //region: EventBus 操作


    //endregion


}
