package com.xxl.hello.main.data.model.entity;

import com.xxl.core.data.model.enums.UserSex;
import com.xxl.core.utils.PinyinUtils;

/**
 * @author xxl.
 * @date 2021/7/15.
 */
public class Student implements PinyinUtils.PinyinEntity {

    private String mName;

    private String mNamePinyin;

    private String mSex;

    public Student(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(@UserSex String sex) {
        this.mSex = sex;
    }

    /**
     * 获取需要转化成拼音的目标字段
     *
     * @return
     */
    @Override
    public String getConvertTarget() {
        return mName;
    }

    /**
     * 获取拼音字段
     *
     * @return
     */
    @Override
    public String getTargetPinyin() {
        return mNamePinyin;
    }

    /**
     * 设置拼音字段
     *
     * @param pinyin
     * @return
     */
    @Override
    public void setTargetPinyin(String pinyin) {
        mNamePinyin = pinyin;
    }
}