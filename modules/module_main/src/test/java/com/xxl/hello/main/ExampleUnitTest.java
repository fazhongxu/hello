package com.xxl.hello.main;

import android.util.Log;

import com.xxl.kit.TimeUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        boolean today = TimeUtils.isToday(1720798417000L);

        int b = 2;

        float a = 0.5F;

        int angle = (int) (360 * a - 180);

        assertTrue(today);
    }

}