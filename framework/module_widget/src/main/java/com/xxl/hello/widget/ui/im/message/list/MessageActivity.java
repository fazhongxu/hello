package com.xxl.hello.widget.ui.im.message.session.privites;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.luck.picture.lib.entity.LocalMedia;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.ChatRouterApi;
import com.xxl.hello.widget.ui.view.plugin.impl.AlbumPlugin;

import java.util.List;

/**
 * 单聊会话
 *
 * @author xxl.
 * @date 2024/6/14.
 */
@Route(path = ChatRouterApi.PrivateChat.PATH)
public class PrivateChatSessionActivity extends SingleFragmentBarActivity<PrivateChatSessionFragment>
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
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PrivateChatSessionFragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
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