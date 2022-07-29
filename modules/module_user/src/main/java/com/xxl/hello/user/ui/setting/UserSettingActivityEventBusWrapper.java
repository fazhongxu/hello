package com.xxl.hello.user.ui.setting;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.event.SystemEventApi;
import com.xxl.hello.service.ui.BaseEventBusWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * 用户设置页面EventBus通知事件监听
 *
 * @author xxl.
 * @date 2021/7/23.
 */
public class UserSettingActivityEventBusWrapper extends BaseEventBusWrapper {

    //region: 构造函数

    @Inject
    public UserSettingActivityEventBusWrapper() {

    }

    //endregion

    //region: 素材提交到服务端通知事件

    /**
     * 在主线程监听素材提交到服务端通知事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThreed(@NonNull final SystemEventApi.OnMaterialSubmitToServiceEvent event) {
//        final UserSettingActivity activity = getActivity();
//        if (activity != null) {
//            activity.handleMaterialSubmitToServiceEvent(event);
//        }
        // TODO: 2022/7/29  
    }

    //endregion
}