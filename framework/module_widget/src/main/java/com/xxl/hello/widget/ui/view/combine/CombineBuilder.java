package com.xxl.hello.widget.ui.view.combine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.ColorInt;

import com.othershe.combinebitmap.helper.Builder;
import com.othershe.combinebitmap.helper.CombineHelper;
import com.othershe.combinebitmap.helper.Utils;
import com.othershe.combinebitmap.layout.DingLayoutManager;
import com.othershe.combinebitmap.layout.ILayoutManager;
import com.othershe.combinebitmap.layout.WechatLayoutManager;
import com.othershe.combinebitmap.listener.OnProgressListener;
import com.othershe.combinebitmap.listener.OnSubItemClickListener;
import com.othershe.combinebitmap.region.DingRegionManager;
import com.othershe.combinebitmap.region.IRegionManager;
import com.othershe.combinebitmap.region.WechatRegionManager;

/**
 * 组合图片构建类
 *
 * @author xxl.
 * @date 2024/2/2.
 */
public class CombineBuilder extends Builder {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public CombineBuilder(Context context) {
        super(context);
    }

    //endregion

    //region: 页面生命周期

    @Override
    public CombineBuilder setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    @Override
    public CombineBuilder setSize(int size) {
        this.size = Utils.dp2px(context, size);
        return this;
    }

    @Override
    public CombineBuilder setGap(int gap) {
        this.gap = Utils.dp2px(context, gap);
        return this;
    }

    @Override
    public CombineBuilder setGapColor(@ColorInt int gapColor) {
        this.gapColor = gapColor;
        return this;
    }

    @Override
    public CombineBuilder setPlaceholder(int placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    @Override
    public CombineBuilder setLayoutManager(ILayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        return this;
    }

    @Override
    public CombineBuilder setOnProgressListener(OnProgressListener progressListener) {
        this.progressListener = progressListener;
        return this;
    }

    @Override
    public CombineBuilder setOnSubItemClickListener(OnSubItemClickListener subItemClickListener) {
        this.subItemClickListener = subItemClickListener;
        return this;
    }

    @Override
    public CombineBuilder setBitmaps(Bitmap... bitmaps) {
        this.bitmaps = bitmaps;
        this.count = bitmaps.length;
        return this;
    }

    @Override
    public CombineBuilder setUrls(String... urls) {
        this.urls = urls;
        this.count = urls.length;
        return this;
    }

    @Override
    public CombineBuilder setResourceIds(int... resourceIds) {
        this.resourceIds = resourceIds;
        this.count = resourceIds.length;
        return this;
    }

    @Override
    public void build() {
        subSize = getSubSize(size, gap, layoutManager, count);
        initRegions();
        CombineHelper.init().load(this);
    }

    /**
     * 根据最终生成bitmap的尺寸，计算单个bitmap尺寸
     *
     * @param size
     * @param gap
     * @param layoutManager
     * @param count
     * @return
     */
    private int getSubSize(int size, int gap, ILayoutManager layoutManager, int count) {
        int subSize = 0;
        if (layoutManager instanceof DingLayoutManager) {
            subSize = size;
        } else if (layoutManager instanceof WechatLayoutManager) {
            if (count < 2) {
                subSize = size;
            } else if (count < 5) {
                subSize = (size - 3 * gap) / 2;
            } else if (count < 10) {
                subSize = (size - 4 * gap) / 3;
            }
        } else if (layoutManager instanceof CustomLayoutManager) {
            subSize = size;
        } else {
            throw new IllegalArgumentException("Must use DingLayoutManager or WechatRegionManager or CustomLayoutManager!");
        }
        return subSize;
    }

    /**
     * 初始化RegionManager
     */
    private void initRegions() {
        if (subItemClickListener == null || imageView == null) {
            return;
        }

        IRegionManager regionManager;

        if (layoutManager instanceof DingLayoutManager) {
            regionManager = new DingRegionManager();
        } else if (layoutManager instanceof WechatLayoutManager) {
            regionManager = new WechatRegionManager();
        } else if (layoutManager instanceof CustomLayoutManager) {
            regionManager = new CustomRegionManager();
        } else {
            throw new IllegalArgumentException("Must use DingLayoutManager or WechatRegionManager or CustomLayoutManager!");
        }

        regions = regionManager.calculateRegion(size, subSize, gap, count);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            int initIndex = -1;
            int currentIndex = -1;
            Point point = new Point();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                point.set((int) event.getX(), (int) event.getY());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initIndex = getRegionIndex(point.x, point.y);
                        currentIndex = initIndex;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        currentIndex = getRegionIndex(point.x, point.y);
                        break;
                    case MotionEvent.ACTION_UP:
                        currentIndex = getRegionIndex(point.x, point.y);
                        if (currentIndex != -1 && currentIndex == initIndex) {
                            subItemClickListener.onSubItemClick(currentIndex);
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        initIndex = currentIndex = -1;
                        break;
                }
                return true;
            }
        });
    }


    /**
     * 根据触摸点计算对应的Region索引
     *
     * @param x
     * @param y
     * @return
     */
    private int getRegionIndex(int x, int y) {
        for (int i = 0; i < regions.length; i++) {
            if (regions[i].contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    //endregion

    //region: 提供方法

    //endregion


}