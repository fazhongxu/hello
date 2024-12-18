package com.xxl.hello.widget.ui.view.plugin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xxl.hello.widget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl.
 * @date 2023/9/15.
 */
public class PluginLayout extends LinearLayout {

    //region: 成员变量

    private static final int PAGE_PLUGIN_MAX_COUNT = 8;

    private ViewPager mViewPager;

    /**
     * 插件适配器
     */
    private PluginPagerAdapter mPluginPagerAdapter;

    /**
     * 插件
     */
    private List<Plugin> mPlugins = new ArrayList<>();

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
        inflate(context, R.layout.widget_layout_common_plugin, this);
        mViewPager = findViewById(R.id.viewpager);
        mPluginPagerAdapter = new PluginPagerAdapter();
        mViewPager.setAdapter(mPluginPagerAdapter);
    }

    private class PluginPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            int page = (int) Math.ceil(mPlugins.size() * 1.0F / PAGE_PLUGIN_MAX_COUNT);
            return page;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.widget_layout_common_plugin_grid, container, false);
            GridView gridView = view.findViewById(R.id.grid_view);

            int totalCount = mPlugins.size();
            int pageCount = PAGE_PLUGIN_MAX_COUNT;

            int count;
            boolean isLastPage = position + 1 == getCount();
            if (isLastPage) {
                if (totalCount % pageCount == 0) {
                    count = pageCount;
                } else {
                    count = totalCount % pageCount;
                }
            } else {
                count = pageCount;
            }

            gridView.setAdapter(new PluginItemPagerAdapter(getContext(), count, position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    private class PluginItemPagerAdapter extends ArrayAdapter<Plugin> {

        private int mCount;
        private int mPageIndex;

        public PluginItemPagerAdapter(@NonNull Context context, int count, int pageIndex) {
            super(context, 0);
            mCount = count;
            mPageIndex = pageIndex;
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.widget_layout_common_plugin_grid_item, parent, false);
            }
            ImageView ivPluginIcon = convertView.findViewById(R.id.iv_plugin_icon);
            TextView tvPluginName = convertView.findViewById(R.id.tv_plugin_name);

            // TODO: 2024/12/18 计算索引

            Plugin plugin = mPlugins.get(0);
            ivPluginIcon.setImageResource(plugin.obtainDrawable(getContext()));
            tvPluginName.setText(plugin.obtainTitle(getContext()));

            return convertView;
        }
    }


    //endregion

    //region: 提供方法

    /**
     * 初始化
     *
     * @param plugins
     */
    public void init(List<Plugin> plugins) {
        mPlugins.clear();
        mPlugins.addAll(plugins);
        mPluginPagerAdapter.notifyDataSetChanged();
    }

    //endregion

}