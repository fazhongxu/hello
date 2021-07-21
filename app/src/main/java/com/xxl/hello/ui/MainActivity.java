package com.xxl.hello.ui;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.hello.common.utils.TestUtils;
import com.xxl.hello.nexus.R;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.ui.BaseActivity;
import com.xxl.hello.user.ui.LoginActivity;

/**
 * @author xxl
 * @date 2021/07/16.
 */
public class MainActivity extends BaseActivity<MainViewModel> implements MainActivityNavigator {

    //region: 成员变量

    /**
     * 首页数据模型
     */
    private MainViewModel mMainViewModel;

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
    }

    /**
     * 设置页面视图
     */
    @Override
    protected void setupLayout() {
        int random = TestUtils.getRandom();
        TextView tvText = findViewById(R.id.tv_test);
        tvText.setText(String.valueOf(random));
        tvText.setText(String.valueOf(TestUtils.currentTimeMillis()));

        tvText.setOnClickListener(view -> {
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


