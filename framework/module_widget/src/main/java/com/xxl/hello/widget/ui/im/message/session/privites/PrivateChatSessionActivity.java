package com.xxl.hello.widget.ui.im.message.session.privites;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.luck.picture.lib.entity.LocalMedia;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.ChatRouterApi;
import com.xxl.hello.widget.ui.im.message.session.base.BaseChatSessionActivity;
import com.xxl.hello.widget.ui.view.plugin.impl.AlbumPlugin;

import java.util.List;

/**
 * 单聊会话
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@Route(path = ChatRouterApi.PrivateChat.PATH)
public class PrivateChatSessionActivity extends BaseChatSessionActivity<PrivateChatSessionFragment>
        implements AlbumPlugin.AlbumPluginObservable {

    //region: 成员变量

    //endregion

    //region: 页面生命周期

    @Override
    protected int getToolbarTitle() {
        return R.string.resources_private_chat_title;
    }

    @Override
    public PrivateChatSessionFragment createFragment() {
        return PrivateChatSessionFragment.newInstance();
    }

    @Override
    public boolean isDisplayRightIcon() {
        return true;
    }

    @Override
    public int getRightIcon() {
        return R.drawable.resources_ic_more_white;
    }

    @Override
    public boolean onToolbarRightLongClick(View view) {
        PrivateChatSessionFragment fragment = getCurrentFragment();
        if (fragment != null) {
            return fragment.onToolbarRightLongClick();
        }
        return super.onToolbarRightLongClick(view);
    }

    //endregion

    //region: AlbumPluginObservable

    @Override
    public void handleAlbumPluginResult(final List<LocalMedia> targetMedias) {
        PrivateChatSessionFragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.handleAlbumPluginResult(targetMedias);
        }
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}