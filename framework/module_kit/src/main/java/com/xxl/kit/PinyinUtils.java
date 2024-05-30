package com.xxl.kit;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 拼音工具类
 *
 * @author xxl.
 * @date 2024/5/30.
 */
public class PinyinUtils {

    public final static String PINYIN_UNKNOWN = "#";

    /**
     * 将输入字符串转为拼音
     *
     * @param str
     * @param separator 分隔符
     * @return
     */
    public static String toPinyin(String str, String separator) {
        return Pinyin.toPinyin(str, separator);
    }

    /**
     * 转换拼音
     *
     * @param data
     */
    public static void convert(List<? extends PinyinEntity> data) {
        if (ListUtils.isEmpty(data)) {
            return;
        }
        for (PinyinEntity entity : data) {
            String pinyin = toPinyin(entity.getConvertTarget(), "");
            entity.setTargetPinyin(pinyin);
        }
    }

    /**
     * 排序
     *
     * @param data
     */
    public static void sort(List<? extends PinyinEntity> data) {
        if (ListUtils.isEmpty(data)) {
            return;
        }
        convert(data);
        Collections.sort(data, (Comparator<PinyinEntity>) (lhs, rhs) -> {
            if (lhs.getTargetPinyin().equals(PINYIN_UNKNOWN)) {
                return 1;
            } else if (rhs.getTargetPinyin().equals(PINYIN_UNKNOWN)) {
                return -1;
            } else {
                return lhs.getTargetPinyin().compareTo(rhs.getTargetPinyin());
            }
        });
    }

    public interface PinyinEntity {

        /**
         * 获取需要转化成拼音的目标字段
         *
         * @return
         */
        String getConvertTarget();

        /**
         * 获取拼音字段
         *
         * @return
         */
        String getTargetPinyin();

        /**
         * 设置拼音字段
         *
         * @return
         */
        String setTargetPinyin(String pinyin);
    }

    private PinyinUtils() {

    }

}