package com.xxl.hello.service.data.model.entity.node;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.core.data.model.entity.node.NodeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试list和树形结构互相转换实体
 *
 * @author xxl.
 * @date 2022/11/15.
 */
public class TestNodeEntity implements NodeEntity<TestNodeEntity> {

    //region: 成员变量

    /**
     * 一级parentID（parentID为0，代表此节点为1级）
     */
    public static final long FIRST_PARENT_NODE_ID = 0;

    /**
     * 节点ID
     */
    private long mNodeId;

    /**
     * 父节点ID
     */
    private long mParentNodeId;

    /**
     * 子类节点数据
     */
    private transient List<TestNodeEntity> mChildrenNodeEntities;

    //endregion

    //region: 构造函数

    private TestNodeEntity() {

    }

    public final static TestNodeEntity obtain() {
        return new TestNodeEntity();
    }

    //endregion

    //region: NodeEntity

    /**
     * 获取当前节点ID
     *
     * @return
     */
    @Override
    public String getNodeId() {
        return String.valueOf(mNodeId);
    }

    /**
     * 获取父节点ID
     *
     * @return
     */
    @Override
    public String getParentNodeId() {
        return String.valueOf(mParentNodeId);
    }

    /**
     * 获取
     *
     * @return
     */
    @Override
    public List<TestNodeEntity> getChildrenNodes() {
        return mChildrenNodeEntities;
    }

    /**
     * 获取long类型的节点ID
     *
     * @return
     */
    public long getNodeLongId() {
        return mNodeId;
    }

    /**
     * 设置父节点ID
     *
     * @param parentNodeId
     */
    @Override
    public TestNodeEntity setParentNodeId(@Nullable final String parentNodeId) {
        try {
            mParentNodeId = Long.parseLong(parentNodeId);
        } catch (Exception e) {
            e.printStackTrace();
            mParentNodeId = 0;
        }
        return this;
    }

    /**
     * 设置子节点数据
     *
     * @param childrenNodes
     * @return
     */
    @Override
    public TestNodeEntity setChildrenNodes(@NonNull final List<TestNodeEntity> childrenNodes) {
        mChildrenNodeEntities = childrenNodes;
        return this;
    }

    //endregion

    //region: 提供方法

    /**
     * 设置节点ID
     *
     * @param nodeId
     * @return
     */
    public TestNodeEntity setNodeId(final long nodeId) {
        this.mNodeId = nodeId;
        return this;
    }

    /**
     * 设置父节点ID
     *
     * @param parentNodeId
     * @return
     */
    public TestNodeEntity setParentNodeId(final long parentNodeId) {
        this.mParentNodeId = parentNodeId;
        return this;
    }

    /**
     * 创建测试数据
     *
     * @return
     */
    public static List<TestNodeEntity> obtainTestEntities() {
        final List<TestNodeEntity> nodeEntities = new ArrayList<>();

        final TestNodeEntity testNodeEntity1 = obtain()
                .setNodeId(1)
                .setParentNodeId(FIRST_PARENT_NODE_ID);

        final TestNodeEntity testNodeEntity2 = obtain()
                .setNodeId(2)
                .setParentNodeId(FIRST_PARENT_NODE_ID);

        final TestNodeEntity testNodeEntity3 = obtain()
                .setNodeId(3)
                .setParentNodeId(FIRST_PARENT_NODE_ID);

        // 给1 节点添加子节点
        final TestNodeEntity testNodeEntity4 = obtain()
                .setNodeId(4)
                .setParentNodeId(1);
        final TestNodeEntity testNodeEntity5 = obtain()
                .setNodeId(5)
                .setParentNodeId(1);

        // 给3 节点添加子节点
        final TestNodeEntity testNodeEntity6 = obtain()
                .setNodeId(6)
                .setParentNodeId(3);
        final TestNodeEntity testNodeEntity7 = obtain()
                .setNodeId(7)
                .setParentNodeId(3);

        // 给4 节点添加子节点
        final TestNodeEntity testNodeEntity8 = obtain()
                .setNodeId(8)
                .setParentNodeId(4);
        final TestNodeEntity testNodeEntity9 = obtain()
                .setNodeId(9)
                .setParentNodeId(4);

        nodeEntities.add(testNodeEntity1);
        nodeEntities.add(testNodeEntity2);
        nodeEntities.add(testNodeEntity3);

        nodeEntities.add(testNodeEntity4);
        nodeEntities.add(testNodeEntity5);

        nodeEntities.add(testNodeEntity6);
        nodeEntities.add(testNodeEntity7);

        nodeEntities.add(testNodeEntity8);
        nodeEntities.add(testNodeEntity9);

        return nodeEntities;
    }

    @Override
    public String toString() {
        return "TestNodeEntity{" +
                "mNodeId=" + mNodeId +
                ", mParentNodeId=" + mParentNodeId +
                ", mChildrenNodeEntities=" + mChildrenNodeEntities +
                '}';
    }

    //endregion


}