package com.xxl.hello.main.ui.main.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xxl.hello.main.R;

/**
 * @author xxl
 * @date 2021/07/16.
 */
public class BottomSheetDialog extends BottomSheetDialogFragment {

    private Dialog mDialog;

    private BottomSheetDialog() {

    }

    public static BottomSheetDialog newInstance() {
        return new BottomSheetDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (mDialog != null) {
            return mDialog;
        }
        final Dialog dialog = super.onCreateDialog(savedInstanceState);

        final View rootView = getLayoutInflater().inflate(R.layout.main_layout_bottom_sheet_dialog, null, false);
        dialog.setContentView(rootView);
        final ViewGroup parent = (ViewGroup) rootView.getParent();
        parent.setBackgroundColor(Color.TRANSPARENT);

        if (dialog instanceof com.google.android.material.bottomsheet.BottomSheetDialog) {
            com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = (com.google.android.material.bottomsheet.BottomSheetDialog) dialog;
            BottomSheetBehavior<FrameLayout> behavior = bottomSheetDialog.getBehavior();
            //禁用 BottomSheet 的拖拽
            behavior.setDraggable(false);
        }

        // 软键盘弹出时，完全顶起视图，不加这个只能顶起看到输入框
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return mDialog = dialog;
    }

    /**
     * 调整键盘布局(有时候项目遇到如何设置setSoftInputMode也没有反应，
     * 监听软键盘，并且设置  dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);调用这个）
     *
     * @param keyboardVisible
     */
    private void adjustLayoutForKeyboard(boolean keyboardVisible) {
        if (getDialog() instanceof com.google.android.material.bottomsheet.BottomSheetDialog) {
            com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = (com.google.android.material.bottomsheet.BottomSheetDialog) getDialog();
            BottomSheetBehavior<FrameLayout> behavior = bottomSheetDialog.getBehavior();

            if (keyboardVisible) {
                // 键盘显示时，确保 BottomSheet 完全展开
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }
    }


}