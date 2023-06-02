package com.xxl.core.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * 常用的FragmentAdapter
 *
 * @author xxl.
 * @date 2023/6/2.
 */
public class CommonFragmentAdapter extends FragmentStateAdapter {

    private List<Fragment> mFragments;

    public CommonFragmentAdapter(@NonNull FragmentActivity fragmentActivity,
                                 @NonNull List<Fragment> fragments) {
        super(fragmentActivity);
        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    public Fragment getFragment(final int position) {
        if (position >= 0 && position < getItemCount()) {
            return mFragments.get(position);
        }
        return null;
    }
}