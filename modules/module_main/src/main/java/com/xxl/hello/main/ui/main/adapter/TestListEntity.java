package com.xxl.hello.main.ui.main.adapter;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.CircleMediaType;
import com.xxl.kit.Bool;

/**
 * 测条目实体信息
 *
 * @author xxl.
 * @date 2022/10/25.
 */
public class TestListEntity implements SectionEntity {

    //region: 成员变量

    /**
     * 条目类型
     */
    @CircleMediaType
    private int mMediaType;

    /**
     * 内容
     */
    private String mContent;

    /**
     * 排序时间戳
     */
    private long mSortTime;

    /**
     * 是否置顶
     */
    private int mTop;

    /**
     * 是否是头部
     */
    private boolean mHeader;

    //endregion

    //region: 构造函数

    private TestListEntity() {

    }

    public final static TestListEntity obtain() {
        return new TestListEntity();
    }

    //endregion

    //region: get or set

    public int getMediaType() {
        return mMediaType;
    }

    public String getContent() {
        return mContent;
    }

    public long getSortTime() {
        return mSortTime;
    }

    public boolean isTop() {
        return Bool.convert(mTop);
    }

    /**
     * 设置类型
     *
     * @param mediaType
     * @return
     */
    public TestListEntity setMediaType(int mediaType) {
        this.mMediaType = mediaType;
        return this;
    }

    public TestListEntity setContent(String content) {
        this.mContent = content;
        return this;
    }

    public TestListEntity setSortTime(long sortTime) {
        mSortTime = sortTime;
        return this;
    }

    public TestListEntity setTop(boolean isTop) {
        mTop = Bool.convert(isTop);
        return this;
    }

    public TestListEntity setHeader(boolean isHeader) {
        mHeader = isHeader;
        return this;
    }

    //endregion

    //region: SectionEntity

    @Override
    public boolean isHeader() {
        return mHeader;
    }

    @Override
    public int getItemType() {
        if (isHeader()) {
            return SectionEntity.HEADER_TYPE;
        } else {
            return SectionEntity.NORMAL_TYPE;
        }
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}