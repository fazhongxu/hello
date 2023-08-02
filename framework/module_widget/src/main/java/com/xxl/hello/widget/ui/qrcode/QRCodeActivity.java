package com.xxl.hello.widget.ui.qrcode;

import android.graphics.Color;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi.QRCode;
import com.xxl.kit.StatusBarUtil;
import com.xxl.kit.StringUtils;

/**
 * 二维码页面
 *
 * @author xxl.
 * @date 2023/08/02.
 */
@Route(path = QRCode.PATH)
public class QRCodeActivity extends SingleFragmentBarActivity<QRCodeFragment> {

    //region: 页面生命周期

    /**
     * 创建Fragment
     *
     * @return
     */
    @Override
    public QRCodeFragment createFragment() {
        return QRCodeFragment.newInstance(getExtras());
    }

    @Override
    protected void setupToolbarLayout() {
        super.setupToolbarLayout();
        StatusBarUtil.setLightMode(this);
    }

    @Override
    public int getBackgroundColor() {
        return Color.BLACK;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.resources_qr_code;
    }

    @Override
    public boolean isDisplayRightText() {
        return true;
    }

    @Override
    public String getRightText() {
        return StringUtils.getString(R.string.resources_album_title);
    }

    @Override
    public void onToolbarRightClick(View view) {
        final QRCodeFragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.onToolbarRightClick(view);
        }
    }

    //endregion

}