package com.xxl.hello.main.ui.main;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xxl.hello.main.R;

/**
 * @author xxl
 * @date 2021/07/16.
 */
public class BottomSheetDialog extends BottomSheetDialogFragment {

    private BottomSheetDialog() {

    }

    public static BottomSheetDialog newInstance() {
        return new BottomSheetDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);

        final View rootView = getLayoutInflater().inflate(R.layout.main_layout_bottom_sheet_dialog, null, false);
        dialog.setContentView(rootView);
        final ViewGroup parent = (ViewGroup) rootView.getParent();
        parent.setBackgroundColor(Color.TRANSPARENT);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return dialog;
    }


}