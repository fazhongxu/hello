package com.xxl.hello.main.ui.main.adapter;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.CircleMediaType;
import com.xxl.kit.Bool;

import java.util.UUID;

/**
 * 测条目实体信息
 *
 * @author xxl.
 * @date 2022/10/25.
 */
public class TestListEntity implements SectionEntity {

    //region: 成员变量

    private String mId;

    /**
     * 条目类型
     */
    @CircleMediaType
    private int mMediaType;

    public int mPosition;

    /**
     * 内容
     */
    private String mContent;

    /**
     * url
     */
    private String mUrl;

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
        mId = UUID.randomUUID().toString();
    }

    public final static TestListEntity obtain() {
        return new TestListEntity();
    }

    //endregion

    //region: get or set

    public String getId() {
        return mId;
    }

    public int getMediaType() {
        return mMediaType;
    }

    public String getContent() {
        return mContent;
    }

    public String getUrl() {
        return mUrl;
    }

    public long getSortTime() {
        return mSortTime;
    }

    public boolean isTop() {
        return Bool.convert(mTop);
    }

    public TestListEntity setId(String id) {
        this.mId = id;
        return this;
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

    public TestListEntity setUrl(String url) {
        mUrl = url;
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