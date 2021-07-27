package com.xxl.hello.core.utils;

import java.util.Random;

/**
 * @author xxl.
 * @date 2020/8/17.
 */
public class TestUtils {

    /**
     * 返回Hello
     *
     * @return
     */
    public String sayHello() {
        return "Hello";
    }

    /**
     * 获取一个随机数
     *
     * @param bound
     * @return
     */
    public static int getRandom(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    /**
     * 获取一个随机数
     *
     * @return
     */
    public static int getRandom() {
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
