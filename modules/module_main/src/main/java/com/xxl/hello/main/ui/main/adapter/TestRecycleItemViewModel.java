package com.xxl.hello.main.ui.main.adapter;

import androidx.databinding.ObservableField;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public class TestRecycleItemViewModel {


    private TestProviderMultiEntity mProviderMultiEntity;

    private String mValue;

    private ObservableField<String> mObservableTestValue = new ObservableField<>();

    public void setItemEntity(TestProviderMultiEntity itemEntity) {
        mProviderMultiEntity = itemEntity;
    }

    public ObservableField<String> getObservableTestValue() {
        return mObservableTestValue;
    }

    public TestProviderMultiEntity getProviderMultiEntity() {
        return mProviderMultiEntity;
    }

    public String getValue() {
        return mValue;
    }
}