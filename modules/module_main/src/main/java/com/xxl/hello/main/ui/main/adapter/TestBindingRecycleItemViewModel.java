package com.xxl.hello.main.ui.main.adapter;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public class TestBindingRecycleItemViewModel {


    private TestListEntity mTargetItemEntity;

    private ObservableField<String> mObservableTestValue = new ObservableField<>();

    public ObservableBoolean mObservableTop = new ObservableBoolean();

    public void setItemEntity(TestListEntity itemEntity) {
        mTargetItemEntity = itemEntity;
        mObservableTestValue.set(itemEntity.getContent());
        mObservableTop.set(itemEntity.isTop());
    }

    public TestListEntity getTargetItemEntity() {
        return mTargetItemEntity;
    }

    public ObservableField<String> getObservableTestValue() {
        return mObservableTestValue;
    }

    public ObservableBoolean getObservableTop() {
        return mObservableTop;
    }
}