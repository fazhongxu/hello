package com.xxl.hello.main.ui.wx;

import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * 微信回调处理页面
 *
 * @author xxl.
 * @date 2022/8/17.
 */
public class WeChatCallbackActivity extends WXCallbackActivity {

    //region: 成员变量

    public static final String CLASS_NAME = "com.xxl.hello.main.ui.wx.WeChatCallbackActivity";

    //endregion

    //region: 页面生命周期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResp(BaseResp resp) {
        super.onResp(resp);
    }

    @Override
    public void onReq(BaseReq req) {
        super.onReq(req);
    }

    //endregion

}