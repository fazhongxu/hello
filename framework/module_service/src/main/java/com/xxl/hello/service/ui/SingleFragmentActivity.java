package com.xxl.hello.service.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.xxl.hello.service.R;

/**
 * 单个Fragment在Activity页面
 *
 * @author xxl.
 * @date 2021/7/19.
 */
public abstract class SingleFragmentActivity<F extends Fragment> extends BaseActivity {

    //region: 成员变量

    /**
     * fragment
     */
    private F mCurrentFragment;

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.core_activity_single_fragment;
    }

    /**
     * 设置数据
     */
    @Override
    protected void setupData() {

    }

    /**
     * 设置页面视图
     */
    @Override
    protected void setupLayout() {

    }

    /**
     * 获取fragment容器ID
     *
     * @return
     */
    public int getContainerViewId() {
        return R.id.ll_container;
    }

    @Override
    protected void setContentView() {
        setContentView(getLayoutRes());
        final FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(getContainerViewId()) == null) {
            mCurrentFragment = createFragment();
            addFragmentToContainer(getContainerViewId(), mCurrentFragment);
        } else {
            mCurrentFragment = (F) fm.findFragmentById(getContainerViewId());
        }
    }

    /**
     * 获取当前fragment
     *
     * @return
     */
    public F getCurrentFragment() {
        if (mCurrentFragment == null) {
            FragmentManager fm = getSupportFragmentManager();
            mCurrentFragment = (F) fm.findFragmentById(getContainerViewId());
        }
        return mCurrentFragment;
    }

    /**
     * 创建Fragment
     *
     * @return
     */
    public abstract F createFragment();

    /**
     * 添加fragment到容器里
     *
     * @param containerViewId 容器ID
     * @param fragment        fragment
     */
    private void addFragmentToContainer(final int containerViewId,
                                        final Fragment fragment) {
        if (fragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.findFragmentById(containerViewId) == null) {
                fm.beginTransaction()
                        .add(getContainerViewId(), fragment)
                        .commit();
            }
        }
    }

    /**
     * 替换容器fragment
     *
     * @param containerViewId 容器ID
     * @param fragment        fragment
     */
    private void replaceFragmentToContainer(final int containerViewId,
                                            final Fragment fragment) {
        if (fragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.findFragmentById(containerViewId) == null) {
                fm.beginTransaction()
                        .replace(getContainerViewId(), fragment)
                        .commit();
            }
        }
    }

    //endregion
}