package com.xxl.core.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.xxl.core.data.model.entity.node.NodeEntity;
import com.xxl.kit.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树（list 嵌套）形和list（单层list）结构互相转换的工具类
 *
 * @author xxl.
 * @date 2022/11/15.
 */
public class NodeUtils {

    /**
     * 递归把list结构转换为tree结构
     *
     * @param targetNodeEntities 所有list
     * @param parentNodeId       父节点ID
     */
    public static <T extends NodeEntity> List<T> recessionListToTree(@NonNull final List<T> targetNodeEntities,
                                                                     @NonNull String parentNodeId) {
        final List<T> nodeEntities = new ArrayList<>();
        for (T nodeEntity : targetNodeEntities) {
            // 找出父节点
            if (TextUtils.equals(parentNodeId, nodeEntity.getParentNodeId())) {
                // 递归方式填充子节点列表
                nodeEntities.add(findChildrenNode(targetNodeEntities, nodeEntity));
            }
        }
        return nodeEntities;
    }

    /**
     * 找子节点
     *
     * @param targetNodeEntities 所有list
     * @param targetNodeEntity   父节点对象
     */
    public static <T extends NodeEntity> T findChildrenNode(@NonNull final List<T> targetNodeEntities,
                                                            @NonNull final T targetNodeEntity) {
        for (T nodeEntity : targetNodeEntities) {
            if (TextUtils.equals(targetNodeEntity.getNodeId(), nodeEntity.getParentNodeId())) {
                if (ListUtils.isEmpty(targetNodeEntity.getChildrenNodes())) {
                    targetNodeEntity.setChildrenNodes(new ArrayList());
                }
                // 递归 调用自身
                targetNodeEntity.getChildrenNodes().add(findChildrenNode(targetNodeEntities, nodeEntity));
            }
        }
        return targetNodeEntity;
    }


}