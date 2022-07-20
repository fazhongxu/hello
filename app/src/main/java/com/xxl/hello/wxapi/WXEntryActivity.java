package com.xxl.hello.wxapi;

import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * 微信回调(支付，分享，小程序交互)页面
 *
 * @author xxl.
 * @date 2022/7/20.
 */
public class WXEntryActivity extends WXCallbackActivity {

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