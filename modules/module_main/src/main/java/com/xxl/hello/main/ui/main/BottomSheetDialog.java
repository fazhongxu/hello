package com.xxl.hello.main.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup parent,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_layout_bottom_sheet_dialog, parent, false);
        return rootView;
    }
}