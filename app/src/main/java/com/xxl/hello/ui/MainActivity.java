package com.xxl.hello.ui;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.hello.common.utils.LogUtils;
import com.xxl.hello.common.utils.TestUtils;
import com.xxl.hello.nexus.BR;
import com.xxl.hello.nexus.R;
import com.xxl.hello.nexus.databinding.ActivityMainBinding;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.ui.BaseEventBusWrapper;
import com.xxl.hello.service.ui.DataBindingActivity;
import com.xxl.hello.service.utils.AppExpandUtils;
import com.xxl.hello.user.ui.login.LoginActivity;

import javax.inject.Inject;

/**
 * @author xxl
 * @date 2021/07/16.
 */
public class MainActivity extends DataBindingActivity<MainViewModel, ActivityMainBinding> implements MainActivityNavigator {

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

    /**
     * 设置数据
     */
    @Override
    protected void setupData() {
        LogUtils.d("当前登录用户ID..." + AppExpandUtils.getCurrentUserId());
    }

    /**
     * 设置页面视图
     */
    @Override
    protected void setupLayout() {

    }

    @Override
    protected void requestData() {
        mMainViewModel.requestQueryUserInfo();
    }

    //endregion

    //region: MainActivityNavigator

    /**
     * 请求查询用户信息完成
     *
     * @param response
     */
    @Override
    public void onRequestQueryUserInfoComplete(@NonNull final QueryUserInfoResponse response) {
        final LoginUserEntity loginUserEntity = mMainViewModel.requestGetCurrentLoginUserEntity();
        if (loginUserEntity != null) {
            mMainViewModel.setObservableUserId(loginUserEntity.getUserId());
        } else {
            mMainViewModel.setObservableUserId(String.valueOf(TestUtils.currentTimeMillis()));
        }
    }

    /**
     * 测试按钮点击
     */
    @Override
    public void onTestClick() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    //endregion

    //region: Event Bus 操作

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


