package com.xxl.hello.widget.ui.view.cut;

import android.graphics.RectF;

/**
 * 切线或切面相关区域信息
 *
 * @author xxl.
 * @date 2024/3/27.
 */
public class CutRectEntity {

    //region: 成员变量

    /**
     * 切线或切面
     */
    private RectF mRect = new RectF();

    /**
     * 是否隐藏（边框也是切线，隐藏）
     */
    private boolean mIsHide;

    //endregion

    //region: 构造函数

    private CutRectEntity() {

    }

    public final static CutRectEntity obtain() {
        return new CutRectEntity();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}