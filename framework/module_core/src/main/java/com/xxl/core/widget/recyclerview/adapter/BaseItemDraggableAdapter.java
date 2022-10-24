package com.xxl.core.widget.recyclerview.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.dragswipe.DragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.module.BaseDraggableModule;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 条目可以拖拽的适配器
 *
 * @author xxl.
 * @date 2022/8/1.
 */
public abstract class BaseItemDraggableAdapter<T, L extends BaseRecycleItemListener, V extends BaseViewHolder> extends BaseAdapter<T, L, V>
        implements DraggableModule {

    //region: 构造函数

    public BaseItemDraggableAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    @NonNull
    public BaseDraggableModule addDraggableModule(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        return new BaseDraggableModule(baseQuickAdapter);
    }

    //endregion

    //region: 提供方法

    /**
     * 设置是否可以拖拽
     *
     * @param dragEnabled
     */
    public void setDragEnabled(final boolean dragEnabled) {
        BaseDraggableModule draggableModule = getDraggableModule();
        draggableModule.setDragEnabled(dragEnabled);
    }

    /**
     * 设置是否是长按启用拖拽
     *
     * @param dragOnLongPressEnabled
     */
    public void setDragOnLongPressEnabled(final boolean dragOnLongPressEnabled) {
        BaseDraggableModule draggableModule = getDraggableModule();
        draggableModule.setDragOnLongPressEnabled(dragOnLongPressEnabled);
    }

    /**
     * 设置拖拽监听
     *
     * @param onItemDragListener
     */
    public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
        BaseDraggableModule draggableModule = getDraggableModule();
        draggableModule.setOnItemDragListener(onItemDragListener);
    }

    /**
     * 获取拖拽帮助类
     *
     * @return
     */
    public ItemTouchHelper getItemTouchHelper() {
        BaseDraggableModule draggableModule = getDraggableModule();
        return draggableModule.getItemTouchHelper();
    }

    /**
     * 创建条目拖拽帮助类
     *
     * @return
     */
    public ItemTouchHelper createItemTouchHelper() {
        return new ItemTouchHelper(createDragAndSwipeCallback());
    }

    /**
     * 创建拖拽和滑动的回调
     *
     * <pre>
     * 重写拖拽回调，可以实现拖拽范围限制 如
     * new DragAndSwipeCallback(dataAdapter) {
     *
     *     @Override
     *     public boolean onMove(@NonNull RecyclerView recyclerView,
     *                           @NonNull RecyclerView.ViewHolder source,
     *                           @NonNull RecyclerView.ViewHolder target) {
     *         final List<Entity> entities = mAdapter.getData();
     *         if (ListUtils.isEmpty(entities)) {
     *             return super.onMove(recyclerView, source, target);
     *         }
     *         try {
     *             // 限制拖拽范围，不同类型不能跨越范围
     *             final Entity sourceEntity = entities.get(source.getBindingAdapterPosition());
     *             final Entity targetEntity = entities.get(target.getBindingAdapterPosition());
     *             if (sourceEntity != null && targetEntity != null) {
     *                 return sourceEntity.getMoveType() == targetEntity.getMoveType();
     *             }
     *         } catch (Exception e) {
     *             e.printStackTrace();
     *         }
     *         return super.onMove(recyclerView, source, target);
     *     }
     * };
     * </pre>
     *
     * @return
     */
    public DragAndSwipeCallback createDragAndSwipeCallback() {
        return new DragAndSwipeCallback(getDraggableModule());
    }

    /**
     * 设置条目是否可以拖拽
     *
     * @param enable       是否可以拖拽
     * @param toggleViewId 拖拽的视图的ID
     */
    public void setDragItemEnable(final boolean enable,
                                  int toggleViewId,
                                  RecyclerView recyclerView) {
        setDragItemEnable(enable, true, toggleViewId, createItemTouchHelper(), recyclerView);
    }

    /**
     * 设置条目是否可以拖拽
     *
     * @param enable                 是否可以拖拽
     * @param dragOnLongPressEnabled 是否是长按触发拖拽(true 长按启用拖拽，false 触摸到view就启用拖拽）
     * @param toggleViewId           拖拽的视图的ID
     */
    public void setDragItemEnable(final boolean enable,
                                  final boolean dragOnLongPressEnabled,
                                  int toggleViewId,
                                  RecyclerView recyclerView) {
        setDragItemEnable(enable, dragOnLongPressEnabled, toggleViewId, createItemTouchHelper(), recyclerView);
    }

    /**
     * 设置条目是否可以拖拽
     *
     * @param enable                 是否可以拖拽
     * @param dragOnLongPressEnabled 是否是长按触发拖拽(true 长按启用拖拽，false 触摸到view就启用拖拽）
     * @param toggleViewId           拖拽的视图的ID
     * @param itemTouchHelper        拖拽帮助类
     */
    public void setDragItemEnable(final boolean enable,
                                  final boolean dragOnLongPressEnabled,
                                  int toggleViewId,
                                  ItemTouchHelper itemTouchHelper,
                                  RecyclerView recyclerView) {
        BaseDraggableModule draggableModule = getDraggableModule();
        draggableModule.setDragEnabled(enable);
        draggableModule.setToggleViewId(toggleViewId);
        draggableModule.setDragOnLongPressEnabled(dragOnLongPressEnabled);
        draggableModule.setItemTouchHelper(itemTouchHelper);
        onAttachedToRecyclerView(recyclerView);
    }

    //endregion

}