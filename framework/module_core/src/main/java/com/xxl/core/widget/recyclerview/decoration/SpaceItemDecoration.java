package com.xxl.core.widget.recyclerview.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView设置间距
 *
 * @author xxl.
 * @date 2022/10/10.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 行间距
     */
    private int verticalSpacing;

    /**
     * 列间距
     */
    private int horizontalSpacing;

    /**
     * 列数量
     */
    private int horizontalSize;

    /**
     * 是否填充四周
     */
    private boolean includeEdge;

    public SpaceItemDecoration(int verticalSpacing, int horizontalSpacing, int horizontalSize, boolean includeEdge) {
        this.verticalSpacing = verticalSpacing;
        this.horizontalSpacing = horizontalSpacing;
        this.horizontalSize = horizontalSize;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // item position
        int position = parent.getChildAdapterPosition(view);
        // item column
        int column = position % horizontalSize;
        if (includeEdge) {
            // spacing - column * ((1f / spanCount) * spacing)
            outRect.left = horizontalSpacing - column * horizontalSpacing / horizontalSize;
            // (column + 1) * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * horizontalSpacing / horizontalSize;
            // top edge
            if (position < horizontalSize) {
                outRect.top = verticalSpacing;
            }
            // item bottom
            outRect.bottom = horizontalSpacing;
        } else {
            // column * ((1f / spanCount) * spacing)
            outRect.left = column * horizontalSpacing / horizontalSize;
            // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / horizontalSize;
            if (position >= horizontalSize) {
                // item top
                outRect.top = verticalSpacing;
            }
        }
    }
}
