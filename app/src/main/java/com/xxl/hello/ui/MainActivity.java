package com.xxl.hello.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.hello.common.utils.TestUtils;
import com.xxl.hello.nexus.R;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.ui.BaseActivity;
import com.xxl.hello.service.ui.DataBindingActivity;
import com.xxl.hello.user.ui.LoginActivity;

/**
 * @author xxl
 * @date 2021/07/16.
 */
public class MainActivity extends DataBindingActivity<MainViewModel> implements MainActivityNavigator {

    //region: 成员变量

    /**
     * 首页数据模型
     */
    private MainViewModel mMainViewModel;

    private TextView mTvTest;

    //endregion

    //region: 页面视图渲染

    //endregion

    //region: 页面生命周期

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
     * 获取视图资源ID
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    /**
     * 设置数据
     */
    @Override
    protected void setupData() {

    }

    @Override
    protected void requestData() {
        mMainViewModel.requestQueryUserInfo();
        final LoginUserEntity loginUserEntity = mMainViewModel.requestGetCurrentLoginUserEntity();
        if (loginUserEntity != null) {
            String userInfo = "";
            if (!TextUtils.isEmpty(loginUserEntity.getUserId())) {
                userInfo = userInfo.concat(loginUserEntity.getUserId());
            }
            if (!TextUtils.isEmpty(loginUserEntity.getUserName())) {
                userInfo = userInfo.concat("--")
                        .concat(loginUserEntity.getUserName());
            }
            if (!TextUtils.isEmpty(userInfo)) {
                mTvTest.setText(userInfo);
            }
        }
    }

    /**
     * 设置页面视图
     */
    @Override
    protected void setupLayout() {
        int random = TestUtils.getRandom();
        mTvTest = findViewById(R.id.tv_test);
        mTvTest.setText(String.valueOf(random));
        mTvTest.setText(String.valueOf(TestUtils.currentTimeMillis()));

        mTvTest.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
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
        Toast.makeText(this, "请求成功", Toast.LENGTH_SHORT).show();
    }

    //endregion
}


