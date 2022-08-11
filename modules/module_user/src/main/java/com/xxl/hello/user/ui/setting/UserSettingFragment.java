package com.xxl.hello.user.ui.setting;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xxl.core.data.router.SystemRouterApi;
import com.xxl.core.image.selector.MediaSelector;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.common.config.AppConfig;
import com.xxl.hello.common.config.CacheDirConfig;
import com.xxl.hello.common.config.NetworkConfig;
import com.xxl.hello.service.data.local.db.entity.ResourcesUploadQueueDBEntity;
import com.xxl.hello.service.data.model.entity.share.ImageShareResourceEntity;
import com.xxl.hello.service.data.model.entity.share.ShareOperateItem;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.service.data.model.event.SystemEventApi;
import com.xxl.hello.service.qunlifier.ForUserBaseUrl;
import com.xxl.hello.user.BR;
import com.xxl.hello.user.R;
import com.xxl.hello.user.databinding.UserFragmentSettingBinding;
import com.xxl.hello.widget.ui.view.share.OnShareItemOperate;
import com.xxl.hello.widget.ui.view.share.ResourcesShareWindow;
import com.xxl.hello.widget.ui.view.share.api.ResourcesSharePickerKit;
import com.xxl.kit.FileUtils;
import com.xxl.kit.MomentShareUtils;
import com.xxl.kit.PathUtils;
import com.xxl.kit.ToastUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 用户设置页面
 *
 * @author xxl.
 * @date 2022/7/29.
 */
public class UserSettingFragment extends BaseViewModelFragment<UserSettingModel, UserFragmentSettingBinding> implements UserSettingNavigator {

    //region: 成员变量

    /**
     * 用户设置数据模型
     */
    private UserSettingModel mUserSettingModel;

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
    UserSettingEventBusWrapper mEventBusWrapper;

    /**
     * 资源分享器
     */
    @Inject
    ResourcesSharePickerKit mResourcesSharePickerKit;

    //endregion

    //region: 构造函数

    public final static UserSettingFragment newInstance() {
        return new UserSettingFragment();
    }

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.user_fragment_setting;
    }

    @Override
    protected UserSettingModel createViewModel() {
        mUserSettingModel = createViewModel(UserSettingModel.class);
        mUserSettingModel.setNavigator(this);
        return mUserSettingModel;
    }

    @Override
    protected UserSettingEventBusWrapper getEventBusWrapper() {
        return mEventBusWrapper;
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
    public void onDestroy() {
        super.onDestroy();
        mResourcesSharePickerKit.unregister();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (MediaSelector.isMediaRequestCode(requestCode)) {
                final List<LocalMedia> mediaList = MediaSelector.obtainMultipleResult(data);
                final LocalMedia media = mediaList.get(0);
                final Uri uri = Uri.parse(media.getPath());
                final Uri targetUri = PathUtils.getUriByFilePath(PathUtils.getFilePathByUri(uri));
                mUserSettingModel.requestPutResourcesUploadQueueDBEntities(new ArrayList<>(mediaList));
            }
        }
    }

    @Override
    protected void setupData() {
        mViewDataBinding = getViewDataBinding();
        setNetworkConfig();
    }

    @Override
    protected void setupLayout(@NonNull View view) {
        mResourcesSharePickerKit.register(this);
        mUserSettingModel.setObservableUserAvatarUrl("https://avatars.githubusercontent.com/u/24353536?s=400&u=43f37f2e73f15a1dfad58f0d63c35418715a5621&v=4");
    }

    @Override
    protected void requestData() {
        super.requestData();
        final LoginUserEntity loginUserEntity = mUserSettingModel.requestGetCurrentLoginUserEntity();
        mUserSettingModel.setTargetUserInfo(loginUserEntity);
    }

    //endregion

    //region: 页面试图渲染

    /**
     * 设置用户头像
     *
     * @param imageUrl
     */
    private void setupUserAvatar(@NonNull final String imageUrl) {
        mUserSettingModel.setObservableUserAvatarUrl(imageUrl);
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
        if (isActivityFinishing()) {
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
            mResourcesSharePickerKit.operateHandle(this, ShareOperateType.WE_CHAT, ImageShareResourceEntity.obtain());
            return true;
        }
        mResourcesSharePickerKit.showSharePicker(this, ImageShareResourceEntity.obtain(), new OnShareItemOperate() {

            @Override
            public boolean onClick(@NonNull ResourcesShareWindow window,
                                   @NonNull ShareOperateItem operateItem,
                                   @NonNull View targetView,
                                   int position) {
                if (operateItem.getOperateType() == ShareOperateType.WE_CHAT_CIRCLE) {
                    ToastUtils.success("自定义点击事件" + operateItem.getTitle()).show();
                    final List<File> files = FileUtils.listFilesInDirWithFilter(CacheDirConfig.SHARE_FILE_DIR, new FileFilter() {
                        @Override
                        public boolean accept(File pathname) {
                            return pathname.getName().endsWith(".jpg")
                                    || pathname.getName().endsWith(".jpeg")
                                    || pathname.getName().endsWith(".png");
                        }
                    });
                    final List<String> imagePaths = new ArrayList<>();
                    for (File file : files) {
                        imagePaths.add(file.getAbsolutePath());
                    }
                    MomentShareUtils.shareSingleImageToWeChatMoment(getActivity(), imagePaths.size() > 0 ? imagePaths.get(0) : "");
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
        SystemRouterApi.WebView.newBuilder(NetworkConfig.API_HOST + AppConfig.User.GITHUB_USER_NAME + "/hello")
                .navigation(getActivity());
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
        mUserSettingModel.setNetworkConfig(networkConfigInfo);
    }

    //endregion

    //region: EventBus 操作

    /**
     * 处理素材发布到服务端的操作
     *
     * @param event
     */
    public void handleMaterialSubmitToServiceEvent(@NonNull final SystemEventApi.OnMaterialSubmitToServiceEvent event) {
        if (isActivityFinishing()) {
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