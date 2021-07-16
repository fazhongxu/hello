package com.xxl.service.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public class BaseViewModel extends AndroidViewModel {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    //endregion

}