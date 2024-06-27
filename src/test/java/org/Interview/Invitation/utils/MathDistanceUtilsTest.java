package org.Interview.Invitation.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yujie.fan
 * @description TODO
 * @date 2024-06-27
 */
public class MathDistanceUtilsTest {

    @Test
    public void testNearestNumOfmultiplesOfTen() {
        int step = 10;
        Integer res = IntensityNodeNumUtils.findNearestTimesNumByStep(11, step);
        Assert.assertTrue(res == 10);
        res = IntensityNodeNumUtils.findNearestTimesNumByStep(4, step);
        Assert.assertTrue(res == 0);
        res = IntensityNodeNumUtils.findNearestTimesNumByStep(5, step);
        Assert.assertTrue(res == 10);
        res = IntensityNodeNumUtils.findNearestTimesNumByStep(6, step);
        Assert.assertTrue(res == 10);
        res = IntensityNodeNumUtils.findNearestTimesNumByStep(19, step);
        Assert.assertTrue(res == 20);
        res = IntensityNodeNumUtils.findNearestTimesNumByStep(-1, step);
        Assert.assertTrue(res == 0);
        res = IntensityNodeNumUtils.findNearestTimesNumByStep(-19, step);
        Assert.assertTrue(res == -20);
        res = IntensityNodeNumUtils.findNearestTimesNumByStep(-7, step);
        Assert.assertTrue(res == -10);
    }
}