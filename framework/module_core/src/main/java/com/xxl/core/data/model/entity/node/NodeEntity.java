package com.xxl.core.data.model.entity.node;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * list（单层list实现一级、二级、多级效果） 和 tree（list嵌套） 结构的节点信息
 *
 * @author xxl.
 * @date 2022/11/15.
 */
public interface NodeEntity<T extends NodeEntity> {

    //region: 提供方法

    /**
     * 获取当前节点ID
     *
     * @return
     */
    String getNodeId();

    /**
     * 获取父节点ID
     *
     * @return
     */
    String getParentNodeId();

    /**
     * 获取
     *
     * @return
     */
    List<T> getChildrenNodes();

    /**
     * 设置子节点数据
     *
     * @param childrenNodes
     * @return
     */
    T setChildrenNodes(@NonNull final List<T> childrenNodes);

    //endregion


}