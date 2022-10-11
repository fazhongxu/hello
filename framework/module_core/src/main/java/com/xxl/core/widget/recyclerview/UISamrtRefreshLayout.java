package com.xxl.core.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/**
 * 刷新视图
 *
 * @author xxl.
 * @date 2022/8/1.
 */
public class UISamrtRefreshLayout extends SmartRefreshLayout implements OnRefreshListener {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public UISamrtRefreshLayout(@NonNull Context context) {
        this(context,null);
    }

    public UISamrtRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnRefreshListener(this);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}