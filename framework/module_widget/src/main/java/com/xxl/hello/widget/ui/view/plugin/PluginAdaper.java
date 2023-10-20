package com.xxl.hello.widget.ui.view.plugin;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetLayoutCommonPluginBinding;
import com.xxl.kit.DisplayUtils;

/**
 * @author xxl.
 * @date 2023/9/15.
 */
public class PluginAdaper {

    //region: 成员变量

    private LayoutInflater mLayoutInflater;

    /**
     * 插件视图
     */
    private WidgetLayoutCommonPluginBinding mPluginBinding;

    //endregion

    //region: 构造函数

    private PluginAdaper() {

    }

    public final static PluginAdaper obtain() {
        return new PluginAdaper();
    }

    //endregion

    //region: 提供方法

    /**
     * 绑定到视图上
     *
     * @param viewGroup
     */
    public void bindView(@NonNull final ViewGroup viewGroup) {
        setupLayout(viewGroup);
    }

    /**
     * 设置视图
     *
     * @param viewGroup
     */
    private void setupLayout(@NonNull final ViewGroup viewGroup) {
        mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        mPluginBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout.widget_layout_common_plugin, viewGroup, true);
        // TODO: 2023/10/20 dimin 引用键盘高度
        int height = DisplayUtils.dp2px(viewGroup.getContext(), 250);
        mPluginBinding.getRoot().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        // View Pager 设置adapter
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}