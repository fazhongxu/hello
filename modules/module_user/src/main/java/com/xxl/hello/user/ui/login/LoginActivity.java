package com.xxl.hello.user.ui.login;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.hello.router.api.UserRouterApi;
import com.xxl.hello.service.ui.SingleFragmentBarActivity;

/**
 * @author xxl.
 * @date 2021/07/16.
 */
@Route(path = UserRouterApi.Login.PATH)
public class LoginActivity extends SingleFragmentBarActivity<LoginFragment> {

    //region: 页面生命周期

    /**
     * 创建Fragment
     *
     * @return
     */
    @Override
    public LoginFragment createFragment() {
        return LoginFragment.newInstance();
    }

    //endregion


}
