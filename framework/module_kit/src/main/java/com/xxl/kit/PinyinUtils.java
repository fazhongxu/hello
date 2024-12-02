package com.xxl.kit;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

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
        return toPinyin(str, separator, PinyinFormat.WITH_TONE_MARK);
    }

    /**
     * 将输入字符串转为拼音
     *
     * @param str
     * @param separator    分隔符
     * @param pinyinFormat 拼音格式：WITH_TONE_NUMBER--数字代表声调，WITHOUT_TONE--不带声调，WITH_TONE_MARK--带声调
     * @return
     */
    public static String toPinyin(String str, String separator, PinyinFormat pinyinFormat) {
        try {
            com.github.stuxuhai.jpinyin.PinyinFormat targetPinyinFormat = com.github.stuxuhai.jpinyin.PinyinFormat.WITHOUT_TONE;
            if (pinyinFormat == PinyinFormat.WITH_TONE_NUMBER) {
                targetPinyinFormat = com.github.stuxuhai.jpinyin.PinyinFormat.WITH_TONE_NUMBER;
            }else if (pinyinFormat == PinyinFormat.WITH_TONE_MARK) {
                targetPinyinFormat = com.github.stuxuhai.jpinyin.PinyinFormat.WITH_TONE_MARK;
            }
            return PinyinHelper.convertToPinyinString(str, separator, targetPinyinFormat);
        } catch (PinyinException e) {
            e.printStackTrace();
        }
        return "";
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
         * @param pinyin
         */
        void setTargetPinyin(String pinyin);
    }

    public enum PinyinFormat {
        WITH_TONE_MARK, WITHOUT_TONE, WITH_TONE_NUMBER;
    }

    private PinyinUtils() {

    }

}