package com.xxl.hello.user.ui.setting;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.hello.router.api.UserRouterApi;
import com.xxl.hello.service.ui.SingleFragmentBarActivity;

/**
 * 用户设置页面
 *
 * @author xxl.
 * @date 2021/07/26.
 */
@Route(path = UserRouterApi.UserSetting.PATH)
public class UserSettingActivity extends SingleFragmentBarActivity<UserSettingFragment> {

    //region: 页面生命周期

    @Override
    public UserSettingFragment createFragment() {
        return UserSettingFragment.newInstance();
    }

    //endregion

}
