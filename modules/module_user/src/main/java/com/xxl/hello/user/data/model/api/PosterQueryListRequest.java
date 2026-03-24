package com.xxl.hello.user.data.model.api;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

/**
 * 海报查询列表请求参数
 *
 * @author xxl.
 * @date 2025/6/10.
 */
@Keep
public class PosterQueryListRequest {

    //region: 成员变量

    /**
     * 页码
     */
    private int mPage;

    /**
     * 每页条数，最大50
     */
    private int mPageSize;

    /**
     * 排序类型：1权重排序，2最新，3最热，4最近收藏，5最近下载，6热门常用
     */
    private int mSortType;

    /**
     * 类型：0查询最近列表时使用，1图片，2视频，8特效视频
     */
    private int mType;

    /**
     * 是否安卓
     */
    private int mIsAdr;

    /**
     * 标签id，选中一级下热门时传一级标签id，选中二级标签时传二级标签id
     */
    private Integer mLabelId;

    /**
     * 比例id
     */
    private Integer mRatioId;

    /**
     * 宽高比id
     */
    private Integer mWhRatioId;

    /**
     * 张数
     */
    private Integer mMaxPhotoNum;

    /**
     * 非vip传2，vip传1，所有不传或0
     */
    private Integer mIsVipTag;

    /**
     * 行业id
     */
    private Integer mTradeLabelId;

    //endregion

    //region: 构造函数

    private PosterQueryListRequest() {

    }

    public final static PosterQueryListRequest obtain() {
        return new PosterQueryListRequest();
    }

    //endregion

    //region: 提供方法

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public void setPageSize(int pageSize) {
        this.mPageSize = pageSize;
    }

    public int getSortType() {
        return mSortType;
    }

    public void setSortType(int sortType) {
        this.mSortType = sortType;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public int getIsAdr() {
        return mIsAdr;
    }

    public void setIsAdr(int isAdr) {
        this.mIsAdr = isAdr;
    }

    public Integer getLabelId() {
        return mLabelId;
    }

    public void setLabelId(Integer labelId) {
        this.mLabelId = labelId;
    }

    public Integer getRatioId() {
        return mRatioId;
    }

    public void setRatioId(Integer ratioId) {
        this.mRatioId = ratioId;
    }

    public Integer getWhRatioId() {
        return mWhRatioId;
    }

    public void setWhRatioId(Integer whRatioId) {
        this.mWhRatioId = whRatioId;
    }

    public Integer getMaxPhotoNum() {
        return mMaxPhotoNum;
    }

    public void setMaxPhotoNum(Integer maxPhotoNum) {
        this.mMaxPhotoNum = maxPhotoNum;
    }

    public Integer getIsVipTag() {
        return mIsVipTag;
    }

    public void setIsVipTag(Integer isVipTag) {
        this.mIsVipTag = isVipTag;
    }

    public Integer getTradeLabelId() {
        return mTradeLabelId;
    }

    public void setTradeLabelId(Integer tradeLabelId) {
        this.mTradeLabelId = tradeLabelId;
    }

    //endregion

    //region: 内部辅助方法

    /**
     * 构建请求参数
     *
     * @param page        页码
     * @param pageSize    每页条数
     * @param sortType    排序类型
     * @param type        类型
     * @param isAdr       是否安卓
     * @return
     */
    @NonNull
    public static PosterQueryListRequest build(int page, int pageSize, int sortType, int type, int isAdr) {
        PosterQueryListRequest request = obtain();
        request.setPage(page);
        request.setPageSize(pageSize);
        request.setSortType(sortType);
        request.setType(type);
        request.setIsAdr(isAdr);
        return request;
    }

    //endregion
}
