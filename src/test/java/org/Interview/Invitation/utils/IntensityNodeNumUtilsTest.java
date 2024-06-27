package org.Interview.Invitation.utils;


import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author yujie.fan
 * @description TODO
 * @date 2024-06-27
 */
public class IntensityNodeNumUtilsTest {

    @Test
    public void testFindNearestTimesNumByStep() {
        //step是偶数
        int step = 10;

        Integer res = IntensityNodeNumUtils.findNearestTimesNumByStep(-19, step);
        Assert.assertTrue(res == -20);

        res = IntensityNodeNumUtils.findNearestTimesNumByStep(-9, step);
        Assert.assertTrue(res == -10);

        res = IntensityNodeNumUtils.findNearestTimesNumByStep(-4, step);
        Assert.assertTrue(res == 0);

        res = IntensityNodeNumUtils.findNearestTimesNumByStep(-17, step);
        Assert.assertTrue(res == -20);

        res = IntensityNodeNumUtils.findNearestTimesNumByStep(-13, step);
        Assert.assertTrue(res == -10);

        res = IntensityNodeNumUtils.findNearestTimesNumByStep(11, step);
        Assert.assertTrue(res == 10);

        res = IntensityNodeNumUtils.findNearestTimesNumByStep(16, step);
        Assert.assertTrue(res == 20);

        res = IntensityNodeNumUtils.findNearestTimesNumByStep(10, step);
        Assert.assertTrue(res == 10);

        res = IntensityNodeNumUtils.findNearestTimesNumByStep(15, step);
        Assert.assertTrue(res == 20);


        //step是奇数
        step = 7;
        res = IntensityNodeNumUtils.findNearestTimesNumByStep(15, step);
        Assert.assertTrue(res == 14);

        res = IntensityNodeNumUtils.findNearestTimesNumByStep(8, step);
        Assert.assertTrue(res == 7);

        res = IntensityNodeNumUtils.findNearestTimesNumByStep(7, step);
        Assert.assertTrue(res == 7);

        res = IntensityNodeNumUtils.findNearestTimesNumByStep(1, step);
        Assert.assertTrue(res == 0);
        res = IntensityNodeNumUtils.findNearestTimesNumByStep(5, step);
        Assert.assertTrue(res == 7);

    }

    @Test
    public void testListAllTimesNumInRange() {
        int step = 10;
        List<Integer> result = IntensityNodeNumUtils.listAllTimesNumInRange(20, 40, step); //expected: [10]
        List<Integer> expected = Arrays.asList(20, 30);
        Assert.assertTrue(expected.containsAll(result));
        Assert.assertTrue(result.containsAll(expected));

        result = IntensityNodeNumUtils.listAllTimesNumInRange(10, 30, step); //expected: [10,20)
        expected = Arrays.asList(10, 20);
        Assert.assertTrue(expected.containsAll(result));
        Assert.assertTrue(result.containsAll(expected));


        result = IntensityNodeNumUtils.listAllTimesNumInRange(-30, -10, step); //expected: [10,20)
        expected = Arrays.asList(-30, -20);
        Assert.assertTrue(expected.containsAll(result));
        Assert.assertTrue(result.containsAll(expected));


        result = IntensityNodeNumUtils.listAllTimesNumInRange(-30, 20, step); //expected: [10,20)
        expected = Arrays.asList(-30, -20, -10, 0, 10);
        Assert.assertTrue(expected.containsAll(result));
        Assert.assertTrue(result.containsAll(expected));

    }
}