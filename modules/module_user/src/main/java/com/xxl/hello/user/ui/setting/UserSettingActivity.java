package com.xxl.hello.user.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xxl.core.data.router.SystemRouterApi;
import com.xxl.core.image.selector.MediaSelector;
import com.xxl.hello.common.AppConfig;
import com.xxl.hello.common.NetworkConfig;
import com.xxl.hello.router.UserRouterApi;
import com.xxl.hello.service.data.local.db.entity.ResourcesUploadQueueDBEntity;
import com.xxl.hello.service.data.model.entity.share.ImageShareResoucesEntity;
import com.xxl.hello.service.data.model.entity.share.ShareOperateItem;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.service.data.model.event.SystemEventApi;
import com.xxl.hello.service.qunlifier.ForUserBaseUrl;
import com.xxl.hello.service.ui.DataBindingActivity;
import com.xxl.hello.user.BR;
import com.xxl.hello.user.R;
import com.xxl.hello.user.databinding.UserActivitySettingBinding;
import com.xxl.hello.widget.share.OnShareItemOperate;
import com.xxl.hello.widget.share.ResourcesShareWindow;
import com.xxl.hello.widget.share.api.ResourcesSharePickerKit;
import com.xxl.kit.MomentShareUtils;
import com.xxl.kit.PathUtils;
import com.xxl.kit.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

    /**
     * 点击次数
     */
    private int mClickCount;

    /**
     * 用户设置页面EventBus通知事件监听
     */
    @Inject
    UserSettingActivityEventBusWrapper mEventBusWrapper;

    /**
     * 资源分享器
     */
    @Inject
    ResourcesSharePickerKit mResourcesSharePickerKit;

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
    protected void onDestroy() {
        super.onDestroy();
        mResourcesSharePickerKit.unregister();
    }

    @Override
    protected UserSettingActivityEventBusWrapper getEventBusWrapper() {
        return mEventBusWrapper;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (MediaSelector.isMediaRequestCode(requestCode)) {
                final List<LocalMedia> mediaList = MediaSelector.obtainMultipleResult(data);
                final LocalMedia media = mediaList.get(0);
                final Uri uri = Uri.parse(media.getPath());
                final Uri targetUri = PathUtils.getUriByFilePath(PathUtils.getFilePathByUri(uri));
//                mUserSettingViewModel.requestUpdateUserInfo(PathUtils.getFilePathByUri(uri));
                mUserSettingViewModel.requestPutResourcesUploadQueueDBEntities(new ArrayList<>(mediaList));
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
        mResourcesSharePickerKit.register(this);
        mUserSettingViewModel.setObservableUserAvatarUrl("https://avatars.githubusercontent.com/u/24353536?s=400&u=43f37f2e73f15a1dfad58f0d63c35418715a5621&v=4");
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
        mUserSettingViewModel.setObservableUserAvatarUrl(imageUrl);
    }

    //endregion

    //region: UserSettingNavigator

    /**
     * 请求添加上传资源到数据库完成
     *
     * @param isSuccess
     */
    @Override
    public void onRequestPutResourcesUploadQueueDBEntities(Boolean isSuccess) {
        if (isFinishing()) {
            return;
        }
    }

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
        MediaSelector.create(this)
                .openGallery(PictureMimeType.ofAll())
                .forResult();
    }

    /**
     * 用户头像长按点击
     *
     * @return
     */
    @Override
    public boolean onUserAvatarLongClick() {
        if (++mClickCount % 3 == 0) {
            mResourcesSharePickerKit.operateHandle(this, ShareOperateType.WE_CHAT, ImageShareResoucesEntity.obtain());
            return true;
        }
        mResourcesSharePickerKit.showSharePicker(this, ImageShareResoucesEntity.obtain(), new OnShareItemOperate() {

            @Override
            public boolean onClick(@NonNull ResourcesShareWindow window,
                                   @NonNull ShareOperateItem operateItem,
                                   @NonNull View targetView,
                                   int position) {
                if (operateItem.getOperateType() == ShareOperateType.WE_CHAT_CIRCLE) {
                    ToastUtils.success("自定义点击事件" + operateItem.getTitle()).show();
                    MomentShareUtils.shareImagesToWeChatCircle(UserSettingActivity.this,"","");
                    window.dismiss();
                    return true;
                }
                return false;
            }
        });
        return true;
    }

    /**
     * 关于我点击
     */
    @Override
    public void onAboutMeClick() {
        SystemRouterApi.WebView.newBuilder("https://www.github.com/" + AppConfig.User.GITHUB_USER_NAME)
                .navigation(this);
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

    /**
     * 处理素材发布到服务端的操作
     *
     * @param event
     */
    public void handleMaterialSubmitToServiceEvent(@NonNull final SystemEventApi.OnMaterialSubmitToServiceEvent event) {
        if (isFinishing()) {
            return;
        }
        CharSequence text = mViewDataBinding.tvTest.getText();
        StringBuilder sb = new StringBuilder(text);
        String avatar = "";
        for (ResourcesUploadQueueDBEntity resourcesUploadQueueDBEntity : event.getTargetResourcesUploadQueueDBEntities()) {
            sb.append(resourcesUploadQueueDBEntity.getUploadUrl())
                    .append("\n");
            if (TextUtils.isEmpty(avatar)) {
                avatar = resourcesUploadQueueDBEntity.getWaitUploadPath();
            }
        }
        setupUserAvatar(avatar);
        mViewDataBinding.tvTest.setText(sb);
        mViewDataBinding.scScrollView.fullScroll(View.FOCUS_DOWN);
    }
    //endregion


}
