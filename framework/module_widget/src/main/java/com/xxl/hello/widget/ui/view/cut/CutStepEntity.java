package com.xxl.hello.widget.ui.view.cut;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * 切图操作步骤信息
 *
 * @author xxl.
 * @date 2024/3/27.
 */
public class CutStepEntity {

    //region: 成员变量

    /**
     * 对应的图片
     */
    private Bitmap mTargetBitmap;

    /**
     * 横线
     */
    private List<CutRectEntity> mHorizontalRectList = new ArrayList<>();

    /**
     * 竖线
     */
    private List<CutRectEntity> mVerticalRectList = new ArrayList<>();

    /**
     * 删除区域
     */
    private List<CutRectEntity> mDeleteRectList = new ArrayList<>();

    //endregion

    //region: 构造函数

    private CutStepEntity() {

    }

    public final static CutStepEntity obtain() {
        return new CutStepEntity();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}