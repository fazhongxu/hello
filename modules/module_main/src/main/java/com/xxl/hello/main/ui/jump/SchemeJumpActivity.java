package com.xxl.hello.main.ui.jump;

import com.xxl.core.ui.activity.SingleFragmentActivity;

/**
 * scheme跳转处理页 // TODO: 2022/8/11 功能待写Html测试
 *
 * @author xxl.
 * @date 2022/8/11.
 */
public class SchemeJumpActivity extends SingleFragmentActivity<SchemeJumpFragment> {

    //region: 页面生命周期

    /**
     * 创建Fragment
     *
     * @return
     */
    @Override
    public SchemeJumpFragment createFragment() {
        return SchemeJumpFragment.newInstance(getExtras());
    }

    //endregion


}