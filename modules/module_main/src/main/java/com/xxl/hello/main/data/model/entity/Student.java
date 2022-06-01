package com.xxl.hello.main.data.model.entity;

import com.xxl.core.data.model.enums.UserSex;

/**
 * @author xxl.
 * @date 2021/7/15.
 */
public class Student {

    private String mSex;


    public String getSex() {
        return mSex;
    }

    public void setSex(@UserSex String sex) {
        this.mSex = sex;
    }
}