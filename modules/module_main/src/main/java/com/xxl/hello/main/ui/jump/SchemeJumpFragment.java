package com.xxl.hello.main.ui.jump;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.main.BR;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainFragmentSchemeJumpBinding;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.kit.AppRouterApi;
import com.xxl.kit.LogUtils;
import com.xxl.kit.RouterUtils;

/**
 * scheme跳转处理页
 *
 * @author xxl.
 * @date 2022/8/11.
 */
public class SchemeJumpFragment extends BaseViewModelFragment<SchemeJumpViewModel, MainFragmentSchemeJumpBinding>
        implements SchemeJumpNavigator {

    //region: 成员变量

    /**
     * scheme跳转处理页视图模型
     */
    private SchemeJumpViewModel mSchemeJumpViewModel;

    //endregion

    //region: 构造函数

    public final static SchemeJumpFragment newInstance(@NonNull final Bundle bundle) {
        final SchemeJumpFragment mainFragment = new SchemeJumpFragment();
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
        return R.layout.main_fragment_scheme_jump;
    }

    /**
     * 创建ViewModel数据模型
     *
     * @return
     */
    @Override
    protected SchemeJumpViewModel createViewModel() {
        mSchemeJumpViewModel = createViewModel(SchemeJumpViewModel.class);
        mSchemeJumpViewModel.setNavigator(this);
        return mSchemeJumpViewModel;
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

    }

    /**
     * 设置页面视图
     *
     * @param rootView
     */
    @Override
    protected void setupLayout(@NonNull final View rootView) {

    }

    @Override
    protected void requestData() {
        final LoginUserEntity loginUserEntity = getViewModel().requestGetCurrentLoginUserEntity();
        final boolean isLogged = loginUserEntity != null;
        if (isLogged) {
            if (RouterUtils.hasActivity(AppRouterApi.MAIN_PATH)) {
                // 直接跳转到目标页
                LogUtils.d("Scheme 已登录 主页已经启动 ");
                AppRouterApi.Main.navigationWithFinish(getActivity());
            } else {
                // TODO: 2022/8/11 主页没有启动的情况，第二次打开，就不启动SchemeJump 页面了，待修复 
                // 先跳转打开主页，再跳转到目标页
                LogUtils.d("Scheme 已登录 主页还没启动");
                AppRouterApi.Main.navigationAndClearTop(getActivity());
            }

        } else {
            if (RouterUtils.hasActivity(AppRouterApi.MAIN_PATH)) {
                // 先跳转登录页，再跳转到目标页
                LogUtils.d("Scheme 未登录 主页已经启动");
            } else {
                // 先跳转打开主页，再跳转登录页，再跳转到目标页
                LogUtils.d("Scheme 未登录 主页还没启动");
            }
            AppRouterApi.Login.navigationWithFinish(getActivity());
        }
    }

    //endregion

    //region: MainNavigator


    //endregion

    //region: Fragment 操作


    //endregion


}