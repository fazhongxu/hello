package com.xxl.hello.common.utils;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl.
 * @date 2021/12/29.
 */
public class ArrayExpandUtils {

    /**
     * 笛卡尔乘积算法
     * 把一个3个集合List{[1,2],[A,B],[a,b]} 转化成
     * List{[1,A,a],[1,A,b],[1,B,a],[1,B,b],[2,A,a],[2,A,b],[2,B,a],[2,B,b]} 数组输出
     * <p>
     * 2个集合 List{[1,2],[A,B]} 转化成
     * * List{[1,A],[1,B],[2,A],[2,B]} 数组输出
     *
     * @param dimensionValue 原List
     * @param result         通过乘积转化后的数组
     * @param layer          中间参数 (传0即可）
     * @param currentList    中间参数（new ArrayList 即可）
     */
    public static <T> void descartes(@NonNull final List<List<T>> dimensionValue,
                                     @NonNull final List<List<T>> result,
                                     final int layer,
                                     @NonNull final List<T> currentList) {
        if (layer < dimensionValue.size() - 1) {
            if (dimensionValue.get(layer).size() == 0) {
                descartes(dimensionValue, result, layer + 1, currentList);
            } else {
                for (int i = 0; i < dimensionValue.get(layer).size(); i++) {
                    List<T> list = new ArrayList<>(currentList);
                    list.add(dimensionValue.get(layer).get(i));
                    descartes(dimensionValue, result, layer + 1, list);
                }
            }
        } else if (layer == dimensionValue.size() - 1) {
            if (dimensionValue.get(layer).size() == 0) {
                result.add(currentList);
            } else {
                for (int i = 0; i < dimensionValue.get(layer).size(); i++) {
                    List<T> list = new ArrayList<T>(currentList);
                    list.add(dimensionValue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }

}