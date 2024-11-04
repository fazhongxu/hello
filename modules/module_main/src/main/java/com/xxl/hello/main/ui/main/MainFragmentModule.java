package com.xxl.hello.main.ui.main;

import com.xxl.hello.main.ui.main.adapter.multi.TestMultiAdapter;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

/**
 * @author xxl.
 * @date 2024/11/4.
 * <p>
 * {@link FragmentComponent}对象和fragment生命周期走，避免内存泄露
 */
@InstallIn(FragmentComponent.class)
@Module
public class MainFragmentModule {

    @Provides
    public TestMultiAdapter provideTestMultiAdapter() {
        return new TestMultiAdapter();
    }
}