package com.xxl.core.utils;

import com.xxl.core.widget.recyclerview.decoration.HorizontalDividerItemDecoration;
import com.xxl.core.widget.recyclerview.decoration.VerticalDividerItemDecoration;
import com.xxl.kit.AppUtils;
import com.xxl.kit.DisplayUtils;

/**
 * RecyclerView 间距工具类
 *
 * @author xxl.
 * @date 2022/10/12.
 */
public class DecorationUtils {

    /**
     * 创建水平方向的RecyclerView列表间距
     *
     * @param color       颜色
     * @param marginLeft  左边间距
     * @param marginRight 右边间距
     * @return
     */
    public static HorizontalDividerItemDecoration createHorizontalDividerItemDecoration(int color, int marginLeft, int marginRight) {
        return createHorizontalDividerItemDecoration(color, 1, marginLeft, marginRight);
    }

    /**
     * 创建水平方向的RecyclerView列表间距
     *
     * @param color       颜色
     * @param dividerSize 下划线宽度
     * @param marginLeft  左边间距
     * @param marginRight 右边间距
     * @return
     */
    public static HorizontalDividerItemDecoration createHorizontalDividerItemDecoration(int color, int dividerSize, int marginLeft, int marginRight) {
        return new HorizontalDividerItemDecoration.Builder(AppUtils.getApplication())
                .color(color)
                .size(dividerSize)
                .margin(DisplayUtils.dp2px(AppUtils.getApplication(), marginLeft), DisplayUtils.dp2px(AppUtils.getApplication(), marginRight))
                .build();
    }

    /**
     * 创建水平方向的RecyclerView列表间距
     *
     * @param color        颜色
     * @param dividerSize  下划线宽度
     * @param marginTop    上边间距
     * @param marginBottom 下边间距
     * @return
     */
    public static VerticalDividerItemDecoration createVerticalDividerItemDecoration(int color, int dividerSize, int marginTop, int marginBottom) {
        return new VerticalDividerItemDecoration.Builder(AppUtils.getApplication())
                .color(color)
                .size(dividerSize)
                .margin(DisplayUtils.dp2px(AppUtils.getApplication(), marginTop), DisplayUtils.dp2px(AppUtils.getApplication(), marginBottom))
                .build();
    }

    private DecorationUtils() {

    }

}