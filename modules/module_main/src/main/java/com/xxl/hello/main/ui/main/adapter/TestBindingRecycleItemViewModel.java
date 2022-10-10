package com.xxl.hello.main.ui.main.adapter;

import androidx.databinding.ObservableField;

/**
 * @author xxl.
 * @date 2022/8/1.
 */
public class TestBindingRecycleItemViewModel {


    private String mValue;

    private ObservableField<String> mObservableTestValue = new ObservableField<>();

    public void setItemEntity(String value) {
        mValue = value;
        mObservableTestValue.set(value);
    }

    public ObservableField<String> getObservableTestValue() {
        return mObservableTestValue;
    }

    public String getValue() {
        return mValue;
    }
}