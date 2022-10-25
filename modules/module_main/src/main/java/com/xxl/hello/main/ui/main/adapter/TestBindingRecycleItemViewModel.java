package com.xxl.hello.main.ui.main.adapter;

import androidx.databinding.ObservableField;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public class TestBindingRecycleItemViewModel {


    private TestListEntity mTargetItemEntity;

    private ObservableField<String> mObservableTestValue = new ObservableField<>();

    public void setItemEntity(TestListEntity itemEntity) {
        mTargetItemEntity = itemEntity;
        mObservableTestValue.set(itemEntity.getContent());
    }

    public ObservableField<String> getObservableTestValue() {
        return mObservableTestValue;
    }

    public TestListEntity getTargetItemEntity() {
        return mTargetItemEntity;
    }
}