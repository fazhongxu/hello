package com.xxl.hello.main.ui.wx;

import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.xxl.core.utils.ShareUtils;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareUtils.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onResp(BaseResp resp) {
        super.onResp(resp);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (ShareUtils.onWeChatPayCallback(resp)) {
                finish();
            }
            return;
        }
        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            if (ShareUtils.onWeChatAuthCallback((SendAuth.Resp) resp)) {
                finish();
                return;
            }
        }
    }

    @Override
    public void onReq(BaseReq req) {
        super.onReq(req);
    }

    //endregion

}