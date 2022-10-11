package com.xxl.kit;

/**
 * Boolean 0，1 和 true，false间转换的工具类
 *
 * @author xxl.
 * @date 2022/10/11.
 */
public final class Bool {

    private static final int TRUE = 1;

    private static final int FALSE = 0;

    public static boolean convert(final int value) {
        return value == TRUE;
    }

    public static int convert(final boolean value) {
        return value ? TRUE : FALSE;
    }

    public static int trueValue() {
        return TRUE;
    }

    public static int falseValue() {
        return FALSE;
    }

    private Bool() {

    }
}