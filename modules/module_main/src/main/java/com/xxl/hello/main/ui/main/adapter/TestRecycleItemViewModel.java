package com.xxl.hello.main.ui.main.adapter;

import androidx.databinding.ObservableField;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public class TestRecycleItemViewModel {


    private TestListEntity mTestItemEntity;

    private String mValue;

    private ObservableField<String> mObservableTestValue = new ObservableField<>();

    public void setItemEntity(TestListEntity itemEntity) {
        mTestItemEntity = itemEntity;
    }

    public ObservableField<String> getObservableTestValue() {
        return mObservableTestValue;
    }

    public TestListEntity getItemEntity() {
        return mTestItemEntity;
    }

    public String getValue() {
        return mValue;
    }
}