package com.xxl.hello.main.ui.main;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.core.aop.annotation.CheckLogin;
import com.xxl.core.aop.annotation.Safe;
import com.xxl.core.utils.TestUtils;
import com.xxl.hello.main.BR;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainFragmentBinding;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.ui.DataBindingFragment;

/**
 * @author xxl.
 * @date 2022/4/8.
 */
public class MainFragment extends Fragment/*DataBindingFragment<MainViewModel, MainFragmentBinding>*/ implements MainActivityNavigator{

    //region: 成员变量

    /**
     * 首页数据模型
     */
    private MainViewModel mMainViewModel;

    //endregion

    //region: 构造函数

    public final static MainFragment newInstance() {
        return new MainFragment();
    }

    //endregion

    //region: 页面生命周期

//    /**
//     * 获取视图资源ID
//     *
//     * @return
//     */
//    @Override
//    protected int getLayoutRes() {
//        return R.layout.main_fragment;
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
//    /**
//     * 设置数据
//     */
//    @Override
//    protected void setupData() {
//
//    }
//
//    /**
//     * 设置页面视图
//     *
//     * @param view
//     */
//    @Override
//    protected void setupLayout(View view) {
//
//    }

    //endregion

    //region: MainActivityNavigator

    @Safe
    @CheckLogin
    @Override
    public void onTestClick() {
        // do something
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
            mMainViewModel.setObservableUserId(String.valueOf(TestUtils.currentTimeMillis()));
        }
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}