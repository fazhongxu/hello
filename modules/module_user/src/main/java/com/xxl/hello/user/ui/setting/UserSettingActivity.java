package com.xxl.hello.user.ui.setting;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;
import com.xxl.core.utils.ShareUtils;
import com.xxl.hello.router.api.UserRouterApi;
import com.xxl.hello.user.R;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareUtils.onActivityResult(this,requestCode,resultCode,data);
    }

    @Override
    public UserSettingFragment createFragment() {
        return UserSettingFragment.newInstance();
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.user_setting_title;
    }

    //endregion

}
