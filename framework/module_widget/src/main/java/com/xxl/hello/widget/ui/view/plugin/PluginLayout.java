package com.xxl.hello.widget.ui.view.plugin;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetLayoutCommonPluginBinding;

/**
 * @author xxl.
 * @date 2023/9/15.
 */
public class PluginLayout extends LinearLayout {

    //region: 成员变量

    /**
     * 插件视图
     */
    private WidgetLayoutCommonPluginBinding mPluginBinding;

    //endregion

    //region: 构造函数

    public PluginLayout(Context context) {
        super(context);
        initView(context);
    }

    //endregion

    //region: 页面实图渲染

    /**
     * 初始化实图
     *
     * @param context
     */
    private void initView(Context context) {
        View view = inflate(context, R.layout.widget_layout_common_plugin, this);
        mPluginBinding = DataBindingUtil.bind(view);
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}