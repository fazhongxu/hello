package com.xxl.hello.service.im;

import com.xxl.hello.service.data.model.entity.im.ConversationEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 会话服务 对接SDK会话相关
 *
 * @author xxl.
 * @date 2024/8/7.
 */
public class IMConversationService {

    //region: 会话列表相关

    /**
     * 获取指定的会话列表
     *
     * @param targetConversationIds
     * @return
     */
    public Observable<List<ConversationEntity>> getConversationList(List<String> targetConversationIds) {
        // TODO: 2024/12/16 SDK 返回的会话列表 依赖注入，创建IMRepository
        return null;
    }

    /**
     * 获取会话列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Observable<List<ConversationEntity>> getConversationList(int page,
                                                                    int pageSize) {
        // TODO: 2024/12/16 SDK 返回的会话列表
        return null;
    }

    /**
     * 删除会话
     *
     * @param targetConversationId
     * @return
     */
    public Observable<Boolean> deleteConversation(String targetConversationId) {
        return null;
    }

    //endregion
}