package com.xxl.hello.main.ui.main.adapter;

import com.xxl.hello.service.data.model.enums.SystemEnumsApi.CircleMediaType;
import com.xxl.kit.Bool;

/**
 * 测条目实体信息
 *
 * @author xxl.
 * @date 2022/10/25.
 */
public class TestListEntity {

    //region: 成员变量

    /**
     * 条目类型
     */
    @CircleMediaType
    private int mMediaType;

    private String mContent;

    private long mSortTime;

    private int mTop;

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

    //endregion

    //region: 内部辅助方法

    //endregion

}